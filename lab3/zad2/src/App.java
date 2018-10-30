import java.util.LinkedList;

public class App {
    public static void main (String[] args){
        LinkedList<Printer> printerList = new LinkedList<>();
        int count = 3;
        for(int i=0; i<count; i++){
            printerList.add(new Printer(i));
        }
        Printer_monitor myMonitor = new Printer_monitor(count, printerList);

        int user_count=50;
        LinkedList<User> userList= new LinkedList<>();
        for(int i=0; i<user_count; i++){
            userList.add(new User("u"+i, myMonitor));
        }

        LinkedList<Thread> threadList = new LinkedList<>();
        for (int i=0; i<user_count; i++){
            threadList.add(new Thread(userList.get(i)));
        }

        for (int i=0; i<user_count; i++){
            threadList.get(i).start();
        }

        try{
            for(int i=0; i<user_count; i++){
                threadList.get(i).join();
            }
        }catch (Exception e){
            System.out.println("zad3App" + e);
        }
    }
}
