package rs.ac.bg.etf.prs;

import org.uncommons.maths.random.ExponentialGenerator;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */

/**
 * Ova klasa generise zahteve
 */
public class RequestGenerator {

    private static RequestGenerator requestGenerator;
    private ExponentialGenerator exponentialGenerator;
    private long nextRequestTime;
    private Disc myDisc;

    private RequestGenerator(int randSeed, int expRate, Disc disc) {
        Random rng3 = new Random(randSeed);
        this.myDisc = disc;
        exponentialGenerator = new ExponentialGenerator(expRate, rng3);
    }

    public void nextRequest(double time) {
        //ako je vreme da se generise novi zahtev onda se generise
        while (nextRequestTime <= time) {
            int stazaNum = ThreadLocalRandom.current().nextInt(Simulation.min, Simulation.max + 1);
            int sectorNum = ThreadLocalRandom.current().nextInt(0, Scheduler.numOfSector);
            Request request = new Request(stazaNum, sectorNum);
            myDisc.getMyQueue().getQueue().add(request);
            System.out.println("Dodat request VREME[" + nextRequestTime + "]");
            nextRequestTime += exponentialGenerator.nextValue() * 200;
        }
    }

    public static RequestGenerator Instance() {
        if (requestGenerator != null) {
            return requestGenerator;
        } else {
            int expRate = 2; //exponential generator param
            int randSeed = 20; //exponential generator param
            return requestGenerator = new RequestGenerator(randSeed, expRate, Scheduler.Instance().getMyDisc());
        }
    }
}
