package model;

import java.util.ArrayList;

public class Movie{

	private String title, description, director;
	private int duration, year;
	private ArrayList<String> kinds;
	private ArrayList<String> actors;
	private String type; //Movie or TV serie
	private int id;

	public Movie(){}
	
	public Movie(int i, String t, int y, int du, String dir, String ty, ArrayList<String> k, ArrayList<String> a, String d){
		id=i;
		title=t;
		year=y;
		duration=du;
		director=dir;
		type=ty;
		kinds=(k!=null)?k:new ArrayList<String>();
		actors= (a!=null) ? a : new ArrayList<String>();
		description=d;
	}

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
	* 		-> title 80
	* 		-> kinds 40 (each kind is weight 8/number of kinds)
	* 		-> director 20
	* 		-> actors 10 (each actor is weight 2/number of actors)
	* 		-> type 5
	* Note : the maximal distance is 155.
	* @param m The movie we want to compare the current movie to.
	* @return The distance between the two movies
	*/
	public double dist(Movie m){
		double dist = 0;
		
		if (m.title == null || title==null) return 155; //One of the movies is empty, we return the maximal distance

		dist+=(title.matches(".*"+m.title+".*") || m.title.matches(".*"+title+".*")) ? 0 : 80.0;

		if(kinds != null && m.kinds != null){
			ArrayList<String> forbid = new ArrayList<String>();
			for (int i=0; i<kinds.size(); i++){
				if(!m.kinds.contains(kinds.get(i))){
					dist+=40.0 / kinds.size();
					forbid.add(kinds.get(i));
				}
			}

			for (int i=0; i<m.kinds.size(); i++) {
				if(!kinds.contains(m.kinds.get(i)) && !forbid.contains(m.kinds.get(i))){
					dist+=40.0 / m.kinds.size();
				}
			}
		}

		if(director != null)
			dist+=(director.equals(m.director)) ? 0 : 20.0;

		if(actors != null){
			ArrayList<String> forbid = new ArrayList<String>();
			for (int i=0; i<m.actors.size(); i++) {
				if(!actors.contains(m.actors.get(i))){
					dist+=10.0 / actors.size();	
					forbid.add(m.actors.get(i));
				}
			}

			for (int i=0; i<actors.size(); i++) {
				if(!m.actors.contains(actors.get(i))){
					dist+=10.0 / m.actors.size();	
				}
			}
		}

		dist+=(type.equals(m.type)) ? 0 : 5.0;

		return dist;
	}

	public String getTitle(){return title;}

	/**
	 * éthode utilisée pour le modèle MVC avec AbstractTableModel
	 * @return Un tableau d'objet représentant l'affichage d'une instance de Movie.
	 */
	public Object[] toObjectTable(){
		String act="";
		String k="";

		for (int i=0; i<actors.size(); i++) act+=(i<actors.size()-1) ? actors.get(i)+", " : actors.get(i);
		for (int i=0; i<kinds.size(); i++) k+=(i<kinds.size()-1) ? kinds.get(i)+", " : kinds.get(i);

		return new Object[]{id, title, year, duration, director, type, act, k, description};
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
