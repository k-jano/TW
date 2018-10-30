import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Weiter {
    private final Lock lock = new ReentrantLock();
    private final Condition myCond = lock.newCondition();
    private final Condition waitForSecond = lock.newCondition();
    private final Condition waitForEndEat = lock.newCondition();
    private LinkedList<Couple> listOfCouple;
    private Customer firstCustomer;
    private Customer secondCustomer;
    private boolean isBooked1;
    private boolean isBooked2;
    private boolean eatingFlag;

    Weiter(LinkedList<Couple> list){
        this.listOfCouple=list;
        isBooked1= false;
        isBooked2= false;
        eatingFlag = false;
    }

    //checkCouple TODO
    private boolean checkCouple(Customer c){
        for(Couple couple: listOfCouple){
            if(couple.getC1().equals(c)){
                if(couple.getC2().equals(firstCustomer))
                    return true;
            } else if(couple.getC2().equals(c)){
                if(couple.getC1().equals(firstCustomer))
                    return true;
            }
        }
        return false;
    }

    void wantTable(Customer c) throws InterruptedException{
        lock.lock();
        try {
            while (eatingFlag){
                waitForEndEat.await();
            }

            while (isBooked1) {
                if (!checkCouple(c)) {
                    myCond.await();
                } else {
                    break;
                }
            }

            if (!isBooked1) {
                isBooked1=true;
                this.firstCustomer=c;
                waitForSecond.await();
            } else {
                isBooked2=true;
                this.secondCustomer=c;
                eatingFlag=true;
                waitForSecond.signal();
            }


        } finally {

            lock.unlock();
        }
    }

    void freeTable(Customer c){
        lock.lock();

        try {
            if(c.equals(firstCustomer)){
                isBooked1=false;
                if(!isBooked2){
                    eatingFlag=false;
                    myCond.signalAll();
                    waitForEndEat.signalAll();
                }
            }else {
                isBooked2=false;
                if(!isBooked1){
                    eatingFlag=false;
                    myCond.signalAll();
                    waitForEndEat.signalAll();
                }
            }


        } finally {
            lock.unlock();
        }
    }



}
