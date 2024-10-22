package tile;

import main.GameScreen;
import main.ScalingTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileHandler {

    GameScreen gp;
    public Tile[] tile; //creates an array of tiles
    public int mapTileNum[] []; // creates an array of tile numbers
    public TileHandler(GameScreen gp) {
        this.gp = gp;
        tile = new Tile[40];
        mapTileNum = new int[gp.maxWorldCol] [gp.maxWorldRow];
        getTileImage();
        // loadMap("/maps/map01.txt");
        // loadMap("/maps/map02.txt");
        loadMap("/maps/map03.txt");
    }

    public void getTileImage() {
        //creates all of the images that correspond to each number and sets their collision

            setup(0, "fullGrass", false);
            setup(1, "fullSand", false);
            setup(2, "fullWater", true);
            setup(3, "treeGrass", true);
            setup(4, "rockGrass", true);
            setup(5, "lilyPadWater-1.png", true);
            setup(6, "yellowFlower", false);
            setup(7, "cattailWater-1.png", true);
            setup(8, "treeWater-1.png", true);
            setup(9, "rockWater-1.png", true);
            setup(10, "blueFlowerGrass", false);
            setup(11, "pebblesWater-1.png", true);
            setup(12, "grassTopLeftSand-1.png", false);
            setup(13, "grassLeftSand-1.png", false);
            setup(14, "grassBottomLeftSand-1.png", false);
            setup(15, "grassBottomSand-1.png", false);
            setup(16, "grassBottomRightSand-1.png", false);
            setup(17, "grassRightSand-1.png", false);
            setup(18, "grassTopRightSand-1.png", false);
            setup(19, "grassTopSand-1.png", false);
            setup(20, "sandTopLeftGrass-1.png", false);
            setup(21, "sandLeftGrass-1.png", false);
            setup(22, "sandBottomLeftGrass-1.png", false);
            setup(23, "sandBottomGrass-1.png", false);
            setup(24, "sandBottomRightGrass-1.png (1)", false);
            setup(25, "sandRightGrass-1.png", false);
            setup(26, "sandTopRightGrass-1.png", false);
            setup(27, "sandTopGrass-1.png", false);
            setup(28, "rockSand-1.png", true);
            setup(29, "darkSand", false);
            setup(30, "grassTopRightWater-1.png", false);
            setup(31, "grassTopWater-1.png", false);
            setup(32, "grassTopLeftWater-1.png", false);
            setup(33, "grassLeftWater-1.png", false);
            setup(34, "grassRightWater-1.png", false);
            setup(35, "grassBottomRightWater-1.png", false);
            setup(36, "grassBottomLeftWater-1.png", false);
            setup(37, "grassBottomWater-1.png", false);
            setup(38, "treeGrass-1.png (1)", true);

    }
    public void setup(int index, String imageName, boolean collision) {
        //makes the setting up of tiles faster

        ScalingTool uTool = new ScalingTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {
        //this runs through the text file and loads the tiles of the map based off of what number appears in the text file

        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while(col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch(Exception e) {

        }
    }
    public void draw(Graphics2D g2) {
        //this draws all of the tiles on the screen based off of where the player is on the map, so that the
        //"camera" or world drawing is centered off of the character.

        //old way of having to input each tile before file parsing method

    /*  g2.drawImage(tile[1].image, 0, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 96, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 144, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 192, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 240, 0, gp.tileSize, gp.tileSize, null);
     */
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }

        }
    }

}
