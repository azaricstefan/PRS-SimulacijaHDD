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

    private Disc myDisc;


    public RequestGenerator(Disc disc) {
        myDisc = disc;
    }

    public void generate(int amountOfRequests, int min, int max, int numOfSector) {

        for (int i = 0; i < amountOfRequests; i++){

            int stazaNum = ThreadLocalRandom.current().nextInt(min, max + 1);

            int sectorNum = ThreadLocalRandom.current().nextInt(0, numOfSector);

            Request request = new Request(stazaNum, sectorNum);
            myDisc.putRequest(request);
        }
    }

}
