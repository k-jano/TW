import java.util.LinkedList;
import java.util.Random;

public class Producer implements Runnable{
    private BoundedBuffer myBoundedBuffer;
    private String id;
    private int range;
    private int bufferSize;

    Producer(BoundedBuffer myBuf, String id, int range, int bufferSize) {
        this.myBoundedBuffer = myBuf;
        this.id=id;
        this.range=range;
        this.bufferSize=bufferSize;
    }

    public void run() {
        int globalCounter= 0;
        int globalRand = 0;
        //while (true) {
        for(int j=0; j<10; j++){
            //myBuf.put("from "+id+ " message "+i);
            try{
                Random generator = new Random();
                int rand= generator.nextInt(bufferSize)+1;
                globalRand=rand;
                LinkedList<String> toBuffer = new LinkedList<>();
                for(int i=0; i<rand; i++){
                    toBuffer.add("from "+ id + ": message " + globalCounter + " part " + i);
                }
                long startTime = System.nanoTime();
                myBoundedBuffer.put(toBuffer, rand);
                long estimatedTime = System.nanoTime() - startTime;
                //System.out.println("Producer " + id + " : " + estimatedTime + " ns");
                //System.out.println(estimatedTime);
            }catch (InterruptedException e){
                System.out.println(e);
            }

            for(int i=0; i<globalRand; i++) {
                //System.out.println("Producer " + this.id + ": message " + globalCounter + " part " + i);
            }
            globalCounter++;


            //Sleeping helps analize results
            /*
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                System.out.println("Err in sleep");
            }
            */


        }
    }
}