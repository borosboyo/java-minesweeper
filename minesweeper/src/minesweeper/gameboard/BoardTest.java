package minesweeper.gameboard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A Board osztály tesztelésére szolgál.
 * Fõbb algoritmusokat tesztel, melyek elengedhetek egy játékfolyamat lebonyolításában.
 */
public class BoardTest {


    /**
     * Teszteli a függvényt, mely megnézi, hogy létezik-e a koordináta a pályán.
     */
    @Test
    public void isValidCoord() {
        Board testboard = new Board(16,16,40);
        assertFalse(testboard.isValidCoord(20,0));
        assertTrue(testboard.isValidCoord(5,10));
    }

    /**
     * Teszteli a függvény, mely megszámolja az egy cella körüli zászlók számát.
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
     * Ellenõrzi, hogy megkapjuk-e helyesen egy cella szomszédjait a metódussal.
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
     * Egy lépés lejátszásának tesztelésére szolgáló metódus.
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
     * Zászlózó metódust tesztelõ eset.
     */
    @Test
    public void flagCell() {
        Board testboard = new Board(16,16,40);
        testboard.flagCellByCoords(8,8);
        assertTrue(testboard.getCellFromCoords(8,8).getHasFlag());
    }


    /**
     * Teszteljük az egyszerre két gombbal nyomunk egy megfelelõ cellára funkciót.
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
     * Játék megnyérést ellenörzõ függvény egy tesztesete.
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