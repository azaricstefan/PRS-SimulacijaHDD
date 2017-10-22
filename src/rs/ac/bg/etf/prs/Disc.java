package rs.ac.bg.etf.prs;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */
public class Disc {

    private MyQueue myQueue;
    private Event.TypeOfPhase curPhase;

    /**
     * Trenutni polozaj glave
     */
    private int curCylinder;
    private int curSector;
    private Request curRequest;

    /**
     * Broj okreta u minuti
     */
    private int rpm;
    /**
     * Broj cilindara
     */
    private int cylinders;
    /**
     * Broj sektora
     */
    private int sectors;
    /**
     * Velicina jednog zapisa
     */
    private Double sizeOfOneRecord;

    public Disc(int rpm, int cylinders, Double sizeOfOneRecord, int sectors) {
        this.rpm = rpm;
        this.cylinders = cylinders;
        this.sectors = sectors;
        this.sizeOfOneRecord = sizeOfOneRecord;
        this.myQueue = new MyQueue();
    }

    public MyQueue getMyQueue() { return myQueue; }

    /**
     * @return 60/RPM(of the disc)
     */
    public double Trev(){ return 60.0/rpm; }

    public void process() {
        //ako ne postoji trenutni zahtev uzmi novi
        if (curRequest == null) {
            curRequest = myQueue.getQueue().poll();
            curPhase = Event.TypeOfPhase.SEEK_PHASE;
        }

        if (curRequest != null) {
            //PRVA FAZA
            if (Event.TypeOfPhase.SEEK_PHASE == curPhase) {
                int cylinderDiff = Math.abs(curRequest.getNumOfStaza() - curCylinder);
                Scheduler.Instance().setCurTime(cylinderDiff * sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
                Scheduler.Instance().putEvent(new Event(cylinderDiff * sizeOfOneRecord * Trev() * 1000, Event.TypeOfPhase.SEEK_PHASE));
                curSector = (curSector + cylinderDiff) % sectors; // vrti se
                curPhase = Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE;
            }//DRUGA FAZA
            else if (Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE == curPhase) {
                int sectorDiff = curRequest.getNumOfSector() - curSector;
                if (sectorDiff < 0)
                    sectorDiff += sectors;
                Scheduler.Instance().setCurTime(sectorDiff * sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
                Scheduler.Instance().putEvent(new Event(sectorDiff * sizeOfOneRecord * Trev() * 1000, Event.TypeOfPhase.ROTATIONAL_DELAY_PHASE));
                curSector = curRequest.getNumOfSector();
                curPhase = Event.TypeOfPhase.TRANSFER_TIME_PHASE;

            }//TRECA FAZA
            else if (curPhase == Event.TypeOfPhase.TRANSFER_TIME_PHASE) {
                int oneSector = 1;
                Scheduler.Instance().setCurTime(oneSector * sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
                Scheduler.Instance().putEvent(new Event(oneSector * sizeOfOneRecord * Trev() * 1000, Event.TypeOfPhase.TRANSFER_TIME_PHASE));
                curSector = (curSector + oneSector) % sectors; // vrti se
                curRequest = null;
            }
        } else {
            //nema zahteva vrti u prazno
            Scheduler.Instance().setCurTime(sizeOfOneRecord * Trev() * 1000 + Scheduler.Instance().getCurTime());
            curSector = (curSector + 1) % sectors; // vrti se
        }
    }
}
