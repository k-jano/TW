import sun.security.util.ManifestEntryVerifier;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    int threadCounter = 8;
    private ExecutorService executorService = Executors.newFixedThreadPool(threadCounter);
    private Set<Future<Part>> set = new HashSet<>();
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    //private int HeightCount = 600/threadCounter;
    //private int WidthCount = 800/threadCounter;
    //private int taskCounter=10;
    //private int HeightCount = 600/threadCounter/taskCounter;
    //private int WidthCount =800/threadCounter/taskCounter;
    private int HeightCount=600;
    private int WidthCount=800;


    public Mandelbrot() throws InterruptedException, ExecutionException{
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        int xStep = getWidth()/ WidthCount;
        int yStep = getHeight()/HeightCount;

        long start = System.currentTimeMillis();

        for(int i=0; i<WidthCount; i++){
            for(int j=0; j<HeightCount; j++){
                Callable<Part> callPart = new Parser(i*xStep, j*yStep, xStep, yStep, MAX_ITER, ZOOM);
                Future<Part> futPart = executorService.submit(callPart);
                set.add(futPart);
            }
        }

        for(Future<Part> future : set){
            Part part = future.get();
            for(int i=part.Ystart;  i<part.Ystart+part.Ystep; i++){
                for(int j=part.Xstart; j<part.Xstart+part.Xstep; j++)
                    I.setRGB(j,i, part.array[j-part.Xstart+(i-part.Ystart)*part.Xstep]);
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("Time: " + (end-start) + " ms");
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        try{
            for(int i=0; i<10; i++){
                new Mandelbrot();
            }
            //new Mandelbrot().setVisible(true);
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

    }
}

