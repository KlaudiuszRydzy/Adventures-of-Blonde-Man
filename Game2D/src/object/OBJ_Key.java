package object;

import entity.Entity;
import main.GameScreen;

public class OBJ_Key extends Entity {
    //creates a "key" object

    public OBJ_Key(GameScreen gp) {
        super(gp);

        name = "Key";
        down1 = setup("/objects/key_01a", gp.tileSize, gp.tileSize);
    }
}
