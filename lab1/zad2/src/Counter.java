public class Counter {

    public int var=0;

    public void increment(){
        this.var++;
    }

    public void decrement(){
        this.var--;
    }

    void printing(){
        System.out.println("Var =" + this.var);
    }
}