package rs.ac.bg.etf.prs;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */

/**
 * Ova klasa generise zahteve
 */
public class RequestGenerator {

    private Scheduler myScheduler;


    public RequestGenerator(Scheduler scheduler) {
        myScheduler = scheduler;
    }

    public void generate(int amountOfRequests, int min, int max) {

        for (int i = 0; i < amountOfRequests; i++){
            int randomFrom = ThreadLocalRandom.current().nextInt(min, max + 1);
            int randomTo = ThreadLocalRandom.current().nextInt(min, max + 1);

            Request request = new Request(randomFrom,randomTo);
            myScheduler.putRequest(request);
        }
    }

}
