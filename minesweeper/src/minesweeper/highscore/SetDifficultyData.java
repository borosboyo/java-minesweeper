package minesweeper.highscore;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Az adatmodell egy elõre megadott nehézségi fokozat rekordjainak kezelésére szolgál. A HighScoreData osztállyal ellentétben,
 * ez csak 3 oszloppal rendelkezik, hiszen egy elõre megadott fokozatnál a pálya paraméterei adottak, ezért azok megjelenítése teljesen irreveláns,
 * mert minden rekordnál ugyan azok lennének
 * Természetesen ez preferencia, hiszen ha a HighScoreData osztály használnánk a fentebb említett fokozatokra,
 * akkor minden pályaparamétert látnánk, pontosabban minden sorban ugyan azt.
 * A modellnek köszönhetõen könnyen megjeleníthetõ a táblázat, továbbá kezelhetõ az esemény,
 * amikor a felhasználó különöbözõ nehézségeket választ a HighScoreUI JComboBox-ában.
 * Ilyen típusú adatmodellekbe rendezi a HighScoreUI a rekordokat, melyeket az összes adatból szortíroz ki.
 */
public class SetDifficultyData extends AbstractTableModel {

    /**
     * A rekordokat tartalmazó lista.
     */
    List<Highscore> highscores = new ArrayList<Highscore>();

    /**
     * A rekordok számának gettere.
     *
     * @return A rekordok száma.
     */
    @Override
    public int getRowCount() {
        return highscores.size();
    }

    /**
     * A rekordok oszlopainak számának gettere.
     *
     * @return A rekordok oszlopainak száma: Ranking, Név, Idõ.
     */
    @Override
    public int getColumnCount() {
        return 3;
    }


    /**
     * A táblázat egy bizonyos sor és oszlopindexén található adat gettere.
     *
     * @param rowIndex A lekérdezendõ rekord sorindexe.
     * @param columnIndex A lekérdezendõ rekord oszlopindexe.
     * @return A két index helyén található adat.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Highscore highscore = highscores.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> highscore.getName();
            default -> highscore.getTime();
        };
    }

    /**
     * A táblázat egy oszlopnevének gettere.
     *
     * @param columnIndex Oszlopindex, melynek a nevét szeretnénk tudni.
     * @return  Az oszlop neve.
     */
    @Override
    public String getColumnName(int columnIndex){
        return switch (columnIndex) {
            case 0 -> "Rank";
            case 1 -> "Name";
            default -> "Time";
        };
    }

    /**
     * Egy adott oszlop osztályának gettere.
     *
     * @param columnIndex A táblázat oszlopindexe, ahol szeretnénk tudni, milyen osztályok találhatóak.
     * @return Az oszlopban található osztálytípus
     */
    @Override
    public Class<?> getColumnClass(int columnIndex){
        if(columnIndex == 1) return String.class;
        else return Integer.class;
    }


    /**
     * Törli a rekordokat tároló listát.
     */
    public void clear(){
        highscores.clear();
    }

    
}
