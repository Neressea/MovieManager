package model;

public class Duo<K, V>{
	private K key;
	private V value;

	public static void main(String[] args) {
		Duo d = new Duo<Integer, String>();
	}
}
