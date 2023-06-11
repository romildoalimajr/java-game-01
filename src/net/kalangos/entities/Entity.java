package net.kalangos.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.kalangos.main.Game;
import net.kalangos.world.Camera;

public class Entity { 
	
	public static BufferedImage LIFE_PACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
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
	
	public void render(Graphics g) { 
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
	
}
