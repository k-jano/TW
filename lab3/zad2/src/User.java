import java.util.Random;

public class User implements Runnable {
    private String id;
    private Printer_monitor monitor;
    private Printer printer;
    private int counter;

    User(String id, Printer_monitor monitor){
        this.id=id;
        this.monitor=monitor;
        this.counter=0;
    }

    public void run(){
        while(true){
            try{
                this.printer=monitor.book();
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("User "+this.id+" reserved printer " + this.printer.getID());
            String msg= "document "+counter+" from user "+this.id;
            this.printer.print(msg);



            monitor.give(this.printer);
            counter++;

            /*
            Random generator =  new Random();
            int i = generator.nextInt(6)+1;
            //System.out.println("User " + id + " waiting " + i + " seconds");
            try{
                Thread.sleep(i*1000);
            }catch (InterruptedException ex){
                System.out.println("Err in sleep");
            }
            */
        }
    }

}
