package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    GameScreen gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //Debug
    boolean checkDrawTime = false;
    //This class is used for handling all of the key inputs in the game based off of what screen the player is on in
    //the game at that time. Basically if the player is in the title screen certain character presses will do certain
    //actions, and if the player is in the game screen it will do other actions.

    public KeyHandler(GameScreen gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //Title State
        if(gp.gameState == gp.titleState) {

            if(gp.ui.titleScreenState == 0) {

                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    }
                    if (gp.ui.commandNum == 1) {

                    }
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }
            }

                else if(gp.ui.titleScreenState == 1) {
                    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                        gp.ui.commandNum--;
                        if (gp.ui.commandNum < 0) {
                            gp.ui.commandNum = 3;
                        }
                    }
                    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                        gp.ui.commandNum++;
                        if (gp.ui.commandNum > 3) {
                            gp.ui.commandNum = 0;
                        }
                    }
                    if (code == KeyEvent.VK_ENTER) {
                        if (gp.ui.commandNum == 0) {
                            gp.gameState = gp.playState;
                            gp.playMusic(0);
                        }
                        if (gp.ui.commandNum == 1) {
                            gp.gameState = gp.playState;
                        }
                        if (gp.ui.commandNum == 2) {
                            gp.gameState = gp.playState;
                        }
                        if (gp.ui.commandNum == 3) {
                            gp.ui.titleScreenState = 0;
                        }
                    }
                }
        }
        // play state
        else if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            //Debug
            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;
                } else if (checkDrawTime == true) {
                    checkDrawTime = false;
                }
            }
        }
        // Pause State
        else if(gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        // Dialogue State
        else if(gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        } else if(gp.gameState == gp.gameOverState) { //game over screen
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum <0) {
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum >1) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.retry();
                } else if(gp.ui.commandNum == 1) {
                    gp.ui.titleScreenState = 0;
                    gp.gameState = gp.titleState;
                    gp.restart();
                }
            }
        } else if(gp.gameState == gp.gameWonState) { //game won screen
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum <0) {
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum >1) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.retry();
                } else if(gp.ui.commandNum == 1) {
                    gp.ui.titleScreenState = 0;
                    gp.gameState = gp.titleState;
                    gp.restart();
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
