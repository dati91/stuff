//DUASABT.SZE DUSNOKI ATTILA
package{
	import flash.display.*;
	import flash.events.*;
	import flash.utils.Timer;
	import flash.utils.getTimer;
	import flash.text.*;
	import flash.media.Sound;
	import flash.media.SoundChannel;
	import flash.net.SharedObject;
	
	public class SpaceInvaders extends MovieClip {
		
		private var invaders:Array;
		private var pbullets:Array;
		private var ibullets:Array;
		private var walls:Array;
		private var turret:Turret;
		
		public var leftArrow:Boolean;
		public var rightArrow:Boolean;
		public var moveDir:Boolean;
		
		private var gameScore:int;
		private var gameScoreField:TextField;
		
		private var gameStartTime:uint;
		private var gameTime:uint;
		private var gameTimeField:TextField;
		
		private var playerLivesField:TextField;
		
		private var textFormat:TextFormat;
		
		private var nextUFO:Timer;
		private var nextShoot:Timer;
		private var powerUpSpeed:Timer;
		private var powerUpBullet:Timer;
		
		private var bulletSpeed:int;
		private var bulletNum:int;
		
		private var round:uint;
		
		var explodeSound:ExplodeSound;
		var shootSound:ShootSound;
		var menuMusic:MenuMusic;
		var musicChannel:SoundChannel;
		var isMusicPlaying:Boolean = false;
		
		var highScoreData:SharedObject = SharedObject.getLocal("highscore");
		private var highScoreField:TextField;
		
		public function SpaceInvaders(){
			if (highScoreData.size == 0)
			{
				// Default highscore
				highScoreData.data.highscore = [];
				highScoreData.flush();
			}
			textFormat = new TextFormat("Arial", 24, 0xFFFFFF, true);
			explodeSound = new ExplodeSound();
			shootSound = new ShootSound();
			menuMusic = new MenuMusic();
		}
		
		public function startGame(){
			leftArrow = false;
			rightArrow = false;
			moveDir = false;
			
			gameScore = 0;
			gameStartTime = getTimer();
			gameTime = 0;
			round = 1;
			bulletSpeed = -300;
			bulletNum = 1;

			initGameScore();
			
			initGameTime();
			
			initTurret();

			initPlayerLives();
			
			ibullets = new Array();
			pbullets = new Array();
			
			invaders = new Array();
			
			initInvaders();
			
			walls = new Array();
			
			initWalls();
			
			setNextUFO();
			setNextShoot();
			
			stage.addEventListener(KeyboardEvent.KEY_DOWN,keyDownFunction);
			stage.addEventListener(KeyboardEvent.KEY_UP,keyUpFunction);
			addEventListener(Event.ENTER_FRAME,checkForHits);
			addEventListener(Event.ENTER_FRAME,checkForMoveDir);
			addEventListener(Event.ENTER_FRAME,showTime);
		}
		
		public function keyDownFunction(event:KeyboardEvent) {
			if (event.keyCode == 37) {
				leftArrow = true;
			} else if (event.keyCode == 39) {
				rightArrow = true;
			} else if (event.keyCode == 32) {
				fireBullet();
			}
		}
		
		public function keyUpFunction(event:KeyboardEvent) {
			if (event.keyCode == 37) {
				leftArrow = false;
			} else if (event.keyCode == 39) {
				rightArrow = false;
			}
		}
		
		public function fireBullet(){
			if(pbullets.length == 0){
				var pos:int = -1*((bulletNum-1)*2);
				for(var i:int=0;i<bulletNum;i++){
					var bullet:Bullet = new Bullet(turret.x + i*4 + pos,turret.y,bulletSpeed);
					addChild(bullet);
					pbullets.push(bullet);
				}
				playShootSound();
			}
		}
		
		public function removeBullet(bullet:Bullet){
			if(bullet.isEnemy()){
				for(var ib in ibullets){
					if(ibullets[ib] == bullet){
						ibullets.splice(ib,1);
						break;
					}
				}
			}else{
				for(var pb in pbullets){
					if(pbullets[pb] == bullet){
						pbullets.splice(pb,1);
						break;
					}
				}
			}
		}
		
		public function removeInvader(invader:Invader){
			for(var i in invaders){
				if(invaders[i] == invader){
					invaders.splice(i,1);
					break;
				}
			}
		}
		
		public function removeWall(wall:Wall){
			for(var w in walls){
				if(walls[w] == wall){
					walls.splice(w,1);
					break;
				}
			}
		}
		
		public function checkForHits(event:Event){
			var deleted:Boolean = false;
			for(var pb:int=pbullets.length-1;pb>=0;pb--){
				deleted = false;
				for (var i:int=invaders.length-1;i>=0;i--) {
					if (pbullets[pb].hitTestObject(invaders[i])) {
						incGameScore(invaders[i].getInvaderType());
						invaders[i].invaderHit();
						pbullets[pb].deleteBullet();
						showGameScore();
						deleted = true;
						break;
					}
				}
				if(deleted){
					break;
				}
				for (var w:int=walls.length-1;w>=0;w--) {
					if (pbullets[pb].hitTestObject(walls[w])) {
						walls[w].wallHit();
						pbullets[pb].deleteBullet();
						break;
					}
				}
			}
			
			for(var ib:int=ibullets.length-1;ib>=0;ib--){
				deleted = false;
				if(ibullets[ib].hitTestObject(turret)){
					ibullets[ib].deleteBullet();
					turret.turretHit();
					playExplodeSound();
					deleted = true;
				}
				if(deleted){
					break;
				}
				for (var wi:int=walls.length-1;wi>=0;wi--) {
					if (ibullets[ib].hitTestObject(walls[wi])) {
						walls[wi].wallHit();
						ibullets[ib].deleteBullet();
						break;
					}
				}
			}
			
			if(invaders.length == 0){
				turret.incLives();
				round++;
				showPlayerLives();
				initInvaders();
			}else if(turret.getLives() < 0){
				endGame();
			}
		}
		
		public function checkForMoveDir(event:Event){
			for (var i:int=invaders.length-1;i>=0;i--) {
				if (invaders[i].getInvaderType() < 5 && ((moveDir && invaders[i].x < 40) || (!moveDir && invaders[i].x > 760))) {
					moveDir =!moveDir;
					break;
				}
			}
		}
		
		public function endGame(){
			//ha eppen fut valamilyen funkcio es torlunk, nullra fog helyenkent hivatkozni
			stage.removeEventListener(KeyboardEvent.KEY_DOWN, keyDownFunction);
			stage.removeEventListener(KeyboardEvent.KEY_UP, keyUpFunction);
			removeEventListener(Event.ENTER_FRAME, checkForHits);
			removeEventListener(Event.ENTER_FRAME, checkForMoveDir);
			removeEventListener(Event.ENTER_FRAME, showTime);
			
			nextUFO.stop();
			nextUFO = null;
			nextShoot.stop();
			nextShoot = null;
			
			for(var i:int=invaders.length-1;i>=0;i--){
				invaders[i].deleteInvader();
			}
			invaders = null;
			
			for(var ib:int=ibullets.length-1;ib>=0;ib--){
				ibullets[ib].deleteBullet();
			}
			ibullets = null;
			
			for(var pb:int=pbullets.length-1;pb>=0;pb--){
				pbullets[pb].deleteBullet();
			}
			pbullets = null;
			
			for(var w:int=walls.length-1;w>=0;w--){
				walls[w].deleteWall();
			}
			walls = null;
			
			turret.deleteTurret();
			turret = null;
			
			showGameScore();
			gameScoreField.x = 400 - gameScoreField.textWidth/2;
			gameScoreField.y = 200 - gameScoreField.textHeight/2;
			gameTimeField.x = 400 - gameScoreField.textWidth/2;
			gameTimeField.y = 250 - gameScoreField.textHeight/2;
			
			removeChild(playerLivesField);
			playerLivesField = null;
			
			gotoAndStop("gameover");
		}
		
		public function initInvaders(){
			for(var i:int=0;i<10;i++){
				for(var j:int=0;j<4;j++){
					var invader:Invader = new Invader(20+round*10,40*i+40,40*j+40,moveDir,4-j);
					addChild(invader);
					invaders.push(invader);
				}
			}
		}
		
		public function initTurret(){
			turret = new Turret(400,550,250,3);
			addChild(turret);
		}
		
		public function initWalls(){
			for(var i:int=0;i<4;i++){
				buildBase(100+i*175,450);
			}
		}
		
		public function buildBase(x,y:Number){
			var offset:Array = new Array(2,1,0,0,0,1,2);
			for(var i:int=0;i<7;i++){
				for(var j:int=0;j<3;j++){
					var wall:Wall = new Wall(x+i*12,y+j*12+offset[i]*12);
					addChild(wall);
					walls.push(wall);
				}
			}
		}
		
		public function showGameScore(){
			gameScoreField.text = "Score: "+String(gameScore);
		}
		
		public function showPlayerLives(){
			playerLivesField.text = "Lives: "+String(turret.getLives());
		}
		
		public function initGameScore(){
			if(gameScoreField == null){
				gameScoreField = new TextField();
			}
			gameScoreField.selectable = false;
			gameScoreField.defaultTextFormat = textFormat;
			gameScoreField.x = 10;
			gameScoreField.y = 10;
			gameScoreField.autoSize = TextFieldAutoSize.LEFT
			addChild(gameScoreField);
			showGameScore();
			
		}
		
		public function initGameTime(){
			gameTimeField = new TextField();
			gameTimeField.selectable = false;
			gameTimeField.defaultTextFormat = textFormat;
			gameTimeField.x = 650;
			gameTimeField.y = 10;
			gameTimeField.autoSize = TextFieldAutoSize.LEFT

			addChild(gameTimeField);
		}
		
		public function initHighScore(){
			highScoreField = new TextField();
			highScoreField.selectable = false;
			highScoreField.defaultTextFormat = textFormat;
			highScoreField.x = 300;
			highScoreField.y = 400;
			highScoreField.autoSize = TextFieldAutoSize.LEFT

			addChild(highScoreField);
		}
		
		public function initPlayerLives(){
			playerLivesField = new TextField();
			playerLivesField.selectable = false;
			playerLivesField.defaultTextFormat = textFormat;
			playerLivesField.x = 500;
			playerLivesField.y = 10;
			playerLivesField.autoSize = TextFieldAutoSize.LEFT

			addChild(playerLivesField);
			showPlayerLives();
		}
		
		public function showTime(event:Event) {
			gameTime = getTimer()-gameStartTime;
			gameTimeField.text = "Time: "+clockTime(gameTime);
		}
		
		public function clockTime(ms:int):String {
			var seconds:int = Math.floor(ms/1000);
			var minutes:int = Math.floor(seconds/60);
			seconds -= minutes*60;
			var timeString:String = minutes+":"+String(seconds+100).substr(1,2);
			return timeString;
		}
		
		public function incGameScore(point:uint){
			switch(point){
				case 1: gameScore+= 10;
						break;
				case 2: gameScore+= 20;
						break;
				case 3: gameScore+= 25;
						break;
				case 4: gameScore+= 30;
						break;
				case 5: gameScore+= 150;
						Math.random() > 0.5?setPowerUpBullet():setPowerUpSpeed();
						break;
				default: break;
			}
		}
		
		public function setNextUFO(){
			nextUFO = new Timer(10000+Math.random()*20000,1);
			nextUFO.addEventListener(TimerEvent.TIMER_COMPLETE,newUFO);
			nextUFO.start();			
		}
		
		public function newUFO(event:TimerEvent){
			var side:Boolean = Math.random() > .5;
			var ufo:Invader = new Invader(80,side?800:0,20,side,5);
			addChild(ufo);
			invaders.push(ufo);
			
			setNextUFO();
		}
		
		public function setNextShoot(){
			nextShoot = new Timer(1000+Math.random()*10000,1);
			nextShoot.addEventListener(TimerEvent.TIMER_COMPLETE,invaderShoot);
			nextShoot.start();			
		}
		
		public function invaderShoot(event:TimerEvent){
			var shooter:int = Math.floor( Math.random()* invaders.length);
			var shoot:Bullet = new Bullet(invaders[shooter].x,invaders[shooter].y,150);
			addChild(shoot);
			ibullets.push(shoot);
			playShootSound();
			setNextShoot();
		}
		
		public function setPowerUpSpeed(){
			powerUpSpeed = new Timer(15000,1);
			powerUpSpeed.addEventListener(TimerEvent.TIMER_COMPLETE,powerUpSpeedOff);
			powerUpSpeed.start();
			bulletSpeed -= 100;
		}
		
		public function powerUpSpeedOff(event:TimerEvent){
			bulletSpeed += 100;
		}
		
		public function setPowerUpBullet(){
			powerUpBullet = new Timer(10000,1);
			powerUpBullet.addEventListener(TimerEvent.TIMER_COMPLETE,powerUpBulletOff);
			powerUpBullet.start();
			bulletNum+=2;
		}
		
		public function powerUpBulletOff(event:TimerEvent){
			bulletNum-=2;
		}
		
		public function clearData(){
			removeChild(gameScoreField);
			gameScoreField = null;
			removeChild(gameTimeField);
			gameTimeField = null;
		}
		
		public function playShootSound() {
			shootSound.play();
		}
		
		public function playExplodeSound() {
			explodeSound.play();
		}
		
		public function playMenuMusic() {
			if(isMusicPlaying){
				return;
			}
			musicChannel = menuMusic.play();
			musicChannel.addEventListener(Event.SOUND_COMPLETE, onMusicComplete);
			isMusicPlaying = true;
		}
		
		function onMusicComplete(event:Event):void
		{
			musicChannel.removeEventListener(Event.SOUND_COMPLETE, onMusicComplete);
			playMenuMusic();
		}
		
		public function stopMenuMusic() {
			musicChannel.removeEventListener(Event.SOUND_COMPLETE, onMusicComplete);
			musicChannel.stop();
			isMusicPlaying = false;
		}
		
		public function submitScore(n:String){
			highScoreData.data.highscore.push({user:n, score:gameScore});
			if(highScoreData.data.highscore.length > 10){
				highScoreData.data.highscore.sortOn("score", Array.NUMERIC | Array.DESCENDING);
				highScoreData.data.highscore.pop();
			}
			highScoreData.flush();
		}
		
		public function showHighScore(){
			if(highScoreData.size == 0){
				return;
			}else if(highScoreField == null){
				initHighScore();
			}
			highScoreData.data.highscore.sortOn("score", Array.NUMERIC | Array.DESCENDING);
			var i:int = 0;
			var hs:String = "";
			for (i = 0; i < highScoreData.data.highscore.length; i++){
				hs+=highScoreData.data.highscore[i].user+"\t"+highScoreData.data.highscore[i].score;
			}
			highScoreField.text = hs;
			highScoreField.x = 400 - highScoreField.textWidth/2;
			highScoreField.y = 280 - highScoreField.textHeight/2;
		}
		
		public function removeHighScore(){
			removeChild(highScoreField);
			highScoreField = null;
		}
			
	}	
}