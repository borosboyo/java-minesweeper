package minesweeper.gui;

import minesweeper.highscore.HighscoreGUI;
import minesweeper.gameboard.Board;
import minesweeper.gameboard.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Az osztály a tényleges játékmenet közbeni ablak megjelenítésére, felhasználói inputok feldolgozására szolgál.
 *  * Az ablakban a felhasználó szembesül a táblát reprezentáló JButton-ök kétdimenziós táblázatával,
 *  * melyeken keresztül interakcióba léphet a játékos a táblával.
 *  A táblán kívül a felhasználó lát egy információs panelt is, ahol különbözõ játékmeneten felüli funkciókat ér el.
 * Meg lehet állítani a játék közbeni idõszámlálót, visszalépni a menübe, vagy újrakezdeni az aktuális játékmenetet,
 * illetve a játék végeztével a dicsõséglista is megtekinthetõ.
 */
public class GameGUI extends JFrame {

    /**
     * A cellák megjelenítési mérete.
     */
    private int cellSize = 25;

    /**
     * Az ablak mérete.
     */
    private int height;

    /**
     *  Az ablak szélessége.
     */
    private int width;

    /**
     *  A tábla, amin játszik a felhasználó.
     */
    private Board board;

    /**
     * A panel, ami tartalmazza a játékteret reprezentáló gombokat.
     */
    private JPanel playfield = new JPanel();

    /**
     * Az infópanel, ami tartalmazza a játékon kívüli gombokat és statisztikákat.
     */
    private JPanel  infos = new JPanel();

    /**
     *  Az idõszámláló.
     */
    private TimeCounter time = new TimeCounter(this);

    /**
     * Megmutatja, hogy a felhasználó megállította-e a játékot, vagy sem.
     */
    private boolean paused;

    /**
     * Ebben a JLabelben jelenítjük meg a felhasználó által lerakott zászlók számát.
     */
    JLabel flagCounter = new JLabel();

    /**
     * Zászlószámláló feletti logo.
     */
    JLabel flagLogo = new JLabel();

    /**
     *  Ebben a JLabel-ben jelenítjük meg az eltelt másodperceket.
     */
    JLabel timeCounter = new JLabel();

    /**
     * Az idõszámláló logoja.
     */
    JLabel timeLogo = new JLabel();

    /**
     * A gomb, amivel meg lehet állítani a játékot.
     */
    JButton pauseButton = new JButton("Pause");

    /**
     * Vissza a fõmenübe gomb.
     */
    JButton menuButton = new JButton("Back to menu");

    /**
     * Újraindítja a játékot ugyanazokkal a táblaparaméterekkel.
     */
    JButton restartButton = new JButton("Restart");

    /**
     * A játék befejezése után válik láthatóvá, a dicsõséglistát megjelenítõ gomb.
     */
    JButton besttimesButton = new JButton("Best times");

    /**
     * A lista, mely tárolja az összes játéktéren cellát reprezentáló gombot.
     */
    private ArrayList<JButton> cellButtons = new ArrayList<JButton>();

    /**
     * Ez a lista tárolja az összes beolvasott képet.
     */
    private ArrayList<ImageIcon> imageContainer = new ArrayList<ImageIcon>();

    /**
     * Az információs panelen elhelyezkedõ gombotkat tartalmazza.
     */
    private ArrayList<JButton> infoButtons = new ArrayList<JButton>();

    /**
     * A dicsõséglistát kezelõ osztály egy példánya, ide írjuk majd bele az új rekordot, ha megszületett.
     */
    private HighscoreGUI hsUI;

    /**
     * Megmutatja, hogy a felhasználó megkapta-e már a végõs képernyõt gyõzelem esetén.
     */
    private boolean gotGameScreen = false;


    /**
     * A játékablak konstruktora, amely beállítja a játék táblájának adatait,
     * és meghívja magát az ablakot inicializáló függvényt.
     *
     * @param newBoard A játéktábla, ami alapján generálódik az ablak.
     * @throws IOException A képek beolvasásának sikerességének függvényében dob errort a konstruktor.
     */
    public GameGUI(Board newBoard) throws IOException {
        board = newBoard;
        height = cellSize * board.getRows() ;
        width = cellSize * board.getColumns();
        paused = false;
        time.start();
        initGameWindow();
    }

    /**
     * A tábla cellagombjainak gettere.
     *
     * @return A cellák gombjai.
     */
    public ArrayList<JButton> getCellButtons(){ return cellButtons; }

    /**
     * A képek tárolójának gettere.
     *
     * @return A képek tárolója.
     */
    public ArrayList<ImageIcon> getImageContainer() { return imageContainer; }

    /**
     * A paused settere. Ezt hívja meg a Pause gomb a késõbbiekben,
     * amikor a felhasználó meg szeretné állítani a játékot.
     *
     * @param isPaused Az új paused state.
     */
    public void setPaused(boolean isPaused){ paused = isPaused; }

    /**
     * A paused gettere. Megnézhetjük vele, hogy a felhasználó megállította-e a játékot.
     *
     * @return Meg van-e állítva a játék vagy sem.
     */
    public boolean getPaused(){ return paused; }

    /**
     * A játék táblájának gettere.
     *
     * @return A játéktábla.
     */
    public Board getBoard(){ return board; }

    /**
     * Az ablak inicializálásáért felelõs fõ metódus, amely beállítja az ablak paramétereit,
     * majd meghívja a képekért, a játéktérért és az információs panelért felelõs inicializáció metódusokat.
     *
     * @throws IOException Ha nem sikerülni a képek beolvasása, errort dob az inicializáló metódus
     */
    public void initGameWindow() throws IOException {
        setTitle("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width + 150, height);
        setResizable(false);
        setVisible(true);
        setLayout(new BorderLayout());

        initImages();
        initPlayfield();
        initInfos();

        add(playfield, BorderLayout.WEST);
        add(infos, BorderLayout.CENTER);

    }


    /**
     * Inicializálja a játéktér kinézetét, és létrehozza a kattintható gombokat.
     */
    public void initPlayfield(){
        playfield.setLayout(new GridLayout(board.getRows(),board.getColumns(), 0,0));
        playfield.setPreferredSize(new Dimension(cellSize * board.getColumns(), cellSize * board.getRows()));
        createAllCellButtons(board.getNumberOfCells());
    }


    /**
     * Inicializálja a információs panel kinézetét, és hozzáadja a megfelelõ ikonokat, szövegeket, és gombokat.
     */
    public void initInfos(){
        infos.setLayout(new BoxLayout(infos, BoxLayout.Y_AXIS));

        flagLogo.setIcon(imageContainer.get(11));
        flagLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        flagCounter.setText(board.getFlagsPlaced() + " / " + board.getMines());
        flagCounter.setAlignmentX(Component.CENTER_ALIGNMENT);

        timeLogo.setIcon(imageContainer.get(13));
        timeLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        timeCounter.setText(time.getSecondsPassed() / 60 + ":" + time.getSecondsPassed() % 60);
        timeCounter.setAlignmentX(Component.CENTER_ALIGNMENT);

        infos.add(flagLogo);
        infos.add(flagCounter);

        infos.add(timeLogo);
        infos.add(timeCounter);

        initInfoButtons();

    }

    /**
     * Beállítja az információs panelen szereplõ gombok paramétereit,
     * a gombokhoz listenert rendel hozzá, és középre igazítja õket.
     * A dicsõséglistát csak a játék végén lehet megnyitni.
     */
    public void initInfoButtons(){
        infoButtons.add(menuButton);
        infoButtons.add(restartButton);
        infoButtons.add(pauseButton);
        infoButtons.add(besttimesButton);

        for (JButton infoButton : infoButtons) {
            infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoButton.addActionListener(new ClickListener());
            infos.add(infoButton);
        }

        besttimesButton.setVisible(false);
    }

    /**
     * Az infopanelen szereplõ gombokhoz tartozó listener.
     * A megfelelõ gombokhoz rendeli a megfelelõ meghívandó metódusokat, pl: a pause gomb megállítja az idõt,
     * a restart gomb újraindítja a játékmenetet, a best times gomb megnyitja a dicsõséglistát.
     */
    class ClickListener implements ActionListener {
        /**
         * Ez a metódus kezeli a különbözõ gombokhoz rendelt metódusok meghívását.
         * @param e A vizsgálandó esemény.
         */
        @Override
        public void actionPerformed(ActionEvent e){
           if(e.getSource() == pauseButton){
               setPaused(!paused);
           }
           if(e.getSource() == restartButton){
               restartGame();
           }
           if(e.getSource() == menuButton){
               setVisible(false);
               dispose();
               MenuGUI newMenu = new MenuGUI();
               newMenu.setVisible(true);
           }
           if(e.getSource() == besttimesButton){
               hsUI.refresh();
               hsUI.setVisible(true);
           }
        }
    }

    /**
     * Újraindítja a játékmenetet.
     * Fontos, hogy a tábla paraméterei nem változnak.
     */
    public void restartGame(){
        Board tmp = new Board(board.getColumns(), board.getRows(), board.getMines());
        setVisible(false);
        dispose();
        try {
            GameGUI newGameUI = new GameGUI(tmp);
            newGameUI.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Frissíti az infópanelen a lerakott zászlók számát.
     */
    public void updateFlagCounter(){
        flagCounter.setText(board.getFlagsPlaced() + " / " + board.getMines());
    }

    /**
     * Másodpercenként frissíti az infópanelen található számlálót.
     */
    public void updateTimeCounter(){
        timeCounter.setText(time.getSecondsPassed() / 60 + ":" + time.getSecondsPassed() % 60);
    }

    /**
     * Betölti a játékhoz tartozó képeket, majd eltárolja õket egy tárolóban.
     *
     * @throws IOException Ha nem sikerülne a képek beolvasása, nem léteznének a fájlok, errort kapunk.
     */
    private void initImages() throws IOException {
        new File("src/images");
        for(int ii = 0; ii < 14; ii++)
            imageContainer.add(new ImageIcon(ImageIO.read(getClass().getResource("/images/image" + ii + ".png"))));
    }

    /**
     * Létrehozza az összes cellát reprezentáló JButton-t.
     *
     * @param numberOfCells A létrehozandó cellák száma.
     */
    public void createAllCellButtons(int numberOfCells){
        for(int ii = 0; ii < numberOfCells; ii++){
            cellButtons.add(createCellButton());
            cellButtons.get(ii).addMouseListener(
                    new Move(this, ii % board.getColumns(), ii / board.getColumns()));

            playfield.add(cellButtons.get(ii));
        }
    }

    /**
     * Létrehoz egy gombot, majd beállítja annak paramétereit, például a hozzá tartozó képet.
     *
     * @return A létrehozott gomb.
     */
    private JButton createCellButton() {
        JButton button = new JButton();
        button.setIcon(imageContainer.get(10));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(true);
        button.setMargin(new Insets(0,0,0,0));
        return button;
    }

    /**
     * Felfedi az összes aknás mezõt a pályán, különbözõ ikonnal attól függõen, hogy van-e rajta zászló vagy sem.
     *
     */
    public void revealAllMines(){
        for(int ii = 0; ii < board.getColumns() * board.getRows(); ii++){
            if(board.getCellFromIndex(ii).getIsMine()){
                board.getCellFromIndex(ii).setIsOpen(true);
                if(board.getCellFromIndex(ii).getHasFlag()){
                    cellButtons.get(ii).setIcon(imageContainer.get(12));
                }
                else{
                    cellButtons.get(ii).setIcon(imageContainer.get(9));
                }
            }
        }
    }

    /**
     * Frissíti a pálya kinézetét, attól függõen, hogy mi az adott gombhoz tartozó cella állapota.
     *
     */
    public void updateBoard(){
        if(board.getGameover()){
            revealAllMines();
        } else{
            for(int ii = 0; ii < board.getColumns() * board.getRows(); ii++){
                Cell tmp = board.getCellFromIndex(ii);
                if(tmp.getIsOpen()){
                    if(!tmp.getIsMine() && tmp.getAdjacentMines() != 0 ){
                        cellButtons.get(ii).setIcon(imageContainer.get(tmp.getAdjacentMines()));
                    } else if(!tmp.getIsMine() && tmp.getAdjacentMines() == 0){
                        cellButtons.get(ii).setIcon(imageContainer.get(0));
                    }
                } else{
                    if(tmp.getHasFlag()){
                        cellButtons.get(ii).setIcon(imageContainer.get(11));
                    } else{
                        cellButtons.get(ii).setIcon(imageContainer.get(10));
                    }
                }
            }
        }
    }


    /**
     * Ellenõrzi, hogy a játékos megnyerte-e a játékot,
     * illetve megkapta-e már a nyerés utáni ablakot, ahol begépelheti a nevét a dicsõséglistába.
     */
    public void checkWon(){
        if(board.isGameWon() && !gotGameScreen){
            gotGameScreen = true;
            WinScreen ws = new WinScreen(board, time.getSecondsPassed(), hsUI);
            ws.setVisible(true);
        }
    }

    /**
     * Ellenõrzi, hogy vége van-e már a játéknak.
     * Ha igen frissíti az infópanelt, megnyithatóvá válik a dicsõséglista.
     */
    public void checkEndGame(){
        if(board.getGameover()){
            pauseButton.setVisible(false);
            besttimesButton.setVisible(true);
            infos.revalidate();
            infos.repaint();
            hsUI = new HighscoreGUI();
        }
    }
}
