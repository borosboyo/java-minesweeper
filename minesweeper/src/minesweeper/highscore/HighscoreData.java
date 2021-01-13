package minesweeper.highscore;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Olyan adatmodellt jelk�pez, mely tartalmaz egy rekordokb�l �ll� list�t. Hat oszloppal rendelkezik,
 * hiszen az �sszes rekordr�l szeretn�nk tudni, hogy milyen param�terekkel ment v�gbe a j�t�k.
 * K�l�nb�z� adatmodellek l�trehoz�s�val, �s a param�terekre vonatkoz� felt�telekkel vagy kik�t�sekkel szort�rozni tudjuk a rekordokat neh�zs�gi fokozat szerint.
 * A JTable �s JScrollPane haszn�lata sor�n nagyon k�nyelmesess� v�lik a k�l�nb�z� neh�zs�gi fokozatok rekordjainak list�z�sa,
 * hiszen minden neh�zs�ghez k�l�n adatmodell tartozik, melyben vannak a neh�zs�ghez tartoz� rekordok.
 * Ezt az adatmodellt az �sszes adat �s az egy�ni rekordok kezel�s�re szolg�l, hiszen egy p�ly�hoz tartalmaz� minden param�tert tartalmaz.
 */
public class HighscoreData extends AbstractTableModel{


    /**
     * A rekordokat tartalmaz� lista.
     */
    List<Highscore> highscores = new ArrayList<Highscore>();


    /**
     * A rekordok sz�m�nak gettere.
     *
     * @return A rekordok sz�ma.
     */
    @Override
    public int getRowCount() {
        return highscores.size();
    }


    /**
     * A rekordok oszlopainak sz�m�nak gettere.
     *
     * @return A rekordok oszlopainak sz�ma: Ranking, N�v, Id�, Oszlopsz�m, Sorsz�m, Aknasz�m
     */
    @Override
    public int getColumnCount() {
        return 6;
    }


    /**
     * A t�bl�zat egy bizonyos sor �s oszlopindex�n tal�lhat� adat gettere.
     *
     * @param rowIndex A lek�rdezend� rekord sorindexe.
     * @param columnIndex A lek�rdezend� rekord oszlopindexe.
     * @return A k�t index hely�n tal�lhat� adat.
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
     * A t�bl�zat egy oszlopnev�nek gettere.
     *
     * @param columnIndex Oszlopindex, melynek a nev�t szeretn�nk tudni.
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
     * Egy adott oszlop oszt�ly�nak gettere.
     *
     * @param columnIndex A t�bl�zat oszlopindexe, ahol szeretn�nk tudni, milyen oszt�lyok tal�lhat�ak.
     * @return Az oszlopban tal�lhat� oszt�lyt�pus
     */
    @Override
    public Class<?> getColumnClass(int columnIndex){
        if(columnIndex == 1) return String.class;
        else return Integer.class;
    }

    /**
     * Hozz�ad egy �j rekordot a m�r megl�v�ekhez.
     *
     * @param name A rekordfel�ll�t� neve.
     * @param time A rekord ideje.
     * @param cols A rekord t�bl�j�nak oszlopsz�ma.
     * @param rows A rekord t�bl�j�nak sorsz�ma.
     * @param mines A rekord t�bl�j�nak aknasz�ma.
     */
    public void addHighscore(String name, Integer time, Integer cols, Integer rows, Integer mines){
        highscores.add(new Highscore(name, time, cols, rows, mines));
        fireTableRowsInserted(0, highscores.size()-1);
    }


    /**
     * T�rli a rekordokat t�rol� list�t.
     */
    public void clear(){
        highscores.clear();
    }


}
