package cas.jee.techniques.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cas.jee.ejb.client.ServerStorage;

public class Storage implements ServerStorage {

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
		List<String> result = new ArrayList<>();
		for (String value : storage.values()) {
			if (value.contains(contains)) {
				result.add(value);
			}
		}
		return result;
	}

	@Override
	public void removeData(String key) {
		storage.remove(key);
	}

}
