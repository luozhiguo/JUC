public class Tlock {

    public synchronized void test1(){
        System.out.println(Thread.currentThread().getName()+" start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" end");

    }

    public void test2(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            System.out.println(Thread.currentThread().getName()+" end");

    }

    public static void main(String[] args) {
        final Tlock tlock = new Tlock();
        Thread t1 = new Thread(new Runnable() {
                    public void run() {
                        tlock.test1();
                    }
                }, "t1");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                tlock.test2();
            }
        });
        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        t2.start();
    }

}
