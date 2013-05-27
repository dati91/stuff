using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using Umba;

public class Umba_SensoryMemory{
	
	public Dictionary<Umba_bot,MemoryRecord> MemoryMap;
	public Dictionary<Umba_Projectile,MemoryRecord> MemorySpellMap;
	
	Umba_bot owner;
	
	float MemorySpan;

	public Umba_SensoryMemory(Umba_bot owner, float MemorySpan){
		MemoryMap = new Dictionary<Umba_bot, MemoryRecord>();
		MemorySpellMap = new Dictionary<Umba_Projectile, MemoryRecord>();
		this.owner = owner;
		this.MemorySpan = MemorySpan;
	}
	
	public void MakeNewRecordIfNotAlreadyPresent(Umba_bot bot){
		if(!MemoryMap.ContainsKey(bot)){
			MemoryMap.Add(bot, new MemoryRecord() );
		}
	}
	
	public void MakeNewRecordIfNotAlreadyPresent(Umba_Projectile spell){
		if(!MemorySpellMap.ContainsKey(spell)){
			MemorySpellMap.Add(spell, new MemoryRecord() );
		}
	}
	
	public void RemoveBotFromMemory(Umba_bot bot){
		if(MemoryMap.ContainsKey(bot)){
			MemoryMap.Remove(bot);
		}
	}
	
	public void RemoveSpellFromMemory(Umba_Projectile spell){
		if(MemorySpellMap.ContainsKey(spell)){
			if(owner.TargetSys.SpellThreat.Equals(spell))
				owner.TargetSys.SpellThreat = null;
			MemorySpellMap.Remove(spell);
		}
	}
	
	public void UpdateWithSoundSource(Umba_bot NoiseMaker){
		if(!(NoiseMaker.Equals(owner))){
			MakeNewRecordIfNotAlreadyPresent(NoiseMaker);
			
			MemoryRecord info = MemoryMap[NoiseMaker];
			
			if(!owner.Game.isPathObstructed(owner.Position2D,NoiseMaker.Position2D)){
				info.Shootable = true;
				info.LastSensedPosition = NoiseMaker.Position2D;
			}else{
				info.Shootable = false;
			}
			info.TimeLastSensed = Time.time;
			info.WithinRange = owner.Game.inViewRange(owner.Position2D,NoiseMaker.Position2D,owner.ViewRange);
			
			MemoryMap[NoiseMaker] = info;
		}
	}
	
	public void UpdateVision(){
		List<Umba_bot> bots = owner.Game.Bots;
		foreach(Umba_bot bot in bots){
			if(!(bot.Equals(owner))){
				MakeNewRecordIfNotAlreadyPresent(bot);
				MemoryRecord info = MemoryMap[bot];
				
					info.Shootable = !owner.Game.isPathObstructed(owner.Position2D,bot.Position2D);
					if(owner.Game.inViewRange(owner.Position2D,bot.Position2D,owner.ViewRange)){
						info.TimeLastSensed = Time.time;
						info.LastSensedPosition = bot.Position2D;
						info.TimeLastVisible = Time.time;
						
						
						if(info.WithinRange == false){
							info.TimeBecameVisible = info.TimeLastSensed;
							info.WithinRange = true;
						}
							
					}else{
						info.WithinRange = false;
					}
				MemoryMap[bot] = info;
			}
		}
		
		List<Umba_Projectile> spells = owner.Game.Spells;
		foreach(Umba_Projectile spell in spells){
			if(!spell.Shooter.Equals(owner)){
				MakeNewRecordIfNotAlreadyPresent(spell);
				MemoryRecord info = MemorySpellMap[spell];
				
				info.Shootable = spell.type_of_spell == Type.type_fireball;
				
				if(owner.Game.inViewRange(owner.Position2D,spell.Position2D,owner.ViewRange)){
					info.TimeLastSensed = Time.time;
					info.LastSensedPosition = spell.Position2D;
					info.TimeLastVisible = Time.time;
					if(info.WithinRange == false){
						info.TimeBecameVisible = info.TimeLastSensed;
						info.WithinRange = true;
					}
					
				}else{
					info.WithinRange = false;
				}
				
				MemorySpellMap[spell] = info;
			}
		}
		
	}
	
	public bool isOpponentShootable(Umba_bot Opponent){
		if(MemoryMap.ContainsKey(Opponent)){
			return MemoryMap[Opponent].Shootable;
		}
		return false;
	}
	
	public bool isOpponentWithinRange(Umba_bot Opponent){
		if(MemoryMap.ContainsKey(Opponent)){
			return MemoryMap[Opponent].WithinRange;
		}
		return false;
	}
	
	public Vector2 GetLastRecordedPositionOfOpponent(Umba_bot Opponent){
		if(MemoryMap.ContainsKey(Opponent)){
			return MemoryMap[Opponent].LastSensedPosition;
		}
		Debug.LogError("<Umba_SensoryMemory :: GetLastRecordedPositionOfOpponnet> attempting to get unrecorded data");
		return new Vector2();
	}
	
	public float GetTimeOpponnetHasBeenVisible(Umba_bot Opponent){
		if(MemoryMap.ContainsKey(Opponent) && MemoryMap[Opponent].WithinRange){
			return Time.time - MemoryMap[Opponent].TimeBecameVisible;
		}
		return 0;
	}
	
	public float GetTimeSinceLastSensed(Umba_bot Opponent){
		if(MemoryMap.ContainsKey(Opponent) && MemoryMap[Opponent].WithinRange){
			return Time.time - MemoryMap[Opponent].TimeLastSensed;
		}
		return 0;
	}
	
	public float GetTimeOpponnetHasBeenOutOfView(Umba_bot Opponent){
		if(MemoryMap.ContainsKey(Opponent)){
			return Time.time - MemoryMap[Opponent].TimeLastVisible;
		}
		return float.MaxValue;
	}
	
	public List<Umba_bot> GetListOfRecentlySensedOpponents(){
		List<Umba_bot> Opponents = new List<Umba_bot>();
		float curTime = Time.time;
		foreach(KeyValuePair<Umba_bot,MemoryRecord> curMem in MemoryMap){
			if((curTime - curMem.Value.TimeLastSensed) <= MemorySpan){
				Opponents.Add(curMem.Key);
			}
		}
		return Opponents;
	}
	
	public List<Umba_Projectile> GetListOfSensedSpells(){
		List<Umba_Projectile> Spells = new List<Umba_Projectile>();
		foreach(KeyValuePair<Umba_Projectile,MemoryRecord> curMem in MemorySpellMap){
				Spells.Add(curMem.Key);
		}
		return Spells;
	}
}
