package rs.ac.bg.etf.prs;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by staz on 1.10.2017. 21:23
 */
public class MyQueue {

    private Queue<Double> queue;

    public MyQueue(){
        queue = new ConcurrentLinkedQueue<>();
    }

    public Queue<Double> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Double> queue) {
        this.queue = queue;
    }
}
