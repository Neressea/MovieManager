package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

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

	public static ListMovies<Movie> load(String file) throws IOException{
		ListMovies<Movie> movies = new ListMovies<Movie>();
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line = reader.readLine();
		while (line != null){

			Movie m = new Movie();

			//We read the first line
			String [] specs = line.split("\\.");
			m.id = Integer.parseInt(specs[0]);
			specs = line.split("[0-9]+\\.");
			specs = specs[1].split("\\(");
			m.title = specs[0];
			specs = specs[1].split("\\)");
			specs = specs[0].split("TV");
			m.year = Integer.parseInt(specs[0].trim());
			m.type = (specs.length > 1) ? "TV" : "Movie";

			//We read the description
			line=reader.readLine();
			m.description="";
			while(line != null && !(line.contains("With : ") || line.contains("Director : "))){
				m.description = m.description.concat(line);
				line=reader.readLine();
			}

			while(!line.equals("")){
				fill(m, line);
				line = reader.readLine();
			}
			
			movies.addMovie(m);

			//We read all the blank lines
			while(line != null && line.equals("")) line = reader.readLine();
		}

		reader.close();
		return movies;
	}

	public static void fill(Movie m, String line){
		if(line.contains("Director"))
			m.director = line.split(":")[1];
		else if(line.contains("With")){
			m.actors = new ArrayList<String>(Arrays.asList(line.split(":")[1].split(",")));
		}else{
			m.kinds = new ArrayList<String>();
			String [] k = line.split("\\|");
			String last_case = k[k.length - 1];

			if(k.length == 1){
				m.duration = (last_case.split(" ").length >= 3) ? Integer.parseInt(last_case.split(" ")[1]) : 0;
			}else if(k.length > 1){
				m.duration = (last_case.split(" ").length >= 3) ? Integer.parseInt(last_case.split(" ")[2]) : 0;
			}

			
			k[k.length-1] = k[k.length-1].replaceFirst("[0-9]+ mins\\.", "");
			for (int i=0; i<k.length; i++) {
				m.kinds.add(k[i]);
			}
		}
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

		dist+=(title.matches("*"+m.title+"*") || m.title.matches("*"+title+"*")) ? 0 : 16;
		dist+=(true) ? 0 : 8.0 / kinds.size();
		dist+=(director.equals(m.director)) ? 0 : 4;
		dist+=(true) ? 0 : 2.0 / actors.size();
		dist+=(type.equals(m.type)) ? 0 : 1;

		return dist;
	}

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
}