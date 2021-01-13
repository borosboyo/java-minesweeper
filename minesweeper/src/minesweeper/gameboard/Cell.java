package minesweeper.gameboard;

import java.util.ArrayList;

/**
 * Egy mez�t jelk�pez a t�bl�n. Ebb�l az oszt�lyb�l sz�rmaz� p�ld�nyokat tartalmaz egy Board oszt�ly.
 * Maga az oszt�ly l�nyeg�ben szinte csak egy "adatt�rol�", ami tartalmaz egy t�blaegys�ghez tartoz� param�tereket:
 * van-e akna a cell�n, h�ny akna van k�r�l�tte, van-e z�szl� rajta, hol helyezkedik el a t�bl�n, van-e rajta z�szl�, felder�tette-e m�r a felhaszn�l�.
 * A legfontosabb met�dusa a setIsMine, mely sor�n, ha egy cell�t akn�val l�tunk el, akkor a cella megn�veli a szomsz�djainak az adjacentMines v�ltoz�j�t egyel,
 * hiszen a szomsz�dok mostm�r eggyel t�bb akn�val hat�rosak.
 * Egy ilyen Cell oszt�ly haszn�lata nagyban megk�nny�ti a j�t�kmenet sor�n a teljes t�bla kezel�s�t, illetve algoritmusok futtat�s�t.
 */
public class Cell {
    /**
     * A t�bla, ami tartalmmazza a cell�t.
     */
    Board container;

    /**
     * Megmutatja, hogy van-e z�szl� a cell�n.
     */
    private boolean hasFlag;

    /**
     * Megmutatja, hogy nyitva van-e a cella.
     */
    private boolean isOpen;

    /**
     * A cell�val szomsz�dos akn�s mez�k sz�ma.
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
     * Megmutatja, hogy akn�s mez�r�l besz�l�nk-e.
     */
    private boolean isMine;

    /**
     * A cella konstruktora.
     * Alap�rtelmezetten nincs rajta se akna, se z�szl�, �s nincs felfedve.
     *
     * @param newContainer A cella kont�ner t�bl�ja.
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
     * Megn�veli eggyel a cella �ltal ismert szomsz�dos akn�k sz�m�t.
     */
    public void increaseAdjacentMines() { adjacentMines++;}


    /**
     * Lerak egy akn�t a cell�ra, tov�bb� a lek�ri az adott cella szomsz�djait,
     * akiknek megn�veli a szomsz�dos aknasz�m�t eggyel.
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
     * Felt�r vagy lefed egy cell�t.
     *
     * @param newIsOpen Z�rt, vagy nyitott legyen a cella
     */
    public void setIsOpen(boolean newIsOpen) { isOpen = newIsOpen; }


    /**
     * Lerak vagy felvesz egy z�szl�t a cell�r�l.
     *
     * @param newHasFlag Legyen-e z�szl� a cell�n vagy sem.
     */
    public void setHasFlag(boolean newHasFlag){ hasFlag = newHasFlag; }

    /**
     * A cella oszlopindex�nek gettere.
     *
     * @return A cella oszlopindexe.
     */
    public int getColID(){
        return colID;
    }

    /**
     * A cella sorindex�nek gettere.
     *
     * @return A cella sorindexe.
     */
    public int getRowID(){
        return rowID;
    }

    /**
     * Visszaadja, hogy a mez� tartalmaz-e akn�t vagy sem.
     *
     * @return Van-e akna a mez�n, vagy sem.
     */
    public boolean getIsMine(){
        return isMine;
    }

    /**
     * A szomsz�dos akn�k sz�m�nak gettere.
     *
     * @return Szomsz�dos akn�k sz�ma a cella k�r�l.
     */
    public int getAdjacentMines(){ return adjacentMines; }

    /**
     * Visszadja, hogy a mez�n van-e z�szl�.
     *
     * @return Van-e z�szl� a mez�n, vagy sem.
     */
    public boolean getHasFlag(){ return hasFlag; }

    /**
     * Visszaadja, hogy nyitott vagy fedett-e a mez�.
     *
     * @return Nyitott vagy fedett-e a cella.
     */
    public boolean getIsOpen() { return isOpen; }


}
