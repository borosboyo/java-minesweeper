package minesweeper.highscore;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A HighscoreData oszt�ly tesztel�s�re szolg�l.
 */
public class HighscoreDataTest {

    /**
     * Tesztelj�k, hogy helyesen kapunk-e adatot a t�bl�zatb�l egy adott oszlop �s sorindexen.
     */
    @Test
    public void getValueAt() {
        HighscoreData testData = new HighscoreData();
        testData.addHighscore("test1", 1, 16, 16, 40);
        assertEquals(1,testData.getValueAt(0,2));

        testData.addHighscore("test2", 2, 16, 16, 40);
        assertEquals("test2",testData.getValueAt(1,1));
    }
}