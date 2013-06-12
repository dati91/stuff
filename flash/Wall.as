package {
	
	import flash.display.*;
	import flash.events.*;
	import flash.utils.getTimer;
	
	public class Wall extends MovieClip{
		
		private var damage:uint
		public function Wall(x,y:Number){
			this.x = x;
			this.y = y;
			damage = 1;
			gotoAndStop(damage);
		}
		
		public function wallHit(){
			damage++;
			if(damage == 6){
				deleteWall();
			}else{
				gotoAndStop(damage);
			}
		}
		
		public function deleteWall(){
			MovieClip(parent).removeWall(this);
			parent.removeChild(this);
		}
		
	}
}