package minesweeper.highscore;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Az adatmodell egy el�re megadott neh�zs�gi fokozat rekordjainak kezel�s�re szolg�l. A HighScoreData oszt�llyal ellent�tben,
 * ez csak 3 oszloppal rendelkezik, hiszen egy el�re megadott fokozatn�l a p�lya param�terei adottak, ez�rt azok megjelen�t�se teljesen irrevel�ns,
 * mert minden rekordn�l ugyan azok lenn�nek
 * Term�szetesen ez preferencia, hiszen ha a HighScoreData oszt�ly haszn�ln�nk a fentebb eml�tett fokozatokra,
 * akkor minden p�lyaparam�tert l�tn�nk, pontosabban minden sorban ugyan azt.
 * A modellnek k�sz�nhet�en k�nnyen megjelen�thet� a t�bl�zat, tov�bb� kezelhet� az esem�ny,
 * amikor a felhaszn�l� k�l�n�b�z� neh�zs�geket v�laszt a HighScoreUI JComboBox-�ban.
 * Ilyen t�pus� adatmodellekbe rendezi a HighScoreUI a rekordokat, melyeket az �sszes adatb�l szort�roz ki.
 */
public class SetDifficultyData extends AbstractTableModel {

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
     * @return A rekordok oszlopainak sz�ma: Ranking, N�v, Id�.
     */
    @Override
    public int getColumnCount() {
        return 3;
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
            default -> highscore.getTime();
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
            default -> "Time";
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
     * T�rli a rekordokat t�rol� list�t.
     */
    public void clear(){
        highscores.clear();
    }

    
}
