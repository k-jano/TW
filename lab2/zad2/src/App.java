public class App {
    public static void main (String[] args){
        int myCounter = 3;
        Shop shop = new Shop(myCounter);


        Customer c1 = new Customer(shop, "c1");
        Customer c2 = new Customer(shop, "c2");
        Customer c3 = new Customer(shop, "c3");
        Customer c4 = new Customer(shop, "c4");
        Customer c5 = new Customer(shop, "c5");
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        Thread t3 = new Thread(c3);
        Thread t4 = new Thread(c4);
        Thread t5 = new Thread(c5);

        t3.start();
        t1.start();
        t2.start();
        t4.start();
        t5.start();

        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        }catch (Exception e){
            System.out.println("App" + e);
        }
    }
}