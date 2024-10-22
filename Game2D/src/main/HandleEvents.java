package main;

public class HandleEvents {

    GameScreen gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public HandleEvents(GameScreen gp) {
        //checks for events occurring based off of tile on the map
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
                    if(col == gp.maxWorldCol) {
                        col = 0;
                        row++;
                    }
        }
    }

    public void checkEvent() {

        //check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            //checks if player is close enough to touch event
            canTouchEvent = true;
        }
        if(canTouchEvent == true) {
            //handles events occurring in the game
            if (hit(25, 27, "down") == true) {
                //event happens
                damagePit(25, 27, gp.dialogueState);
            }
            if (hit(19, 29, "down") == true) {
                healingPool(19, 29, gp.dialogueState);
            }
            if (hit(24, 24, "any") == true) {
                teleport(gp.dialogueState);
            }
        }
    }
    public boolean hit(int col, int row, String reqDirection) {
        //checks if the player is "hitting" some event

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if(gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public void damagePit(int col, int row, int gameState) {
        //creates a pit of damage that player takes if he goes over tile faacing specific direction.
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell into a put!";
        gp.player.life -= 1;
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healingPool(int col, int row, int gameState) {
// heals player if he steps into this tile
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water.\nYour life has been recovered.";
            gp.player.life = gp.player.maxLife;
        }
    }
    public void teleport(int gameState) {
        //teleports player if they step into the tile
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.player.worldX = gp.tileSize*30;
        gp.player.worldY = gp.tileSize*25;
    }


}
