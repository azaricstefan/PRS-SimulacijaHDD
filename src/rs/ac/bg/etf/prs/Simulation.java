package rs.ac.bg.etf.prs;

public class Simulation {

    public static int min = 0; //min broj cilindara
    public static int max = 100; //max broj cilindara

    public static void main(String[] args) {

        while (Scheduler.Instance().getCurTime() <= 500000.0)
            Scheduler.Instance().processNow();
    }
}
