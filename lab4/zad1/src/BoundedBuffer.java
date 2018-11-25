import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    private int procCount;
    private int[] flagArray;
    private int Var = 100;
    //private final Lock lock = new ReentrantLock();
    private final ArrayList<Lock> lockArrayList = new ArrayList<>();
    private final ArrayList<Condition> toProduceList = new ArrayList<>();
    private final ArrayList<Condition> toConsumeList = new ArrayList<>();
    private final ArrayList<Condition> toProcessList = new ArrayList<>();
    //private final Condition notFull  = lock.newCondition();
    //private final Condition toConsume = lock.newCondition();
    //private final Condition toProcess = lock.newCondition();


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

        for (int i=0; i<Var; i++){
            Lock lock = new ReentrantLock();
            lockArrayList.add(lock);

            Condition toProduce = lock.newCondition();
            Condition toConsume = lock.newCondition();
            Condition toProcess = lock.newCondition();

            toProduceList.add(toProduce);
            toConsumeList.add(toConsume);
            toProcessList.add(toProcess);
        }

    }

    void test(){
        for(int i=0; i<procCount; i++)
            System.out.println(i+" : " + flagArray[i]);
    }

    void put(Object x) throws InterruptedException {
        lockArrayList.get(putptr).lock();
        int toUnlock=putptr;
        try {
            while (count == items.length) {
                Condition condition= toProduceList.get(putptr);
                condition.await();
            }
            items[putptr] = x;
            Condition condition= toProcessList.get(putptr);
            if (++putptr == items.length) putptr = 0;
            ++count;
            condition.signalAll();
            //for(int i=0; i<Var; i++)
              //  toProcessList.get(i).signalAll();

        } finally {
            lockArrayList.get(toUnlock).unlock();
        }

    }

    Object take() throws InterruptedException{
            int toUnlock = takeptr;
            lockArrayList.get(takeptr).lock();
            try {
                while (count == 0 || toConsumeCounter==0){
                    //System.out.println("Poszedlem spac takeptr" + takeptr);
                    Condition condition = toConsumeList.get(takeptr);
                    condition.await();
                }
                Object x = items[takeptr];
                //System.out.println("Pobralem " + takeptr);
                toConsumeCounter--;
                Condition condition = toProduceList.get(takeptr);
                if (++takeptr == items.length) takeptr = 0;
                --count;
                condition.signalAll();
                //for (int i=0; i<Var; i++)
                 //   toProduceList.get(i).signalAll();
                return x;
            } finally {
                lockArrayList.get(toUnlock).unlock();
            }

    }

    int process(ProcessingProcess pp) throws InterruptedException{
        Lock lock = lockArrayList.get(procptr);
        lock.lock();
        try {
            while (count == 0){
                Condition condition = toProcessList.get(procptr);
                condition.await();
            }

            int toReturn = 0;
            if(flagArray[pp.getId()]==1) {
                items[procptr] += " pp" + pp.getId();
                toReturn=1;

                if(pp.getId()<procCount-1){
                    flagArray[pp.getId()]=0;
                    flagArray[pp.getId()+1]=1;
                }
                if(pp.getId()==procCount-1) {
                    flagArray[pp.getId()]=0;
                    flagArray[0]=1;
                    flag[procptr]=true;
                    Condition condition = toConsumeList.get(procptr);
                    if (++procptr == items.length) procptr = 0;
                    toConsumeCounter++;
                    condition.signalAll();
                    //for(int i=0; i<Var; i++)
                     //   toProcessList.get(i).signalAll();
                }
            }

            return toReturn;
        } finally {
            lock.unlock();
        }
    }
}