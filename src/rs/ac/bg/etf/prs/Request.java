package rs.ac.bg.etf.prs;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */
public class Request {

    private int fromCylinder;
    private int toCylinder;

    public Request(int fromCylinder, int toCylinder){
        this.fromCylinder = fromCylinder;
        this.toCylinder = toCylinder;
    }

    public int difference(){
        return Math.abs(toCylinder - fromCylinder);
    }
}
