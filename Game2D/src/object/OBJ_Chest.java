package object;

import entity.Entity;
import main.GameScreen;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(GameScreen gp) {

        super(gp);

        name = "Chest";
        down1 = setup("/objects/Chest-1.png", gp.tileSize, gp.tileSize);
    }
}
