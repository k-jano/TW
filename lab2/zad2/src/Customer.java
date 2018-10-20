public class Customer implements Runnable {
    private Shop shop;
    private String id;

    Customer(Shop shop, String id) {
        this.shop = shop;
        this.id = id;
    }

    public void run() {
        for(int i = 0;  i < 10; i++) {
            shop.enter(id, i);
            try{
                Thread.sleep(500);
            }catch (InterruptedException ex){
                System.out.println("Err in sleep in Customer");
            }
        }

    }
}
