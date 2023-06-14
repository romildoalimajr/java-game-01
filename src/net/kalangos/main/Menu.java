package net.kalangos.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public String[] options = {"Novo Jogo","Carregar Jogo", "Sair"};
	
	public int currentOption =0;
	public int maxOption = options.length - 1;
	
	public boolean up, down;
	

	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption < maxOption) {
				currentOption = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.red);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("> Kalangos Game <", (Game.WIDTH*Game.SCALE) / 2 - 185, (Game.HEIGHT*Game.SCALE) / 2 - 190);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 26));
		g.drawString("Novo Jogo", (Game.WIDTH*Game.SCALE) / 2 - 50, 160);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 26));
		g.drawString("Carregar Jogo", (Game.WIDTH*Game.SCALE)/ 2 - 70,  200);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 26));
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) / 2 - 10,  240);
		
		if(options[currentOption] ==  "Novo Jogo") {
			g.drawString(" > ", (Game.WIDTH*Game.SCALE) / 2 - 90, 160);
		}else if(options[currentOption] ==  "Carregar Jogo") {
			g.drawString(" > ", (Game.WIDTH*Game.SCALE) / 2 - 100, 200);
		}else if(options[currentOption] ==  "Sair") {
			g.drawString(" > ", (Game.WIDTH*Game.SCALE) / 2 - 40, 240);
		}
	}
}
