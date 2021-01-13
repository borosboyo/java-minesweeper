package minesweeper.gameboard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A Cell oszt�ly tesztel�s�re szolg�l� oszt�ly.
 */
public class CellTest {

    /**
     * Akna be�ll�t�s�na tesztel�s�t v�gz� teszt.
     */
    @Test
    public void setIsMine() {
        Board testboard = new Board(10,10,0);
        Cell testcell = testboard.getCellFromCoords(5,5);

        int sum = 0;
        for(int ii = 0; ii < testboard.getNeighbours(testcell).size(); ii++)
            sum = sum + testboard.getNeighbours(testcell).get(ii).getAdjacentMines();

        assertNotNull(testboard.getNeighbours(testcell));
        assertEquals(8, sum);
    }
}