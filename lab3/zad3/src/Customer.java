import java.util.Random;

public class Customer implements Runnable{
    private String id;
    private Weiter weiter;

    Customer(String id, Weiter weiter){
        this.id=id;
        this.weiter=weiter;
    }

    String getId(){
        return this.id;
    }

    public void run(){
        while (true){
            //#WłasneSprawe
            Random generator =  new Random();
            int i = generator.nextInt(10)+1;
            try{
                Thread.sleep(i*1000);
            }catch (InterruptedException ex){
                System.out.println("Err in sleep");
            }

            try {
                weiter.wantTable(this);
            } catch (Exception e){
                System.out.println(e);
            }


            //#Jedzenie
            Random generator2 =  new Random();
            int i2 = generator2.nextInt(5)+1;
            System.out.println("Customer " + id + " eating for " + i2 + " seconds");
            try{
                Thread.sleep(i2*1000);
            }catch (InterruptedException ex) {
                System.out.println("Err in sleep");
            }


            weiter.freeTable(this);


            System.out.println("Customer " + id + " left table");
        }
    }
}
