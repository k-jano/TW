public class Consumer implements Runnable{
    //private myBuffer myBuf;
    private BoundedBuffer myBoundedBuffer;
    private String id;
    private int range;

    Consumer(BoundedBuffer myBuf, String id, int range) {
        this.myBoundedBuffer=myBuf;
        this.id = id;
        this.range=range;
    }

    public void run() {
        for(int i = 0;  i < range; i++) {
            //String message = myBuf.take();
            try{
                String message= myBoundedBuffer.take().toString();
                System.out.println("Consumer "+ this.id +": " +message);
            }catch (InterruptedException e){
                System.out.println(e);
            }


        }
    }
}