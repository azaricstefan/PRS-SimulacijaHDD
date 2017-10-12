package rs.ac.bg.etf.prs;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by staz on 2.10.2017. 10:28
 */
public class Request {

    private double length;
    private int id;

    public Request(double length, int id) {
        this.length = length;
        this.id = id;
    }


    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
