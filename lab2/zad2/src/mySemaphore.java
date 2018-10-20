class mySemaphore {

    private int Counter;

    mySemaphore(int Counter){
        this.Counter = Counter;
    }


    synchronized void decrement(){
        while (Counter <= 0) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("e in waiting");
            }
        }
        Counter --;
    }

    synchronized void increment(){
        Counter ++;
        notifyAll();
    }
}
