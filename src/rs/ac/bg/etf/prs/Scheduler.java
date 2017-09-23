package rs.ac.bg.etf.prs;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 20-Sep-17.
 */

/**
 * Ova klasa sadrži Event-ove koje je izgenerisao {@link Disc} u redu za čekanje
 */
public class Scheduler {

    private Request currentRequest;

    private Queue<Event> queue;

    public Scheduler(){
        queue = new ArrayDeque<>();
    }

    /**
     *
     * @return next request from the queue or null if no more requests
     */
    public List<Event> nextEvent() {
        while(queue.isEmpty());
        List<Event> ret = new LinkedList<>();

        ret.add(queue.poll());
        while(queue.isEmpty());

        ret.add(queue.poll());
        while(queue.isEmpty());

        ret.add(queue.poll());
        return ret;
    }

    public void putEvent(Event event){
        queue.add(event);
    }
}
