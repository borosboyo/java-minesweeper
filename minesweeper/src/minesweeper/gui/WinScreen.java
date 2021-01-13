package minesweeper.gui;

import minesweeper.highscore.HighscoreGUI;
import minesweeper.gameboard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Az osztály azt a képernyõt kezeli, mellyel a felhasználó csak akkor szembesül,
 * ha helyesen felderíti az összes aknamentes mezõt a pályán, ezáltal megnyerve a játékot.
 * Az ablakban megtalálható a kijátszott pálya paraméterei (oszlopok,sorok,aknák száma),
 * továbbá a pálya teljesítéséhez szükséges idõ.
 * A játékos begépelheti a nevét az idõ mellé, majd az eredménye elmentõdik a dicsõséglistába a HighScoreUI osztály segítségével
 */
public class WinScreen extends JFrame {

    /**
     * Ide írhatja, be a felhasználó a nevét.
     */
    private JTextField nameField = new JTextField(20);

    /**
     * A játéktábla statisztikáinak megjelenítésére szolgáló JLabel.
     */
    private JLabel gamestats = new JLabel();
    /**
     * A rekord idejének megjelenítésére szolgáló JLabel.
     */
    private JLabel timeStat = new JLabel();

    /**
     * Ezután a JLabel után íródik ki a játékos ideje.
     */
    private JLabel timeLabel = new JLabel("Time: ");

    /**
     * Ezután a JLabel után következõ JTextField-be gépelheti be a nevét a játékos.
     */
    private JLabel nameLabel = new JLabel("Name: ");

    /**
     *  Megerõsíti a nevét a felhasználó, így az eredménye elmentõdik a dicsõséglistába.
     */
    private JButton confirmButton = new JButton("Confirm");

    /**
     * A felsõ panel, ami tartalmazza a pálya paramétereit.
     */
    private JPanel upper = new JPanel();

    /**
     * A középsõ panel, ami tartalmazza a 2 JLabel-t és a JTextField-et, ahova a felhasználó gépelheti a nevét.
     */
    private JPanel middle = new JPanel();

    /**
     * Az alsó panel, ami tartalmazza a megerõsítõ gombot.
     */
    private JPanel lower = new JPanel();

    /**
     * A játéktábla, melyen felállították a rekordot.
     */
    private Board gameBoard;

    /**
     * A felállított rekordidõ.
     */
    private int time;

    /**
     * A rekordokat tartalmazó osztály, melyen mûveleteket végzünk, a
     * benne lévõ adatokhoz adjuk hozzá az új rekordot, ami született a gyõzelem után
     */
    private HighscoreGUI hsUI;


    /**
     * Az ablak konstruktora, ahol beállítunk egyes paramétereket
     * és meghívjuk a statisztikák inicializásáért felelõs metódust.
     *
     * @param newBoard A játéktábla.
     * @param newTime Az idõ, ami kellett a játék megnyeréséhez.
     * @param newHsUI A dicsõséglista, amin dolgozunk késõbb.
     */
    public WinScreen(Board newBoard, int newTime, HighscoreGUI newHsUI){
        gameBoard = newBoard;
        time = newTime;
        hsUI = newHsUI;
        setTitle("Congratulations!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,150);
        setResizable(false);
        setVisible(true);
        setLayout(new GridLayout(3,1));
        initStats();

        add(upper, BorderLayout.NORTH);
        add(middle);
        add(lower, BorderLayout.SOUTH);
    }

    /**
     * Inicializálja a megnyert játék statisztikáinak kiírását: milyen táblát sikerült nyerni, mennyi idõ alatt.
     */
    private void initStats(){
        gamestats.setText("Minefield: " + gameBoard.getColumns() + " x " + gameBoard.getRows() + ", " + gameBoard.getMines() + " mines");
        gamestats.setAlignmentX(Component.CENTER_ALIGNMENT);
        upper.add(gamestats);

        timeStat.setText(time / 60 + " minute(s) " + time % 60 + " seconds");
        middle.add(timeLabel);
        middle.add(timeStat);
        middle.add(nameLabel);
        middle.add(nameField);

        confirmButton.addActionListener(new confirmButtonListener());
        lower.add(confirmButton);
    }


    /**
     * A megerõsítõ gomb listenerje.
     */
    class confirmButtonListener implements ActionListener {
        /**
         * Hozzáadjuk az új rekordot a tárolóhoz, majd elmentjük azt és bezárjuk az ablakot.
         *
         * @param e A vizsgálandó esemény.
         */
        @Override
        public void actionPerformed(ActionEvent e){
            hsUI.getAllData().addHighscore(nameField.getText(),time, gameBoard.getColumns(), gameBoard.getRows(), gameBoard.getMines());
            hsUI.save();
            setVisible(false);
            dispose();
        }
    }

}
