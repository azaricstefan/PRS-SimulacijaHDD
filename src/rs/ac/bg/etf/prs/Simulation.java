package rs.ac.bg.etf.prs;

import javax.swing.*;

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


        //GUI
        JFrame frame = new JFrame("Form");
        frame.setContentPane(new Form(disc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(500,500);
        //


        while(true){
            Request currentRequest = scheduler.nextRequest();



            if(currentRequest == null){
                break;
            }
        }
    }
}
