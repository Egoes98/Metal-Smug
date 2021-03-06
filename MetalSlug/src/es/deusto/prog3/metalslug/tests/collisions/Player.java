package es.deusto.prog3.metalslug.tests.collisions;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Character {
	
	private Input input;
	private HashMap<String,HashMap<String, Animation>> animations = new HashMap<String,HashMap<String, Animation>>();;
	private HashMap<String,Animation> animation;

	private boolean move = false;
	private boolean firstRun = true;
	private String isFacing;
	
	private boolean movingLeft = false;
	private boolean shoot = false;
	private boolean canShoot = false;
	private boolean hasShot = false;
	
	public Player() {
		super(200, 200, 93, 114, 300);
		input = new Input(720);
		addAnimation("StandbyHead", AnimationImages.eriStandbyHead, 400);
		addAnimation("StandbyFoot", AnimationImages.eriStandbyFoot, 300);
		addAnimation("RunHead", AnimationImages.eriRunHead, 100);
		addAnimation("RunFoot1", AnimationImages.eriRunFoot1, 100);
		addAnimation("RunFoot2", AnimationImages.eriRunFoot2, 90);
		addAnimation("Shoot", AnimationImages.eriShoot, 50);
		addAnimation("JumpHead1", AnimationImages.eriJumpHead1, 270);
		addAnimation("JumpFoot1", AnimationImages.eriJumpFoot1, 270);
		addAnimation("JumpHead2", AnimationImages.eriJumpHead2, 270);
		addAnimation("JumpFoot2", AnimationImages .eriJumpFoot2, 270);
		isFacing = "RIGHT";
	}

	private void addAnimation(String name, Image[] images, int duration) {
		animation = new HashMap<String, Animation>();
		
		
		animations.put(name, animation);
		animation.put("RIGHT", new Animation(images, duration));
		
		Animation LeftAnimation = new Animation();
		for(Image i : images) {
			LeftAnimation.addFrame(i.getFlippedCopy(true, false), duration);
		}
		animation.put("LEFT", LeftAnimation);
		
		
	}

	protected void jump() {
		// TODO Auto-generated method stub
		System.out.println(hasjumped);
		if(!hasjumped) {
			vy = -800;
			setY(getY() - 2);
		}
		hasjumped = true;
	}

	private static final long serialVersionUID = 1L;
	
	public void update(int delta) {
		if(input.isKeyDown(Input.KEY_LEFT)) {
			moveX(delta, true);
			movingLeft = true;
			move = true;
			
			isFacing = "LEFT";
		}else if(input.isKeyDown(Input.KEY_RIGHT)) {
			moveX(delta, false);
			movingLeft = false;
			
			move = true;
			isFacing = "RIGHT";
		}else {
			move = false;
			firstRun = true;
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			shoot  = true;
		}else {
			shoot = false;
		}
		
		moveY(delta);
		detectPlatformCollisions();
		if(animations.get("Shoot").get(isFacing).getFrame() == 1) {
			if(!hasShot) {
				canShoot = true;
				hasShot = true;
			}
		} else {
			canShoot = false;
			hasShot = false;
		}
	}
	
	public boolean getMovingLeft() {
		return movingLeft;
	}

	public void drawCabeza() {
		
		if(move && shoot && isFacing == "LEFT") {
			animations.get("Shoot").get(isFacing).draw(x-72,y);
		}else if(move && shoot) {
			animations.get("Shoot").get(isFacing).draw(x+3,y);
		}else if(move && hasjumped && isFacing == "LEFT"){
			animations.get("JumpHead2").get(isFacing).draw(x+18,y-12);
		}else if(move && hasjumped && isFacing == "RIGHT"){
			animations.get("JumpHead2").get(isFacing).draw(x-10,y-12);
		}else if(move) {
			animations.get("RunHead").get(isFacing).draw(x,y);
		}else if(!move && shoot && isFacing == "LEFT"){
			animations.get("Shoot").get(isFacing).draw(x-72,y);
		}else if(!move && shoot){
			animations.get("Shoot").get(isFacing).draw(x+3,y);
		}else if (!move && hasjumped) {
			animations.get("JumpHead1").get(isFacing).draw(x,y);
		}else if (!move) {
			animations.get("StandbyHead").get(isFacing).draw(x,y);
		}
		
	}

	public void drawPiernas() {
		if(move && firstRun) {
			animations.get("RunFoot1").get(isFacing).draw(x,y);
			firstRun = false;
		}else if(move && !firstRun){
			animations.get("RunFoot2").get(isFacing).draw(x,y);
		}else if(!move && hasjumped) {
			animations.get("JumpFoot1").get(isFacing).draw(x,y);
		}else if(!move) {
			animations.get("StandbyFoot").get(isFacing).draw(x,y);
		}
	}
	
	public boolean isCanShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}
	
	

}
