package rs.ac.bg.etf.prs;

import java.util.List;

public class Simulation {

    public static void main(String[] args) {

        int amountOfRequests = Integer.parseInt(args[0]); //how many requests to generate
        int min = Integer.parseInt(args[1]); //lowest possible cylinder
        int max = Integer.parseInt(args[2]); //maximum possible cylinder
        int numOfSector = Integer.parseInt(args[3]); //number of sectors in the disc


        Scheduler scheduler = new Scheduler();
        Disc disc = new Disc(7200,2000,numOfSector, 1.0/6.0, scheduler); //
        RequestGenerator requestGenerator = new RequestGenerator(disc);


        Event event;

        requestGenerator.generate(amountOfRequests, min, max, numOfSector);

        disc.start();

        double sum = 0;
        double sum1 = 0, sum2 = 0, sum3 = 0;

        for(int i = 0; i < amountOfRequests; i++){
            List<Event> threeEvents = scheduler.nextEvent();

            sum1 += threeEvents.get(0).getTime();
            sum2 += threeEvents.get(1).getTime();
            sum3 += threeEvents.get(2).getTime();
            for (Event e: threeEvents) {
                sum += e.getTime();
                System.out.println(e);
            }

        }
        sum = sum / amountOfRequests;

        System.out.println("Average time: " + sum + " ms");
        System.out.println("Average time phase 1: " + sum1/amountOfRequests + " ms");
        System.out.println("Average time phase 2: " + sum2/amountOfRequests + " ms");
        System.out.println("Average time phase 3: " + sum3/amountOfRequests + " ms");
        disc.interrupt();
    }
}
