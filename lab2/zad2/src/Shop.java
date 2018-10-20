import java.util.Random;

class Shop {

    private mySemaphore sem;

    Shop(int Counter){
        this.sem = new mySemaphore(Counter);
    }



    void enter(String id, int attempt){
        sem.decrement();
        System.out.println("Customer "+id+"." + attempt +" entered the shop");
        Random generator =  new Random();
        int i = generator.nextInt(10)+1;
        System.out.println("Customer " + id +"." + attempt + " goes sleep for " + i + " seconds");
        try{
            Thread.sleep(i*1000);
        }catch (InterruptedException ex){
            System.out.println("Err in sleep");
        }
        System.out.println("Customer "+id+"." + attempt +" left the shop");
        sem.increment();
    }

}
