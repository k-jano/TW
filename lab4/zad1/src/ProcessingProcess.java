public class ProcessingProcess implements Runnable {
    private BoundedBuffer myBoundedBuffer;
    private int id;
    private int range;

    ProcessingProcess(BoundedBuffer myBuf, int id, int range){
        this.myBoundedBuffer=myBuf;
        this.id=id;
        this.range=range;
    }

    int getId() {
        return id;
    }

    public void run(){
        int i=0;
        while (i<range){
            try {
                int res =myBoundedBuffer.process(this);
                i+=res;
            } catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}
