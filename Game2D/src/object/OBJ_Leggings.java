package object;

import entity.Entity;
import main.GameScreen;


public class OBJ_Leggings extends Entity {
    //creates a "leggings" object

    public OBJ_Leggings(GameScreen gp) {

        super(gp);

        name = "Leggings";
        down1 = setup("/objects/boots_01a", gp.tileSize, gp.tileSize);
    }

}
