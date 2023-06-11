package net.kalangos.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.kalangos.main.Game;
import net.kalangos.world.Camera;
import net.kalangos.world.World;

public class Enemy extends Entity {

	private double speed = 1;
	private int xMask = 8, yMask = 8, wMask = 10, hMask = 10;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		
		xMask = 8;
		yMask = 8;
		wMask = 5;
		hMask = 5;
		
		// if (Game.rand.nextInt(100) < 30) {
		if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
				&& !isColliding((int) (x + speed), this.getY())) {
			x += speed;
		} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
				&& !isColliding((int) (x - speed), this.getY())) {
			x -= speed;
		}

		if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
				&& !isColliding(this.getX(), (int) (y + speed))) {
			y += speed;
		} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
				&& !isColliding(this.getX(), (int) (y - speed))) {
			y -= speed;
		}
		// }

	}

	public boolean isColliding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext + xMask, yNext + yMask, wMask, hMask);

		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this) {
				continue;
			}
			Rectangle enemyTarget = new Rectangle(e.getX() + xMask, e.getY() + yMask, wMask, hMask);
			if (enemyCurrent.intersects(enemyTarget)) {
				return true;
			}
		}

		return false;
	}
	
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.BLUE);
		g.fillRect(this.getX() + xMask - Camera.x, this.getY() + yMask - Camera.y, wMask, hMask);
	}

}
