package es.deusto.prog3.metalslug.game.entities;

import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import es.deusto.prog3.metalslug.game.data.AnimationImages;

public class Enemy extends Character {

	private Player player;
	private boolean shooting;
	private boolean movingLeft;
	private int timeCounter;
	private float minMovementX, maxMovementX;
	
	private HashMap<String,HashMap<Boolean, Animation>> animations = new HashMap<String,HashMap<Boolean, Animation>>();
	private HashMap<Boolean, Animation> animation;
	
	private ArrayList<Bullet> bullets;
	private boolean moving;

	public Enemy(float x, float y, float minX, float maxX) {
		super(x, y, 78, 111, 100, null);
		this.minMovementX = minX;
		this.maxMovementX = maxX;
		moving = true;
		addAnimation("Correr", AnimationImages.soldierWalk, 100);
		addAnimation("Disparar", AnimationImages.soldierShoot, 270);
		animations.get("Disparar").get(movingLeft).setLooping(false);
	}
	/**
	 * Añade una animación al mapa
	 * @param name
	 * @param images
	 * @param duration
	 */
	private void addAnimation(String name, Image[] images, int duration) {
		animation = new HashMap<Boolean, Animation>();
		
		
		animations.put(name, animation);
		animation.put(true, new Animation(images, duration));
		
		Animation LeftAnimation = new Animation();
		for(Image i : images) {
			LeftAnimation.addFrame(i.getFlippedCopy(true, false), duration);
		}
		animation.put(false, LeftAnimation);
	
	}
	/**
	 * Dispara una bala
	 */
	private void shoot() {
		bullets.add(new Bullet(this.getCenterX(), this.getCenterY(), player.getCenterX(), player.getCenterY(), 0.5f));
	}
	/**
	 * Calcula la distancia del punto a al punto b
	 * @param a {x,y}
	 * @param b {x,y}
	 * @return Distancia
	 */
	private float distanceTo(float[] a, float[] b) {
		// TODO Auto-generated method stub
		return (float) Math.hypot(a[0] - b[0], a[1] - b[1]);
	}
	/**
	 * Actualiza la posición, detectaa colisiones y dispara.
	 * @param delta
	 */
	public void update(int delta) {
		timeCounter += delta;
		if(!shooting) {
			moveX(delta, movingLeft);
		}else {
			moving = false;
		}

		moveY(delta);
		detectPlatformCollisions();
		if (distanceTo(this.getCenter(), player.getCenter()) < 500 && !player.isDead()) {
			moving = false;
			if(timeCounter > 1000) {
				shoot();
				shooting = true;
				timeCounter = 0;
			}
		} else {
			moving = true;
			shooting = false;
		}
		if(getX() < minMovementX && movingLeft || getX() > maxMovementX && !movingLeft) {
			movingLeft = !movingLeft;
		}

	}
	/**
	 * Dibuja el sprite en pantalla
	 */
	public void draw() {
		
		if(moving) {
			animations.get("Correr").get(movingLeft).draw(x,y);
		}else if(!moving) {
			if(movingLeft) {
				animations.get("Disparar").get(movingLeft).draw(x,y);
			}else {
				animations.get("Disparar").get(!movingLeft).draw(x,y);
			}
	
		}

	}
	
	public void stop() {
		animations.get("Correr").get(movingLeft).stop();
	}
	
	public void start() {
		animations.get("Correr").get(movingLeft).start();
	}
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public float getMinMovementX() {
		return minMovementX;
	}

	public float getMaxMovementX() {
		return maxMovementX;
	}
	
	

}
