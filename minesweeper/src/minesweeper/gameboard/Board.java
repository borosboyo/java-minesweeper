package minesweeper.gameboard;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Maga a t�nyleges aknakeres� j�t�kt�rnek a kezel�je. Tartalmazza a p�lya param�tereit
 * (oszlopok,sorok sz�ma, akn�k sz�ma, v�ge van-e a j�t�knak, lerakott z�szl�k), tov�bb� a cell�kat.
 * Lekezeli, ha egy cell�val valamilyen interakci� t�rt�nik. Az oszt�ly a teljes program gerince: az eg�sz men�rendszer-e k�r� �p�l fel.
 * A j�t�kmenet f� algoritmusait is mag�ba foglalja: egy cella �s szomsz�dainak rekurz�v megjelen�t�se vagy
 * egy cella szomsz�dainak meghat�roz�sa, majd a rajtuk v�gzett m�veletek.
 * Egy list�ban t�rolja az �sszes cell�t, hogy azok k�nnyen kezelhet�ek legyen.
 * Lehet�v� teszi, hogy egy cell�t koordin�t�i, vagy ak�r a list�ban szerepl� indexe alapj�n meghat�rozzunk, vagy m�veletet v�gezz�nk rajta.
 */
public class Board {

    /**
     * Oszlopok sz�ma.
     */
    private int columns;

    /**
     * Sorok sz�ma.
     */
    private int rows;

    /**
     * Akn�k sz�ma a t�bl�ban.
     */
    private int mines;

    /**
     * Jelzi, hogy v�get�rt-e m�r a j�t�k.
     */
    private boolean gameover;

    /**
     * A cell�kat tartalmaz� lista.
     */
    private ArrayList<Cell> mineField;

    /**
     * A lerakott z�szl�k sz�ma.
     */
    private int flagsPlaced;


    /**
     * A j�t�kt�bla konstruktora. Megh�vja a t�bla inicializ�l�s�rt felel�s met�dust.
     *
     * @param newColumns Az �j t�bla oszlopainak sz�ma.
     * @param newRows   Az �j t�bla sorainak sz�ma.
     * @param newMines Az �j t�bl�ban tal�lhat� akn�k sz�ma.
     */
    public Board(int newColumns, int newRows, int newMines) {
        columns = newColumns;
        rows = newRows;
        mines = newMines;
        gameover = false;
        flagsPlaced = 0;
        mineField = new ArrayList<Cell>();
        mineFieldInitialize();
    }


    /**
     * L�trehozza a t�bl�ban tal�lhat� cell�kat, majd megh�vja az aknalehelyez� f�ggv�nyt.
     */
    public void mineFieldInitialize(){
        for(int ii = 0; ii < rows; ii++){
            for(int jj = 0; jj < columns; jj++){
                mineField.add(new Cell(this, jj, ii));
            }
        }
        initMines();
    }


    /**
     * V�letlenszer�en lehelyezi az el�re meghat�rozott sz�m� akn�t.
     */
    public void initMines(){
        int minesToPlace = mines;
        while(minesToPlace != 0){
            int randomIndex = ThreadLocalRandom.current().nextInt(0, rows * columns);
            if(!mineField.get(randomIndex).getIsMine()){
                mineField.get(randomIndex).setIsMine();
                minesToPlace--;
            }
        }
    }


    /**
     * A met�dus meghat�rozza, hogy az adott koordin�tap�r megtal�lhat�-e a t�bl�n.
     *
     * @param colID A cella oszlopindexe.
     * @param rowID A cella sorindexe.
     * @return Igaz/Hamis att�l f�gg�en, hogy benne tal�lhat�-e a t�bl�ban az aktu�lis koordin�ta.
     */
    public boolean isValidCoord(int colID, int rowID){
        return (colID >= 0 && rowID >= 0 && colID < rows && rowID < columns);
    }


    /**
     * Visszaadja egy cella �sszes szomsz�dj�t egy ArrayList-ben.
     *
     * @param target A cella, melynek keress�k a szomsz�djait.
     * @return  A lista, mely tartalmazza a target cella szomsz�djait.
     */
    public ArrayList<Cell> getNeighbours(Cell target){
        ArrayList<Cell> neighbours =  new ArrayList<Cell>();

        //Szomsz�dos cell�k x �s y koordin�t�inak k�l�nbs�ge a target cell�t�l.
        int[] dimensionsX = { -1, 0, 1, -1, 1, -1, 0, 1};
        int[] dimensionsY =  {-1, -1, -1, 0, 0, 1, 1, 1};
        
        for(int ii = 0; ii < dimensionsX.length; ii++){
            Cell neighbour = getCellFromCoords(target.getColID() + dimensionsX[ii], target.getRowID() + dimensionsY[ii]);
            if(neighbour != null)
                neighbours.add(neighbour);
        }

        return neighbours;
    }


    /**
     * A cell�k sz�m�nak gettere.
     *
     * @return A cell�k sz�ma a t�bl�n.
     */
    public int getNumberOfCells(){
        return columns * rows;
    }

    /**
     * A t�bla oszlopsz�m�nak gettere.
     *
     * @return A t�bla oszlopainak sz�ma.
     */
    public int getColumns(){ return columns; }

    /**
     * A t�bla sorsz�m�nak gettere.
     *
     * @return A t�bla sorainak sz�ma.
     */
    public int getRows(){ return rows; }


    /**
     * A t�bl�n lerakott z�szl�k sz�m�nak gettere.
     *
     * @return A lerakott z�szl�k sz�ma.
     */
    public int getFlagsPlaced() { return flagsPlaced; }

    /**
     * A t�bl�n tal�lhat� akn�k sz�m�nak gettere.
     *
     * @return Az akn�k sz�ma.
     */
    public int getMines() { return mines; }

    /**
     * A j�t�k v�g�nek gettere.
     *
     * @return Igaz/Hamis: V�ge van-e a j�t�knak vagy sem.
     */
    public boolean getGameover(){ return gameover; }


    /**
     * Az ArrayListben tal�lhat� egy keresett cella listaindex�nek gettere.
     *
     * @param target A cella, melynek index�re vagyunk k�v�ncsiak.
     * @return A target cella indexe.
     */
    public int getCellIndex(Cell target){
        return mineField.indexOf(target);
    }


    /**
     * Visszaadja egy cella index�t az ArrayList-ben, a t�bl�n tal�lhat� koordin�t�ib�l.
     *
     * @param colID A cella oszlopindexe.
     * @param rowID A cella sorindexe.
     * @return A keresett cella indexe-
     */
    public int getCellIndexFromCoords(int colID, int rowID){
        return rowID * columns + colID;
    }

    /**
     * Visszaad egy cell�t a list�b�l egy index alapj�n.
     *
     * @param cellIndex A cella indexe.
     * @return Maga a cella
     */
    public Cell getCellFromIndex(int cellIndex){ return mineField.get(cellIndex); }

    /**
     * A met�dus seg�s�g�vel megkaphatunk egy cell�t a koordin�t�ib�l.
     *
     * @param colID A cella oszlopindexe.
     * @param rowID A cella sorindexe.
     * @return A keresett cella.
     */
    public Cell getCellFromCoords(int colID, int rowID) {
        if (isValidCoord(rowID, colID))
            return mineField.get(getCellIndexFromCoords(colID, rowID));
        else
            return null;
    }


    /**
     * A met�dus v�gigmegy az eg�sz aknamez�n, �s ha a felhaszn�l� felfedte az �sszes aknamentes mez�t,
     * akkor megnyerte a j�t�kot.
     *
     * @return Igaz/Hamis az alapj�n, hogy a j�t�kos felfedte-e az �sszes aknan�lk�li cell�t, vagy sem.
     */
    public boolean isGameWon(){
        for (Cell cell : mineField) {
            if (!cell.getIsOpen() && !cell.getIsMine()) return false;
        }
        return true;
    }

    /**
     * Megsz�molja, hogy egy adott cella k�r�l h�ny lerakott z�szl� tal�lhat�.
     *
     * @param target A cella, mellyel szomsz�dos lerakott z�szl�kat keres�nk.
     * @return A szomsz�dos z�szl�k sz�ma.
     */
    public int countNeighbouringFlags(Cell target){
        int neighbouringFlags = 0;
        ArrayList<Cell> neighbours = getNeighbours(target);

        for (Cell cell : neighbours)
            if (cell.getHasFlag())
                neighbouringFlags++;

        return neighbouringFlags;
    }


    /**
     * Megh�vja a cell�t megj�tsz� met�dust a koordin�t�i alapj�n.
     *
     * @param targetColID A j�tszand� cella oszlopindexe.
     * @param targetRowID A j�tszand� cella sorindexe.
     * @return Igaz/Hamis az alapj�n, hogy siker�lt-e megj�tszani a cell�t.
     */
    public boolean playCellByCoords(int targetColID, int targetRowID){
        Cell target = mineField.get(getCellIndexFromCoords(targetColID, targetRowID));
        return playCell(target);
    }


    /**
     * A kattint�st reprezent�lja egy cell�n.
     * Ha egy aknamentes mez�t tal�ltunk, azonban a k�zvetlen k�rnyezet�ben tal�lhat� akna,
     * akkor a mez�n bel�l megjelenik egy sz�m (minimum: 1, maximum 8), amely a cella melletti akn�k sz�m�t jelzi.
     * M�sik lehet�s�g, ha olyan aknamentes mez�re bukkanunk, amelynek a k�rnyezete sem tartalmaz akn�t,
     * ekkor az adott mez�vel hat�ros cell�k mindegyike felt�rul("megj�tsz�dik"),
     * tov�bb� az �gy felt�rul� aknamentes �szigettel� hat�ros cell�k is felt�rulnak(rekurz�van), ha nincs alattuk akna.
     * Ha akn�s mez�re lett megj�tszva, akkor v�ge a j�t�knak.
     *
     * @param target A megj�tszand� cella.
     * @return Siker�lt-e megj�tszani a cell�t, vagy sem.
     */
    public boolean playCell(Cell target){
        if(target != null) {
            if (!target.getHasFlag() && !gameover) {
                if (!target.getIsOpen()) {
                    target.setIsOpen(true);

                    if (isGameWon()) {
                        gameover = true;
                        return true;
                    }

                    if (target.getIsMine()) {
                        gameover = true;
                    } else if (target.getAdjacentMines() == 0) {
                        ArrayList<Cell> neighbours = getNeighbours(target);
                        for (Cell cell : neighbours) {
                            playCell(cell);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Mindk�t eg�rgombbal kattint koordin�ta alapj�n egy target cell�ra.
     *
     * @param targetColID A cella oszlopindexe.
     * @param targetRowID A cella sorindexe.
     */
    public void doubleClickByCoords(int targetColID, int targetRowID){
        Cell target = mineField.get(getCellIndexFromCoords(targetColID, targetRowID));
        doubleClick(target);
    }

    /**
     * Ha ugyanannyi z�szl� van egy cella k�r�l, ah�ny akna van k�r�l�tte,
     * �s mindk�t eg�rgombbal egyszerre kattintunk r�, akkor megj�tssza az adott cell�nak a szomsz�djait.
     *
     * @param target A dupl�n kattintand� target cella.
     */
    public void doubleClick(Cell target){
        ArrayList<Cell> neighbours = getNeighbours(target);
        if (countNeighbouringFlags(target) == target.getAdjacentMines())
            for (Cell neighbour : neighbours)
                if (!neighbour.getIsOpen() && !neighbour.getHasFlag())
                    playCell(neighbour);
    }


    /**
     * Megh�vja a z�szl�z� met�dust az adott cell�ra, annak koordin�t�i alapj�n.
     *
     * @param targetColID A cella oszlopindexe.
     * @param targetRowID A cella sorindexe.
     * @return Siker�lt-e megz�szl�zni a cell�t
     */
    public boolean flagCellByCoords(int targetColID, int targetRowID){
        Cell target = mineField.get(getCellIndexFromCoords(targetColID, targetRowID));
        return flagCell(target);
    }

    /**
     *
     *
     * @param target A cella, amire z�szl�t akarunk helyezni.
     * @return Siker�lt-e megz�szl�zni a cell�t vagy sem.
     */
    public boolean flagCell(Cell target){
        if(target != null){
            if(!target.getIsOpen() && !gameover){
                if(!target.getHasFlag() && flagsPlaced != mines){
                    target.setHasFlag(true);
                    flagsPlaced++;
                }
                else if(target.getHasFlag()){
                    target.setHasFlag(false);
                    flagsPlaced--;
                }
            }
        }
        return false;
    }

    /*
    public void testboard1(){
        for (int ii = 0; ii < rows; ii++) {
            for(int jj = 0; jj < columns; jj++){
                Cell tmp = getCellFromIndex(getCellIndexFromCoords(jj,ii));
                if(tmp.getIsOpen()) System.out.print(".");
                if(tmp.getIsMine()){
                    System.out.print("* ");
                }
                else{
                    System.out.print(getCellFromCoords(jj,ii).getAdjacentMines() + " ");
                }
            }
            System.out.println();
        }
    }

    public void testboard2(){
        for (int ii = 0; ii < rows; ii++) {
            for(int jj = 0; jj < columns; jj++){
                Cell tmp = getCellFromIndex(getCellIndexFromCoords(jj,ii));
                System.out.print(tmp.getColID() + "." + tmp.getRowID() + " ");
            }
            System.out.println();
        }
    }

    public void printneighbours(Cell target){
        ArrayList<Cell> neighbours = getNeighbours(target);
        for (Cell neighbour : neighbours) {
            if (neighbour.getIsMine()) {
                System.out.print("* ");
            } else {
                System.out.print(neighbour.getAdjacentMines());
            }
        }
        System.out.println();
        System.out.println(target.getAdjacentMines());
    }

     */

}


