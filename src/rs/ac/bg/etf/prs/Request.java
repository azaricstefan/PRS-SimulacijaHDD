package rs.ac.bg.etf.prs;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */
public class Request {

    private int numOfSector;
    private int numOfStaza;

    public Request(int numOfStaza, int numOfSector) {
        this.numOfSector = numOfSector;
        this.numOfStaza = numOfStaza;
    }

    @Override
    public String toString() {
        return "Broj staze: " + numOfStaza + " Broj sektora: " + numOfSector;

    }

    public int getNumOfSector() {
        return numOfSector;
    }

    public int getNumOfStaza() {
        return numOfStaza;
    }

}