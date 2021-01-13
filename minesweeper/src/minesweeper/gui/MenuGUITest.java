package minesweeper.gui;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * A MenuGUI osztály tesztelésére szolgál.
 */
public class MenuGUITest {

    /**
     * Ellenõrzi, hogy sikeres volt-e az összes gomb létrehozása.
     */
    @Test
    public void initButtons(){
        MenuGUI testUi = new MenuGUI();
        assertEquals(4,testUi.getButtons().size());
    }

}