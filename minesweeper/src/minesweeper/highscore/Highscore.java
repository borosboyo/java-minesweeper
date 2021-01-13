package minesweeper.highscore;

import java.io.Serializable;


/**
 * Az osztály egy nyert játékot, vagyis egy rekordot jelképez. Ezeket a rekordokat (legjobb eredményekhez tartozó), objektumokat,
 * sorosítjuk, vagyis konvertáljuk át bináris formába, melyeket késõbb visszaolvasunk.
 * Implementálja a Comparable interfészt is, hogy össze tudjunk hasonlítani két rekordot, idõ szerint, hogy sorbarendezzük õket.
 * Ezekkel a tervezõi döntésekkel kényelmessé válik a különöbözõ rekordok vizsgálata, szervezése.
 */
public class Highscore implements Serializable, Comparable<Highscore>{

    /**
     * A rekordfelállító neve.
     */
    private String name;

    /**
     * A rekordidõ.
     */
    private int time;

    /**
     * A tábla oszlopainak száma, melyben a rekordot állították.
     */
    private int cols;

    /**
     * A tábla sorainak száma, melyben a rekordot állították.
     */
    private int rows;

    /**
     * A táblában szereplõ aknák száma.
     */
    private int mines;


    /**
     * A rekordot felállító ember nevének gettere.
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
     * @return Annak a táblának a oszlopainak a száma, melyben a rekordot felállították.
     */
    public Integer getCols() {
        return cols;
    }


    /**
     * @return Annak a táblának a sorainak a száma, melyben a rekordot felállították.
     */
    public Integer getRows() {
        return rows;
    }


    /**
     * @return Abban a táblában szereplõ aknák száma, melyben a rekordot felállították.
     */
    public Integer getMines() { return mines; }


    /**
     * A rekord konstruktora.
     *
     * @param name A rekordfelállító neve.
     * @param time A rekord ideje.
     * @param cols A rekord táblájának oszlopszáma.
     * @param rows A rekord táblájának sorszáma.
     * @param mines A rekord táblájának aknaszáma.
     */
    public Highscore(String name, Integer time, Integer cols, Integer rows, Integer mines) {
        this.name = name;
        this.time = time;
        this.cols = cols;
        this.rows = rows;
        this.mines = mines;
    }


    /**
     * Összehasonlít két rekordot idõ szerint.
     * Szükséges késõbb, amikor a Collections.sortot hívunk meg, hogy idõ szerint felállítsuk a rekordtáblázatot.
     *
     * @param comparesto A rekord, amihez hasonlítani akarunk.
     * @return A hasonlítandó idõbõl kivont hasonlított idõ.
     */
    @Override
    public int compareTo(Highscore comparesto) {
        int comparage = comparesto.getTime();
        return this.time-comparage;
    }
}
