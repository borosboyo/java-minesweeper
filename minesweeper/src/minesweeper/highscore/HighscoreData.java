package minesweeper.highscore;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Olyan adatmodellt jelképez, mely tartalmaz egy rekordokból álló listát. Hat oszloppal rendelkezik,
 * hiszen az összes rekordról szeretnénk tudni, hogy milyen paraméterekkel ment végbe a játék.
 * Különbözõ adatmodellek létrehozásával, és a paraméterekre vonatkozó feltételekkel vagy kikötésekkel szortírozni tudjuk a rekordokat nehézségi fokozat szerint.
 * A JTable és JScrollPane használata során nagyon kényelmesessé válik a különbözõ nehézségi fokozatok rekordjainak listázása,
 * hiszen minden nehézséghez külön adatmodell tartozik, melyben vannak a nehézséghez tartozó rekordok.
 * Ezt az adatmodellt az összes adat és az egyéni rekordok kezelésére szolgál, hiszen egy pályához tartalmazó minden paramétert tartalmaz.
 */
public class HighscoreData extends AbstractTableModel{


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
     * @return A rekordok oszlopainak száma: Ranking, Név, Idõ, Oszlopszám, Sorszám, Aknaszám
     */
    @Override
    public int getColumnCount() {
        return 6;
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
            case 2 -> highscore.getTime();
            case 3 -> highscore.getCols();
            case 4 -> highscore.getRows();
            default -> highscore.getMines();
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
            case 2 -> "Time";
            case 3 -> "Columns";
            case 4 -> "Rows";
            default -> "Mines";
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
     * Hozzáad egy új rekordot a már meglévõekhez.
     *
     * @param name A rekordfelállító neve.
     * @param time A rekord ideje.
     * @param cols A rekord táblájának oszlopszáma.
     * @param rows A rekord táblájának sorszáma.
     * @param mines A rekord táblájának aknaszáma.
     */
    public void addHighscore(String name, Integer time, Integer cols, Integer rows, Integer mines){
        highscores.add(new Highscore(name, time, cols, rows, mines));
        fireTableRowsInserted(0, highscores.size()-1);
    }


    /**
     * Törli a rekordokat tároló listát.
     */
    public void clear(){
        highscores.clear();
    }


}
