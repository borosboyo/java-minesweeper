package minesweeper.gui;

import minesweeper.gameboard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Az oszt�ly annak az ablak megjeln�t�s�re szolg�l, amivel akkor szembes�l a felhaszn�l�, ha egy�ni param�terekkel rendelkez� t�bl�n szeretne j�tszani.
 * A felhaszn�l� megadhatja a JSpinnerek seg�ts�g�vel az oszlop, sor, illetve aknasz�mot is.
 * Az akn�k sz�zal�kos ar�nya minimum 1% maximum 96% lehet.
 * Ennek tekintet�ben minden t�bla �rv�nyesnek tekinthet�, melyen legal�bb 1 akna van,
 * vagy ak�r a nagy aknasz�m miatt szinte megnyerhetetlen t�bla is.
 * Az ablakb�l elind�that� a megadott t�bla, vagy ak�r vissza is l�phet�nk bel�le a f�men�be.
 * */
public class CustomDiffGUI extends JFrame {
    /**
     * Ebben a panelben k�rdezz�k ki a felhaszn�l�t az egy�ni neh�zs�g param�tereir�l.
     */
    private JPanel form = new JPanel();
    /**
     * Ezen a panelen tal�lhat� a Start game �s vissza gomb.
     */
    private JPanel buttons = new JPanel();

    /**
     * Elind�tja a j�t�kot, ha r�kattintunk.
     */
    private JButton startGame = new JButton("Start game");

    /**
     * Visszal�p a f�men�be, ha r�kattintunk.
     */
    private JButton cancel =  new JButton("Cancel");

    /**
     * Sz�less�g sz�veg�nek megjelen�t�s�hez sz�ks�ges.
     */
    private JLabel widthLabel =  new JLabel("Width: ");

    /**
     *  Magass�g sz�veg�nek megjelen�t�s�hez sz�ks�ges.
     */
    private JLabel heightLabel =  new JLabel("Height: ");

    /**
     *  Az akn�k sz�zal�k�nak sz�veg�nek megjelen�t�s�hez sz�ks�ges.
     */
    private JLabel minesLabel =  new JLabel("Percent mines: ");

    /**
     * A sz�less�g be�ll�t�s�hoz sz�ks�ges JSpinner elem.
     */
    private JSpinner widthSpinner = new JSpinner();

    /**
     * A magass�g be�ll�t�s�hoz sz�ks�ges JSpinner elem.
     */
    private JSpinner heightSpinner = new JSpinner();

    /**
     * Az akn�k sz�m�nak be�ll�t�s�hoz sz�ks�ges JSpinner elem.
     */
    private JSpinner minesSpinner = new JSpinner();

    /**
     * Az egy�ni neh�zs�g be�ll�t�s�ra szolg�l� ablak konstruktora.
     * Megh�vja a k�t JPanel inicializ�l�s�rt felel�s met�dust.
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
     * Be�ll�tja a fels� JPanelt, ahol a felhaszn�l� megadhatja az oszlop,sor, illetve aknasz�mot.
     * Hozz�adja a panelhez a 3 JLabelt �s JSpinnert.
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
     * Inicializ�lja a 3 JSpinnert, melyeken kereszt�l lehet be�ll�tani a t�bla param�tereit.
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
     * Inicializ�lja az als� JPanel 2 gombj�t.
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
     * Az ablak 2 JButton-j�nek listenerje, ahol meghat�rozzuk,
     * hogy mit csin�ljanak a gombok, ha a felhaszn�l� r�juk kattint.
     */
    class ClickListener implements ActionListener{
        /**
         * A k�t k�l�nb�z� gombhoz tartoz� esem�nyeket kezelj�k a met�dus k�t �g�ban.
         *
         * @param e A vizsg�land� esem�ny.
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
