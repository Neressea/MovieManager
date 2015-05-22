package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Movie{

	private String title, description, director;
	private int duration, year;
	private ArrayList<String> kinds;
	private ArrayList<String> actors;
	private String type; //Movie or TV serie
	private int id;

	public Movie(){}

	public String toJson(){
		String json="{\"title\":\""+title+"\",";
		json+="\"description\":\""+description+"\",";
		json+="\"director\":\""+director+"\",";
		json+="\"duration\":\""+duration+"\",";
		json+="\"year\":\""+year+"\",";
		json+="\"type\":\""+type+"\",";
		json+="\"id\":\""+id+"\",";
		json+="\"kinds\":[";

		for (int i=0; i<kinds.size(); i++) {
			json+="\""+kinds.get(i)+"\"";
			json+= (i < kinds.size()-1) ? "," : "";
		}

		json+="],\"actors\":[";

		for (int i=0; i<actors.size(); i++) {
			json+="\""+actors.get(i)+"\"";
			json+= (i < actors.size()-1) ? "," : "";
		}
		
		return json+"]}";
	}

	public static Movie fromJson(String json){
		Movie m = new Movie();

		return m;
	}

	public Movie(int i, String t, int y, int du, String dir, String ty, ArrayList<String> k, ArrayList<String> a, String d){
		id=i;
		title=t;
		year=y;
		duration=du;
		director=dir;
		type=ty;
		kinds=k;
		actors=a;
		description=d;
	}

	public String toString(){
		String show = id+". "+title+" ("+year;
		show += (type.equals("TV")) ? " TV Series)\n\n" : ")\n\n";
		show+=description+"\n\n";

		if(director != null) show+="Director: "+director+"\n";


		if(actors != null){
			show+="With: ";
			for (int i=0; i<actors.size()-1; i++) {
				show+=actors.get(i) + ", ";
			}
			show+=actors.get(actors.size()-1)+"\n";
		}

		if(kinds != null){
			for (int i=0; i<kinds.size()-1; i++) {
				show+=kinds.get(i) + " | ";
			}
			show+=kinds.get(kinds.size()-1);
		}

		if(duration != 0) show+=duration + " mins.\n\n";

		return show;
	}

	/**
	* Compute the "distance" between two movies.
	* For each spec, we add a weight if they arre different, or 0 if they are the same.
	* In order to add a hierarchy in the specs, each weight is the double of the weight of the precedent spec.
	* Here is the hierarchy :
	* 		-> title 16
	* 		-> kinds 8 (each kind is weight 8/number of kinds)
	* 		-> director 4
	* 		-> actors 2 (each actor is weight 2/number of actors)
	* 		-> type 1
	* @param m The movie we want to compare the current movie to.
	* @return The distance between the two movies
	*/
	public double dist(Movie m){
		double dist = 0;

		dist+=(title.matches(".*"+m.title+".*") || m.title.matches(".*"+title+".*")) ? 0 : 16;

		if(kinds != null)
			dist+=(true) ? 0 : 8.0 / kinds.size();

		if(director != null)
			dist+=(director.equals(m.director)) ? 0 : 4;

		if(actors != null)
			dist+=(true) ? 0 : 2.0 / actors.size();

		dist+=(type.equals(m.type)) ? 0 : 1;

		return dist;
	}

	public String getTitle(){return title;}

	public Object[] toObjectTable(){
		return new Object[]{id, title, year, duration, director, type, actors, kinds, description};
	}

	public static void main(String[] args) {
	
		/*ArrayList<Movie> movies=null;

		try{
			movies = load("films.txt");
		}catch(Exception e){
			System.out.println("Erreur durant la lecture du fichier");
			e.printStackTrace();
		}

		for (int i=0; i<movies.size() && movies != null; i++) {
			System.out.println(movies.get(i));
		}*/
	}

	public void setId(int i){
		id=i;
	}

	public void setTitle(String t){
		title=t;
	}
	public void setDirector(String d){
		director=d;
	}
	public void setDuration(int d){
		duration=d;
	}
	public void setYear(int y){
		year=y;
	}
	public void setType(String t){
		type=t;
	}
	public void setActors(ArrayList<String> l){
		actors = l;		
	}
	public void setKinds(ArrayList<String> l){
		kinds = l;	
	}
	public void setDesc(String desc){
		description=desc;
	}
}