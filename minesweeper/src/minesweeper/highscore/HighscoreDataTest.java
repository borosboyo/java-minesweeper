package minesweeper.highscore;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A HighscoreData osztály tesztelésére szolgál.
 */
public class HighscoreDataTest {

    /**
     * Teszteljük, hogy helyesen kapunk-e adatot a táblázatból egy adott oszlop és sorindexen.
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