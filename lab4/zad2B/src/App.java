import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class App {
    public static void main (String[] args){

        int ProducerCount = 10;
        int ConsumerCount = 10;
        int bufferSize = 1000;

        BoundedBuffer myBoundedBuffer =  new BoundedBuffer(bufferSize);

        LinkedList<Producer> producers = new LinkedList<>();
        LinkedList<Consumer> consumers = new LinkedList<>();

        for(int i=0; i<ProducerCount; i++)
            producers.add(new Producer(myBoundedBuffer, "P" + i, 10, bufferSize));

        for(int i=0; i<ConsumerCount; i++)
            consumers.add(new Consumer(myBoundedBuffer, "c"+i, 10, bufferSize));


        LinkedList<Thread> prodThreat = new LinkedList<>();
        LinkedList<Thread> consThreat = new LinkedList<>();

        for(int i=0; i<ProducerCount; i++)
            prodThreat.add(new Thread(producers.get(i)));

        for(int i=0; i<ConsumerCount; i++)
            consThreat.add(new Thread(consumers.get(i)));


        for(int i=0; i<ProducerCount; i++)
            prodThreat.get(i).start();

        for(int i=0; i<ConsumerCount; i++)
            consThreat.get(i).start();


        try{
            for(int i=0; i<ProducerCount; i++)
                prodThreat.get(i).join();

            for(int i=0; i<ConsumerCount; i++)
                consThreat.get(i).join();


        }catch (Exception e){
            System.out.println("zad1App" + e);
        }
    }
}