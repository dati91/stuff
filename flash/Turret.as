package {
	
	import flash.display.*;
	import flash.events.*;
	import flash.utils.getTimer;
	
	public class Turret extends MovieClip{
		private var speed:Number;
		private var lastTime:int;
		private var lives:int;
		private var origx:Number;
		private var origy:Number;
		
		
		public function Turret(x,y:Number,speed:Number,lives:int){
			this.x = x;
			origx = x;
			this.y = y;
			origy = y;
			this.speed = speed;
			addEventListener(Event.ENTER_FRAME,moveTurret);
			lastTime = getTimer();
			this.lives = lives;
		}
		
		public function moveTurret(event:Event){
			var timePassed:int = getTimer()-lastTime;
			lastTime += timePassed;
			
			var newx = this.x;

			if (MovieClip(parent).leftArrow) {
			newx -= speed*timePassed/1000;
			}

			if (MovieClip(parent).rightArrow) {
			newx += speed*timePassed/1000;
			}

			if (newx < 40) newx = 40;
			if (newx > 760) newx = 760;

			this.x = newx;
		}
		
		public function deleteTurret(){
			parent.removeChild(this);
			removeEventListener(Event.ENTER_FRAME,moveTurret);
		}
		
		public function getLives():int{
			return lives;
		}
		
		public function incLives(){
			lives++;
		}
		
		public function decLives(){
			lives--;
		}
		
		public function turretHit(){
			decLives();
			this.x=origx;
			this.y=origy;
			MovieClip(parent).showPlayerLives();
		}
	}
}