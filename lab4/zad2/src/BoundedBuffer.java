import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private Object[] items;
    private int putptr, takeptr, count;


    BoundedBuffer(int bufferSize){
        this.items= new Object[2*bufferSize];
    }


    void put(LinkedList<String> x, int size) throws InterruptedException {
        lock.lock();
        try {
            //Naive Way
            while (count >= items.length-size)
                notFull.await();
            for(int i=0; i<size; i++){
                items[putptr]= x.get(i);
                if (++putptr == items.length) putptr = 0;
                ++count;
            }
            notEmpty.signal();


        } finally {
            lock.unlock();
        }
    }

    LinkedList<Object> take(int size) throws InterruptedException {
        lock.lock();
        try {
            //Naive way
            while (count - size < 0)
                notEmpty.await();

            LinkedList<Object> toReturn = new LinkedList<>();
            for(int i=0; i<size; i++) {
                toReturn.add(items[takeptr]);
                if (++takeptr == items.length) takeptr = 0;
                --count;
            }
            notFull.signal();
            return toReturn;

        } finally {
            lock.unlock();
        }
    }

}