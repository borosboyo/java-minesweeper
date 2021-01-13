package minesweeper.gui;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Az oszt�ly lehet�v� teszi az id� m�l�s�nak ny�lv�ntart�s�t, tov�bb� a meg�ll�t�s�t, ha a felhaszn�l� �ppen azt szeretn�.
 * A t�nyleges j�t�kmenet k�zben haszn�ljuk, ahol fontos az id� megjelen�t�se is, ez�rt haszn�lja az oszt�ly a GameGUI attrib�tomot is.
 */
public class TimeCounter {

    /**
     * Az eltelt m�sodperceket m�ri.
     */
    private int secondsPassed = 0;

    /**
     * A j�t�k grafikus fel�lete, amin bel�l van a TimeCounter.
     */
    private GameGUI gui;

    /**
     * A timer, ami v�gzi az id�sz�ml�l�st.
     */
    Timer timer = new Timer();

    /**
     * A TimerTask, amit a timer haszn�l, hogy folyamatosan sz�molja az eltelt m�sodperceket.
     */
    TimerTask task = new TimerTask(){
        public void run(){
            if(gui.getBoard().getGameover()){
                stop();
            }
            else if(!gui.getPaused()){
                secondsPassed++;
                gui.updateTimeCounter();
            }
        }
    };

    /**
     * A sz�ml�l� konstruktora.
     *
     * @param newGui A UI, amin bel�l elhelyezkedik a sz�ml�l�.
     */
    public TimeCounter(GameGUI newGui){
        gui = newGui;
    }


    /**
     * Elind�tja az id�sz�ml�l�t.
     */
    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }


    /**
     * Meg�ll�tja a sz�ml�l�t.
     */
    public void stop(){
        task.cancel();
        timer.cancel();
        timer.purge();
    }

    /**
     * Az eltelt m�sodpercek gettere.
     *
     * @return Az eltelt m�sodpercek.
     */
    public int getSecondsPassed(){ return secondsPassed; }

}
