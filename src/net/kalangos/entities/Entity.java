package net.kalangos.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.kalangos.main.Game;
import net.kalangos.world.Camera;

public class Entity { 
	
	public static BufferedImage LIFE_PACK_EN = Game.spritesheet.getSprite(16*6, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(32, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(96, 32, 16, 16);
 	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 0, 16, 16);
 	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(128+16, 0, 16, 16);

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	private int xMask, yMask, wMask, hMask;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.xMask = 0;
		this.yMask = 0;
		this.wMask = width;
		this.hMask = height;
	}
	
	public void setMask(int xMask, int yMask, int wMask, int hMask) {
		this.xMask = xMask;
		this.yMask = yMask;
		this.wMask = wMask;
		this.hMask = hMask;
	}

	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void tick() {
		
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.xMask, e1.getY() + e1.yMask, e1.wMask, e1.hMask);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.xMask, e1.getY() + e2.yMask, e2.wMask, e2.hMask);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) { 
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		g.setColor(Color.RED);
		g.fillRect(this.getX() + xMask - Camera.x, this.getY() + yMask - Camera.y, wMask, hMask);
	}
	
	
}
