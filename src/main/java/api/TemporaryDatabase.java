package api;

import java.util.HashMap;
import java.util.Map;

public class TemporaryDatabase {
	
	private Map<Integer,User> users;
	private Map<Integer,Item> items;
	
	public TemporaryDatabase() {
		users = new HashMap<>();
		items = new HashMap<>();
	}
	
	public int addUser(User user) {
		int uid = users.size()+1;
		users.put(uid, user);
		return uid;
	}
	
	public int addItem(Item item) {
		int id = items.size()+1;
		items.put(id, item);
		return id;
	}
	
	public Tuple<Integer,User> getUserByID(int uid) {
		User u = users.get(uid);
		
		if (u==null) return new Tuple<>(null,null);
		else return new Tuple<>(uid,u);
	}
	
	public Tuple<Integer,User> getUserByName(String name) {
		for (Integer k: users.keySet()) {
			if (users.get(k).getName().equals(name)) return new Tuple<>(k,users.get(k));
		}
		return new Tuple<>(null,null);
	}
	
	public Tuple<Integer,Item> getItemByID(int uid) {
		Item i = items.get(uid);
		
		if (i==null) return new Tuple<>(null,null);
		else return new Tuple<>(uid,i);
	}
	
	public Tuple<Integer,Item> getItemByName(String name) {
		for (Integer k: items.keySet()) {
			if (items.get(k).getName().equals(name)) return new Tuple<>(k,items.get(k));
		}
		return new Tuple<>(null,null);
	}

}
