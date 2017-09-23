package rs.ac.bg.etf.prs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

public class Simulation {

    public static void main(String[] args) {

        int amountOfRequests = Integer.parseInt(args[0]); //how many requests to generate
        int min = Integer.parseInt(args[1]); //lowest possible cylinder
        int max = Integer.parseInt(args[2]); //maximum possible cylinder
        int numOfSector = Integer.parseInt(args[3]); //number of sectors in the disc


        Scheduler scheduler = new Scheduler();
        Disc disc = new Disc(7200, 2000, numOfSector, 1.0 / numOfSector, scheduler); //RPM,CYLINDERS,NUMBER OF SECTORS, SIZEofONErecord,SCHEDULER
        RequestGenerator requestGenerator = new RequestGenerator(disc);


        Event event;

        requestGenerator.generate(amountOfRequests, min, max, numOfSector);

        disc.start();

        double sum = 0;
        double sum1 = 0, sum2 = 0, sum3 = 0;

        PrintWriter writer = null;
        PrintWriter csvWriter = null;
        try {
            writer = new PrintWriter("simResult1.txt", "UTF-8");
            csvWriter = new PrintWriter("csvResult1.txt", "UTF-8");
            csvWriter.println("Request number, Phase 1(SeekPhase), Phase 2 (Rotational Delay), Phase 3 (Transfer Time)");

            for (int i = 0; i < amountOfRequests; i++) {
                writer.println("Request number: " + i + 1);
                int tmpCnt = i+1;
                csvWriter.print("" + tmpCnt + ",");
                List<Event> threeEvents = scheduler.nextEvent();

                sum1 += threeEvents.get(0).getTime(); //testing only
                sum2 += threeEvents.get(1).getTime(); //testing only
                sum3 += threeEvents.get(2).getTime(); //testing only

                csvWriter.println(threeEvents.get(0).getTime() + "," +
                        threeEvents.get(1).getTime() + "," +
                        threeEvents.get(2).getTime());

                for (Event e : threeEvents) {
                    sum += e.getTime();
                    System.out.println(e);
                    writer.println(e);
                }
            }
            sum = sum / amountOfRequests; //racunanje proseka po zahtevu

            System.out.println("Average time per request: " + sum + " ms");
            writer.println("Average time per request: " + sum + " ms");

            System.out.println("Average time phase 1: " + sum1 / amountOfRequests + " ms");
            writer.println("Average time phase 1: " + sum1 / amountOfRequests + " ms");

            System.out.println("Average time phase 2: " + sum2 / amountOfRequests + " ms");
            writer.println("Average time phase 2: " + sum2 / amountOfRequests + " ms");

            System.out.println("Average time phase 3: " + sum3 / amountOfRequests + " ms");
            writer.println("Average time phase 3: " + sum3 / amountOfRequests + " ms");

            disc.interrupt();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
            if (csvWriter != null)
                csvWriter.close();
        }
    }
}
