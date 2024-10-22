package mob;

import entity.Entity;
import main.GameScreen;

import java.util.Random;

public class MOB_Slime extends Entity {
    GameScreen gp;
    public MOB_Slime(GameScreen gp) {
        super(gp);
        this.gp = gp;

        //sets the characteristics of the Slime mob, all of the stats and hitbox size
        type = 2;
        name = "Slime";
        speed = 2;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 0;
        solidArea.y = 21;
        solidArea.width = 48;
        solidArea.height = 27;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();

    }
    public void getImage() {
        //sets images for the slime, handles animation

        up1 = setup("/mob/slime1-1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/mob/slime2-2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/mob/slime1-1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/mob/slime2-2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/mob/slime1-1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/mob/slime2-2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/mob/slime1-1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/mob/slime2-2.png", gp.tileSize, gp.tileSize);
    }
    public void setAction() {
        //sets the "action" of the slime, in this case direction of movement randomly changing

        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; //number from 1-100

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
    public void damageReaction() {
        // makes slime run away from character on hit.
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
