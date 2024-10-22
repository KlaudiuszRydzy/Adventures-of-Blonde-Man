package main;

import entity.NPC_Carol;
import mob.MOB_Slime;
import object.OBJ_Boots;

public class SetAssets {
    //this sets all of the assets in the game onto the map in the position I want them to be at.

    GameScreen gp;

    public SetAssets(GameScreen gp) {
        this.gp = gp;
    }
    public void setObject() {
        //sets all objects on the map
        gp.obj[0] = new OBJ_Boots(gp);
        gp.obj[0].worldX = gp.tileSize*24;
        gp.obj[0].worldY = gp.tileSize*28;
    }
    public void setNPC() {
        //sets all objects on the map
        gp.npc[0] = new NPC_Carol(gp);
        gp.npc[0].worldX = gp.tileSize*25;
        gp.npc[0].worldY = gp.tileSize*23;
    }
    public void setMOB() {
        //sets all mobs on the map

        gp.mob[0] = new MOB_Slime(gp);
        gp.mob[0].worldX = gp.tileSize*38;
        gp.mob[0].worldY = gp.tileSize*40;

        gp.mob[1] = new MOB_Slime(gp);
        gp.mob[1].worldX = gp.tileSize*38;
        gp.mob[1].worldY = gp.tileSize*38;

        gp.mob[2] = new MOB_Slime(gp);
        gp.mob[2].worldX = gp.tileSize*40;
        gp.mob[2].worldY = gp.tileSize*38;
    }
}
