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
                myBoundedBuffer.put(toBuffer, rand);1557776
                12000
                1555554
                171555
                31556
                11720879
                1037777
                104000
                2333776
                367555
                11011546
                11214657
                40000
                11605323
                2298664
                19839538
                1771110
                1371110
                20873759
                3556
                1206221
                1201332
                18374650
                17960872
                11556
                1520888
                1935999
                19051094
                19243538
                2053776
                1554221
                1577332
                9158658
                1070221
                1493776
                8355104
                1898665
                2195554
                1847998
                7055549
                12202212
                2125776
                2020887
                1819998
                1746665
                1529776
                5368884
                1746221
                1802665
                1139999
                1117332
                1172443
                12889
                405778
                23111
                1558665
                1070221
                1410221
                1364443
                580889
                1490666
                4292885
                1919554
                3024886
                13450210
                1540888
                1559554
                606666
                2492886
                2399998
                2237776
                13852877
                2111109
                3616886
                28444
                3111
                2059553
                1343110
                1311554
                1458221
                1581776
                1497777
                1589777
                1528443
                1516888
                1407999
                1115999
                2210220
                1360443
                12889
                824888
                1080888
                720888
                552888
                571999
                700888
                1293777
                736000
                183111
                20000
                long estimatedTime = System.nanoTime() - startTime;
                //System.out.println("Producer " + id + " : " + estimatedTime + " ns");
                System.out.println(estimatedTime);
            }catch (InterruptedException e){
                System.out.println(e);
            }

            for(int i=0; i<globalRand; i++) {
                //System.out.println("Producer " + this.id + ": message " + globalCounter + " part " + i);
            }
            globalCounter++;


            /*
            //Sleeping helps analize results
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                System.out.println("Err in sleep");
            }
            */

        }
    }
}