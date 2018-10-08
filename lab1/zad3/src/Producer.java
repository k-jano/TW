
public class Producer implements Runnable {
    private Buffer buffer;
    String id;

    public Producer(Buffer buffer, String id) {
        this.buffer = buffer;
        this.id=id;
    }

    public void run() {
        for(int i = 0;  i < 10; i++) {
            buffer.put("from "+id+ " message "+i);
            System.out.println("Producer "+ this.id+ ": message "+i);
        }
    }
}
