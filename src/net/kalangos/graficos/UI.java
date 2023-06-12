package net.kalangos.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.kalangos.entities.Player;
import net.kalangos.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(8, 4, 70, 8);
		g.setColor(Color.GREEN);
		g.fillRect(8, 4, (int)((Game.player.life/Game.player.life)*70), 8);
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)Game.player.life + "/" + (int )Game.player.maxLife, 30, 11);
	}
}
