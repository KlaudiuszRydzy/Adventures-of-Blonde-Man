package object;

import entity.Entity;
import main.GameScreen;

public class OBJ_Door extends Entity {

    //creates a "Door" object

        public OBJ_Door(GameScreen gp) {
            super(gp);

            name = "Door";
            down1 = setup("/objects/woodDoor-1.png", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
