package rs.ac.bg.etf.prs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */
public class Disc extends Thread{

    /**
     * Revolutions per minute
     */
    private int rpm;
    private int cylinders;
    private int sectors; //check: is this needed?

    /**
     * Current placement of the head
     */
    private int curCylinder;
    private int curSector;
    private Request curRequest;
    private long curTime;

    private Event.TypeOfPhase curPhase;

    private Scheduler myScheduler;

    /**
     * Rotational delay time
     */
    private Double Trd;


    /**
     * Time to read one sector/part
     */
    private Double Ttr;

    private Double sizeOfOneRecord;

    private Queue<Request> queue = new ArrayDeque<>();

    //TODO: sta treba da ima disk jos?

    public Disc(int rpm, int cylinders, int sectors, Double sizeOfOneRecord, Scheduler myScheduler) {
        this.rpm = rpm;
        this.cylinders = cylinders;
        this.sectors = sectors;
        this.sizeOfOneRecord = sizeOfOneRecord;
        this.myScheduler = myScheduler;
    }

    public double Trev(){
        return 60.0/rpm;
    }

    @Override
    public void run() {
        while(!interrupted()) {
            if(curRequest == null) {
                curTime = 0;
                curRequest = queue.poll();
                curPhase = Event.TypeOfPhase.SEEK_PHASE;
            }
            if(curRequest != null) {
                if (Event.TypeOfPhase.SEEK_PHASE == curPhase) {
                    if (curRequest.getNumOfStaza() == curCylinder) {
                        curPhase = Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE;
                        myScheduler.putEvent(new Event(curTime*1/sectors*Trev()*1000, Event.TypeOfPhase.SEEK_PHASE));
                        curTime = 0;
                    } else if (curRequest.getNumOfStaza() < curCylinder) {
                        curCylinder--;
                    } else curCylinder++;
                }

                if (Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE == curPhase) {
                    if (curRequest.getNumOfSector() == curSector) {
                        myScheduler.putEvent(new Event(curTime*1.0/sectors*Trev()*1000, Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE));
                        curTime = 0;
                        curPhase = Event.TypeOfPhase.TRANSFER_TIME_PHASE;
                    }
                } else if (curPhase == Event.TypeOfPhase.TRANSFER_TIME_PHASE) {
                    myScheduler.putEvent(new Event(1.0/sectors*Trev()*1000, Event.TypeOfPhase.TRANSFER_TIME_PHASE));
                    curTime = 0;
                    curRequest = null;
                }


            }
            curSector = (curSector + 1) % sectors; // vrti se
            curTime++;
        }
    }

    /**
     * First phase, calculate Trd?
     */
    public void seek(){
        this.Trd = 30/(rpm*1.0); // (60/rpm)/2
        //TODO: phase 1 calculation
    }

    /**
     * Second phase, calculate rotational delay
     */
    public void rotationalDelay(){
        this.Ttr = sizeOfOneRecord * Trd;
        //TODO: phase 2 calculation

    }

    /**
     * Third phase, calculate transfer time
     */
    public void transferTime(){
        //TODO: phase 3 calculation [transfer time]
        //broj slogova koji trebaju da se ucitaju?
    }



    public void putRequest(Request request) {
        queue.add(request);
    }

    /**
     * Get next request from the Disc    queue
     * @return null if queue is empty
     */
    public Request getRequest(){
        return queue.poll();
    }
}
