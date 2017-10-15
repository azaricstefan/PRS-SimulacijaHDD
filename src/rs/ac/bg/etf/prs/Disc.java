package rs.ac.bg.etf.prs;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */
public class Disc {

    private Double sizeOfOneRecord;

    private static int cylinder = 100;

    private MyQueue myQueue;
    private Event.TypeOfPhase curPhase;

    /**
     * Current placement of the head
     */
    private int curCylinder;
    private int curSector;
    private Request curRequest;

    /**
     * Revolutions per minute
     */
    private int rpm;
    private int cylinders;
    private int sectors; //check: is this needed?

    public Disc(int rpm, int cylinders, Double sizeOfOneRecord, int sectors) {
        this.rpm = rpm;
        this.cylinders = cylinders;
        this.sectors = sectors;
        this.sizeOfOneRecord = sizeOfOneRecord;
        this.myQueue = new MyQueue();
    }

    public MyQueue getMyQueue() {
        return myQueue;
    }

    /**
     * @return 60/RPM(of the disc)
     */
    public double Trev(){
        return 60.0/rpm;
    }

    public void process() {
        if (curRequest == null) {
            curRequest = myQueue.getQueue().poll();
            curPhase = Event.TypeOfPhase.SEEK_PHASE;
        }

        if (curRequest != null) {
            if (Event.TypeOfPhase.SEEK_PHASE == curPhase) {
                int cylinderDiff = Math.abs(curRequest.getNumOfStaza() - curCylinder);
                Scheduler.Instance().setCurTime(cylinderDiff * sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
                Scheduler.Instance().putEvent(new Event(cylinderDiff * sizeOfOneRecord * Trev() * 1000, Event.TypeOfPhase.SEEK_PHASE));
                curSector = (curSector + cylinderDiff) % sectors; // vrti se
                curPhase = Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE;
            } else if (Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE == curPhase) {
                int sectorDiff = curRequest.getNumOfSector() - curSector;
                if (sectorDiff < 0)
                    sectorDiff += sectors;
                Scheduler.Instance().setCurTime(sectorDiff * sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
                Scheduler.Instance().putEvent(new Event(sectorDiff * sizeOfOneRecord * Trev() * 1000, Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE));
                curSector = curRequest.getNumOfSector(); // vrti se
                curPhase = Event.TypeOfPhase.TRANSFER_TIME_PHASE;

            } else if (curPhase == Event.TypeOfPhase.TRANSFER_TIME_PHASE) {
                int oneSector = 1;
                Scheduler.Instance().setCurTime(oneSector * sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
                Scheduler.Instance().putEvent(new Event(oneSector * sizeOfOneRecord * Trev() * 1000, Event.TypeOfPhase.TRANSFER_TIME_PHASE));
                curSector = (curSector + oneSector) % sectors; // vrti se
                curRequest = null;
            }
        } else {
            //nema requesta vrti u prazno
            Scheduler.Instance().setCurTime(sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
            curSector = (curSector + 1) % sectors; // vrti se
        }
    }
}
