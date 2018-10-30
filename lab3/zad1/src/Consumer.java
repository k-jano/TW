public class Consumer implements Runnable{
    //private myBuffer myBuf;
    private BoundedBuffer myBoundedBuffer;
    String id;

    public Consumer(BoundedBuffer myBuf, String id) {
        this.myBoundedBuffer=myBuf;
        this.id = id;
    }

    public void run() {
        for(int i = 0;  i < 10; i++) {
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