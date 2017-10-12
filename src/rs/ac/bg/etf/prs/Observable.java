package rs.ac.bg.etf.prs;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by staz on 2.10.2017. 10:15
 */
public interface Observable {
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObserver(int reqId);
}
