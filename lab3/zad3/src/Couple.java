public class Couple {
    private Customer c1;
    private Customer c2;

    Couple(Customer c1, Customer c2){
        this.c1=c1;
        this.c2=c2;
    }

    Customer getC1(){
        return c1;
    }

    Customer getC2(){
        return c2;
    }
}
