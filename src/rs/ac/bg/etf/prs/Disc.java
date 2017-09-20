package rs.ac.bg.etf.prs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */
public class Disc {

    /**
     * Revolutions per minute
     */
    private int rpm;
    private int cylinders;
    private int sectors; //check: is this needed?

    private Event event;

    private Queue<Event> queue = new ArrayDeque<>();

    /**
     * Rotational delay time
     */
    private Double Trd;

    //TODO: sta treba da ima disk jos?

    public Disc(int rpm, int cylinders, int sectors){
        this.rpm = rpm;
        this.cylinders = cylinders;
        this.sectors = sectors;
        event = new Event();
    }

    /**
     * First phase, calculate Trd?
     */
    public void seek(){
        this.Trd = 30/(rpm*1.0); // (60/rpm)/2
        //TODO: phase 1 calculation

        //uradi nesto sa event
    }

    /**
     * Second phase, calculate rotational delay
     */
    public void rotationalDelay(){
        //TODO: phase 2 calculation

        //uradi nesto sa event
    }

    /**
     * Third phase, calculate transfer time
     */
    public void transferTime(){
        //TODO: phase 3 calculation [transfer time]
        //broj slogova koji trebaju da se ucitaju?

        //uradi nesto sa event
    }

    public void putEvent() {
         queue.add(event);
    }
}
