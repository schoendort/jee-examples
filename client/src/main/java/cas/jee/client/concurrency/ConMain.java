package cas.jee.client.concurrency;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import cas.jee.ejb.client.ServerStorage;

public class ConMain {

	public static void main(String[] args) throws InterruptedException {
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		env.put("jboss.naming.client.ejb.context", true);
		try {
			Context remoteContext = new InitialContext(env);
			ServerStorage storage = (ServerStorage) remoteContext
					.lookup("concurrency/ServerStorageImpl!cas.jee.ejb.client.ServerStorage");
			storage.saveData("key", "value");
			System.out.println(storage.getClass());
			System.out.println(storage.readData("key"));

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
		} catch (NamingException e)

		{
			e.printStackTrace();
		}

	}

}
