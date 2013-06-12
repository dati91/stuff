package {
	import flash.display.*;
	import flash.events.*;
	import flash.utils.getTimer;
	import fl.transitions.Tween;
	import fl.transitions.easing.*;
	import fl.transitions.TweenEvent;
	
	public class Invader extends MovieClip {
		private var dx:Number; //speed and direction
		private var lastTime:int //animation time
		private var moveDir:Boolean;
		
		var explodeTween:Tween;
		private var moveStep:uint;
		
		private var invaderType:uint;
		
		public function Invader(speed:Number,x,y:Number,md:Boolean,type:uint){
			this.x = x;
			this.y = y;
			moveDir = md;
			dx = speed;
			if(moveDir){
				dx *= -1;
			}
			this.gotoAndStop(1);
			addEventListener(Event.ENTER_FRAME,moveUnit);
			lastTime = getTimer();
			invaderType = type;
			gotoAndStop(invaderType);
		}
	
		public function moveUnit(event:Event) {
			var timePassed:int = getTimer()-lastTime;
			lastTime += timePassed;
			
			this.x += dx*timePassed/1000;
			
			if(invaderType == 5 ){
				if((!moveDir && x > 800) || (moveDir && x < 0 )){
					deleteInvader();
				}
				return;
			}
			
			if(moveDir != MovieClip(parent).moveDir){
				dx *= -1;
				this.y += 40;
				moveDir = !moveDir;
			}
			
			if(y > 540){
				MovieClip(parent).endGame();
			}
		}
		
		public function invaderHit(){
			removeEventListener(Event.ENTER_FRAME,moveUnit);
			MovieClip(parent).removeInvader(this);
			gotoAndStop("explode");
			explodeTween = new Tween(this, "scaleX", Back.easeOut, 0, 1, 0.2, true);
			explodeTween = new Tween(this, "scaleY", Back.easeOut, 0, 1, 0.2, true);
			explodeTween.addEventListener(TweenEvent.MOTION_FINISH,explode);
			MovieClip(parent).playExplodeSound();
		}
		
		public function deleteInvader(){
			MovieClip(parent).removeInvader(this);
			parent.removeChild(this);
			removeEventListener(Event.ENTER_FRAME,moveUnit);
		}
		
		public function explode(event:TweenEvent){
			explodeTween.removeEventListener(TweenEvent.MOTION_FINISH,explode);
			explodeTween = null;
			parent.removeChild(this);
		}
		
		public function getInvaderType():uint{
			return this.invaderType;
		}
	}
}