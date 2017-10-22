package rs.ac.bg.etf.prs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */

/**
 * Ova klasa sadrži Event-ove koje je izgenerisao {@link Disc} u redu za čekanje.
 * Ova klasa je Singleton.
 * Ona je zadužena za vreme simulacije.
 * Sadrži metode za stavljanje novog događaja u red i za uzimanje događaja iz reda.
 */
public class Scheduler {

    private Queue<Event> queue;
    private static Scheduler scheduler;
    private Disc myDisc;
    private double curTime;

    public static int numOfSector = 6;

    public Scheduler(){
        queue = new ArrayDeque<>();
        myDisc = new Disc(7200, 2000, 1.0 / numOfSector, numOfSector); //RPM,CYLINDERS,SIZEofONErecord,numberOfSectors
    }

    /**
     *
     * @return sledeći događaj iz reda ili null ako nema više događaja
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

    /**
     * Ova metoda služi za obradu, uzima novi zahtev od generatora zahteva i poziva metodu diska za obradu
     */
    public void processNow() {
        RequestGenerator.Instance().nextRequest(curTime); //generisi zahtev i stavi u red diska
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
