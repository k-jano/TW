public class mySemaphore {

    private boolean isActive;

    mySemaphore(boolean myBool){
        isActive=myBool;
    }


    public synchronized void decrement(){
        while (!isActive) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("e in waiting");
            }
        }
        isActive = false;
    }

    public synchronized void increment(){
        isActive=true;
        notifyAll();
    }
}
