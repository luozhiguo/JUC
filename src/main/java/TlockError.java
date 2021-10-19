import java.util.concurrent.TimeUnit;

/**
 *
 * 当锁发生异常的时候，会默认释放锁，那本来该程序应该所有代码都执行完，在继续下一个线程调用，这个时候就会产生第一个线程调用同步方法时，执行了一半，而产生了异常，线程还没有执行完就中断了并且释放了锁
 * 那第二个线程发现获取锁成功了，接着使用上一个程序未执行完的临界区（本示例中就是count变量），这个时候临界区是脏数据，本该100的，但是此时是5，但是第二个线程接着执行，从5往上加，执行了100次
 * 本应该两个线程，每个线程count++100次，两个线程全部执行完应该是200次，但是现在都执行完了，会变成105次
 *
 */
public class TlockError {
    int count = 0;
    public synchronized void test1(){
        for(int i=0; i<100; i++){
            count++;
            System.out.println(Thread.currentThread().getName()+" "+count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                if(count == 5){
                    int a = 1/0 ;
                }
            }catch(Exception e){
                //e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        final TlockError obj = new TlockError();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                obj.test1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                obj.test1();
            }
        });
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
    }
}
