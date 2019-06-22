package api;

public class Tuple<K,V> {
	
	private K k;
	private V v;

	public Tuple(K k, V v) {
		this.k = k;
		this.v = v;
	}
	
	public boolean empty() {
		return k==null && v==null;
	}

	public K getKey() {
		return k;
	}

	public void setKey(K k) {
		this.k = k;
	}

	public V getValue() {
		return v;
	}

	public void setValue(V v) {
		this.v = v;
	}
	
	
}
