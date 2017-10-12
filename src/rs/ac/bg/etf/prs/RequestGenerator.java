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
public class RequestGenerator extends Thread{

    private Random rng3;
    private MyQueue myQueue;
    private int maxCylinder;
    private ExponentialGenerator exponentialGenerator;
    int id;

    public RequestGenerator(int randSeed, int expRate, MyQueue myQueue, int maxCylinder) {
        rng3 = new Random(randSeed);
        this.maxCylinder = maxCylinder;
        this.myQueue = myQueue;
        exponentialGenerator = new ExponentialGenerator(expRate,rng3);
        id = 0;
    }

    @Override
    public void run() {
        boolean done = false;
        while(!done){
            try {
                Double expValue = exponentialGenerator.nextValue();
                System.out.println("Dodat request![" + expValue +" ]");
                if(expValue > 1)
                    expValue = maxCylinder*1.00;
                else
                    expValue = expValue * maxCylinder;

                Request request = new Request(expValue,id++);
                System.out.println("Dodat request![" + expValue +" ]");
                myQueue.getQueue().add(request);
                sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Request generator: STOPPED!");
                done = true;
            }
        }
    }
}
