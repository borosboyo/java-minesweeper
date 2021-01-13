package minesweeper.gui;

import minesweeper.gameboard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A fõmenü megjelenítésre szolgáló osztály. Itt választhatja ki a nehézséget a felhasználó,
 * és a program indításakkor ezzel az ablakkal szembesül elõször.
 * Az elõre megadott beállítások kiválasztása után elindul a tényleges játékmenet,
 * azonban ha egyéni beállításokat szeretne a felhasználó egy másik ablakkal szembesül.
 * Ez a main osztály, itt található a program belépési program is.
 */
public class MenuGUI extends JFrame{

    /**
     * A négy gombot tartalmazó panel.
     */
    private JPanel content = new JPanel();

    /**
     * A könnyû nehézséghez tartozó gomb.
     */
    private JButton easyButton = new JButton("<html><center> 8 x 8 <br /> 10 mines </center></html>");

    /**
     * A közepes nehézséghez tartozó gomb.
     */
    private JButton mediumButton = new JButton("<html><center> 16 x 16 <br /> 40 mines <center></html>");

    /**
     * A nehéz nehézséghez tartozó gomb.
     */
    private JButton hardButton = new JButton("<html><center> 30 x 16 <br /> 99 mines <center></html>");

    /**
     * Az egyéni nehézséghez tartozó gomb.
     */
    private JButton customButton = new JButton("<html><center> ? <br /> Custom <center></html>");


    /**
     * Az összes (4) gombot tartalmazó lista.
     */
    private ArrayList<JButton> buttons = new ArrayList<>();


    /**
     * A menü konstruktora.
     */
    public MenuGUI(){
        setTitle("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        content.setLayout(new GridLayout(2,2,20,20));
        content.setBorder(BorderFactory.createEmptyBorder(50,75,50,75));
        content.setBackground(Color.lightGray);
        initButtons();
        add(content);
        setVisible(true);
    }


    /**
     * Inicializálja a menügombokat.
     * Hozzáadja mind a négy menügombot egy JButton-öket tároló ArrayListhez, majd végigmegy az egész listán és
     * beállítja az összes gombra a megfelelõ tulajdonságokat(kinézetek, listener).
     */
    public void initButtons(){
        buttons.add(easyButton);
        buttons.add(mediumButton);
        buttons.add(hardButton);
        buttons.add(customButton);

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.PLAIN, 40));
            button.setBackground(Color.white);
            button.setOpaque(true);
            button.setFocusPainted(false);
            button.addActionListener(new ClickListener());
            content.add(button);
        }
    }

    /**
     * A menügombok listájának gettere.
     *
     * @return A gombok listája.
     */
    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    /**
     * A menügombok ActionListener-je.
     * A megfelelõ gombok megnyomásuk után létrehoznak az adott nehézségi fokozatnak megfelelõ táblát, melyet átad a
     * newGameStarts függvénynek.
     */
    class ClickListener implements ActionListener {
        /**
         * Ebben a metódusban kezeljük a különbözõ gombokhoz tartozó eseményeket.
         * Különbözõ nehézségekhez különbözõ táblák létrehozása szükséges, illetve az egyéni nehézség inicializálást egy másik osztály kezeli.
         *
         * @param e A vizsgálandó esemény.
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == easyButton){
                Board board = new Board(8, 8, 10);
                newGameStarts(board);
            }
            if(e.getSource() == mediumButton){
                Board board = new Board(16, 16, 40);
                newGameStarts(board);
            }
            if(e.getSource() == hardButton){
                Board board = new Board(30, 16, 99);
                newGameStarts(board);
            }
            if(e.getSource() == customButton){
                setVisible(false);
                dispose();
                CustomDiffGUI customGUI = new CustomDiffGUI();
                customGUI.setVisible(true);
            }
        }
    }


    /**
     * Elkezdõdik egy új játék, létrehozza a játék ablakját, egy paraméterül kapott táblából.
     * @param board A tábla, mely tartalmazza a játék vezérléséhez szükséges cellákat.
     *              Ezen paraméter alapján generálódik a játékablak, hiszen a tábla tartalmazza a méreteket
     *              és a játék alapvetõ információt(táblaméret, aknák száma).
     */
    public void newGameStarts(Board board){
        setVisible(false);
        dispose();
        try {
            GameGUI gui = new GameGUI(board);
            gui.setVisible(true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * A program belépési pontja.
     *
     * @param args Egy String tömb, ami a parancssori argumentumokat tárolja karaktersorozatokként.
     */
    public static void main(String[] args) {
        new MenuGUI();
    }

}
