package minesweeper.gameboard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A Board oszt�ly tesztel�s�re szolg�l.
 * F�bb algoritmusokat tesztel, melyek elengedhetek egy j�t�kfolyamat lebonyol�t�s�ban.
 */
public class BoardTest {


    /**
     * Teszteli a f�ggv�nyt, mely megn�zi, hogy l�tezik-e a koordin�ta a p�ly�n.
     */
    @Test
    public void isValidCoord() {
        Board testboard = new Board(16,16,40);
        assertFalse(testboard.isValidCoord(20,0));
        assertTrue(testboard.isValidCoord(5,10));
    }

    /**
     * Teszteli a f�ggv�ny, mely megsz�molja az egy cella k�r�li z�szl�k sz�m�t.
     */
    @Test
    public void countNeighbouringFlags() {
        Board testboard = new Board(16,16,40);
        testboard.flagCellByCoords(8,8);
        testboard.flagCellByCoords(8,9);
        testboard.flagCellByCoords(10,8);
        testboard.flagCellByCoords(10,9);
        Cell testcell = testboard.getCellFromCoords(9,9);
        assertEquals(4,testboard.countNeighbouringFlags(testcell));
    }


    /**
     * Ellen�rzi, hogy megkapjuk-e helyesen egy cella szomsz�djait a met�dussal.
     */
    @Test
    public void getNeighbours() {
        Board testboard = new Board(16,16,40);
        Cell testcell1 = testboard.getCellFromCoords(0,0);
        Cell testcell2 = testboard.getCellFromCoords(8,8);
        assertNotNull(testboard.getNeighbours(testcell1));
        assertNotNull(testboard.getNeighbours(testcell2));
        assertEquals(3,testboard.getNeighbours(testcell1).size());
        assertEquals(8,testboard.getNeighbours(testcell2).size());
    }

    /**
     * Egy l�p�s lej�tsz�s�nak tesztel�s�re szolg�l� met�dus.
     */
    @Test
    public void playCell() {
        Board testboard = new Board(16,16,40);
        Cell testcell =  testboard.getCellFromCoords(8,8);
        testboard.playCell(testcell);
        assertTrue(testcell.getIsOpen());
        if(testcell.getIsMine())
            assertTrue(testboard.getGameover());

        if(testcell.getHasFlag())
            assertFalse(testcell.getIsOpen());

    }

    /**
     * Z�szl�z� met�dust tesztel� eset.
     */
    @Test
    public void flagCell() {
        Board testboard = new Board(16,16,40);
        testboard.flagCellByCoords(8,8);
        assertTrue(testboard.getCellFromCoords(8,8).getHasFlag());
    }


    /**
     * Tesztelj�k az egyszerre k�t gombbal nyomunk egy megfelel� cell�ra funkci�t.
     */
    @Test
    public void doubleClick() {
        Board testboard = new Board(16,16,40);
        Cell testcell =  testboard.getCellFromCoords(8,8);
        testboard.doubleClick(testcell);

        if(testcell.getAdjacentMines() == testboard.countNeighbouringFlags(testcell))
            assertTrue(testcell.getIsOpen());

    }

    /**
     * J�t�k megny�r�st ellen�rz� f�ggv�ny egy tesztesete.
     */
    @Test
    public void isGameWon() {
        Board testboard = new Board(16,16,1);
        if(!testboard.getCellFromCoords(10,10).getIsMine()){
            testboard.playCell(testboard.getCellFromCoords(10,10));
        }
        else{
            testboard.playCell(testboard.getCellFromCoords(11,11));
        }
        assertTrue(testboard.isGameWon());
    }
}