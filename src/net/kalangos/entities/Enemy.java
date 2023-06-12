package net.kalangos.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.kalangos.main.Game;
import net.kalangos.world.Camera;
import net.kalangos.world.World;

public class Enemy extends Entity {

	private boolean moved = true;
	private double speed = 0.4;
	private int xMask = 8, yMask = 8, wMask = 10, hMask = 10;
	private BufferedImage[] sprites;

	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

	private int life = 10;
	
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(16 * 6, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(16 * 7, 16, 16, 16);
		sprites[2] = Game.spritesheet.getSprite(16 * 8, 16, 16, 16);
		sprites[3] = Game.spritesheet.getSprite(16 * 9, 16, 16, 16);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		/*
		 * xMask = 8; yMask = 8; wMask = 5; hMask = 5;
		 */
		// if (Game.rand.nextInt(100) < 30) {
		if (this.isCollidingWithPlayer() == false) {
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
		} else {
			if (Game.rand.nextInt(100) < 10) {
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			}
		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		
		collidingBullet();
		
		if(life <= 0) {
			destroySelf();
			return;
		}
		
		if(isDamaged) {
			this.damageCurrent++;
			if(this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;
			}
			
		}
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	public void collidingBullet() {
		for(int i = 0; i < Game.shoot.size(); i++) {
			Entity e = Game.shoot.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColliding(this, e)) {
					isDamaged = true;
					life--;
					Game.shoot.remove(i);
					return;
				}
			}
		}
	}

	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + xMask, this.getY() + yMask, wMask, hMask);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);

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
		if(!isDamaged) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else {
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		/*
		 * g.setColor(Color.BLUE); g.fillRect(this.getX() + xMask - Camera.x,
		 * this.getY() + yMask - Camera.y, wMask, hMask);
		 */
	}

}
