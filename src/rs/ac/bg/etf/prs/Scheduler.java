package rs.ac.bg.etf.prs;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */

/**
 * Ova klasa sadrži Event-ove koje je izgenerisao {@link Disc} u redu za čekanje
 */
public class Scheduler {

    private Queue<Event> queue;
    private static Scheduler scheduler;
    private Disc myDisc;
    private double curTime;

    public static int numOfSector = 6;

    public Scheduler(){
        queue = new ArrayDeque<>();
        myDisc = new Disc(7200, 2000, 1.0 / numOfSector, numOfSector); //RPM,CYLINDERS,SIZEofONErecord,SCHEDULER
    }

    /**
     *
     * @return next request from the queue or null if no more requests
     */
    public Event nextEvent() {
        return queue.poll();
    }

    public void putEvent(Event event){
        queue.add(event);
    }


    public static Scheduler Instance() {
        if (scheduler != null)
            return scheduler;
        else
            return scheduler = new Scheduler();
    }

    public double getCurTime() {
        return curTime;
    }

    public void processNow() {
        RequestGenerator.Instance().nextRequest(curTime);
        myDisc.process();
        Event e = Scheduler.Instance().nextEvent();
        if (e != null)
            System.out.println(e);
    }

    public void setCurTime(double curTime) {
        this.curTime = curTime;
    }


    public Disc getMyDisc() {
        return myDisc;
    }

}
