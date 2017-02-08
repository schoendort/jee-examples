package cas.jee.techniques.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import cas.jee.ejb.client.ServerStorage;
import cas.jee.techniques.lock.LockInvocationHandler;

public class ProxyMain {

	public static void main(String[] args) throws InterruptedException {
		ServerStorage storage = getServerStorage();

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

	private static ServerStorage getServerStorage() {
		Storage target = new Storage();
		InvocationHandler handler = new LockInvocationHandler(target);
		ServerStorage proxyObj = (ServerStorage) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(),
				new Class<?>[] { ServerStorage.class }, handler);
		System.out.println(proxyObj.getClass());
		System.out.println(proxyObj instanceof ServerStorage);
		System.out.println(proxyObj instanceof Storage);
		System.out.println(proxyObj instanceof Proxy);
		return proxyObj;
	}

}
