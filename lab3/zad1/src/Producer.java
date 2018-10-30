public class Producer implements Runnable{
    //private myBuffer myBuf;
    private BoundedBuffer myBoundedBuffer;
    String id;

    public Producer(BoundedBuffer myBuf, String id) {
        this.myBoundedBuffer = myBuf;
        this.id=id;
    }

    public void run() {
        for(int i = 0;  i < 10; i++) {
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
