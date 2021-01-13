package minesweeper.gui;

import minesweeper.gameboard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Az osztály annak az ablak megjelnítésére szolgál, amivel akkor szembesül a felhasználó, ha egyéni paraméterekkel rendelkezõ táblán szeretne játszani.
 * A felhasználó megadhatja a JSpinnerek segítségével az oszlop, sor, illetve aknaszámot is.
 * Az aknák százalékos aránya minimum 1% maximum 96% lehet.
 * Ennek tekintetében minden tábla érvényesnek tekinthetõ, melyen legalább 1 akna van,
 * vagy akár a nagy aknaszám miatt szinte megnyerhetetlen tábla is.
 * Az ablakból elindítható a megadott tábla, vagy akár vissza is léphetünk belõle a fõmenübe.
 * */
public class CustomDiffGUI extends JFrame {
    /**
     * Ebben a panelben kérdezzük ki a felhasználót az egyéni nehézség paramétereirõl.
     */
    private JPanel form = new JPanel();
    /**
     * Ezen a panelen található a Start game és vissza gomb.
     */
    private JPanel buttons = new JPanel();

    /**
     * Elindítja a játékot, ha rákattintunk.
     */
    private JButton startGame = new JButton("Start game");

    /**
     * Visszalép a fõmenübe, ha rákattintunk.
     */
    private JButton cancel =  new JButton("Cancel");

    /**
     * Szélesség szövegének megjelenítéséhez szükséges.
     */
    private JLabel widthLabel =  new JLabel("Width: ");

    /**
     *  Magasság szövegének megjelenítéséhez szükséges.
     */
    private JLabel heightLabel =  new JLabel("Height: ");

    /**
     *  Az aknák százalékának szövegének megjelenítéséhez szükséges.
     */
    private JLabel minesLabel =  new JLabel("Percent mines: ");

    /**
     * A szélesség beállításához szükséges JSpinner elem.
     */
    private JSpinner widthSpinner = new JSpinner();

    /**
     * A magasság beállításához szükséges JSpinner elem.
     */
    private JSpinner heightSpinner = new JSpinner();

    /**
     * Az aknák számának beállításához szükséges JSpinner elem.
     */
    private JSpinner minesSpinner = new JSpinner();

    /**
     * Az egyéni nehézség beállítására szolgáló ablak konstruktora.
     * Meghívja a két JPanel inicializálásért felelõs metódust.
     */
    public CustomDiffGUI(){
        setTitle("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 225);
        setResizable(false);
        initForm();
        initButtons();

        add(form, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
    }

    /**
     * Beállítja a felsõ JPanelt, ahol a felhasználó megadhatja az oszlop,sor, illetve aknaszámot.
     * Hozzáadja a panelhez a 3 JLabelt és JSpinnert.
     */
    public void initForm(){
        form.setLayout(new GridLayout(3,2,0,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));

        initSpinners();

        form.add(widthLabel);
        form.add(widthSpinner);

        form.add(heightLabel);
        form.add(heightSpinner);

        form.add(minesLabel);
        form.add(minesSpinner);
    }

    /**
     * Inicializálja a 3 JSpinnert, melyeken keresztül lehet beállítani a tábla paramétereit.
     */
    public void initSpinners(){
        SpinnerNumberModel widthModel = new SpinnerNumberModel(16,8,100,1);
        widthSpinner.setModel(widthModel);
        SpinnerNumberModel heightModel = new SpinnerNumberModel(16,8,100,1);
        heightSpinner.setModel(heightModel);
        SpinnerNumberModel minesModel = new SpinnerNumberModel(15,1,96,1);
        minesSpinner.setModel(minesModel);
    }

    /**
     * Inicializálja az alsó JPanel 2 gombját.
     */
    public void initButtons(){
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));

        startGame.addActionListener(new ClickListener());
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancel.addActionListener(new ClickListener());
        cancel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons.add(startGame);
        buttons.add(cancel);

    }

    /**
     * Az ablak 2 JButton-jének listenerje, ahol meghatározzuk,
     * hogy mit csináljanak a gombok, ha a felhasználó rájuk kattint.
     */
    class ClickListener implements ActionListener{
        /**
         * A két különbözõ gombhoz tartozó eseményeket kezeljük a metódus két ágában.
         *
         * @param e A vizsgálandó esemény.
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == startGame){
                setVisible(false);
                dispose();
                int newWidth = (Integer) widthSpinner.getValue();
                int newHeight = (Integer) heightSpinner.getValue();
                int newMines = (newHeight * newWidth) * (Integer) minesSpinner.getValue() / 100 + 1;
                try {
                    GameGUI newUI = new GameGUI(new Board(newWidth, newHeight, newMines));
                    newUI.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if(e.getSource() == cancel){
                setVisible(false);
                dispose();
                MenuGUI newUI = new MenuGUI();
                newUI.setVisible(true);

            }
        }
    }
}
