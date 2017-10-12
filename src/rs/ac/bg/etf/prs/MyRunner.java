package rs.ac.bg.etf.prs;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by staz on 1.10.2017. 21:37
 */
public class MyRunner extends Thread implements Observer{

    private Disc disc;
    private MyQueue myQueue;
    private long doneRequests = 1;
    private Double sum;
    private Double sum1;
    private Double sum2;
    private Double sum3;
    boolean done = false;
    PrintWriter csvWriter;

    public MyRunner(Disc disc, MyQueue myQueue){
        this.disc = disc;
        this.myQueue = myQueue;
        doneRequests = 0;
        sum = 0.0;
        sum1 = 0.0;
        sum2 = 0.0;
        sum3 = 0.0;
        try {
            csvWriter = new PrintWriter("csvResult1.txt", "UTF-8");
        } catch (Exception e) { e.printStackTrace(); }
        csvWriter.println("Request number, Phase 1(SeekPhase), Phase 2 (Rotational Delay), Phase 3 (Transfer Time)");
    }

    public void startRunner() {
        while(myQueue.getQueue().isEmpty());
        Request firstRequest = this.myQueue.getQueue().get(0);
        this.sendToHDD(firstRequest);
    }

    public void sendToHDD(Request request){
        List<Double> list = disc.receiveRequest(request); //HDD obradi zahtev
        try {
            csvWriter.print(doneRequests + ",");

            Double d = list.remove(0);
            System.out.println("Obradjen zahtev za [SEEK PHASE]: " + d);
            sleep(d.longValue());
            sum1 += d;
            csvWriter.print(d + ",");

            sum +=d;
            d = list.remove(0);
            System.out.println("Obradjen zahtev za [ROTATIONAL PHASE]: " + d);
            sleep(d.longValue());
            sum2 += d;
            csvWriter.print(d + ",");

            sum +=d;
            d = list.remove(0);
            System.out.println("Obradjen zahtev za [TRANSFER PHASE]: " + d);
            sleep(d.longValue());
            sum3 += d;
            csvWriter.println(d);

            sum +=d;
            doneRequests++;
        } catch (InterruptedException e) {
            done = true;
            System.out.println("RUNNER: STOPPED!");
        }
    }

 //   @Override
   // public void run() {
//        try {
//            csvWriter = new PrintWriter("csvResult1.txt", "UTF-8");
//        } catch (Exception e) { e.printStackTrace(); }
//        csvWriter.println("Request number, Phase 1(SeekPhase), Phase 2 (Rotational Delay), Phase 3 (Transfer Time)");

//        while(!done){
//            while(disc.isBusy()); //cekaj da disk zavrsi sa poslom
//            while(myQueue.getQueue().isEmpty());
//            sendToHDD(myQueue.getQueue().remove(0).getLength()); //posalji mu posao
//        }
//        System.out.println("Ostalo je jos: " + myQueue.getQueue().size() + " zahteva.");
//        while(!myQueue.getQueue().isEmpty())
//            sendToHDD(myQueue.getQueue().remove(0).getLength()); //posalji mu posao

//        System.out.println("Prosecno vreme po zahtevu : " + sum/doneRequests);
//        System.out.println("Prosecno vreme po zahtevu [FAZA 1]:" + sum1/doneRequests);
//        System.out.println("Prosecno vreme po zahtevu [FAZA 2]: " + sum2/doneRequests);
//        System.out.println("Prosecno vreme po zahtevu [FAZA 3]: " + sum3/doneRequests);
//        System.out.println("RUNNER: GOTOV, zavrsio preostale zahteve!");
//        if(csvWriter!= null)
//            csvWriter.close();
    //}

    @Override
    public void update(int requestId) {
        Request r = myQueue.getQueue().remove(0);

        if(myQueue.getQueue().isEmpty()){
            //krajnji ispis
            System.out.println("Prosecno vreme po zahtevu : " + sum/doneRequests);
            System.out.println("Prosecno vreme po zahtevu [FAZA 1]:" + sum1/doneRequests);
            System.out.println("Prosecno vreme po zahtevu [FAZA 2]: " + sum2/doneRequests);
            System.out.println("Prosecno vreme po zahtevu [FAZA 3]: " + sum3/doneRequests);

            if(csvWriter != null)
                csvWriter.close();
        } else {
            sendToHDD(r);
        }



    }
}
