package minesweeper.gui;

import minesweeper.gameboard.Board;
import minesweeper.gameboard.Cell;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * A GameGUI osztály tesztelésére szolgál.
 */
public class GameGUITest {

    /**
     * Megnézzük, hogy meg van-e az összes gomb a játékhoz.
     *
     * @throws IOException Error ha nem sikerült új táblát létrehoznunk.
     */
    @Test
    public void createAllCellButtons() throws IOException {
        GameGUI testUi = new GameGUI(new Board(16,16,40));
        assertNotNull(testUi.getCellButtons());
        assertEquals(16*16,testUi.getCellButtons().size());
    }

    /**
     * Teszteset a tábla kinézeti frissítésére.
     *
     * @throws IOException Ha nem sikerülne a tábla létrehozása.
     */
    @Test
    public void updateBoard() throws IOException {
        GameGUI testUi = new GameGUI(new Board(16,16,40));
        Cell testcell1 = testUi.getBoard().getCellFromCoords(8,8);
        testcell1.setIsOpen(true);
        Cell testcell2 = testUi.getBoard().getCellFromCoords(10,10);
        testcell2.setHasFlag(true);

        testUi.updateBoard();

        if(!testcell1.getIsMine() && testcell1.getAdjacentMines() == 0)
            assertEquals(testUi.getImageContainer().get(0),testUi.getCellButtons().get(8 * 16 + 8).getIcon());

        if(testcell2.getIsMine() && testcell2.getIsOpen())
            assertEquals(testUi.getImageContainer().get(12),testUi.getCellButtons().get(10 * 16 + 10).getIcon());
        else
            assertEquals(testUi.getImageContainer().get(11),testUi.getCellButtons().get(10 * 16 + 10).getIcon());

    }

    /**
     * Az összes akna felderítését tesztelõ metódus.
     *
     * @throws IOException Ha nem sikerült létrehozni a táblát.
     */
    @Test
    public void revealAllMines() throws IOException {
        GameGUI testUi = new GameGUI(new Board(16,16,40));
        testUi.revealAllMines();
        for(int ii = 0; ii < testUi.getCellButtons().size(); ii++)
            if(testUi.getBoard().getCellFromIndex(ii).getIsMine() && testUi.getBoard().getCellFromIndex(ii).getIsOpen())
                assertEquals(testUi.getImageContainer().get(9),testUi.getCellButtons().get(ii).getIcon());

    }


    /**
     * Játék újraindítását teszteli.
     *
     * @throws IOException Ha nem sikerülne táblát létrehozni.
     */
    @Test
    public void restartGame() throws IOException {
        GameGUI testUi = new GameGUI(new Board(16,16,40));
        testUi.restartGame();
        assertNotNull(testUi);
    }
}