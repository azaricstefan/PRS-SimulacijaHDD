package rs.ac.bg.etf.prs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */

/**
 * Ova klasa sadrži zahteve koje je izgenerisao {@link RequestGenerator} u redu za čekanje
 */
public class Scheduler {

    private Request currentRequest;

    private Queue<Request> queue;

    public Scheduler(){
        queue = new ArrayDeque<>();
    }

    /**
     *
     * @return next request from the queue or null if no more requests
     */
    public Request nextRequest() {
        return queue.poll();
    }

    public void putRequest(Request request){
        queue.add(request);
    }
}
