package entity;

import main.GameScreen;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    GameScreen gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int standCounter = 0;
    public int mobsKilled = 0;
    // public int hasKey = 0;

    public Player(GameScreen gp, KeyHandler keyH) {

        super(gp);

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //This creates the solid area which is checked during collision to show what part of the character should hit what
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 16;
        solidArea.height = gp.tileSize - 16;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultPositions() {
        //default position in world
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        direction = "down";
    }
    public void restoreLife() {
        //restores health
        life = maxLife;
        invincible = false;
    }
    public void setDefaultValues() {

        //where the character spawns on the map
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 4;
        direction = "down";

        //player status
        maxLife = 6;
        life = maxLife;
    }
    public void getPlayerImage() {

        //obtains all the scaled images of the player in the res player package
        up1 = setup("/player/Up1-1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/Up2-1.png", gp.tileSize, gp.tileSize);
        down1 = setup("/player/Down1-1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/Down2-1.png", gp.tileSize, gp.tileSize);
        left1 = setup("/player/Left1.png-1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/Left2-1.png", gp.tileSize, gp.tileSize);
        right1 = setup("/player/Right1-1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/Right2-1.png", gp.tileSize, gp.tileSize);
    }
    public void getPlayerAttackImage() {
        //obtains all of the scaled images of the attacking player in the res payer package
        attackUp1 = setup("/player/attackUp1-1.png", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/attackUp2-1.png", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/attackDown1-1.png", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/attackDown2.png-1.png", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/attackLeft1.png-1.png", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/attackLeft2.png-1.png", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/attackRight1.png-1.png", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/attackRight2.png-1.png", gp.tileSize*2, gp.tileSize);
    }
    public void update() {
        //checks all of the actions of the character and updates him based off what the player is doing
        if(isAttacking == true) {
            isAttacking();
        }
        else if(keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {

            if(keyH.upPressed == true){
                direction = "up";
            }
           if(keyH.downPressed == true) {
                direction = "down";
            }
            if(keyH.leftPressed == true) {
                direction = "left";
            }
            if(keyH.rightPressed == true) {
                direction = "right";
            }
            //Checks Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check Object Collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // checks NPC Collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Checks MOB Collision
            int mobIndex = gp.cChecker.checkEntity(this, gp.mob);
            contactMob(mobIndex);

            //checks event
            gp.eHandler.checkEvent();


            //if collision is false, player can move
            if(collisionOn == false && keyH.enterPressed == false) {

                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            gp.keyH.enterPressed = false;

            spriteCounter++;
            if(spriteCounter > 14) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;
        }

        // if player is or is not moving
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(life <= 0) {
            //if player dies then end game
            mobsKilled = 0;
            gp.gameState = gp.gameOverState;
        }
        if(mobsKilled == 3) {
            //if 3 Mobs are killed then create congratulations screen
            gp.gameState = gp.gameWonState;
            mobsKilled = 0;
        }

    }
    public void isAttacking() {
        //checks if player is attacking
        spriteCounter++;
        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            //temporary variables to store player's location, used for hitting location check
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            // adjusts the players x and Y values for the attackArea
            switch(direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //the attack area becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //checks the monster collision with new variables
            int mobIndex = gp.cChecker.checkEntity(this, gp.mob);
            damageMob(mobIndex);

            //restores the variables back to normal after checking for attack collision
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            isAttacking = false;
        }
    }
    public void pickUpObject(int i) {
//handles picking up objects
        if( i != 999) {

            String objectName = gp.obj[i].name;
            switch(objectName) {
                case"Boots":
                    //increases speed if picked up
                    speed+=1;
                    gp.obj[i] = null;
                    break;
            }
        }
    }
    public void interactNPC(int i) {
        //handles interaction of NPCs
        if(gp.keyH.enterPressed == true) {
            if( i != 999) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
            } else {
                    isAttacking = true;
            }
        }
    }
    public void contactMob(int i) {
        //Checks if there is contact with a mob
        if( i != 999) {

            if(invincible == false) {
                life -= 1;
                invincible = true;
            }
        }
    }
    public void damageMob(int i) {
        //handles damage between mob and player
        if(i != 999) {
            System.out.println("Hit");
            if(gp.mob[i].invincible == false) {
                gp.mob[i].life -=1;
                gp.mob[i].invincible = true;
                gp.mob[i].damageReaction();

                if(gp.mob[i].life <= 0) {
                    gp.mob[i].dying = true;
                }
            }

        } else {
            System.out.println("miss");
        }
    }
    public void draw(Graphics2D g2) {

    /*    g2.setColor(Color.white);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    */

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        //handles animation of character based on if he is attacking or not and which direction hes facing
        switch(direction) {
            case "up":
                if(isAttacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if(isAttacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if(isAttacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if(isAttacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if(isAttacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if(isAttacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if(isAttacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if(isAttacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        if(invincible == true && ((invincibleCounter > 0 && invincibleCounter < 15) || (invincibleCounter > 30 && invincibleCounter < 45))) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
