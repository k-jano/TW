import java.util.LinkedList;

public class App {
    public static void main (String[] args){
        LinkedList<Couple> customerList= new LinkedList<>();

        Weiter weiter = new Weiter(customerList);

        int customer_count=50;

        //LinkedList<Couple> customerList= new LinkedList<>();
        for(int i=0; i<customer_count/2; i++){
            Customer c1= new Customer("c"+i+".1", weiter);
            Customer c2 = new Customer("c"+i+".2", weiter);
            Couple couple = new Couple(c1, c2);
            customerList.add(couple);
        }

        LinkedList<Thread> threadList = new LinkedList<>();
        for (int i=0; i<customer_count/2; i++){
            Couple tmp = customerList.get(i);
            threadList.add(new Thread(tmp.getC1()));
            threadList.add(new Thread(tmp.getC2()));
        }

        for (int i=0; i<customer_count; i++){
            threadList.get(i).start();
        }

        try{
            for(int i=0; i<customer_count; i++){
                threadList.get(i).join();
            }
        }catch (Exception e){
            System.out.println("zad3App" + e);
        }
    }
}
