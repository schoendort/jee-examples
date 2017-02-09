package cas.jee.concurrency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;

import cas.jee.cdi.AuditInterceptor;
import cas.jee.ejb.client.ServerStorage;

@Singleton
@Remote(ServerStorage.class)
@Interceptors(AuditInterceptor.class)
public class ServerStorageImpl
    implements ServerStorage
{

    private static final Logger LOGGER =
        Logger.getLogger(ServerStorageImpl.class.getName());

    private Map<String, String> storage = new HashMap<>();

    @Override
    @Interceptors(AuditInterceptor.class)
    public void saveData(String key, String value)
    {
        LOGGER.severe("save data [" + key + "=" + value + "]");
        storage.put(key, value);
    }

    @Override
    public String readData(String key)
    {
        LOGGER.severe("read data for key = [" + key + "]");
        return storage.get(key);
    }

    @Override
    public List<String> filterValues(String contains)
    {
        return storage.values()
            .stream()
            .filter(v -> v.contains(contains))
            .collect(Collectors.toList());
    }

    @Override
    public void removeData(String key)
    {
        storage.remove(key);
    }

}
