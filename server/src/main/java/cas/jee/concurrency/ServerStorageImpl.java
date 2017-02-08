package cas.jee.concurrency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import cas.jee.ejb.client.ServerStorage;

@Singleton
@Remote(ServerStorage.class)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ServerStorageImpl implements ServerStorage {

	private static final Logger LOGGER = Logger.getLogger(ServerStorageImpl.class.getName());

	private Map<String, String> storage = new HashMap<>();

	@Override
	@Lock(LockType.WRITE)
	public void saveData(String key, String value) {
		LOGGER.severe("save data [" + key + "=" + value + "]");
		storage.put(key, value);
	}

	@Override
	@Lock(LockType.WRITE)
	public String readData(String key) {
		LOGGER.severe("read data for key = [" + key + "]");
		return storage.get(key);
	}

	@Override
	@Lock(LockType.WRITE)
	public List<String> filterValues(String contains) {
		return storage.values().stream().filter(v -> v.contains(contains)).collect(Collectors.toList());
	}

	@Override
	@Lock(LockType.WRITE)
	public void removeData(String key) {
		storage.remove(key);
	}

}
