package minesweeper.highscore;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableModel;


/**
 * A dics�s�glista megjelen�t�s�re, azon adatainak menedzsel�s�re szolg�l. Minden ind�t�skor visszaolvassa f�jlb�l az eddigi rekordokat,
 * szort�rozza azokat k�l�nb�z� adatmodellekbe, majd a felhaszn�l� k�r�s�re esetlegesen megjelen�ti azokat.
 * K�nyelmess� teszik a k�l�nb�z� neh�zs�gekkel �s t�bl�kkal val� munk�t az,
 * hogy a neh�zs�geknek k�l�n adatmodellj�k van �s rendszerezve vannak id� szerint is.
 * Bez�r�skor, vagy a visszal�p�skor term�szetesen
 * elmentj�k az adatokat, hogy azok k�s�bb is visszaolvashat�k legyenek.
 * Az oszt�ly csak akkor l�that�, vagy jelen�thet� meg, a t�nyleges j�t�kmenet m�r v�get�rt.
 */
public class HighscoreGUI extends JFrame {


    /**
     * Az �sszes rekordadatot tartalmaz� modell.
     */
    private HighscoreData allData;

    /**
     * A k�nny� neh�zs�g� rekordokat tartalmaz� modell.
     */
    private SetDifficultyData easyData = new SetDifficultyData();

    /**
     * A k�nny� neh�zs�g� rekordokat tartalmaz� modell.
     */
    private SetDifficultyData mediumData =  new SetDifficultyData();

    /**
     * A k�nny� neh�zs�g� rekordokat tartalmaz� modell.
     */
    private SetDifficultyData hardData = new SetDifficultyData();

    /**
     * A k�nny� neh�zs�g� rekordokat tartalmaz� modell.
     */
    private HighscoreData customData = new HighscoreData();

    /**
     * Az �sszes neh�zs�gi fokozatot tartalmaz� modellek list�ja.
     */
    private ArrayList<Object> datas = new ArrayList<Object>();

    /**
     * A k�nny� neh�zs�ghez tartoz� t�bla, melyhez tartozik a k�nny� neh�zs�gi rekordokat tartalmaz� adatmodell.
     */
    private JTable easyTable = new JTable(easyData);

    /**
     * A k�zepes neh�zs�ghez tartoz� t�bla, melyhez tartozik a k�zepes neh�zs�gi rekordokat tartalmaz� adatmodell.
     */
    private JTable mediumTable = new JTable(mediumData);

    /**
     * A neh�z neh�zs�ghez tartoz� t�bla, melyhez tartozik a neh�z neh�zs�gi rekordokat tartalmaz� adatmodell.
     */
    private JTable hardTable = new JTable(hardData);

    /**
     * Az egy�ni neh�zs�ghez tartoz� t�bla, melyhez tartozik az egy�ni neh�zs�gi rekordokat tartalmaz� adatmodell.
     */
    private JTable customTable = new JTable(customData);

    /**
     * A k�l�nb�z� neh�zs�gi fokozatokhoz tartalmaz� t�bl�k list�ja.
     */
    private ArrayList<JTable> tables = new ArrayList<JTable>();

    /**
     * A t�bl�zaton k�v�li als� JPanel
     */
    private JPanel lowerPanel = new JPanel();

    /**
     * A neh�zs�gi fokozatokat tartalmaz� t�mb
     */
    private String[] difficulties = {"Easy", "Medium", "Hard", "Custom"};

    /**
     * A combobox, mely seg�ts�g�vel ki lehet v�lasztani, melyik neh�zs�g t�mbj�t szeretn�nk l�tni.
     */
    private JComboBox diffCombo =  new JComboBox(difficulties);

    /**
     * A t�bl�zathoz tartoz� JScrollPane kont�neroszt�ly.
     */
    private JScrollPane scroll = new JScrollPane();

    /**
     * Visszagomb az ablakb�l.
     */
    private JButton backButton = new JButton("Back");

    /**
     * Az ablak konstruktora.
     * Bet�lti az adatokat, majd ha bez�rjuk az ablakot akkor elmenti �ket.
     */
    @SuppressWarnings("unchecked")
    public HighscoreGUI() {
        setTitle("Best times");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 200));
        initComponents();
        load();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
            }
        });

    }

    /**
     * Az �sszes rekord gettere.
     *
     * @return Az �sszes rekordot tartalmaz� adat.
     */
    public HighscoreData getAllData(){ return allData; }


    /**
     * Inicializ�lja az ablakot fel�p�t� komponenseket, legyen az a t�bla, a g�rget�s�v, a JComboBox,
     * vagy a visszagomb.
     */
    public void initComponents() {
        this.setLayout(new BorderLayout());
        initTables();

        scroll.getViewport().add(easyTable);
        add(scroll, BorderLayout.CENTER);

        diffCombo.addItemListener(new ComboBoxListener());
        backButton.addActionListener(new BackButtonListener());
        lowerPanel.add(diffCombo);
        lowerPanel.add(backButton);
        add(lowerPanel, BorderLayout.SOUTH);
    }


    /**
     * A visszagomb listenerje, amely elmenti az adatokat, ha visszal�p�nk a t�bl�zatb�l,
     * majd elt�nteti az ablakot.
     */
    class BackButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            save();
            setVisible(false);
            dispose();
        }
    }

    /**
     * A combobox listenerje, ami az aktu�lisan kijel�lt elem szerint meghat�rozza,
     * milyen neh�zs�gi fokozat rekordjait kell mutatni.
     */
    class ComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            updateTable((String) diffCombo.getSelectedItem());
        }
    }

    /**
     * Hozz�adja a k�l�nb�z� t�pus� adatmodelleket az ezeket t�rol� list�hoz,
     * tov�bb� hozz�adja a k�l�nb�z� t�pus� megjelen�tend� t�bl�kat a t�blat�rol� list�hoz.
     */
    public void initArrays(){
        datas.add(easyData);
        datas.add(mediumData);
        datas.add(hardData);
        datas.add(customData);

        tables.add(easyTable);
        tables.add(mediumTable);
        tables.add(hardTable);
        tables.add(customTable);
    }


    /**
     * Hozz�rendeli a k�l�nb�z� neh�zs�get megjelen�t� t�bl�khoz
     * a megfelel� neh�zs�gi fokozathoz tartoz� adatmodelleket.
     */
    public void initTables(){
        initArrays();
        for(int ii = 0; ii < tables.size(); ii++){
            tables.get(ii).setModel((TableModel) datas.get(ii));
            tables.get(ii).setFillsViewportHeight(true);
        }
    }


    /**
     * Friss�ti a megjelen�tett neh�zs�g� rekordok t�bl�j�t.
     * @param difficulty A megjelen�tend� neh�zs�g� rekordok.
     */
    public void updateTable(String difficulty){
        switch (difficulty) {
            case "Easy" -> changeScrollPane(0);
            case "Medium" -> changeScrollPane(1);
            case "Hard" -> changeScrollPane(2);
            default -> changeScrollPane(3);
        }
    }

    /**
     * Lecser�li a megjelen�tett t�bl�t.
     *
     * @param selectedDiff A kiv�lasztott neh�zsg�t, melyhez tartoz� t�bl�t meg kell jelen�teni.
     */
    public void changeScrollPane(int selectedDiff){
        scroll.repaint();
        scroll.getViewport().add(tables.get(selectedDiff));
        add(scroll, BorderLayout.CENTER);
    }


    /**
     * Bet�lti f�jlb�l a deszerializ�lt rekordokat, majd szort�rozza �ket neh�zs�g szerint.
     */
    public void load(){
        try {
            allData = new HighscoreData();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("besttimes.dat"));
            allData.highscores = (List<Highscore>)ois.readObject();
            ois.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        dataByDiffs();
    }


    /**
     * Elmenti/Szerializ�lja a bet�lt�tt rekordokat.
     */
    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("besttimes.dat"));
            oos.writeObject(allData.highscores);
            oos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * T�rli, majd �jra bet�lti a rekordokat tartalmaz� adatmodelleket.
     */
    public void refresh(){
        allData.clear();
        easyData.clear();
        mediumData.clear();
        hardData.clear();
        customData.clear();
        load();
    }

    /**
     * Rendezi az �sszes rekordot id� szerint, majd felt�lti a k�l�nb�z� neh�zs�gekhez tartoz� rekordokat t�rol� adatmodelleket.
     */
    public void dataByDiffs(){
        Collections.sort(allData.highscores);

        for(int ii = 0; ii < allData.highscores.size(); ii++){
            if(allData.highscores.get(ii).getCols() == 8 && allData.highscores.get(ii).getRows() == 8 && allData.highscores.get(ii).getMines() == 10){
                easyData.highscores.add(allData.highscores.get(ii));
            }
            else if(allData.highscores.get(ii).getCols() == 16 && allData.highscores.get(ii).getRows() == 16 && allData.highscores.get(ii).getMines() == 40){
                mediumData.highscores.add(allData.highscores.get(ii));
            }
            else if(allData.highscores.get(ii).getCols() == 30 && allData.highscores.get(ii).getRows() == 16 && allData.highscores.get(ii).getMines() == 99){
                hardData.highscores.add(allData.highscores.get(ii));
            }
            else{
                customData.highscores.add(allData.highscores.get(ii));
            }
        }
    }

}



