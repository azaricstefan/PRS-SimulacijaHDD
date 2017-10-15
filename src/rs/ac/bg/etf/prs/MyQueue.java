package rs.ac.bg.etf.prs;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by staz on 1.10.2017. 21:23
 */
public class MyQueue {

    private Queue<Request> queue;

    public MyQueue(){
        queue = new ArrayDeque<Request>();
    }

    public Queue<Request> getQueue() {
        return queue;
    }

}
