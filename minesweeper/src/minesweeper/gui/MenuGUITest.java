package minesweeper.gui;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * A MenuGUI oszt�ly tesztel�s�re szolg�l.
 */
public class MenuGUITest {

    /**
     * Ellen�rzi, hogy sikeres volt-e az �sszes gomb l�trehoz�sa.
     */
    @Test
    public void initButtons(){
        MenuGUI testUi = new MenuGUI();
        assertEquals(4,testUi.getButtons().size());
    }

}