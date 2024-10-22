package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.Entity;
import entity.Player;
import tile.TileHandler;

public class GameScreen extends JPanel implements Runnable{
    // the screen settings
    final int startingTileSize = 16; // 16x16 tile
    final int scale = 3; // scales what the pixels look like, doing it by a factor of 3 here
    public final int tileSize = startingTileSize * scale; // tiles will look 48x48 pixels
    public final int maxScreenCol = 16; //total amount of tiles allowed in the X direction
    public final int maxScreenRow = 12; // total amount of tiles allowed in the Y direction
   public final int screenWidth = tileSize * maxScreenCol; // 768 pixels (Horizontally)
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels (Vertically)
    // WORLD MAP PARAMETERS
    public final int maxWorldCol = 50; // total tile size of the map in the X direction
    public final int maxWorldRow = 50; // total tile size of the map in the Y direction
    public final int worldWidth = tileSize * maxWorldCol; // this shows the total pixel length of world in X direction
    public final int worldHeight = tileSize * maxWorldRow; // this shows the total pixel length of the world in the Y direction

    int FPS = 60; //Intended or target frames per second of the game
// SYSTEM
    TileHandler tileM = new TileHandler(this); //"this" (GameScreen) is ran through the TileHandler in order to draw the tiles
    // in TileHandler later in the class
    public KeyHandler keyH = new KeyHandler(this); //"this (GameScreen) is ran throught the keyHandler class in order to
    // inherit the key handling properties
    Sound music = new Sound(); //creates a new sound for the music used in the game (will contain an array of different sounds)
    Sound se = new Sound(); // creates a new sound for the sound effects in the game (will also contains an array of sound effects)
    public CheckForCollisions cChecker = new CheckForCollisions(this); // creates a new CheckForCollisions to check collisions
    // between objects, entities, mobs, players, etc. using the variables obtained through this GameScreen class
    public SetAssets aSetter = new SetAssets(this); // creates a new SetAssets, in order to allow for the text reader
    // to draw its contents on the GameScreen for all "Assets"
    public UI ui = new UI(this); // creates a new "UI" which creates the hearts, soon will create mana for the player, creates
    // different screens for the different states of the game "paused" "title" and any other screen I'd like to add in the guture
    public HandleEvents eHandler = new HandleEvents(this); // creates a new "HandleEvents" which handles the events in the game
    // such as teleporting, interacting with assets, etc.
    Thread gameThread; // creates a new thread for the game to run continuously until it is closed out

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH); //Creates a new "Player" to handle all methods within the Player class
    public Entity obj[] = new Entity[10]; //Creates an entity array of objects
    public Entity npc[] = new Entity[10]; //Creates an entity array of NPCs
    public Entity mob[] = new Entity[20]; //Creates an entity array of mobs
    ArrayList<Entity> entityList = new ArrayList<>(); //Creates an arraylist of all entities that will be treversed through
    // in the draw method to handle drawing all entities

    // GAME STATE
    public int gameState; // determines the state of the game
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int gameOverState = 4;
    public final int gameWonState = 5;


    public GameScreen() {
        //This creates the game panel we see where we play the game on.
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        //This creates all of the images of the things that arent "tiles" or "players"
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMOB();
        // playMusic(0);
        gameState = titleState;

    }
    public void retry() {
        //resets player stats and positions of everything to restart the game
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setMOB();
        aSetter.setNPC();

    }
    public void restart() {
        //completely restarts game
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setMOB();
        aSetter.setNPC();
        aSetter.setObject();
    }

    public void startGameThread() {
        //This creates the start of the thread that continues as the game is running
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    //old method of running a loop I tried, found a new one online which handles the game
    //through a delta variable which is much better

   /* public void run() {
        double drawInterval = 1_000_000_000/FPS; // 0.016666... seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread !=null) {
            //  System.out.println("The game loop is running");
            // 1 Update: updates information, such as orientation of objects
            update();
            // 2 Draw: draw the objects on the screen at the accurate time
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) (remainingTime));
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    */
    public void run() {
        //this run loop is a delta counter I found on the internet, handles the updates of the game  as it runs
        //continuously

        double drawInterval = 1_000_000_000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1_000_000_000) {

                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        //this updates the state of the game 60 times per second, so all counters within the classes of player or entity, etc
        //are reliant on the game updating once every 60th of a second
        if(gameState == playState) {
            //PLAYER
            player.update();
            //NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
            for(int i = 0; i < mob.length; i++) {
                if (mob[i] != null) {
                    if(mob[i].alive == true && mob[i].dying == false){
                        mob[i].update();
                    }
                    if(mob[i].alive == false) {
                        mob[i] = null;
                    }
                }
            }
        }
        if(gameState == pauseState) {
            //nothing
        }
    }
    public void paintComponent(Graphics g) {

        //This method handles the drawing of all things on the screen, all tiles, the player, and where the camera is along the map
        //are all drawn by this method
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Debug
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }
        drawStart = System.nanoTime();
        //title screen
        if(gameState == titleState) {
            ui.draw(g2);
        }
        //others
        else {
            //Tile
            tileM.draw(g2);
            //add entities to list
            entityList.add(player);

            for(int i = 0; i <npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i <obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i <mob.length; i++) {
                if(mob[i] != null) {
                    entityList.add(mob[i]);
                }
            }
            //sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
            //Draw Entities
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //Empty Entity list
            entityList.clear();

            //for(int i = 0; i < entityList.size(); i++) {
            //    entityList.remove(i);
            //} this decreases the size of the arrayList (dont use)

            // UI
            ui.draw(g2);
        }
            // Debug
            if (keyH.checkDrawTime == true) {
                //I use this to check the draw time for my program, so I know that things are being drawn efficiently
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + passed, 10, 400);
                System.out.println("Draw Time: " + passed);
            }
            g2.dispose();
        }

    public void playMusic(int i) {

        //Starts playing the music of the game handled by the Sound class
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
