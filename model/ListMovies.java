package model;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ListMovies<K extends Movie> extends AbstractTableModel implements Barycentrable<K>{
	private static String[] head = new String[]{"Id", "Titre", "Année", "Durée", "Réalisateur", "Type", "Acteurs", "Genre", "Description"};

	private ArrayList<K> list;
	private K barycentre;

	public ListMovies(){
		super();
		list = new ArrayList<K>();
		barycentre = null;
	}

	public ListMovies(K movie){
		super();
		ArrayList<K> list = new ArrayList<K>();
		barycentre = movie;
	}

	public void findBarycentre(){
		int min = 32; // i.e. here 32 mean infinity
		K temp;	

		if (barycentre != null){
			min = 0;			
			for (int i = 0 ; i < this.list.size(); i++){
				min += this.barycentre.dist(list.get(i));
			}
		}	

		for (int i = 0 ; i < this.list.size(); i++){
			int dist = 0;			
			for (int j = 0; j < this.list.size(); j++){
				dist += this.list.get(i).dist(list.get(j));
			}
			if (barycentre != null){
				dist += this.list.get(i).dist(barycentre);
				
				if (dist < min){
					temp = this.barycentre;
					barycentre = this.list.get(i);
					this.list.add(i,temp);
					min = dist;
				}			


			}else{
				if (dist < min){
					barycentre = this.list.get(i);
					min = dist;
				}
			}		

			
		}

	}

	public static ListMovies<Movie> load(String file) throws IOException{
		ListMovies<Movie> movies = new ListMovies<Movie>();
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line = reader.readLine();
		while (line != null){
			Movie m = new Movie();

			//We read the first line
			String [] specs = line.split("\\.");
			m.setId(Integer.parseInt(specs[0]));
			specs = line.split("[0-9]+\\.");
			specs = specs[1].split("\\(");
			m.setTitle (specs[0]);
			specs = specs[1].split("\\)");
			specs = specs[0].split("TV");
			m.setYear(Integer.parseInt(specs[0].trim()));
			m.setType(((specs.length > 1) ? "TV" : "Movie"));

			//We read the description
			line=reader.readLine();
			String d="";
			while(line != null && !(line.contains("With : ") || line.contains("Director : "))){
				d = d.concat(line);
				line=reader.readLine();
			}
			m.setDesc(d);

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
			m.setDirector(line.split(":")[1]);
		else if(line.contains("With")){
			m.setActors(new ArrayList<String>(Arrays.asList(line.split(":")[1].split(","))));
		}else{
			ArrayList<String> kinds = new ArrayList<String>();
			String [] k = line.split("\\|");
			String last_case = k[k.length - 1];

			if(k.length == 1){
				m.setDuration((last_case.split(" ").length >= 3) ? Integer.parseInt(last_case.split(" ")[1]) : 0);
			}else if(k.length > 1){
				m.setDuration((last_case.split(" ").length >= 3) ? Integer.parseInt(last_case.split(" ")[2]) : 0);
			}

			
			k[k.length-1] = k[k.length-1].replaceFirst("[0-9]+ mins\\.", "");
			for (int i=0; i<k.length; i++) {
				kinds.add(k[i]);
			}
			m.setKinds(kinds);
		}
	}

	public String toJson(){
		String json="{\"barycentre\":";
		json+= (barycentre != null) ? barycentre.toJson() : "\"null\"";
		json+= ",\"list\":[";

		for (int i=0; i<list.size(); i++) {
			json+=list.get(i).toJson();
			json+= (i<list.size()-1) ? "," : "";
		}

		return json+"]}";
	}

	public static ListMovies<Movie> fromJson(String json){
		ListMovies<Movie> lm = new ListMovies<Movie>();

		String [] elems = json.split(":");
		lm.barycentre = (!elems[1].equals("null")) ? Movie.fromJson(elems[1]) : null;
		elems = json.split("[");

		return lm;
	}

	public K getBarycentre(){
		return this.barycentre;
	}

	public ArrayList<K> getList(){
		return this.list;
	}

	public void addMovie(K newmovie){
		this.list.add(newmovie);
		fireTableDataChanged();
	}

	//Inherited by AbstractTableModel
	
	public int getRowCount() {
        return list.size();
    }
 
    public int getColumnCount() {
        return head.length;
    }
 
    public String getColumnName(int columnIndex) {
        return head[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Movie)list.get(rowIndex)).toObjectTable()[columnIndex];
    }

	public void sort(int numberMovies, int number){
		ArrayList<ListMovies<K>> sortedlist = new ArrayList<ListMovies<K>>();
		ArrayList<K> buffer = new ArrayList<K>();
		ArrayList<Integer> randomnumber = new ArrayList<Integer>();
		int classe[] = new int[this.list.size()];
		 
		int i = 0;
		int j = 0;
		double distance = 32;
		double temp = 0;
		int current_classe = 0;	
		int nb = 0;

		while(i != number){
			Random rand = new Random();
			int randomNumber = rand.nextInt(numberMovies);
				if (!randomnumber.contains(randomNumber)){
					randomnumber.add(randomNumber);
					i++;
				}
		}

		for (i = 0; i < number; i++){
			sortedlist.add(new ListMovies<K>(this.list.get(randomnumber.get(i))));
			classe[randomnumber.get(i)] = i;
		}

		for (i = 0; i < this.list.size(); i++){
			distance = 32;
			if (!randomnumber.contains(i)){
				for (j = 0; j < randomnumber.size(); j++){
					temp = this.list.get(i).dist(sortedlist.get(j).barycentre);
					if (distance < 	temp){
						distance = temp;
						current_classe = j;
					}
				}
				sortedlist.get(current_classe).addMovie(this.list.get(i));
				classe[i]= current_classe;
			}
		}

		do{
			for (i = 0 ; i < number ; i++){
				sortedlist.get(i).findBarycentre();
			}

			nb = 0;
			
			for(i = 0 ; i < this.list.size(); i++){
				distance = 32;
				for (j = 0; j < number; j++){
					temp = this.list.get(i).dist(sortedlist.get(j).barycentre);
					if (temp < distance){
						distance = temp;
						current_classe = j;
					}
				}

				if (classe[i] != current_classe){
					nb ++;
					sortedlist.get(classe[i]).list.remove(this.list.get(i));
					sortedlist.get(current_classe).list.add(this.list.get(i));
					classe[i] = current_classe;
				}

			}

		}while(nb != 0);

		
		for (i = 0 ; i < number; i++){
				if (sortedlist.get(i).list.size() != numberMovies - 1){
					nb = 1;
			}
		}

		if (nb == 1){
			for (i = 0; i < number;i++){
				if (sortedlist.get(i).list.size() > numberMovies - 1){
					for (j = numberMovies - 1; j < sortedlist.get(i).list.size(); j++){
						buffer.add(sortedlist.get(i).list.get(j));
						sortedlist.get(i).list.remove(j);
					}
					sortedlist.get(i).findBarycentre();
				}
			}

			for (i = 0; i < number;i++){
				while(sortedlist.get(i).list.size() < numberMovies - 1){	
					distance = 32;
					for (j = 0; j < buffer.size(); j++){
						temp = buffer.get(j).dist(sortedlist.get(i).barycentre);
						if(temp < distance){
							distance = temp;
							current_classe = j;
						}
					}
					sortedlist.get(i).list.add(buffer.get(current_classe));
					buffer.remove(classe);
				} 
				sortedlist.get(i).findBarycentre();
			}
		}

		for (i = 0; i < sortedlist.size(); i ++){
			for (j = 0; j < sortedlist.get(i).list.size();j++){
				current_classe = i*number + j;
				this.list.remove(current_classe);
				K m = sortedlist.get(i).list.get(j);
				this.list.add(current_classe, m); 
			}
		}

		
	
	}

	/**
	* Retourne une liste de propositions en fonction de la liste de films qui se trouve dans la base, des films déjà vus par
	* l'utilisateur, du nombre de recommandations désirées et de la distance maximale entre les films vus et les propositions.
	*/
	public static ListMovies<Movie> getPropositions(ListMovies<Movie> movies_base, ListMovies<Movie> viewed, int number_max_of_film, int distance_max){
		
		//On récupère le barycentre des films déjà vus par l'utilisateur avec viewed.getBarycentre(). Il faudra le créer avant avec findBarycentre().

		//On trie la liste pour créer les tables.

		//Appelle le constructeur Table pour construire l'architecture des tables à partir de la liste.

		//On parcourt les tables en comparant les barycentres avec le barycentre utilisateur pour trouver la liste de
		//films qui correspondent le plus à ses attentes. 

		//On vérifie que la taille de la liste ne dépasse pas le nombre max de films et la distance. Si c'est le cas, il faut créer une
		//nouvelle liste.

		return null;
	}

}
