package edu.ucsb.cs48.a_night_in_iv;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

/**
 * A component that draws the map for the treasure hunter game by Alex Wood
 * Edited by Danielle Dodd and George Lieu
 * Edited by Lisa Liao and Patrick Vidican
 *
 * @author Alex Wood (for CS56, W12, UCSB, 2/16/2012)
 * @author Danielle Dodd and George Lieu
 * @author Lisa Liao and Patrick Vidican
 * @version for UCSB CS56, F16, 11/19/2016
 */


public class GameComponent extends JComponent {

    GameModel game;
    RepaintManager paintManager = RepaintManager.currentManager(this);

    public void setGame(GameModel game) {
        this.game = game;
    }

    /*
      paintComponent: It draws all of the tiles on the map. Also loads the player sprite.
      When player find the treasure, the message variable value changes and the "TREASURE # FOUND" message box is drawn onto the screen.

    */
    @Override
    public void paintComponent(Graphics g) {
        MapSection map = game.getCurrentMap();

        // probably draws the tiles
        for (int h = 0; h < map.height; h++)
            for (int w = 0; w < map.width; w++)
                g.drawImage(map.getTerrain(h, w), w * game.PIXEL_SIZE, h * game.PIXEL_SIZE, null);

        for (int h = 0; h < map.height; h++)
            for (int w = 0; w < map.width; w++)
                if (map.getSprite(h, w) != null)
                    g.drawImage(map.getSprite(h, w).getImage(), w * game.PIXEL_SIZE, h * game.PIXEL_SIZE, null);

        for (int h = 0; h < map.height; h++)
            for (int w = 0; w < map.width; w++)
                if (map.getItem(h, w) != null)
                    g.drawImage(map.getItem(h, w).getImage(), w * game.PIXEL_SIZE, h * game.PIXEL_SIZE, null);

        Player player = game.getPlayer();
        g.drawImage(player.getPlayerImage(), player.getXPos(), player.getYPos(), null);

        Graphics2D g2 = (Graphics2D) g;

    }

    /* Draws the player sprite onto a new tile */
    public void updatePlayer() {
        paintManager.addDirtyRegion(this, game.getPlayer().getXPos() - 10, game.getPlayer().getYPos() - 10, 60, 60);
    }

}