import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class App {
    public static void main (String[] args){

        int ProducerCount = 2;
        int ConsumerCount = 2;
        int ProcessingProcCount = 5;

        BoundedBuffer myBoundedBuffer =  new BoundedBuffer(ProcessingProcCount);

        LinkedList<Producer> producers = new LinkedList<>();
        LinkedList<Consumer> consumers = new LinkedList<>();
        LinkedList<ProcessingProcess> processes = new LinkedList<>();

        for(int i=0; i<ProducerCount; i++)
            producers.add(new Producer(myBoundedBuffer, "P" + i, 10));

        for(int i=0; i<ConsumerCount; i++)
            consumers.add(new Consumer(myBoundedBuffer, "c"+i, 10));

        for(int i=0; i<ProcessingProcCount; i++)
            processes.add(new ProcessingProcess(myBoundedBuffer, i, 10*ProducerCount));

        LinkedList<Thread> prodThreat = new LinkedList<>();
        LinkedList<Thread> consThreat = new LinkedList<>();
        LinkedList<Thread> procThreat = new LinkedList<>();

        for(int i=0; i<ProducerCount; i++)
            prodThreat.add(new Thread(producers.get(i)));

        for(int i=0; i<ConsumerCount; i++)
            consThreat.add(new Thread(consumers.get(i)));

        for(int i=0; i<ProcessingProcCount; i++)
            procThreat.add(new Thread(processes.get(i)));


        for(int i=0; i<ProducerCount; i++)
            prodThreat.get(i).start();

        for(int i=0; i<ConsumerCount; i++)
            consThreat.get(i).start();

        for(int i=0; i<ProcessingProcCount; i++)
            procThreat.get(i).start();


        try{
            myBoundedBuffer.test();
            for(int i=0; i<ProducerCount; i++)
                prodThreat.get(i).join();

            for(int i=0; i<ConsumerCount; i++)
                consThreat.get(i).join();

            for(int i=0; i<ProcessingProcCount; i++)
                procThreat.get(i).join();

        }catch (Exception e){
            System.out.println("zad1App" + e);
        }
    }
}