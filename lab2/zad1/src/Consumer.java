public class Consumer implements Runnable{
    private myBuffer myBuf;
    String id;

    public Consumer(myBuffer myBuf, String id) {
        this.myBuf=myBuf;
        this.id = id;
    }

    public void run() {
        for(int i = 0;  i < 10; i++) {
            String message = myBuf.take();
            System.out.println("Consumer "+ this.id +": " +message);
        }
    }
}