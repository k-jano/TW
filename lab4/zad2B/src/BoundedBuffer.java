import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition majorityProducers  = lock.newCondition();
    private final Condition majorityConsumers = lock.newCondition();
    private final Condition oneProducer = lock.newCondition();
    private final Condition oneConsumer = lock.newCondition();
    private boolean anyConsumerWaits = false;
    private boolean anyProducerWaits = false;

    private Object[] items;
    private int putptr, takeptr, count;


    BoundedBuffer(int bufferSize){
        this.items= new Object[2*bufferSize];
    }


    void put(LinkedList<String> x, int size) throws InterruptedException {
        lock.lock();
        try {
            //Justice way

            if(anyProducerWaits)
                majorityProducers.await();

            anyProducerWaits=true;
            while (count >= items.length-size)
                oneProducer.await();

            for(int i=0; i<size; i++){
                items[putptr]= x.get(i);
                if (++putptr == items.length) putptr = 0;
                ++count;
            }
            anyProducerWaits=false;
            majorityProducers.signalAll();
            oneConsumer.signalAll();

        } finally {
            lock.unlock();
        }
    }

    LinkedList<Object> take(int size) throws InterruptedException {
        lock.lock();
        try {

            //Naive way
            while (anyConsumerWaits)
                majorityConsumers.await();

            anyConsumerWaits=true;

            while (count - size < 0)
                oneConsumer.await();

            LinkedList<Object> toReturn = new LinkedList<>();
            for(int i=0; i<size; i++) {
                toReturn.add(items[takeptr]);
                if (++takeptr == items.length) takeptr = 0;
                --count;
            }
            anyConsumerWaits=false;
            majorityConsumers.signalAll();
            oneProducer.signalAll();

            return toReturn;

        } finally {
            lock.unlock();
        }
    }

}