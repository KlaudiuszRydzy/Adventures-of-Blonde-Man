package object;

import entity.Entity;
import main.GameScreen;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GameScreen gp) {

        //creates a "boots" object
        super(gp);

        name = "Boots";
        down1 = setup("/objects/boots_01a", gp.tileSize, gp.tileSize);
    }
}
