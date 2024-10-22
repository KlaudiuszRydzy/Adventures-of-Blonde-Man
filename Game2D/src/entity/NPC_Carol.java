package entity;

import main.GameScreen;

import java.util.Random;

public class NPC_Carol extends Entity {

    public NPC_Carol(GameScreen gp) {
        super(gp);
//sets NPC stats
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }
        public void getImage() {
//gets images for NPC
            up1 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
            up2 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
            down1 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
            down2 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
            left1 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
            left2 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
            right1 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
            right2 = setup("/npc/CarolNpc-1.png", gp.tileSize, gp.tileSize);
        }
        public void setDialogue() {
//sets dialogue paths for NPC
        dialogues[0] = "uh.... hello stranger";
        dialogues[1] = "do I know you?";
        dialogues[2] = "please kill these slimes!";
        dialogues[3] = "Press Enter to attack";
        dialogues[4] = "Thanks You!";
        dialogues[5] = "My name is Carol btw";

        }
        public void setAction() {
//manages action, in this case the movement of the NPC
        actionLockCounter++;
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1; //number from 1-100

            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <=50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75 && i <=100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
        }
        public void speak() {

        //Character specific
        super.speak();
        }
    }
