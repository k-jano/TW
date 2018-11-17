import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    private int procCount;
    private int[] flagArray;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition toConsume = lock.newCondition();
    private final Condition toProcess = lock.newCondition();

    private int Var = 100;
    private final Object[] items = new Object[Var];
    private final Boolean[] flag = new Boolean[Var];
    private int putptr, takeptr, procptr, count;


    private int toConsumeCounter = 0;
    BoundedBuffer(int procCount){
        this.procCount=procCount;
        this.flagArray= new int[procCount];
        for(int i=0; i<procCount; i++)
            flagArray[i]=0;

        flagArray[0]=1;

        for(int i=0; i<Var; i++)
            flag[i]=false;
    }

    void test(){
        for(int i=0; i<procCount; i++)
            System.out.println(i+" : " + flagArray[i]);
    }

    void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            toProcess.signalAll();
        } finally {
            lock.unlock();
        }
    }

    Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0 || toConsumeCounter==0)
                toConsume.await();
            Object x = items[takeptr];
            toConsumeCounter--;
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    int process(ProcessingProcess pp) throws InterruptedException{
        lock.lock();
        try {
            while (count == 0)
                toProcess.await();

            int toReturn = 0;
            if(flagArray[pp.getId()]==1) {
                items[procptr] += " pp" + pp.getId();
                //System.out.println("PP " + pp.getId()+" : "+ items[procptr]);
                toReturn=1;

                if(pp.getId()<procCount-1){
                    flagArray[pp.getId()]=0;
                    flagArray[pp.getId()+1]=1;
                }
                if(pp.getId()==procCount-1) {
                    flagArray[pp.getId()]=0;
                    flagArray[0]=1;
                    flag[procptr]=true;
                    if (++procptr == items.length) procptr = 0;
                    toConsumeCounter++;
                    toConsume.signal();
                }
            }

            return toReturn;
        } finally {
            lock.unlock();
        }
    }
}