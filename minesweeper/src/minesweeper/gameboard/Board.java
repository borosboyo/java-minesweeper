package minesweeper.gameboard;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Maga a tényleges aknakeresõ játéktérnek a kezelõje. Tartalmazza a pálya paramétereit
 * (oszlopok,sorok száma, aknák száma, vége van-e a játéknak, lerakott zászlók), továbbá a cellákat.
 * Lekezeli, ha egy cellával valamilyen interakció történik. Az osztály a teljes program gerince: az egész menürendszer-e köré épül fel.
 * A játékmenet fõ algoritmusait is magába foglalja: egy cella és szomszédainak rekurzív megjelenítése vagy
 * egy cella szomszédainak meghatározása, majd a rajtuk végzett mûveletek.
 * Egy listában tárolja az összes cellát, hogy azok könnyen kezelhetõek legyen.
 * Lehetõvé teszi, hogy egy cellát koordinátái, vagy akár a listában szereplõ indexe alapján meghatározzunk, vagy mûveletet végezzünk rajta.
 */
public class Board {

    /**
     * Oszlopok száma.
     */
    private int columns;

    /**
     * Sorok száma.
     */
    private int rows;

    /**
     * Aknák száma a táblában.
     */
    private int mines;

    /**
     * Jelzi, hogy végetért-e már a játék.
     */
    private boolean gameover;

    /**
     * A cellákat tartalmazó lista.
     */
    private ArrayList<Cell> mineField;

    /**
     * A lerakott zászlók száma.
     */
    private int flagsPlaced;


    /**
     * A játéktábla konstruktora. Meghívja a tábla inicializálásért felelõs metódust.
     *
     * @param newColumns Az új tábla oszlopainak száma.
     * @param newRows   Az új tábla sorainak száma.
     * @param newMines Az új táblában található aknák száma.
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
     * Létrehozza a táblában található cellákat, majd meghívja az aknalehelyezõ függvényt.
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
     * Véletlenszerûen lehelyezi az elõre meghatározott számú aknát.
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
     * A metódus meghatározza, hogy az adott koordinátapár megtalálható-e a táblán.
     *
     * @param colID A cella oszlopindexe.
     * @param rowID A cella sorindexe.
     * @return Igaz/Hamis attól függõen, hogy benne található-e a táblában az aktuális koordináta.
     */
    public boolean isValidCoord(int colID, int rowID){
        return (colID >= 0 && rowID >= 0 && colID < rows && rowID < columns);
    }


    /**
     * Visszaadja egy cella összes szomszédját egy ArrayList-ben.
     *
     * @param target A cella, melynek keressük a szomszédjait.
     * @return  A lista, mely tartalmazza a target cella szomszédjait.
     */
    public ArrayList<Cell> getNeighbours(Cell target){
        ArrayList<Cell> neighbours =  new ArrayList<Cell>();

        //Szomszédos cellák x és y koordinátáinak különbsége a target cellától.
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
     * A cellák számának gettere.
     *
     * @return A cellák száma a táblán.
     */
    public int getNumberOfCells(){
        return columns * rows;
    }

    /**
     * A tábla oszlopszámának gettere.
     *
     * @return A tábla oszlopainak száma.
     */
    public int getColumns(){ return columns; }

    /**
     * A tábla sorszámának gettere.
     *
     * @return A tábla sorainak száma.
     */
    public int getRows(){ return rows; }


    /**
     * A táblán lerakott zászlók számának gettere.
     *
     * @return A lerakott zászlók száma.
     */
    public int getFlagsPlaced() { return flagsPlaced; }

    /**
     * A táblán található aknák számának gettere.
     *
     * @return Az aknák száma.
     */
    public int getMines() { return mines; }

    /**
     * A játék végének gettere.
     *
     * @return Igaz/Hamis: Vége van-e a játéknak vagy sem.
     */
    public boolean getGameover(){ return gameover; }


    /**
     * Az ArrayListben található egy keresett cella listaindexének gettere.
     *
     * @param target A cella, melynek indexére vagyunk kíváncsiak.
     * @return A target cella indexe.
     */
    public int getCellIndex(Cell target){
        return mineField.indexOf(target);
    }


    /**
     * Visszaadja egy cella indexét az ArrayList-ben, a táblán található koordinátáiból.
     *
     * @param colID A cella oszlopindexe.
     * @param rowID A cella sorindexe.
     * @return A keresett cella indexe-
     */
    public int getCellIndexFromCoords(int colID, int rowID){
        return rowID * columns + colID;
    }

    /**
     * Visszaad egy cellát a listából egy index alapján.
     *
     * @param cellIndex A cella indexe.
     * @return Maga a cella
     */
    public Cell getCellFromIndex(int cellIndex){ return mineField.get(cellIndex); }

    /**
     * A metódus segíségével megkaphatunk egy cellát a koordinátáiból.
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
     * A metódus végigmegy az egész aknamezõn, és ha a felhasználó felfedte az összes aknamentes mezõt,
     * akkor megnyerte a játékot.
     *
     * @return Igaz/Hamis az alapján, hogy a játékos felfedte-e az összes aknanélküli cellát, vagy sem.
     */
    public boolean isGameWon(){
        for (Cell cell : mineField) {
            if (!cell.getIsOpen() && !cell.getIsMine()) return false;
        }
        return true;
    }

    /**
     * Megszámolja, hogy egy adott cella körül hány lerakott zászló található.
     *
     * @param target A cella, mellyel szomszédos lerakott zászlókat keresünk.
     * @return A szomszédos zászlók száma.
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
     * Meghívja a cellát megjátszó metódust a koordinátái alapján.
     *
     * @param targetColID A játszandó cella oszlopindexe.
     * @param targetRowID A játszandó cella sorindexe.
     * @return Igaz/Hamis az alapján, hogy sikerült-e megjátszani a cellát.
     */
    public boolean playCellByCoords(int targetColID, int targetRowID){
        Cell target = mineField.get(getCellIndexFromCoords(targetColID, targetRowID));
        return playCell(target);
    }


    /**
     * A kattintást reprezentálja egy cellán.
     * Ha egy aknamentes mezõt találtunk, azonban a közvetlen környezetében található akna,
     * akkor a mezõn belül megjelenik egy szám (minimum: 1, maximum 8), amely a cella melletti aknák számát jelzi.
     * Másik lehetõség, ha olyan aknamentes mezõre bukkanunk, amelynek a környezete sem tartalmaz aknát,
     * ekkor az adott mezõvel határos cellák mindegyike feltárul("megjátszódik"),
     * továbbá az így feltáruló aknamentes „szigettel” határos cellák is feltárulnak(rekurzívan), ha nincs alattuk akna.
     * Ha aknás mezõre lett megjátszva, akkor vége a játéknak.
     *
     * @param target A megjátszandó cella.
     * @return Sikerült-e megjátszani a cellát, vagy sem.
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
     * Mindkét egérgombbal kattint koordináta alapján egy target cellára.
     *
     * @param targetColID A cella oszlopindexe.
     * @param targetRowID A cella sorindexe.
     */
    public void doubleClickByCoords(int targetColID, int targetRowID){
        Cell target = mineField.get(getCellIndexFromCoords(targetColID, targetRowID));
        doubleClick(target);
    }

    /**
     * Ha ugyanannyi zászló van egy cella körül, ahány akna van körülötte,
     * és mindkét egérgombbal egyszerre kattintunk rá, akkor megjátssza az adott cellának a szomszédjait.
     *
     * @param target A duplán kattintandó target cella.
     */
    public void doubleClick(Cell target){
        ArrayList<Cell> neighbours = getNeighbours(target);
        if (countNeighbouringFlags(target) == target.getAdjacentMines())
            for (Cell neighbour : neighbours)
                if (!neighbour.getIsOpen() && !neighbour.getHasFlag())
                    playCell(neighbour);
    }


    /**
     * Meghívja a zászlózó metódust az adott cellára, annak koordinátái alapján.
     *
     * @param targetColID A cella oszlopindexe.
     * @param targetRowID A cella sorindexe.
     * @return Sikerült-e megzászlózni a cellát
     */
    public boolean flagCellByCoords(int targetColID, int targetRowID){
        Cell target = mineField.get(getCellIndexFromCoords(targetColID, targetRowID));
        return flagCell(target);
    }

    /**
     *
     *
     * @param target A cella, amire zászlót akarunk helyezni.
     * @return Sikerült-e megzászlózni a cellát vagy sem.
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


