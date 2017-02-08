package cas.jee.techniques.proxy;

import cas.jee.ejb.client.ServerStorage;

public class ProxyMain {

	public static void main(String[] args) throws InterruptedException {
		ServerStorage storage = new Storage();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					storage.saveData("" + i, "v" + 1);
				}

			}
		});
		t1.start();
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					storage.filterValues("v" + 1);
				}

			}
		});
		t2.start();
		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					storage.removeData("" + 1);
				}

			}
		});
		t3.start();

		while (t1.isAlive() || t2.isAlive() || t3.isAlive()) {
			Thread.sleep(100);
		}
	}

}
