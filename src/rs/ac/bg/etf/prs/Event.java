package rs.ac.bg.etf.prs;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 21-Sep-17.
 */
public class Event {
    //TODO: Event

    private double time;

    public enum TypeOfPhase {
        SEEK_PHASE,
        ROTATIONAL_DELAY_PHASE,
        TRANSFER_TIME_PHASE
    }

    private TypeOfPhase type;

    public Event(double time, TypeOfPhase type){
        this.time = time;
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Type: " + type + " Time: " + time;
    }
}
