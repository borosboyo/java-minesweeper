package minesweeper.highscore;

import java.io.Serializable;


/**
 * Az oszt�ly egy nyert j�t�kot, vagyis egy rekordot jelk�pez. Ezeket a rekordokat (legjobb eredm�nyekhez tartoz�), objektumokat,
 * soros�tjuk, vagyis konvert�ljuk �t bin�ris form�ba, melyeket k�s�bb visszaolvasunk.
 * Implement�lja a Comparable interf�szt is, hogy �ssze tudjunk hasonl�tani k�t rekordot, id� szerint, hogy sorbarendezz�k �ket.
 * Ezekkel a tervez�i d�nt�sekkel k�nyelmess� v�lik a k�l�n�b�z� rekordok vizsg�lata, szervez�se.
 */
public class Highscore implements Serializable, Comparable<Highscore>{

    /**
     * A rekordfel�ll�t� neve.
     */
    private String name;

    /**
     * A rekordid�.
     */
    private int time;

    /**
     * A t�bla oszlopainak sz�ma, melyben a rekordot �ll�tott�k.
     */
    private int cols;

    /**
     * A t�bla sorainak sz�ma, melyben a rekordot �ll�tott�k.
     */
    private int rows;

    /**
     * A t�bl�ban szerepl� akn�k sz�ma.
     */
    private int mines;


    /**
     * A rekordot fel�ll�t� ember nev�nek gettere.
     * @return A rekord neve,
     */
    public String getName() {
        return name;
    }


    /**
     * @return Az adott rekord ideje.
     */
    public Integer getTime() {
        return time;
    }


    /**
     * @return Annak a t�bl�nak a oszlopainak a sz�ma, melyben a rekordot fel�ll�tott�k.
     */
    public Integer getCols() {
        return cols;
    }


    /**
     * @return Annak a t�bl�nak a sorainak a sz�ma, melyben a rekordot fel�ll�tott�k.
     */
    public Integer getRows() {
        return rows;
    }


    /**
     * @return Abban a t�bl�ban szerepl� akn�k sz�ma, melyben a rekordot fel�ll�tott�k.
     */
    public Integer getMines() { return mines; }


    /**
     * A rekord konstruktora.
     *
     * @param name A rekordfel�ll�t� neve.
     * @param time A rekord ideje.
     * @param cols A rekord t�bl�j�nak oszlopsz�ma.
     * @param rows A rekord t�bl�j�nak sorsz�ma.
     * @param mines A rekord t�bl�j�nak aknasz�ma.
     */
    public Highscore(String name, Integer time, Integer cols, Integer rows, Integer mines) {
        this.name = name;
        this.time = time;
        this.cols = cols;
        this.rows = rows;
        this.mines = mines;
    }


    /**
     * �sszehasonl�t k�t rekordot id� szerint.
     * Sz�ks�ges k�s�bb, amikor a Collections.sortot h�vunk meg, hogy id� szerint fel�ll�tsuk a rekordt�bl�zatot.
     *
     * @param comparesto A rekord, amihez hasonl�tani akarunk.
     * @return A hasonl�tand� id�b�l kivont hasonl�tott id�.
     */
    @Override
    public int compareTo(Highscore comparesto) {
        int comparage = comparesto.getTime();
        return this.time-comparage;
    }
}
