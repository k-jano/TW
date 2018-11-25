import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Condition pWaiting = lock.newCondition();
    private final Condition cWaiting = lock.newCondition();

    private Object[] items;
    private int putptr, takeptr, count;


    BoundedBuffer(int bufferSize){
        this.items= new Object[2*bufferSize];
    }


    void put(LinkedList<String> x, int size) throws InterruptedException {
        lock.lock();
        try {
            //Naive Way

            while (count >= items.length)
                notFull.await();

            while (count >= items.length-size)
                pWaiting.await();

            for(int i=0; i<size; i++){
                items[putptr]= x.get(i);
                if (++putptr == items.length) putptr = 0;
                ++count;
            }
            cWaiting.signalAll();
            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    LinkedList<Object> take(int size) throws InterruptedException {
        lock.lock();
        try {

            //Naive way
            while (count < 0)
                notEmpty.await();

            while (count - size < 0)
                cWaiting.await();

            LinkedList<Object> toReturn = new LinkedList<>();
            for(int i=0; i<size; i++) {
                toReturn.add(items[takeptr]);
                if (++takeptr == items.length) takeptr = 0;
                --count;
            }
            pWaiting.signalAll();
            notFull.signalAll();
            return toReturn;

        } finally {
            lock.unlock();
        }
    }

}