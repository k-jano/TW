import java.util.Random;

public class Printer {
    private int id;
    private boolean isActive;

    Printer(int id){
        this.id=id;
    }

    int getID(){
        return id;
    }

    boolean getisActive(){
        return isActive;
    }

    void print(String msg){
        System.out.println("Printer "+ id + " : " + msg);

        /*
        Random generator =  new Random();
        int i = generator.nextInt(3)+1;
        //System.out.println("Printer " + id + " cleaning for " + i + " seconds");
        try{
            Thread.sleep(i*1000);
        }catch (InterruptedException ex){
            System.out.println("Err in sleep");
        }
        */
    }

}
