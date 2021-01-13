package minesweeper.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Egy eg�ren kereszt�li user inputut tud lekezelni, majd megj�tszani a l�p�st a t�nyleges t�bl�n,
 * �s megh�vni a j�t�k UI friss�t�si met�dus�t.
 * Egy l�p�s sor�n leellen�rzi, hogy v�ge van-e m�r a j�t�knak,
 * illetve meg van-e �ll�tva a j�t�kmenet, mert ilyenkor nem reag�lunk a kiadott l�p�sre.
 */
public class Move extends MouseAdapter {

    /**
     * A j�t�kablak, amihez tartozik a l�p�s.
     */
    private GameGUI gui;

    /**
     * A l�p�s oszlopindexe.
     */
    private int colID;

    /**
     * A l�p�s sorindexe.
     */
    private int rowID;

    /**
     * Megmutatja, hogy a felhaszn�l� lenyomta-e a bal eg�rgombot.
     */
    private boolean isLeftPressed;

    /**
     * Megmutatja, hogy a felhaszn�l� lenyomta-e a jobb eg�rgombot.
     */
    private boolean isRightPressed;

    /**
     * Mag�nak a l�p�snek a konstruktora.
     *
     * @param newGUI A UI, amin bel�l t�rt�nik a l�p�s.
     * @param targetColID A l�p�s t�bl�n bel�li oszlopindexe.
     * @param targetRowID A l�p�s t�bl�n bel�li sorindexe.
     */
    public Move(GameGUI newGUI, int targetColID, int targetRowID){
        gui = newGUI;
        colID = targetColID;
        rowID = targetRowID;
    }


    /**
     * A gombra kattint�sok�rt felel�s listener.
     * A met�dusban k�l�nb�z� k�ppen kezelj�k le, hogy �ppen bal, jobb, vagy ak�r mindk�t eg�rgombbal kattint
     * a j�t�kos egy gombra.
     * Kattint�s ut�n friss�tj�k a j�t�kt�r kin�zet�t.
     *
     * @param e A MouseEvent, amit vizsg�lnunk
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
     * Az eg�rgomb felenged�sekor be�ll�tjuk, hogy egyik eg�rgomb sincs lenyomva.
     *
     * @param e A MouseEvent, amit vizsg�lunk.
     */
    @Override
    public void mouseReleased (MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            isLeftPressed = false;
        else if (SwingUtilities.isRightMouseButton(e))
            isRightPressed = false;
    }

}
