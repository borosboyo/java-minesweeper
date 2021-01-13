package minesweeper.gameboard;

import java.util.ArrayList;

/**
 * Egy mezõt jelképez a táblán. Ebbõl az osztályból származó példányokat tartalmaz egy Board osztály.
 * Maga az osztály lényegében szinte csak egy "adattároló", ami tartalmaz egy táblaegységhez tartozó paramétereket:
 * van-e akna a cellán, hány akna van körülötte, van-e zászló rajta, hol helyezkedik el a táblán, van-e rajta zászló, felderítette-e már a felhasználó.
 * A legfontosabb metódusa a setIsMine, mely során, ha egy cellát aknával látunk el, akkor a cella megnöveli a szomszédjainak az adjacentMines változóját egyel,
 * hiszen a szomszédok mostmár eggyel több aknával határosak.
 * Egy ilyen Cell osztály használata nagyban megkönnyíti a játékmenet során a teljes tábla kezelését, illetve algoritmusok futtatását.
 */
public class Cell {
    /**
     * A tábla, ami tartalmmazza a cellát.
     */
    Board container;

    /**
     * Megmutatja, hogy van-e zászló a cellán.
     */
    private boolean hasFlag;

    /**
     * Megmutatja, hogy nyitva van-e a cella.
     */
    private boolean isOpen;

    /**
     * A cellával szomszédos aknás mezõk száma.
     */
    private int adjacentMines;

    /**
     * A cella oszlopindexe
     */
    private int colID;

    /**
     * A cella sorindexe
     */
    private int rowID;

    /**
     * Megmutatja, hogy aknás mezõrõl beszélünk-e.
     */
    private boolean isMine;

    /**
     * A cella konstruktora.
     * Alapértelmezetten nincs rajta se akna, se zászló, és nincs felfedve.
     *
     * @param newContainer A cella konténer táblája.
     * @param newColID A cella oszlopindexe.
     * @param newRowID A cella sorindexe.
     */
    public Cell(Board newContainer, int newColID, int newRowID){
        hasFlag = false;
        isOpen = false;
        isMine = false;
        adjacentMines = 0;
        colID = newColID;
        rowID = newRowID;
        container = newContainer;
    }


    /**
     * Megnöveli eggyel a cella által ismert szomszédos aknák számát.
     */
    public void increaseAdjacentMines() { adjacentMines++;}


    /**
     * Lerak egy aknát a cellára, továbbá a lekéri az adott cella szomszédjait,
     * akiknek megnöveli a szomszédos aknaszámát eggyel.
     */
    public void setIsMine(){
        if(!isMine){
            isMine = true;
            ArrayList<Cell> neighbours = container.getNeighbours(this);
            for(Cell c : neighbours){
                c.increaseAdjacentMines();
            }
        }
    }

    /**
     * Feltár vagy lefed egy cellát.
     *
     * @param newIsOpen Zárt, vagy nyitott legyen a cella
     */
    public void setIsOpen(boolean newIsOpen) { isOpen = newIsOpen; }


    /**
     * Lerak vagy felvesz egy zászlót a celláról.
     *
     * @param newHasFlag Legyen-e zászló a cellán vagy sem.
     */
    public void setHasFlag(boolean newHasFlag){ hasFlag = newHasFlag; }

    /**
     * A cella oszlopindexének gettere.
     *
     * @return A cella oszlopindexe.
     */
    public int getColID(){
        return colID;
    }

    /**
     * A cella sorindexének gettere.
     *
     * @return A cella sorindexe.
     */
    public int getRowID(){
        return rowID;
    }

    /**
     * Visszaadja, hogy a mezõ tartalmaz-e aknát vagy sem.
     *
     * @return Van-e akna a mezõn, vagy sem.
     */
    public boolean getIsMine(){
        return isMine;
    }

    /**
     * A szomszédos aknák számának gettere.
     *
     * @return Szomszédos aknák száma a cella körül.
     */
    public int getAdjacentMines(){ return adjacentMines; }

    /**
     * Visszadja, hogy a mezõn van-e zászló.
     *
     * @return Van-e zászló a mezõn, vagy sem.
     */
    public boolean getHasFlag(){ return hasFlag; }

    /**
     * Visszaadja, hogy nyitott vagy fedett-e a mezõ.
     *
     * @return Nyitott vagy fedett-e a cella.
     */
    public boolean getIsOpen() { return isOpen; }


}
