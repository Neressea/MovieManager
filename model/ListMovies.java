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
		list = new ArrayList<K>();
		barycentre = movie;
	}

	public void findBarycentre(){
		int min = 156; // i.e. here 156 mean infinity
		K temp;	

		if (this.barycentre == null){
			this.barycentre = this.list.get(0);
		}	

		for (int i = 0 ; i < this.list.size(); i++){
			int dist = 0;	
			dist += this.list.get(i).dist(barycentre);		
			
			for (int j = 0; j < this.list.size(); j++){
				dist += this.list.get(i).dist(list.get(j));
			}
				
			if (dist < min){
				temp = this.barycentre;
				barycentre = this.list.get(i);
				this.list.set(i,temp);
				min = dist;
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

	public void affiche_solve(ArrayList<ListMovies<K>> result, int classe[]){

		int temp = 0;		

		for (int i = 0; i < result.size(); i++){
			System.out.println("Contenu de la liste"+i);
			System.out.println("Barycentre :"+result.get(i).barycentre.getTitle());
			temp += result.get(i).getList().size();
			for (int j = 0; j < result.get(i).getList().size(); j++){
				System.out.println(result.get(i).getList().get(j).getTitle()+result.get(i).getList().get(j).dist(result.get(i).barycentre));
			}
		}

		/*for (int i = 0 ; i < this.list.size(); i++){
			System.out.println(list.get(i).getTitle()+" " +classe[i]);
		} */

		System.out.println("TOTAL" + temp);

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

	public ArrayList<ListMovies<K>> sort(int total_movies, int numberMovies, int number){
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

		int tour_max = 200;

		while(i < number){
			Random rand = new Random();
			int randomNumber = rand.nextInt(total_movies);
			while(randomnumber.contains(randomNumber)) randomNumber = rand.nextInt(total_movies);
			randomnumber.add(randomNumber);
			i++;
		}

		for (i = 0; i < number; i++){
			sortedlist.add(new ListMovies<K>(this.list.get(randomnumber.get(i))));
			classe[randomnumber.get(i)] = i;
		}

		for (i = 0; i < this.list.size(); i++){
			distance = 156;
			if (!randomnumber.contains(i)){
				for (j = 0; j < number; j++){
					temp = this.list.get(i).dist(sortedlist.get(j).barycentre);
					if (temp < distance && sortedlist.get(j).getList().size() < numberMovies - 1){
						distance = temp;
						current_classe = j;
					}
				}
				sortedlist.get(current_classe).addMovie(this.list.get(i));
				classe[i]= current_classe;
			}
		}

		//affiche_solve(sortedlist, classe);

		do{
			for (i = 0 ; i < number ; i++){
				sortedlist.get(i).findBarycentre();
			}

			nb = 0;
			
			for(i = 0 ; i < this.list.size(); i++){
				distance = this.list.get(i).dist(sortedlist.get(classe[i]).barycentre);
				
				if (distance != 0){				

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


			}

			tour_max--;

		}while(nb != 0 && tour_max > 0);

		//affiche_solve(sortedlist, classe);

		for (i = 0 ; i < number; i ++){
			for (j = 0 ; j < sortedlist.get(i).getList().size();j++){
				K buf = sortedlist.get(i).getList().get(j);
				temp = 	sortedlist.get(i).getList().get(j).dist(sortedlist.get(i).barycentre);			
				for (int k = 0; k < j; k++){
					if (temp < sortedlist.get(i).getList().get(k).dist(sortedlist.get(i).barycentre));{
						sortedlist.get(i).getList().set(j, sortedlist.get(i).getList().get(k));
						sortedlist.get(i).getList().set(k, buf);
						break;
					}
				}
			}
		}

		affiche_solve(sortedlist, classe);		

		for (i = 0 ; i < number; i++){
				if (sortedlist.get(i).list.size() != numberMovies - 1){
					nb = 1;
			}
		}

		if (nb == 1){
			for (i = 0; i < number;i++){
				if (sortedlist.get(i).list.size() > numberMovies - 1){
					int curr_size = sortedlist.get(i).list.size();

					for (j = curr_size-1; j >= numberMovies - 1; j--){
						buffer.add(sortedlist.get(i).list.get(j));
						sortedlist.get(i).list.remove(j);
					}

					sortedlist.get(i).findBarycentre();
				}
			}

			for (i = 0; i < number;i++){
				while(sortedlist.get(i).list.size() < numberMovies - 1){	
					distance = 156;
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

		/*for (int w=0; w<sortedlist.size(); w++) {
			for (int g=0; g<sortedlist.get(w).getList().size() && sortedlist != null; g++) {
				System.out.println(sortedlist.get(w).list.get(g));
			}
		}*/

		return sortedlist;
	
	}

	/**
	* Retourne une liste de propositions en fonction de la liste de films qui se trouve dans la base, des films déjà vus par
	* l'utilisateur, du nombre de recommandations désirées et de la distance maximale entre les films vus et les propositions.
	*/
	public static ListMovies<Movie> getPropositions(ListMovies<Movie> movies_base, ListMovies<Movie> viewed, int number_max_of_film, int distance_max){
		if(viewed == null || viewed.getList().size() == 0) return null;

		viewed.findBarycentre();
		Movie ref = viewed.getBarycentre();
		//On récupère le barycentre des films déjà vus par l'utilisateur avec viewed.getBarycentre(). Il faudra le créer avant avec findBarycentre().
		ArrayList<ListMovies<Movie>> temp = movies_base.sort(movies_base.getList().size(), 14,7);
		
		//On trie la liste pour créer les tables.		
		
		ArrayList<Table<Movie, ListMovies<Movie>>> data = new ArrayList<Table<Movie, ListMovies<Movie>>>();
		
		ListMovies<Movie> prop = new ListMovies<Movie>();

		ArrayList<ArrayList<Integer>> forbid = new ArrayList<ArrayList<Integer>>(7);

		for (int i=0; i<7; i++) {
			forbid.add(new ArrayList<Integer>());
		}

		for (int i = 0 ; i < 7 ; i ++){
			data.add(new Table<Movie,ListMovies<Movie>>());
			
			for (int j = 0; j < 3; j ++){
				data.get(i).addList(temp.get(j));
			}
		}

		//Appelle le constructeur Table pour construire l'architecture des tables à partir de la liste.

		int current_number = 0;

		while(current_number < number_max_of_film){

			int indice = 0;
			int indice_bis = 0;

			for (int i = 0 ; i < 7; i ++){
				double test = 156;

				double dist_test = ref.dist(data.get(i).getBarycentre());
			
				if (dist_test < test && forbid.get(indice).size() < 3){		
					indice = i;
					test = dist_test;
				}
			}

			for (int i = 0 ; i < 3; i ++){
				double test = 156;

				Duo<Movie,ListMovies<Movie>> atest = data.get(indice).getListe().get(i);

				double dist_test = ref.dist(atest.getK());
			
				if (dist_test < test && !(forbid.get(indice).contains(i))){		
					indice_bis = i;
					test = dist_test;
				}
			}

			prop.addMovie(data.get(indice).getListe().get(indice_bis).getK());
			current_number ++;

			for (int i = 0; i < data.get(indice).getListe().get(indice_bis).getV().getList().size(); i ++){
				
				if (current_number < number_max_of_film){
					prop.addMovie(data.get(indice).getListe().get(indice_bis).getV().getList().get(i));
					current_number ++;
				}
			}

			forbid.get(indice).add(indice_bis);
		}

		

		//On parcourt les tables en comparant les barycentres avec le barycentre utilisateur pour trouver la liste de
		//films qui correspondent le plus à ses attentes. 

		//On vérifie que la taille de la liste ne dépasse pas le nombre max de films et la distance. Si c'est le cas, il faut créer une
		//nouvelle liste.

		return prop;
	}

}
