public class Producer implements Runnable{
    private myBuffer myBuf;
    String id;

    public Producer(myBuffer myBuf, String id) {
        this.myBuf = myBuf;
        this.id=id;
    }

    public void run() {
        for(int i = 0;  i < 10; i++) {
            myBuf.put("from "+id+ " message "+i);
            System.out.println("Producer "+ this.id+ ": message "+i);
        }
    }
}
