public class Zad2App {
    public static void main (String[] args){
        Counter myCounter = new Counter();
        MyThreadInc t1 = new MyThreadInc(myCounter);
        MyThreadDec t2 = new MyThreadDec(myCounter);
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch (Exception e){
            System.out.println("e");
        }

        myCounter.printing();
    }
}
