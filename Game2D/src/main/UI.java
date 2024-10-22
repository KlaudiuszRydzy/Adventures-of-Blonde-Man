package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;

import java.awt.image.BufferedImage;

public class UI {

    GameScreen gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage fullHeart, halfHeart, noHeart;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: second screen

 //    double playTime;
 //   DecimalFormat dFormat = new DecimalFormat("#0.00");


    public UI(GameScreen gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        //create hud object
        Entity heart = new OBJ_Heart(gp);
        fullHeart = heart.image;
        halfHeart = heart.image2;
        noHeart = heart.image3;

    }
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        //this draws what appears on the screen.

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //title state
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // Play State
        if(gp.gameState == gp.playState) {
            //Do playState stuff later
            drawPlayerLife();
        }
        //Pause state
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        //Dialogue state
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        if(gp.gameState == gp.gameWonState) {
            drawGameWonScreen();
        }
    }
    public void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //draws max life
        while(i < gp.player.maxLife/2) {
            g2.drawImage(noHeart, x, y, null);
            i++;
            x += gp.tileSize+8;
        }
        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        //draws current life
        while(i < gp.player.life) {
            g2.drawImage(halfHeart, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(fullHeart, x, y, null);
            }
            i++;
            x += gp.tileSize+8;
        }

    }
    public void drawTitleScreen() {
        if(titleScreenState == 0) {
            //background color
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            //title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            String text = "The Adventures\nof Blonde Man";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3 / 2;

            //shadow
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);
            //main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //blonde man image

            x = gp.screenWidth / 2 - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            //button images
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1) {

            //class selection screen
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x-gp.tileSize, y);
            }

        }


    }
    public void drawGameWonScreen() {
        //creates a game won screen
        g2.setColor(new Color(0,0,0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text = "";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42f));

        text = "Congratulations! You Won!";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);
        //retry
        g2.setFont(g2.getFont().deriveFont(42f));
        text = "Back";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-40, y);
        }

        //back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y+=55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-40, y);
        }
    }
    public void drawGameOverScreen() {
        //creates a game over screen
        g2.setColor(new Color(0,0,0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text = "";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42f));

        text = "Game Over";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);
        //retry
        g2.setFont(g2.getFont().deriveFont(42f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-40, y);
        }

        //back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y+=55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-40, y);
        }
    }
    public void drawPauseScreen() {
        //creates a screen for paused state
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen() {

        // window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26F));
        x += gp.tileSize;
        y += gp.tileSize+5;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x , y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0 , 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public int getXforCenteredText(String text) {
        //centers the text so that it doesn't appear off frame
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
