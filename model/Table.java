package model;

import java.util.ArrayList;

public class Table <K extends Movie, V extends Barycentrable<K>> implements Barycentrable<K>{
	private ArrayList<Duo<K, V>> liste;

	public Table(ListMovies<K> newlist, int number, int numberMovies){
		/*this.liste = new ArrayList<Duo<K, V>>();
		for (int i = 0; i < number ; i ++){
			ListMovies temp = new ListMovies();
			for (int j = 0; j < numberMovies; j ++){
				
				if (i*number+j < newlist.getRowCount()){
					temp.addMovie((Movie)newlist.getList().get(i*number+j));
				}
			}

			if (!temp.getList().isEmpty()){
				temp.findBarycentre();
				this.liste.add(new Duo<K, V>(temp));
			}
		}*/
	}

	public Table(){
		this.liste = new ArrayList<Duo<K, V>>();
	}

	public void addValue(Duo<K, V> newduo){
		this.liste.add(newduo);
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


}
