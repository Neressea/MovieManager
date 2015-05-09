package model;

import java.util.ArrayList;

public class ListMovies{
	private ArrayList<Movie> list;
	private Movie barycentre;

	public ListMovies(){
		ArrayList<Movie> list = new ArrayList<Movie>();
		Movie barycentre = new Movie();
	}

	public void findBarycentre(){
		int min = 20; // i.e. here 20 mean infinity
		Movie temp = new Movie();		

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

	public Movie getBarycentre(){
		return this.barycentre;
	}

	public void addMovie(Movie newmovie){
		this.list.add(newmovie);
	}
	

}
