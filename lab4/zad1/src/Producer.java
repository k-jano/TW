public class Producer implements Runnable{
    //private myBuffer myBuf;
    private BoundedBuffer myBoundedBuffer;
    private String id;
    private int range;

    Producer(BoundedBuffer myBuf, String id, int range) {
        this.myBoundedBuffer = myBuf;
        this.id=id;
        this.range=range;
    }

    public void run() {
        for(int i = 0;  i < range; i++) {
            //myBuf.put("from "+id+ " message "+i);
            try{
                myBoundedBuffer.put("from "+id+ " message "+i);
            }catch (InterruptedException e){
                System.out.println(e);
            }

            System.out.println("Producer "+ this.id+ ": message "+i);
        }
    }
}