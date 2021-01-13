package minesweeper.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Egy egéren keresztüli user inputut tud lekezelni, majd megjátszani a lépést a tényleges táblán,
 * és meghívni a játék UI frissítési metódusát.
 * Egy lépés során leellenõrzi, hogy vége van-e már a játéknak,
 * illetve meg van-e állítva a játékmenet, mert ilyenkor nem reagálunk a kiadott lépésre.
 */
public class Move extends MouseAdapter {

    /**
     * A játékablak, amihez tartozik a lépés.
     */
    private GameGUI gui;

    /**
     * A lépés oszlopindexe.
     */
    private int colID;

    /**
     * A lépés sorindexe.
     */
    private int rowID;

    /**
     * Megmutatja, hogy a felhasználó lenyomta-e a bal egérgombot.
     */
    private boolean isLeftPressed;

    /**
     * Megmutatja, hogy a felhasználó lenyomta-e a jobb egérgombot.
     */
    private boolean isRightPressed;

    /**
     * Magának a lépésnek a konstruktora.
     *
     * @param newGUI A UI, amin belül történik a lépés.
     * @param targetColID A lépés táblán belüli oszlopindexe.
     * @param targetRowID A lépés táblán belüli sorindexe.
     */
    public Move(GameGUI newGUI, int targetColID, int targetRowID){
        gui = newGUI;
        colID = targetColID;
        rowID = targetRowID;
    }


    /**
     * A gombra kattintásokért felelõs listener.
     * A metódusban különbözõ képpen kezeljük le, hogy éppen bal, jobb, vagy akár mindkét egérgombbal kattint
     * a játékos egy gombra.
     * Kattintás után frissítjük a játéktér kinézetét.
     *
     * @param e A MouseEvent, amit vizsgálnunk
     */
    @Override
    public void mousePressed (MouseEvent e) {
        if(!gui.getPaused() && !gui.getBoard().getGameover()){
            if (SwingUtilities.isLeftMouseButton (e)) {
                isLeftPressed = true;
                gui.getBoard().playCellByCoords(colID, rowID);
            }
            else if (SwingUtilities.isRightMouseButton (e)) {
                isRightPressed = true;
                gui.getBoard().flagCellByCoords(colID, rowID);
                gui.updateFlagCounter();
            }
            if (isLeftPressed && isRightPressed){
                gui.getBoard().doubleClickByCoords(colID,rowID);
            }

            gui.checkEndGame();
            gui.checkWon();
            gui.updateBoard();
        }
    }

    /**
     * Az egérgomb felengedésekor beállítjuk, hogy egyik egérgomb sincs lenyomva.
     *
     * @param e A MouseEvent, amit vizsgálunk.
     */
    @Override
    public void mouseReleased (MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            isLeftPressed = false;
        else if (SwingUtilities.isRightMouseButton(e))
            isRightPressed = false;
    }

}
