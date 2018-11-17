
import java.util.LinkedList;
import java.util.Random;

public class Consumer implements Runnable{
    //private myBuffer myBuf;
    private BoundedBuffer myBoundedBuffer;
    private String id;
    private int range;
    private int bufferSize;

    Consumer(BoundedBuffer myBuf, String id, int range, int bufferSize) {
        this.myBoundedBuffer=myBuf;
        this.id = id;
        this.range=range;
        this.bufferSize=bufferSize;
    }

    public void run() {
        while (true){
            //String message = myBuf.take();
            try{
                Random generator = new Random();
                int rand=  generator.nextInt(bufferSize)+1;
                long startTime = System.nanoTime();
                LinkedList<Object> list = myBoundedBuffer.take(rand);
                long estimatedTime = System.nanoTime() - startTime;
                System.out.println("Consumer " + id + " : " + estimatedTime + " ns");
                for(int i=0; i<rand; i++) {
                    //System.out.println("Consumer " + id + " : " + list.get(i));
                }
            }catch (InterruptedException e){
                System.out.println(e);
            }

            //Sleeping helps analize results
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                System.out.println("Err in sleep");
            }



        }
    }
}