package orderprocessingmultithread;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

public class ThreadTest {

    private static int MAX_ORDERS=100;
    private  static  int MAX_THREADS =5;
    private static  int SLEEP =10;
    
    private static  List<Order> list=new CopyOnWriteArrayList<>();
    static {
        for(int i=1;i<=MAX_ORDERS;i++)
            list.add(new Order("NEW", i));
    }
    public  static  void main(String ss[]){
        
        Long t=System.currentTimeMillis();
        
        final CyclicBarrier cyclicBarrier=new CyclicBarrier(MAX_THREADS+1);
        for(int i = 0; i< MAX_THREADS; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                        while (!list.isEmpty()) {
                            Order order = null;
                            
                            String uuid = UUID.randomUUID().toString();
                            int i = 0;
                            for (Order item : list) {
                                i++;
                                if (item.getLock(uuid)) {
                                    order = item;
                                    break;
                                }
                            }
                            if (order.equals("NEW")) {
                                System.out.println(i + ":" + Thread.currentThread().getId() + "(" + SLEEP + ")" + order.getApplyNo());
                                try {
                                    Thread.sleep(SLEEP);
                                    list.remove(order);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    order.unLock(uuid);
                                }
 
                            }
 
                        }
                        try {
                            cyclicBarrier.await();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
 
                    }
 
            }).start();
        }
        try{
            
            cyclicBarrier.await();
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis()-t)+"ms");
 
    }
 
    static  class  Order{
        private String states;
        private int orderNum;
 
        
        public Order(String states, int orderNum) {
			this.states = states;
			this.orderNum = orderNum;
		}
		
        public int getApplyNo() {
			// TODO Auto-generated method stub
			return 0;
		}

		public String getStates() {
			return states;
		}

		public int getOrderNum() {
			return orderNum;
		}

        private    boolean  lock=true;
        
        private  String token="";
 
       
        public boolean getLock(String token){
            if(lock){
                synchronized (this){
                    if(lock){
                        lock=false;
                        this.token=token;
                        return true;
                    }else{
                        System.out.println("Token!");
                    }
 
                }
 
            }
            return lock;
        }
 
        
        public void unLock(String newToken){
            if(!newToken.equals(this.token)||lock){
                System.out.println("error");
            }else{
                lock=true;
            }
        }
 
    }

}
