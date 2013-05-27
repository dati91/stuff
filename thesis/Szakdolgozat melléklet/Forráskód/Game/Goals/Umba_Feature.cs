using UnityEngine;
using System.Collections;
using Umba;

public class Umba_Feature {
	public static float Healt(Umba_bot bot){
		return (float)bot.Health / (float)bot.MaxHealth;
	}
	
	public static float IndividualSpellStrength(Umba_bot bot, Umba_Spell spell){
		return 1.0f - (spell.cdTime() / spell.getCooldown());
	}
	
	public static float TotalSpellStrength(Umba_bot bot){
		float ccd = 0, mcd = 0;
		foreach(var pair in bot.SpellSys.SpellMap){
			ccd+= pair.Value.cdTime();
			mcd+= pair.Value.getCooldown();
		}
		
		return 1 - (ccd/mcd);
	}
	
}
