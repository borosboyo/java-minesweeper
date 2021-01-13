package minesweeper.gui;

import minesweeper.gameboard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A f�men� megjelen�t�sre szolg�l� oszt�ly. Itt v�laszthatja ki a neh�zs�get a felhaszn�l�,
 * �s a program ind�t�sakkor ezzel az ablakkal szembes�l el�sz�r.
 * Az el�re megadott be�ll�t�sok kiv�laszt�sa ut�n elindul a t�nyleges j�t�kmenet,
 * azonban ha egy�ni be�ll�t�sokat szeretne a felhaszn�l� egy m�sik ablakkal szembes�l.
 * Ez a main oszt�ly, itt tal�lhat� a program bel�p�si program is.
 */
public class MenuGUI extends JFrame{

    /**
     * A n�gy gombot tartalmaz� panel.
     */
    private JPanel content = new JPanel();

    /**
     * A k�nny� neh�zs�ghez tartoz� gomb.
     */
    private JButton easyButton = new JButton("<html><center> 8 x 8 <br /> 10 mines </center></html>");

    /**
     * A k�zepes neh�zs�ghez tartoz� gomb.
     */
    private JButton mediumButton = new JButton("<html><center> 16 x 16 <br /> 40 mines <center></html>");

    /**
     * A neh�z neh�zs�ghez tartoz� gomb.
     */
    private JButton hardButton = new JButton("<html><center> 30 x 16 <br /> 99 mines <center></html>");

    /**
     * Az egy�ni neh�zs�ghez tartoz� gomb.
     */
    private JButton customButton = new JButton("<html><center> ? <br /> Custom <center></html>");


    /**
     * Az �sszes (4) gombot tartalmaz� lista.
     */
    private ArrayList<JButton> buttons = new ArrayList<>();


    /**
     * A men� konstruktora.
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
     * Inicializ�lja a men�gombokat.
     * Hozz�adja mind a n�gy men�gombot egy JButton-�ket t�rol� ArrayListhez, majd v�gigmegy az eg�sz list�n �s
     * be�ll�tja az �sszes gombra a megfelel� tulajdons�gokat(kin�zetek, listener).
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
     * A men�gombok list�j�nak gettere.
     *
     * @return A gombok list�ja.
     */
    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    /**
     * A men�gombok ActionListener-je.
     * A megfelel� gombok megnyom�suk ut�n l�trehoznak az adott neh�zs�gi fokozatnak megfelel� t�bl�t, melyet �tad a
     * newGameStarts f�ggv�nynek.
     */
    class ClickListener implements ActionListener {
        /**
         * Ebben a met�dusban kezelj�k a k�l�nb�z� gombokhoz tartoz� esem�nyeket.
         * K�l�nb�z� neh�zs�gekhez k�l�nb�z� t�bl�k l�trehoz�sa sz�ks�ges, illetve az egy�ni neh�zs�g inicializ�l�st egy m�sik oszt�ly kezeli.
         *
         * @param e A vizsg�land� esem�ny.
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
     * Elkezd�dik egy �j j�t�k, l�trehozza a j�t�k ablakj�t, egy param�ter�l kapott t�bl�b�l.
     * @param board A t�bla, mely tartalmazza a j�t�k vez�rl�s�hez sz�ks�ges cell�kat.
     *              Ezen param�ter alapj�n gener�l�dik a j�t�kablak, hiszen a t�bla tartalmazza a m�reteket
     *              �s a j�t�k alapvet� inform�ci�t(t�blam�ret, akn�k sz�ma).
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
     * A program bel�p�si pontja.
     *
     * @param args Egy String t�mb, ami a parancssori argumentumokat t�rolja karaktersorozatokk�nt.
     */
    public static void main(String[] args) {
        new MenuGUI();
    }

}
