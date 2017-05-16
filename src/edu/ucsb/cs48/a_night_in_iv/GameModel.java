package edu.ucsb.cs48.a_night_in_iv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class that constructs the entire game model based upon the game component built
 * Each individual MapSection is populated with various textures of sprites determined in corresponding .txt files
 *
 * Created by Karl Wang (kovlv) on 5/6/2017.
 * @see GameComponent for how game component builds the map sections
 * @see MapSection for how
 */
public class GameModel {
    static final int PIXEL_SIZE = 32;
    Map<String, BufferedImage> textures = new HashMap<String, BufferedImage>();
    Player player;
    String name;
    int mapWidth;
    int mapHeight;
    MapSection[][] sections;
    private int currentMapX;
    private int currentMapY;
    private int sceneWidth;
    private int sceneHeight;

    public GameModel(String name) {
        this.name = name;
        String dir = "/resources/" + name + "/";

        Scanner scanner = new Scanner(getClass().getResourceAsStream(dir + "layoutVars.txt"));

        sceneWidth = scanner.nextInt();
        sceneHeight = scanner.nextInt();
        mapWidth = scanner.nextInt();
        mapHeight = scanner.nextInt();
        currentMapX = scanner.nextInt();
        currentMapY = scanner.nextInt();
        this.sections = new MapSection[sceneHeight][sceneWidth];

        String temp;
        //Must load texture first before loading the sections
        loadTextures("./src/resources/" + name + "/textures/");
        for (int y = 0; y < sceneHeight; ++y)
            for (int x = 0; x < sceneWidth; ++x)
                if (scanner.hasNext()) {
                    temp = scanner.next();
                    if (temp.equals(("0"))) {
                        sections[y][x] = null;
                    } else {
                        sections[y][x] = new MapSection(dir, temp, mapHeight, mapWidth, this);
                    }
                }

        setPlayer(new Player(3, 3, 16, 8, "player", this));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void loadTextures(String path) {
        File folder = new File(path);
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                System.out.println(fileEntry.getName());
                String name = fileEntry.getName();
                int pos = name.lastIndexOf(".");
                if (pos > 0) {
                    name = name.substring(0, pos);
                }
                try {
                    System.out.println("name is:" + fileEntry.getName() + ":" + name);
                    String filepath = "/resources/" + this.name + "/textures/" + fileEntry.getName();
                    URL fileURL = getClass().getResource(filepath);
                    textures.put(name, ImageIO.read(fileURL));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(textures);
    }

    public BufferedImage getTexture(String name) {
        return textures.get(name);
    }

    public MapSection getCurrentMap() {
        return sections[currentMapY][currentMapX];
    }

    public MapSection getMapInDirection(int Y, int X) {
        int newX = currentMapX + X;
        int newY = currentMapY + Y;
        if (newX < 0 || newY < 0 || newX > sceneWidth - 1 || newY > sceneHeight - 1)
            return null;
        else
            return sections[newY][newX];
    }

    public void moveMapInDirection(int Y, int X) {
        int newX = currentMapX + X;
        int newY = currentMapY + Y;
        if (!(newX < 0 || newY < 0 || newX > sceneWidth - 1 || newY > sceneHeight - 1)) {
            currentMapX = newX;
            currentMapY = newY;
        }

    }


}
