package minesweeper.gui;

import minesweeper.highscore.HighscoreGUI;
import minesweeper.gameboard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Az oszt�ly azt a k�perny�t kezeli, mellyel a felhaszn�l� csak akkor szembes�l,
 * ha helyesen felder�ti az �sszes aknamentes mez�t a p�ly�n, ez�ltal megnyerve a j�t�kot.
 * Az ablakban megtal�lhat� a kij�tszott p�lya param�terei (oszlopok,sorok,akn�k sz�ma),
 * tov�bb� a p�lya teljes�t�s�hez sz�ks�ges id�.
 * A j�t�kos beg�pelheti a nev�t az id� mell�, majd az eredm�nye elment�dik a dics�s�glist�ba a HighScoreUI oszt�ly seg�ts�g�vel
 */
public class WinScreen extends JFrame {

    /**
     * Ide �rhatja, be a felhaszn�l� a nev�t.
     */
    private JTextField nameField = new JTextField(20);

    /**
     * A j�t�kt�bla statisztik�inak megjelen�t�s�re szolg�l� JLabel.
     */
    private JLabel gamestats = new JLabel();
    /**
     * A rekord idej�nek megjelen�t�s�re szolg�l� JLabel.
     */
    private JLabel timeStat = new JLabel();

    /**
     * Ezut�n a JLabel ut�n �r�dik ki a j�t�kos ideje.
     */
    private JLabel timeLabel = new JLabel("Time: ");

    /**
     * Ezut�n a JLabel ut�n k�vetkez� JTextField-be g�pelheti be a nev�t a j�t�kos.
     */
    private JLabel nameLabel = new JLabel("Name: ");

    /**
     *  Meger�s�ti a nev�t a felhaszn�l�, �gy az eredm�nye elment�dik a dics�s�glist�ba.
     */
    private JButton confirmButton = new JButton("Confirm");

    /**
     * A fels� panel, ami tartalmazza a p�lya param�tereit.
     */
    private JPanel upper = new JPanel();

    /**
     * A k�z�ps� panel, ami tartalmazza a 2 JLabel-t �s a JTextField-et, ahova a felhaszn�l� g�pelheti a nev�t.
     */
    private JPanel middle = new JPanel();

    /**
     * Az als� panel, ami tartalmazza a meger�s�t� gombot.
     */
    private JPanel lower = new JPanel();

    /**
     * A j�t�kt�bla, melyen fel�ll�tott�k a rekordot.
     */
    private Board gameBoard;

    /**
     * A fel�ll�tott rekordid�.
     */
    private int time;

    /**
     * A rekordokat tartalmaz� oszt�ly, melyen m�veleteket v�gz�nk, a
     * benne l�v� adatokhoz adjuk hozz� az �j rekordot, ami sz�letett a gy�zelem ut�n
     */
    private HighscoreGUI hsUI;


    /**
     * Az ablak konstruktora, ahol be�ll�tunk egyes param�tereket
     * �s megh�vjuk a statisztik�k inicializ�s��rt felel�s met�dust.
     *
     * @param newBoard A j�t�kt�bla.
     * @param newTime Az id�, ami kellett a j�t�k megnyer�s�hez.
     * @param newHsUI A dics�s�glista, amin dolgozunk k�s�bb.
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
     * Inicializ�lja a megnyert j�t�k statisztik�inak ki�r�s�t: milyen t�bl�t siker�lt nyerni, mennyi id� alatt.
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
     * A meger�s�t� gomb listenerje.
     */
    class confirmButtonListener implements ActionListener {
        /**
         * Hozz�adjuk az �j rekordot a t�rol�hoz, majd elmentj�k azt �s bez�rjuk az ablakot.
         *
         * @param e A vizsg�land� esem�ny.
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
