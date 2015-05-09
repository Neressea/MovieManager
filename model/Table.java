package model;

import java.util.ArrayList;

public class Table <K extends Movie, V extends ListMovies>{
	private ArrayList<Duo<K, V>> liste;

	public Table(){
		ArrayList<Duo<K, V>> liste = new ArrayList<Duo<K, V>>();
	}

	public void addValue(Duo<K, V> newduo){
		this.liste.add(newduo);
	}

	public Movie getBarycentre(){
		ListMovies temp = new ListMovies();		

		for (int i = 0; i < liste.size(); i++){
			temp.addMovie(this.liste.get(i).getK());
		}

		temp.findBarycentre();

		return temp.getBarycentre();
	}

}
