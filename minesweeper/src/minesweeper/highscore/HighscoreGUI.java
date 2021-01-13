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
 * A dicsõséglista megjelenítésére, azon adatainak menedzselésére szolgál. Minden indításkor visszaolvassa fájlból az eddigi rekordokat,
 * szortírozza azokat különbözõ adatmodellekbe, majd a felhasználó kérésére esetlegesen megjeleníti azokat.
 * Kényelmessé teszik a különbözõ nehézségekkel és táblákkal való munkát az,
 * hogy a nehézségeknek külön adatmodelljük van és rendszerezve vannak idõ szerint is.
 * Bezáráskor, vagy a visszalépéskor természetesen
 * elmentjük az adatokat, hogy azok késõbb is visszaolvashatók legyenek.
 * Az osztály csak akkor látható, vagy jeleníthetõ meg, a tényleges játékmenet már végetért.
 */
public class HighscoreGUI extends JFrame {


    /**
     * Az összes rekordadatot tartalmazó modell.
     */
    private HighscoreData allData;

    /**
     * A könnyû nehézségû rekordokat tartalmazó modell.
     */
    private SetDifficultyData easyData = new SetDifficultyData();

    /**
     * A könnyû nehézségû rekordokat tartalmazó modell.
     */
    private SetDifficultyData mediumData =  new SetDifficultyData();

    /**
     * A könnyû nehézségû rekordokat tartalmazó modell.
     */
    private SetDifficultyData hardData = new SetDifficultyData();

    /**
     * A könnyû nehézségû rekordokat tartalmazó modell.
     */
    private HighscoreData customData = new HighscoreData();

    /**
     * Az összes nehézségi fokozatot tartalmazó modellek listája.
     */
    private ArrayList<Object> datas = new ArrayList<Object>();

    /**
     * A könnyû nehézséghez tartozó tábla, melyhez tartozik a könnyû nehézségi rekordokat tartalmazó adatmodell.
     */
    private JTable easyTable = new JTable(easyData);

    /**
     * A közepes nehézséghez tartozó tábla, melyhez tartozik a közepes nehézségi rekordokat tartalmazó adatmodell.
     */
    private JTable mediumTable = new JTable(mediumData);

    /**
     * A nehéz nehézséghez tartozó tábla, melyhez tartozik a nehéz nehézségi rekordokat tartalmazó adatmodell.
     */
    private JTable hardTable = new JTable(hardData);

    /**
     * Az egyéni nehézséghez tartozó tábla, melyhez tartozik az egyéni nehézségi rekordokat tartalmazó adatmodell.
     */
    private JTable customTable = new JTable(customData);

    /**
     * A különbözõ nehézségi fokozatokhoz tartalmazó táblák listája.
     */
    private ArrayList<JTable> tables = new ArrayList<JTable>();

    /**
     * A táblázaton kívüli alsó JPanel
     */
    private JPanel lowerPanel = new JPanel();

    /**
     * A nehézségi fokozatokat tartalmazó tömb
     */
    private String[] difficulties = {"Easy", "Medium", "Hard", "Custom"};

    /**
     * A combobox, mely segítségével ki lehet választani, melyik nehézség tömbjét szeretnénk látni.
     */
    private JComboBox diffCombo =  new JComboBox(difficulties);

    /**
     * A táblázathoz tartozó JScrollPane konténerosztály.
     */
    private JScrollPane scroll = new JScrollPane();

    /**
     * Visszagomb az ablakból.
     */
    private JButton backButton = new JButton("Back");

    /**
     * Az ablak konstruktora.
     * Betölti az adatokat, majd ha bezárjuk az ablakot akkor elmenti õket.
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
     * Az összes rekord gettere.
     *
     * @return Az összes rekordot tartalmazó adat.
     */
    public HighscoreData getAllData(){ return allData; }


    /**
     * Inicializálja az ablakot felépító komponenseket, legyen az a tábla, a görgetõsáv, a JComboBox,
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
     * A visszagomb listenerje, amely elmenti az adatokat, ha visszalépünk a táblázatból,
     * majd eltünteti az ablakot.
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
     * A combobox listenerje, ami az aktuálisan kijelölt elem szerint meghatározza,
     * milyen nehézségi fokozat rekordjait kell mutatni.
     */
    class ComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            updateTable((String) diffCombo.getSelectedItem());
        }
    }

    /**
     * Hozzáadja a különbözõ típusú adatmodelleket az ezeket tároló listához,
     * továbbá hozzáadja a különbözõ típusú megjelenítendõ táblákat a táblatároló listához.
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
     * Hozzárendeli a különbözõ nehézséget megjelenítõ táblákhoz
     * a megfelelõ nehézségi fokozathoz tartozó adatmodelleket.
     */
    public void initTables(){
        initArrays();
        for(int ii = 0; ii < tables.size(); ii++){
            tables.get(ii).setModel((TableModel) datas.get(ii));
            tables.get(ii).setFillsViewportHeight(true);
        }
    }


    /**
     * Frissíti a megjelenített nehézségû rekordok tábláját.
     * @param difficulty A megjelenítendõ nehézségû rekordok.
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
     * Lecseréli a megjelenített táblát.
     *
     * @param selectedDiff A kiválasztott nehézsgét, melyhez tartozó táblát meg kell jeleníteni.
     */
    public void changeScrollPane(int selectedDiff){
        scroll.repaint();
        scroll.getViewport().add(tables.get(selectedDiff));
        add(scroll, BorderLayout.CENTER);
    }


    /**
     * Betölti fájlból a deszerializált rekordokat, majd szortírozza õket nehézség szerint.
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
     * Elmenti/Szerializálja a betöltött rekordokat.
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
     * Törli, majd újra betölti a rekordokat tartalmazó adatmodelleket.
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
     * Rendezi az összes rekordot idõ szerint, majd feltölti a különbözõ nehézségekhez tartozó rekordokat tároló adatmodelleket.
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



