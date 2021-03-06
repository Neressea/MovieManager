package model;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class List<K extends Movie> extends AbstractTableModel implements Barycentrable<K>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String[] head = new String[]{"Id", "Titre", "Année", "Durée", "Réalisateur", "Type", "Acteurs", "Genre", "Description"};

	private ArrayList<K> list;
	private K barycentre;

	public List(){
		super();
		list = new ArrayList<K>();
		barycentre = null;
	}

	public List(K movie){
		super();
		list = new ArrayList<K>();
		barycentre = movie;
	}

	public void findBarycentre(){
		int min = 300; // i.e. here 300 means infinity
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

	public static List<Movie> load(String file) throws IOException{
		List<Movie> movies = new List<Movie>();
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

	public void affiche_solve(ArrayList<List<K>> result, int classe[]){

		int temp = 0;		

		for (int i = 0; i < result.size(); i++){
			System.out.println("Contenu de la liste :"+i+" de taille "+result.get(i).list.size());
			System.out.println("Barycentre :"+result.get(i).barycentre.getTitle());
			temp += result.get(i).getList().size();
			for (int j = 0; j < result.get(i).getList().size(); j++){
				System.out.println(result.get(i).getList().get(j).getTitle()+result.get(i).getList().get(j).dist(result.get(i).barycentre));
			}
		}

		System.out.println("TOTAL" + temp);

	}

	public String toJson(){
		findBarycentre();
		String json="{\"barycentre\":";
		json+= (barycentre != null) ? barycentre.toJson() : "\"null\"";
		json+= ",\"list\":[";

		for (int i=0; i<list.size(); i++) {
			json+=list.get(i).toJson();
			json+= (i<list.size()-1) ? "," : "";
		}

		return json+"]}";
	}

	public static List<Movie> fromJson(String json){
		List<Movie> lm = new List<Movie>();

		String [] elems = json.split(":");
		lm.barycentre = (!elems[1].equals("null")) ? Movie.fromJson(elems[1]) : null;
		elems = json.split("\"list\":\\[");
		elems = elems[1].split("\\]}$");
		String l = elems[0];
		System.out.println(l);
		
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


	public void trier_dist_barycentre(){
		int tour_max = this.list.size();
		ArrayList<K> buffer = new ArrayList<K>();
		double distance;
		double temp;
		int current_classe = 0;

		for (int j = 0 ; j < tour_max; j++){			
			distance = 300;
			current_classe = 0;
			for (int k = 0; k < this.list.size(); k++){
				K buf = this.list.get(k);
				temp = buf.dist(this.barycentre);
				if (temp < distance){
					distance = temp;
					current_classe = k;
				}
			}
			buffer.add(this.list.get(current_classe));
			this.list.remove(current_classe);	
		}
		this.list = buffer;
		
	}

	//fonction qui permet de trier une liste Movies dans l'ordre croissant de leur distance au barycentre. Algorithme utilisé est le tri par sélection

	public ArrayList<List<K>> sort(int total_movies, int numberMovies, int number){
		ArrayList<List<K>> sortedlist = new ArrayList<List<K>>();
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
		
		if (this.barycentre != null){
			return null;
		}
		
		if (this.list.size() != total_movies){
			return null;
		}
		
		if (total_movies != number*numberMovies){
			return null;
		}
		
		//On pourra remplacer les null par une exception

		while(i < number){
			Random rand = new Random();
			int randomNumber = rand.nextInt(total_movies);
			while(randomnumber.contains(randomNumber)) randomNumber = rand.nextInt(total_movies);
			randomnumber.add(randomNumber);
			i++;
		}

		for (i = 0; i < number; i++){
			sortedlist.add(new List<K>(this.list.get(randomnumber.get(i))));
			classe[randomnumber.get(i)] = i;
		}

		// On selectionne plusieurs films qui serviront de barycentres initiaux aux listes.

		for (i = 0; i < this.list.size(); i++){
			distance = 300;
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

		//On crée des listes de départ.

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

		// On applique l'algorithme des H-means, en deux phases : phase de barycentrage et phase d'affectation des points.

		for (i = 0 ; i < number; i ++){
			sortedlist.get(i).trier_dist_barycentre();
		}		

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
					current_classe = 0;
					for (j = 0; j < buffer.size(); j++){
						temp = buffer.get(j).dist(sortedlist.get(i).barycentre);
						if(temp < distance){
							distance = temp;
							current_classe = j;
						}
					}
					sortedlist.get(i).list.add(buffer.get(current_classe));
					buffer.remove(current_classe);
				} 
				sortedlist.get(i).findBarycentre();
			}

		}

		//Dans cette partie on homogénise la taille des listes. L'inertie totale n'est plus minimale, mais le stockage est plus homogène.

		affiche_solve(sortedlist, classe);

		return sortedlist;
	
	}

	//Fonction qui créer des List en minimisant les distances entre les films de ces List. L'algorithme principale de cette fonction est H-means dont le but est de minimiser l'inertie totale.

	public static List<Movie> getPropositions(List<Movie> movies_base, List<Movie> viewed, int number_max_of_film, int distance_max){

		int numberofmiddleTable = 2;
		int sizeofmiddleTable = 4;
		double distance;
		double tempor;
		int classe = 0;
		int size = 0;

		if(viewed == null || viewed.getList().size() == 0) return null;

		viewed.findBarycentre();
		Movie ref = viewed.getBarycentre();
		//On récupère le barycentre des films déjà vus par l'utilisateur avec viewed.getBarycentre(). Il faudra le créer avant avec findBarycentre().
		ArrayList<List<Movie>> temp = movies_base.sort(movies_base.getList().size(), 14,7);

		//On trie la liste pour créer les tables.		
		
		ArrayList<Table<Movie, List<Movie>>> data = new ArrayList<Table<Movie, List<Movie>>>();
		
		List<Movie> prop = new List<Movie>();
		List<Movie> current = new List<Movie>();

		ArrayList<ArrayList<Integer>> forbid = new ArrayList<ArrayList<Integer>>(7);

		for (int i = 0; i< numberofmiddleTable; i++) {
			forbid.add(new ArrayList<Integer>());
		}

		for (int i = 0 ; i < numberofmiddleTable ; i ++){
			data.add(new Table<Movie,List<Movie>>());
			data.get(i).addList(temp.get(0));
			temp.remove(0);

			for (int j = 1; j < sizeofmiddleTable; j ++){
				distance = 300;
				if (temp.isEmpty()){
					break;
				}
				
				for (int k = 0; k < temp.size(); k++){
					List<Movie> buff = temp.get(k);
					tempor = buff.getBarycentre().dist(data.get(i).getListe().get(0).getK());
					if (tempor < distance){
						distance = tempor;
						classe = k;
					}

				}
				data.get(i).addList(temp.get(classe));
				temp.remove(classe);
			}
			
		}

		/*for (int i =0; i < 2;i ++){
			for (int j = 0 ; j < data.get(i).getListe().size(); j++){
				System.out.println(data.get(i).getListe().get(j).getV().getBarycentre());
				System.out.println(data.get(i).getListe().get(j).getK());	
		
			}
					
		}*/

		

		//Appelle le constructeur Table pour construire l'architecture des tables à partir de la liste.

		int current_number = 0;
		double test;
		double dist_test;

		while(current_number < number_max_of_film){

			int indice = 0;
			int indice_bis = 0;
			int indice_Movie = 0;

			test = 300;

			for (int i = 0 ; i < data.size(); i ++){
				dist_test = ref.dist(data.get(i).getBarycentre());
			
				if (dist_test < test){		
					indice = i;
					test = dist_test;
				}
			}

			test = 300;

			for (int i = 0 ; i < data.get(indice).getListe().size(); i ++){
				Duo<Movie,List<Movie>> atest = data.get(indice).getListe().get(i);

				dist_test = ref.dist(atest.getK());
			
				if (dist_test < test){		
					indice_bis = i;
					test = dist_test;
				}
			}

			data.get(indice).getListe().get(indice_bis).getV().addMovie(data.get(indice).getListe().get(indice_bis).getV().getBarycentre());
		
			current = data.get(indice).getListe().get(indice_bis).getV();

			size = current.getList().size();

			for (int i = 0 ; i < size; i++){
				test = 300;				
				if (current_number >= number_max_of_film){
					break;
				}
				for (int j = 0; j < current.getList().size(); j++){
					dist_test = ref.dist(current.getList().get(j));					
					if (dist_test < test){
						test = dist_test;
						indice_Movie = j;
					}
				}
				prop.addMovie(current.getList().get(indice_Movie));
				current.getList().remove(indice_Movie);
				current_number++;
			}

			data.get(indice).getListe().remove(indice_bis);
			
			if (data.get(indice).getListe().isEmpty()){
				data.remove(indice);
			}
			
			
			for (int i = 0; i < prop.getList().size(); i++){
				if (prop.getList().get(i).dist(ref) >= distance_max){
					prop.getList().remove(i);
					
				}
			}

		}

		

		//On parcourt les tables en comparant les barycentres avec le barycentre utilisateur pour trouver la liste de
		//films qui correspondent le plus à ses attentes. 

		//On vérifie que la taille de la liste ne dépasse pas le nombre max de films et la distance. Si c'est le cas, il faut créer une
		//nouvelle liste.

		return prop;
	}

}
