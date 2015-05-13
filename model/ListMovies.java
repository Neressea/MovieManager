package model;

import java.util.ArrayList;

public class ListMovies<K extends Movie> implements Barycentrable<K>{
	private ArrayList<K> list;
	private K barycentre;

	public ListMovies(K movie){
		ArrayList<K> list = new ArrayList<K>();
		barycentre = movie;
	}

	public void findBarycentre(){
		int min = 20; // i.e. here 20 mean infinity
		K temp;		

		for (int i =0 ; i < this.list.size(); i++){
			int dist = 0;			
			for (int j = 0; j < this.list.size(); j++){
				dist += list.get(i).dist(list.get(j));
			}

			if (dist < min){
				temp = this.barycentre;
				barycentre = this.list.get(i);
				this.list.add(i,temp);
			}
		}

	}

	public K getBarycentre(){
		return this.barycentre;
	}

	public void addMovie(K newmovie){
		this.list.add(newmovie);
	}
	

}
