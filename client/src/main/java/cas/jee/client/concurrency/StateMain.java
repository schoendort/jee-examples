package cas.jee.client.concurrency;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import cas.jee.ejb.client.ServerStorage;

public class StateMain
{

    public static void main(String[] args)
        throws InterruptedException
    {
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        env.put("jboss.naming.client.ejb.context", true);
        try
        {
            Context remoteContext = new InitialContext(env);
            ServerStorage storageStateful = (ServerStorage) remoteContext
                .lookup("jeeexample/StatefulStorage!cas.jee.ejb.client.ServerStorage");
            ServerStorage storageStateless = (ServerStorage) remoteContext
                .lookup("jeeexample/StatelessStorage!cas.jee.ejb.client.ServerStorage");

            storageStateful.saveData("key", "value");
            storageStateless.saveData("key", "value");

            System.out.println(storageStateless.readData("key"));
            System.out.println(storageStateful.readData("key"));

            ServerStorage storageStateless2 = (ServerStorage) remoteContext
                .lookup("jeeexample/StatelessStorage!cas.jee.ejb.client.ServerStorage");
            ServerStorage storageStateful2 = (ServerStorage) remoteContext
                .lookup("jeeexample/StatefulStorage!cas.jee.ejb.client.ServerStorage");

            System.out.println(storageStateless2.readData("key"));
            System.out.println(storageStateful2.readData("key"));
        }
        catch ( NamingException e )
        {
            e.printStackTrace();
        }
    }
}
