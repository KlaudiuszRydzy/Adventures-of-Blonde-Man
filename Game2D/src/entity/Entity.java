package entity;

import main.GameScreen;
import main.ScalingTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GameScreen gp;
    public int worldX, worldY; //keeps track of the position in the "world" or map for all things

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //creates buffered images for player movement
    public BufferedImage attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, // creates buffered images for player attack
            attackRight2, attackDown1, attackDown2;
    public String direction = "down"; // the direction of any entity in relation to the world
    public int spriteNum = 1; //assignment of number for each animation
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // creates a "hitbox" for an entity
    public Rectangle attackArea = new Rectangle(0,0,0,0); // creates an attack hitbox for the entity
    public int solidAreaDefaultX, solidAreaDefaultY; // parameters for hitbox
    public boolean collisionOn = false; //determines whether collision is happening
    public boolean invincible = false; // creates an invulnerability after an action
    boolean isAttacking = false; // checks if entity is attacking
    String dialogues[] = new String[20]; // array of dialogue texts
    int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    public boolean alive = true;
    public boolean dying = false;
    // COUNTERS
    public int spriteCounter = 0; // counter for changing animations
    public int invincibleCounter = 0; // creates a timer for invincibility
    public int actionLockCounter = 0; // creates a timer so actions aren't performed infinitely consecutively
    int dyingCounter = 0;
    // CHARACTER ATTRIBUTES
    public int maxLife;
    public int life;
    public int speed; //the tile movement speed per second of any entity
    public String name;
    public int type; // 0 = player, 1 = npc, 2 = mob
    public void damageReaction() {

    }

    //Character status

    public Entity(GameScreen gp) {
        this.gp = gp;
    }
    public void setAction() {
    // currently blank
    }
    public void speak() {

        //This handles all of the speaking that any entity will do in the game
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update() {

        //this updates the Entity, checks its direction to see where its going and what its facing, checks all interactions
        //such as collision, interaction, etc.
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.mob);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true) {
            if(gp.player.invincible == false) {
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }
// changes speed based off collision
        if (collisionOn == false) {

            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
//changes animation based on counter
        spriteCounter++;
        if (spriteCounter > 14) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {

        //This draws the entity
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        //draws the entity based of counter, gives animation to entity
            switch(direction) {
                case "up":
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            //Mob Health Bar
            if(type == 2) {

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
            }

            if(invincible == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if(dying == true){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if(dyingCounter <= i) {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i && dyingCounter <= i*2) {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*2 && dyingCounter <= i*3) {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*3 && dyingCounter <= i*4) {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*4 && dyingCounter <= i*5) {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*5 && dyingCounter <= i*6) {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*6 && dyingCounter <= i*7) {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*7 && dyingCounter <= i*8) {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*8) {
            dying = false;
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    //very useful setup method that lets me eliminate having to create a new method every time I want to create
    //a buffered image, instead I can just use this setup with the name of the image path
    public BufferedImage setup(String imagePath, int width, int height) {
        ScalingTool uTool = new ScalingTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
