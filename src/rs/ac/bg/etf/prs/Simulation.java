package rs.ac.bg.etf.prs;

public class Simulation {

    private static RequestGenerator requestGenerator;
    private static MyRunner myRunner;

    public static void main(String[] args) {

        int max = 1000; //maximum possible cylinder
        int numOfSector = 6;

        int expRate = 2; //exponential generator param
        int randSeed = 20; //exponential generator param

        long startTime = System.nanoTime();

        Scheduler scheduler = new Scheduler();
        Disc disc = new Disc(7200, 2000, 1.0 / numOfSector); //RPM,CYLINDERS,SIZEofONErecord,SCHEDULER
        MyQueue myQueue = new MyQueue();
        requestGenerator = new RequestGenerator(randSeed, expRate, myQueue, max);
        requestGenerator.start();

        myRunner = new MyRunner(disc, myQueue);
        disc.addObserver(myRunner);
        myRunner.startRunner();

        while (true) {
            long estimatedTime = System.nanoTime() - startTime;
            double seconds = (double) estimatedTime / 1000000000.0;
            if (seconds > 5) {//izvrsi simulaciju toliko sekundi
                stopSimulation();
                break;
            }
        }
    }

    public static void stopSimulation(){
        requestGenerator.interrupt();
        //myRunner.interrupt();
    }
}
