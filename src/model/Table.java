package model;

import java.util.ArrayList;

public class Table <K extends Movie, V extends Barycentrable<K>> implements Barycentrable<K>{
	private ArrayList<Duo<K, V>> liste;

	public Table(List<K> newlist, int number, int numberMovies){

	}

	public Table(){
		this.liste = new ArrayList<Duo<K, V>>();
	}

	public void addValue(Duo<K, V> newduo){
		this.liste.add(newduo);
	}

	public void addList(V newlist){
		this.addValue(new Duo<K, V>(newlist));
	}

	public K getBarycentre(){
		List<K> temp = new List<K>(liste.get(0).getK());		

		for (int i = 1; i < liste.size(); i++){
			temp.addMovie(this.liste.get(i).getK());
		}

		temp.findBarycentre();

		return temp.getBarycentre();
	}

	public ArrayList<Duo<K, V>> getListe(){
		return this.liste;
	}
}
