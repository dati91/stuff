package {
	
	import flash.display.*;
	import flash.events.*;
	import flash.utils.getTimer;
	
	
	public class Bullet extends MovieClip {
		
		private var dy:Number; //vertical speed
		private var lastTime:int;
		
		public function Bullet(x,y:Number,speed:Number){
			this.x = x;
			this.y = y;
			this.dy = speed;
			lastTime = getTimer();
			addEventListener(Event.ENTER_FRAME,moveBullet);
			if(isEnemy()){
				gotoAndStop(2);
			}
		}
		      
		public function moveBullet(event:Event){
			var timePassed:int = getTimer() - lastTime;
			lastTime += timePassed;
			
			this.y += dy * timePassed/1000;
			
			if(this.y < 0){
				deleteBullet();
			}
		}
		
		public function deleteBullet(){
			MovieClip(parent).removeBullet(this);
			parent.removeChild(this);
			removeEventListener(Event.ENTER_FRAME,moveBullet);
		}
		
		public function isEnemy():Boolean{
			return this.dy > 0;
		}
		
	}	
	
	
}