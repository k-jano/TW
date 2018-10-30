public class myBuffer {

    private String myBuf;
    private mySemaphore semP = new mySemaphore(true);
    private mySemaphore semC = new mySemaphore(false);


    public void put(String msg){
        semP.decrement();
        this.myBuf=msg;
        semC.increment();
    }

    public String take(){
        semC.decrement();
        String toReturn = this.myBuf;
        semP.increment();
        return toReturn;
    }
}
