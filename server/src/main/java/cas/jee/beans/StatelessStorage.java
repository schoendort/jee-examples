package cas.jee.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import cas.jee.ejb.client.ServerStorage;

@Stateless
@Remote(ServerStorage.class)
public class StatelessStorage implements ServerStorage {

	private Map<String, String> storage = new HashMap<>();

	@Override
	public void saveData(String key, String value) {
		storage.put(key, value);
	}

	@Override
	public String readData(String key) {
		return storage.get(key);
	}

	@Override
	public List<String> filterValues(String contains) {
		return storage.values().stream().filter(v -> v.contains(contains)).collect(Collectors.toList());
	}

	@Override
	public void removeData(String key) {
		storage.remove(key);
	}

}
