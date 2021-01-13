package minesweeper.gui;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Az osztály lehetõvé teszi az idõ múlásának nyílvántartását, továbbá a megállítását, ha a felhasználó éppen azt szeretné.
 * A tényleges játékmenet közben használjuk, ahol fontos az idõ megjelenítése is, ezért használja az osztály a GameGUI attribútomot is.
 */
public class TimeCounter {

    /**
     * Az eltelt másodperceket méri.
     */
    private int secondsPassed = 0;

    /**
     * A játék grafikus felülete, amin belül van a TimeCounter.
     */
    private GameGUI gui;

    /**
     * A timer, ami végzi az idõszámlálást.
     */
    Timer timer = new Timer();

    /**
     * A TimerTask, amit a timer használ, hogy folyamatosan számolja az eltelt másodperceket.
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
     * A számláló konstruktora.
     *
     * @param newGui A UI, amin belül elhelyezkedik a számláló.
     */
    public TimeCounter(GameGUI newGui){
        gui = newGui;
    }


    /**
     * Elindítja az idõszámlálót.
     */
    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }


    /**
     * Megállítja a számlálót.
     */
    public void stop(){
        task.cancel();
        timer.cancel();
        timer.purge();
    }

    /**
     * Az eltelt másodpercek gettere.
     *
     * @return Az eltelt másodpercek.
     */
    public int getSecondsPassed(){ return secondsPassed; }

}
