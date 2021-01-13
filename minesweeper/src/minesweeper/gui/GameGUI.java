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
 * Az oszt�ly a t�nyleges j�t�kmenet k�zbeni ablak megjelen�t�s�re, felhaszn�l�i inputok feldolgoz�s�ra szolg�l.
 *  * Az ablakban a felhaszn�l� szembes�l a t�bl�t reprezent�l� JButton-�k k�tdimenzi�s t�bl�zat�val,
 *  * melyeken kereszt�l interakci�ba l�phet a j�t�kos a t�bl�val.
 *  A t�bl�n k�v�l a felhaszn�l� l�t egy inform�ci�s panelt is, ahol k�l�nb�z� j�t�kmeneten fel�li funkci�kat �r el.
 * Meg lehet �ll�tani a j�t�k k�zbeni id�sz�ml�l�t, visszal�pni a men�be, vagy �jrakezdeni az aktu�lis j�t�kmenetet,
 * illetve a j�t�k v�gezt�vel a dics�s�glista is megtekinthet�.
 */
public class GameGUI extends JFrame {

    /**
     * A cell�k megjelen�t�si m�rete.
     */
    private int cellSize = 25;

    /**
     * Az ablak m�rete.
     */
    private int height;

    /**
     *  Az ablak sz�less�ge.
     */
    private int width;

    /**
     *  A t�bla, amin j�tszik a felhaszn�l�.
     */
    private Board board;

    /**
     * A panel, ami tartalmazza a j�t�kteret reprezent�l� gombokat.
     */
    private JPanel playfield = new JPanel();

    /**
     * Az inf�panel, ami tartalmazza a j�t�kon k�v�li gombokat �s statisztik�kat.
     */
    private JPanel  infos = new JPanel();

    /**
     *  Az id�sz�ml�l�.
     */
    private TimeCounter time = new TimeCounter(this);

    /**
     * Megmutatja, hogy a felhaszn�l� meg�ll�totta-e a j�t�kot, vagy sem.
     */
    private boolean paused;

    /**
     * Ebben a JLabelben jelen�tj�k meg a felhaszn�l� �ltal lerakott z�szl�k sz�m�t.
     */
    JLabel flagCounter = new JLabel();

    /**
     * Z�szl�sz�ml�l� feletti logo.
     */
    JLabel flagLogo = new JLabel();

    /**
     *  Ebben a JLabel-ben jelen�tj�k meg az eltelt m�sodperceket.
     */
    JLabel timeCounter = new JLabel();

    /**
     * Az id�sz�ml�l� logoja.
     */
    JLabel timeLogo = new JLabel();

    /**
     * A gomb, amivel meg lehet �ll�tani a j�t�kot.
     */
    JButton pauseButton = new JButton("Pause");

    /**
     * Vissza a f�men�be gomb.
     */
    JButton menuButton = new JButton("Back to menu");

    /**
     * �jraind�tja a j�t�kot ugyanazokkal a t�blaparam�terekkel.
     */
    JButton restartButton = new JButton("Restart");

    /**
     * A j�t�k befejez�se ut�n v�lik l�that�v�, a dics�s�glist�t megjelen�t� gomb.
     */
    JButton besttimesButton = new JButton("Best times");

    /**
     * A lista, mely t�rolja az �sszes j�t�kt�ren cell�t reprezent�l� gombot.
     */
    private ArrayList<JButton> cellButtons = new ArrayList<JButton>();

    /**
     * Ez a lista t�rolja az �sszes beolvasott k�pet.
     */
    private ArrayList<ImageIcon> imageContainer = new ArrayList<ImageIcon>();

    /**
     * Az inform�ci�s panelen elhelyezked� gombotkat tartalmazza.
     */
    private ArrayList<JButton> infoButtons = new ArrayList<JButton>();

    /**
     * A dics�s�glist�t kezel� oszt�ly egy p�ld�nya, ide �rjuk majd bele az �j rekordot, ha megsz�letett.
     */
    private HighscoreGUI hsUI;

    /**
     * Megmutatja, hogy a felhaszn�l� megkapta-e m�r a v�g�s k�perny�t gy�zelem eset�n.
     */
    private boolean gotGameScreen = false;


    /**
     * A j�t�kablak konstruktora, amely be�ll�tja a j�t�k t�bl�j�nak adatait,
     * �s megh�vja mag�t az ablakot inicializ�l� f�ggv�nyt.
     *
     * @param newBoard A j�t�kt�bla, ami alapj�n gener�l�dik az ablak.
     * @throws IOException A k�pek beolvas�s�nak sikeress�g�nek f�ggv�ny�ben dob errort a konstruktor.
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
     * A t�bla cellagombjainak gettere.
     *
     * @return A cell�k gombjai.
     */
    public ArrayList<JButton> getCellButtons(){ return cellButtons; }

    /**
     * A k�pek t�rol�j�nak gettere.
     *
     * @return A k�pek t�rol�ja.
     */
    public ArrayList<ImageIcon> getImageContainer() { return imageContainer; }

    /**
     * A paused settere. Ezt h�vja meg a Pause gomb a k�s�bbiekben,
     * amikor a felhaszn�l� meg szeretn� �ll�tani a j�t�kot.
     *
     * @param isPaused Az �j paused state.
     */
    public void setPaused(boolean isPaused){ paused = isPaused; }

    /**
     * A paused gettere. Megn�zhetj�k vele, hogy a felhaszn�l� meg�ll�totta-e a j�t�kot.
     *
     * @return Meg van-e �ll�tva a j�t�k vagy sem.
     */
    public boolean getPaused(){ return paused; }

    /**
     * A j�t�k t�bl�j�nak gettere.
     *
     * @return A j�t�kt�bla.
     */
    public Board getBoard(){ return board; }

    /**
     * Az ablak inicializ�l�s��rt felel�s f� met�dus, amely be�ll�tja az ablak param�tereit,
     * majd megh�vja a k�pek�rt, a j�t�kt�r�rt �s az inform�ci�s panel�rt felel�s inicializ�ci� met�dusokat.
     *
     * @throws IOException Ha nem siker�lni a k�pek beolvas�sa, errort dob az inicializ�l� met�dus
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
     * Inicializ�lja a j�t�kt�r kin�zet�t, �s l�trehozza a kattinthat� gombokat.
     */
    public void initPlayfield(){
        playfield.setLayout(new GridLayout(board.getRows(),board.getColumns(), 0,0));
        playfield.setPreferredSize(new Dimension(cellSize * board.getColumns(), cellSize * board.getRows()));
        createAllCellButtons(board.getNumberOfCells());
    }


    /**
     * Inicializ�lja a inform�ci�s panel kin�zet�t, �s hozz�adja a megfelel� ikonokat, sz�vegeket, �s gombokat.
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
     * Be�ll�tja az inform�ci�s panelen szerepl� gombok param�tereit,
     * a gombokhoz listenert rendel hozz�, �s k�z�pre igaz�tja �ket.
     * A dics�s�glist�t csak a j�t�k v�g�n lehet megnyitni.
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
     * Az infopanelen szerepl� gombokhoz tartoz� listener.
     * A megfelel� gombokhoz rendeli a megfelel� megh�vand� met�dusokat, pl: a pause gomb meg�ll�tja az id�t,
     * a restart gomb �jraind�tja a j�t�kmenetet, a best times gomb megnyitja a dics�s�glist�t.
     */
    class ClickListener implements ActionListener {
        /**
         * Ez a met�dus kezeli a k�l�nb�z� gombokhoz rendelt met�dusok megh�v�s�t.
         * @param e A vizsg�land� esem�ny.
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
     * �jraind�tja a j�t�kmenetet.
     * Fontos, hogy a t�bla param�terei nem v�ltoznak.
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
     * Friss�ti az inf�panelen a lerakott z�szl�k sz�m�t.
     */
    public void updateFlagCounter(){
        flagCounter.setText(board.getFlagsPlaced() + " / " + board.getMines());
    }

    /**
     * M�sodpercenk�nt friss�ti az inf�panelen tal�lhat� sz�ml�l�t.
     */
    public void updateTimeCounter(){
        timeCounter.setText(time.getSecondsPassed() / 60 + ":" + time.getSecondsPassed() % 60);
    }

    /**
     * Bet�lti a j�t�khoz tartoz� k�peket, majd elt�rolja �ket egy t�rol�ban.
     *
     * @throws IOException Ha nem siker�lne a k�pek beolvas�sa, nem l�tezn�nek a f�jlok, errort kapunk.
     */
    private void initImages() throws IOException {
        new File("src/images");
        for(int ii = 0; ii < 14; ii++)
            imageContainer.add(new ImageIcon(ImageIO.read(getClass().getResource("/images/image" + ii + ".png"))));
    }

    /**
     * L�trehozza az �sszes cell�t reprezent�l� JButton-t.
     *
     * @param numberOfCells A l�trehozand� cell�k sz�ma.
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
     * L�trehoz egy gombot, majd be�ll�tja annak param�tereit, p�ld�ul a hozz� tartoz� k�pet.
     *
     * @return A l�trehozott gomb.
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
     * Felfedi az �sszes akn�s mez�t a p�ly�n, k�l�nb�z� ikonnal att�l f�gg�en, hogy van-e rajta z�szl� vagy sem.
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
     * Friss�ti a p�lya kin�zet�t, att�l f�gg�en, hogy mi az adott gombhoz tartoz� cella �llapota.
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
     * Ellen�rzi, hogy a j�t�kos megnyerte-e a j�t�kot,
     * illetve megkapta-e m�r a nyer�s ut�ni ablakot, ahol beg�pelheti a nev�t a dics�s�glist�ba.
     */
    public void checkWon(){
        if(board.isGameWon() && !gotGameScreen){
            gotGameScreen = true;
            WinScreen ws = new WinScreen(board, time.getSecondsPassed(), hsUI);
            ws.setVisible(true);
        }
    }

    /**
     * Ellen�rzi, hogy v�ge van-e m�r a j�t�knak.
     * Ha igen friss�ti az inf�panelt, megnyithat�v� v�lik a dics�s�glista.
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
