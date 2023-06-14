package net.kalangos.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.kalangos.graficos.Spritesheet;
import net.kalangos.main.Game;
import net.kalangos.world.Camera;
import net.kalangos.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int right_dir = 0;
	public int left_dir = 1;
	public int dir = right_dir;
	public double speed = 1.4;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

	private BufferedImage playerDamage;

	private boolean hasGun = false;

	public int ammo = 50;

	public boolean isDamaged = false;
	private int damageFrames = 0;

	public boolean moved = false;
	public boolean shoot = false;
	public boolean mouseShoot = false;

	public double life = 100, maxLife = 100;
	public int mouseX, mouseY;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);

		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
		}

		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
	}

	public void tick() {
		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}

		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			y += speed;
		} else {
			// Estamos colidindo
			if (Game.rand.nextInt(100) < 10) {
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamaged = true;
				if (Game.player.life <= 0) {
					// Game Over
					// System.exit(1);
				}
				// System.out.println("Vida.: " + Game.player.life);
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

		this.checkCollisionLifePack();
		this.checkCollisionAmmo();
		this.checkCollisionGun();

		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 3) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		if (shoot) {
			shoot = false;
			if (hasGun && ammo > 0) {
				ammo--;
				// criar bala e atirar
				// System.out.println("atirando");
				int dx = 0;
				int px = 0;
				int py = 6;
				if (dir == right_dir) {
					px = 18;
					dx = 1;
				} else {
					px = -8;
					dx = -1;
				}

				BulletShoot shoots = new BulletShoot(this.getX(), this.getY() + py, 3, 3, null, dx, 0);
				Game.shoot.add(shoots);
			}
		}
		if (mouseShoot) {
			mouseShoot = false;
			if (hasGun && ammo > 0) {
				ammo--;
				//Criar balas e atirar com o mouse
				
				int px = 0;
				int py = 8;
				double angle = 0;
				if (dir == right_dir) {
					px = 18;
					angle = Math.atan2(mouseY -(this.getY() + py - Camera.y), mouseX - (this.getX() + px  - Camera.x)); 
				} else {
					px = -8;
					angle = Math.atan2(mouseY -(this.getY() + py - Camera.y), mouseX - (this.getX() + px  - Camera.x)); 
				}
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
			
				BulletShoot shoots = new BulletShoot(this.getX(), this.getY() + py, 3, 3, null, dx, 0);
				Game.shoot.add(shoots);
			}
		}

		if (life <= 0) {
			//game over
			life = 0;
			Game.gameState = "GAME_OVER";
		}

		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}

	public void checkCollisionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Bullets) {
				if (Entity.isColliding(this, atual)) {
					ammo += 10;
					// System.out.println("Munição atual: " + ammo);
					Game.entities.remove(atual);
				}
			}
		}
	}

	public void checkCollisionGun() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Weapon) {
				if (Entity.isColliding(this, atual)) {
					hasGun = true;
					ammo += 10;
					System.out.println("Pegou arma");
					// System.out.println("Munição atual: " + ammo);
					Game.entities.remove(atual);
				}
			}
		}
	}

	public void checkCollisionLifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof LifePack) {
				if (Entity.isColliding(this, atual)) {
					life += 100;
					if (life >= 100) {
						life = 100;
					}
					Game.entities.remove(atual);
				}
			}
		}
	}

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (hasGun) {
					// desenhar arma para direita.
					g.drawImage(Entity.GUN_RIGHT, this.getX() + 10 - Camera.x, this.getY() - Camera.y, null);
				}
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (hasGun) {
					// desenhar arma para esquerda.
					g.drawImage(Entity.GUN_LEFT, this.getX() - 10 - Camera.x, this.getY() - Camera.y, null);
				}
			}
		} else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
