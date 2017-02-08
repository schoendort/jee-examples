package cas.jee.ejb.client;

import java.util.List;

public interface ServerStorage {

	void saveData(String key, String value);

	String readData(String key);

	List<String> filterValues(String contains);

	void removeData(String key);

}
