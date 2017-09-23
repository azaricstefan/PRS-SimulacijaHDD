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

    private Double sizeOfOneRecord;

    private Queue<Request> queue = new ArrayDeque<>();


    public Disc(int rpm, int cylinders, int sectors, Double sizeOfOneRecord, Scheduler myScheduler) {
        this.rpm = rpm;
        this.cylinders = cylinders;
        this.sectors = sectors;
        this.sizeOfOneRecord = sizeOfOneRecord;
        this.myScheduler = myScheduler;
    }

    /**
     * @return 60/RPM(of the disc)
     */
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
                        myScheduler.putEvent(new Event(curTime*sizeOfOneRecord*Trev()*1000, Event.TypeOfPhase.SEEK_PHASE));
                        curTime = 0;
                    } else if (curRequest.getNumOfStaza() < curCylinder) {
                        curCylinder--;
                    } else curCylinder++;
                }

                if (Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE == curPhase) {
                    if (curRequest.getNumOfSector() == curSector) {
                        myScheduler.putEvent(new Event(curTime*sizeOfOneRecord*Trev()*1000, Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE));
                        curTime = 0;
                        curPhase = Event.TypeOfPhase.TRANSFER_TIME_PHASE;
                    }
                } else if (curPhase == Event.TypeOfPhase.TRANSFER_TIME_PHASE) {
                    myScheduler.putEvent(new Event(sizeOfOneRecord*Trev()*1000, Event.TypeOfPhase.TRANSFER_TIME_PHASE));
                    curTime = 0;
                    curRequest = null;
                }


            }
            curSector = (curSector + 1) % sectors; // vrti se
            curTime++; //simuliraj vreme
        }
    }


    /**
     * Put a request from {@link RequestGenerator} in the queue of the Disc
     * @param request
     */
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
