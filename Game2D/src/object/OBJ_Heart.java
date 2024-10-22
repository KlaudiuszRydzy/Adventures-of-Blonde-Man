package object;

import entity.Entity;
import main.GameScreen;

public class OBJ_Heart extends Entity {
    //creates a "heart" object

    public OBJ_Heart(GameScreen gp) {
        super(gp);

        name = "Heart";
        image = setup("/objects/fullHeart-1.png", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/halfHeart-1.png", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/noHeart-1.png", gp.tileSize, gp.tileSize);
    }

}
