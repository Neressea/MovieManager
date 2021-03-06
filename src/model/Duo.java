package model;

public class Duo<K extends Movie, V extends Barycentrable<K>>{
	private K key;
	private V value;

	public Duo (V newlist){
		this.value = newlist;
		this.key = newlist.getBarycentre();
	}

	public K getK(){
		return this.key;
	}

	public V getV(){
		return this.value;
	}
}
