package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Movie{

	private String titre, description, director;
	private int duration, year;
	private ArrayList<String> kinds;
	private ArrayList<String> actors;
	private String type; //Movie or TV serie
	private int id;

	public Movie(){}

	public static ArrayList<Movie> load(String file) throws IOException{
		ArrayList<Movie> movies = new ArrayList<Movie>();
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line;
		while ((line = reader.readLine()) != null){

			Movie m = new Movie();

			//We read the first line
			String [] specs = line.split(".");
			System.out.println(specs.length);
			m.id = Integer.parseInt(specs[0]);
			specs = specs[1].split("(");
			m.titre = specs[0];
			specs = specs[1].split(")");
			specs = specs[0].split("TV");
			m.year = Integer.parseInt(specs[0]);
			m.type = (specs.length > 1) ? "Serie" : "Movie";

			//We read the description
			line=reader.readLine();
			while(!(line.contains("With") || line.contains("Director"))){
				m.description = m.description.concat(line);
				line=reader.readLine();
			}

			line = reader.readLine();
			fill(m, line);
			
		}

		reader.close();
		return movies;
	}

	public static void fill(Movie m, String line){
		if(line.contains("Director"))
			m.director = line.split(":")[1];
		else if(line.contains("With")){
			m.actors = (ArrayList<String>) Arrays.asList(line.split(","));
		}else{
			String [] k = line.split("|");
			m.duration = Integer.parseInt(k[k.length - 1].split(" ")[1]);
			k[k.length-1].replaceFirst("[0-9]+ mins.", "");
		}
	}

	public String toString(){
		String show = id+". "+titre+" ("+year+")\n\n";
		show+=description+"\n\n";

		if(director != null) show+="Director: "+director+"\n";
		if(actors != null) show+="With: "+actors+"\n";

		if(kinds != null) show+=kinds;
		if(duration != 0) show+=duration + "mins.\n\n";

		return show;
	}

	public static void main(String[] args) {
	
		ArrayList<Movie> movies=null;

		try{
			movies = load("films.txt");
		}catch(Exception e){
			System.out.println("Erreur durant la lecture du fichier");
			e.printStackTrace();
		}

		for (int i=0; i<movies.size() && movies != null; i++) {
			System.out.println(movies.get(i));
		}
	}
}