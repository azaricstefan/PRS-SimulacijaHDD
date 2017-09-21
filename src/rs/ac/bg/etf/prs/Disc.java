package rs.ac.bg.etf.prs;

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

    /**
     * Rotational delay time
     */
    private Double Trd;
    /**
     * Time to read one sector/part
     */
    private Double Ttr;

    private Double sizeOfOneRecord;

    //TODO: sta treba da ima disk jos?

    public Disc(int rpm, int cylinders, int sectors, Double sizeOfOneRecord){
        this.rpm = rpm;
        this.cylinders = cylinders;
        this.sectors = sectors;
        this.sizeOfOneRecord = sizeOfOneRecord;
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
}
