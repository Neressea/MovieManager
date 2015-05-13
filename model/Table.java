package model;

import java.util.ArrayList;

public class Table <K extends Movie, V extends Barycentrable<K>> implements Barycentrable<K>{
	private ArrayList<Duo<K, V>> liste;

	public Table(){
		ArrayList<Duo<K, V>> liste = new ArrayList<Duo<K, V>>();
	}

	public void addValue(Duo<K, V> newduo){
		this.liste.add(newduo);
	}

	public K getBarycentre(){
		ListMovies<K> temp = new ListMovies<K>(liste.get(0).getK());		

		for (int i = 1; i < liste.size(); i++){
			temp.addMovie(this.liste.get(i).getK());
		}

		temp.findBarycentre();

		return temp.getBarycentre();
	}

}
