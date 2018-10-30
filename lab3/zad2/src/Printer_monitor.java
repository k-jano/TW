import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printer_monitor {
    final Lock lock = new ReentrantLock();
    final Condition myCond = lock.newCondition();
    private LinkedList<Printer> myList;
    private int count;

    Printer_monitor(int count, LinkedList<Printer> listOfPrinters){
        this.count=count;
        this.myList = listOfPrinters;
    }

    void test(){
        System.out.println("My count " + count);
    }

    void testPrinters(){
        for(Printer p: myList){
            System.out.println("Printer: " +p.getID());
        }
    }

    Printer book() throws InterruptedException{
        lock.lock();
        try{
            while (myList.isEmpty())
                myCond.await();
            return myList.pop();
        } finally {
            lock.unlock();
        }
    }

    void give(Printer printer){
        lock.lock();
        try {
            myList.push(printer);
            myCond.signal();
        } finally {
            lock.unlock();
        }

    }



}
