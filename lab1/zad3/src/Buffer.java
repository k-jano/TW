
public class Buffer {
    private String myBuf;
    private Boolean flag;

    Buffer(){
        flag=false;
    }

    public synchronized void put(String msg){
        while (this.flag){
            try{
                wait();
            }catch (Exception e){
                System.out.println("e in waiting");
            }
        }
        this.myBuf=msg;
        flag=true;
        notifyAll();
    }

    public synchronized String take(){
        while (!this.flag){
            try{
                wait();
            }catch (Exception e){
                System.out.println("e in waiting");
            }
        }
        flag=false;
        notifyAll();
        return this.myBuf;

    }
}
