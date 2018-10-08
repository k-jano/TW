
public class Consumer implements Runnable {
    private Buffer buffer;
    String id;

    public Consumer(Buffer buffer, String id) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {
        for(int i = 0;  i < 10; i++) {
            String message = buffer.take();
            System.out.println("Consumer "+ this.id +": " +message);
        }
    }
}
