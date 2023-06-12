package net.kalangos.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.kalangos.main.Game;
import net.kalangos.world.Camera;

public class BulletShoot extends Entity{
	
	private int dx;
	private int dy;
	private double spd = 4;
	
	private int life = 30, curLife = 0;

	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		x += dx*spd;
		y += dy*spd;
		curLife++;
		if(curLife == life) {
			Game.shoot.add(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
		
	}
}
