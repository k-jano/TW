public class App {
    public static void main (String[] args){

        BoundedBuffer myBoundedBuffer =  new BoundedBuffer();

        Producer p1 = new Producer(myBoundedBuffer, "p1");
        Consumer c1 = new Consumer(myBoundedBuffer, "c1");
        Producer p2 = new Producer(myBoundedBuffer, "p2");
        Consumer c2 = new Consumer(myBoundedBuffer, "c2");
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(c1);
        Thread t3 = new Thread(p2);
        Thread t4 = new Thread(c2);

        t3.start();
        t1.start();
        t2.start();
        t4.start();

        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }catch (Exception e){
            System.out.println("zad3App" + e);
        }
    }
}