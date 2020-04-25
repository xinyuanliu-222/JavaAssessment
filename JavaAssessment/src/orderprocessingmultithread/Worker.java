package orderprocessingmultithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Worker {

	private static int MAX_ORDERS=10;
    private static int MAX_THREADS =5;
    private static int SLEEP =10;
    
    private static  List<Order> list=new ArrayList<>();
    static {
        for(int i = 1; i <= MAX_ORDERS; i++)
            list.add(new Order("NEW", i));
    }
	
	public static void main(String[] args) {
		final CyclicBarrier cyclicBarrier=new CyclicBarrier(MAX_THREADS+1);
		for (int i = 0; i < MAX_THREADS; i++) {
			new Thread(new Runnable() {
				public void run() {
					while(!list.isEmpty()) {
						Order order = null;
						for (Order item : list) {
							if (item.getLock(item.getOrderNum())) {
								order = item;
								break;
							}
						}
						if (order != null && order.getState().equals("NEW")) {
							System.out.println("Processing order is " + order.getOrderNum());
							try {
								Thread.sleep(SLEEP);
								order.setState("FULFILLED");
								System.out.println("Order " + order.getOrderNum() + " is FULFILLED");
							}catch(Exception e) {
								e.printStackTrace();
							}finally {
								order.unLock(order.getOrderNum());
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
		try {
			cyclicBarrier.await();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class Order {
		public String state;
		public int orderNum;
		
		public Order(String state, int orderNum) {
			this.state = state;
			this.orderNum = orderNum;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public int getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(int orderNum) {
			this.orderNum = orderNum;
		}
		
		private boolean lock = true;
		private int num = 0;
		
		public boolean getLock(int num) {
			if (lock) {
				synchronized(this) {
					if (lock) {
						lock = false;
						this.num = num;
						return true;
					} else {
						System.out.println("Order was token!");
					}
				}
			}
			return lock;
		}
		
		public void unLock(int newNum) {
			if (newNum != this.num || lock) {
				System.out.println("error");
			} else {
				lock = true;
			}
		}
		
	}

}
