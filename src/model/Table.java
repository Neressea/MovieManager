package model;

import java.util.ArrayList;

public class Table <K extends Movie, V extends Barycentrable<K>> implements Barycentrable<K>{
	private ArrayList<Duo<K, V>> liste;

	public Table(ListMovies<K> newlist, int number, int numberMovies){

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

	/*public void addValueDuo(Table newtable, int i){
		this.liste.get(i).getV().add(newtable);
		ListMovies temp = new ListMovies();
		for (int j = 0; i < this.liste.get(i).size(); i++){
			temp.add(this.liste.get(i).getV().getBarycentre);
		}
		temp.findBarycentre();
		this.liste.get(i).K = temp.getBarycentre();
	}*/

	public K getBarycentre(){
		ListMovies<K> temp = new ListMovies<K>(liste.get(0).getK());		

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
