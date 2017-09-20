package rs.ac.bg.etf.prs;

public class Simulation {

    public static void main(String[] args) {

        Scheduler scheduler = new Scheduler();
        RequestGenerator requestGenerator = new RequestGenerator(scheduler);

        Disc disc = new Disc(7200,2000,500); //

        Event event;

        int amountOfRequests = Integer.parseInt(args[0]); //how many requests to generate
        int min = Integer.parseInt(args[1]); //lowest possible cylinder
        int max = Integer.parseInt(args[2]); //maximum possible cylinder

        requestGenerator.generate(amountOfRequests, min, max);


        while(true){
            Request currentRequest = scheduler.nextRequest();



            if(currentRequest == null){
                break;
            }
        }
    }
}
