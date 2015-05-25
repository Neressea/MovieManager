package test;


import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.Test;
import model.*;

public class TestListMovies {
	
	@Test
	public final void test_findBarycentre(){
		Movie p = new Movie(0, "Film1", 0, 0, "Rea1", "TV", null , null, null);
		Movie d = new Movie(0, "Film1", 0, 0, "Rea2", "Serie", null , null, null);
		Movie e = new Movie(0, "Film2", 0, 0, "Rea2", "Serie", null , null, null);
		Movie f = new Movie(0, "Film3", 0, 0, "Rea2", "Serie", null , null, null);
		ListMovies<Movie> test = new ListMovies<Movie>(p);
		test.addMovie(d);
		test.addMovie(e);
		test.addMovie(f);
		test.findBarycentre();
		assertTrue("List avec barycentre initialisée",test.getBarycentre() == d);
		test = new ListMovies<Movie>();
		test.addMovie(p);
		test.addMovie(d);
		test.addMovie(e);
		test.addMovie(f);
		test.findBarycentre();
		assertTrue("List sans barycentre initialisée",test.getBarycentre() == d);
	}
	
	@Test
    public final void test_sort() {
		Movie a = new Movie(0, "Film1", 0, 0, "Rea1", "TV", null , null, null);
		Movie b = new Movie(0, "Film1", 0, 0, "Rea2", "Serie", null , null, null);
		Movie c = new Movie(0, "Film2", 0, 0, "Rea2", "Serie", null , null, null);
		Movie d = new Movie(0, "Film3", 0, 0, "Rea2", "Serie", null , null, null);
		ListMovies<Movie> test = new ListMovies<Movie>(a);
		test.addMovie(b);
		test.addMovie(c);
		test.addMovie(d);
		assertTrue("List avec barycentre initialisée avec nombre correct de classe",test.sort(4,1,4) == null);
		assertTrue("List avec barycentre initialisée avec nombre incorrect de classe",test.sort(4,0,1) == null);
		assertTrue("List avec barycentre initialisée avec nombre incorrect de film total",test.sort(3,1,4) == null);
		test = new ListMovies<Movie>();
		test.addMovie(a);
		test.addMovie(b);
		test.addMovie(c);
		test.addMovie(d);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe",test.sort(4,1,4).size() == 4);
		assertTrue("List sans barycentre initialisée avec nombre incorrect de classe",test.sort(4,1,3) == null);
		assertTrue("List sans barycentre initialisée avec nombre incorrect de film total",test.sort(3,1,4) == null);
		ArrayList<ListMovies<Movie>> temp = test.sort(4,2,2);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.size() == 2);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(0).getList().size() == 1);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(1).getList().size() == 1);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(0).getBarycentre() != null);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(1).getBarycentre() != null);

    } 

	@Test
	public final void test_toJson(){
		Movie p = new Movie(0, "Film1", 0, 0, "Rea1", "TV", null , null, null);
		Movie d = new Movie(0, "Film1", 0, 0, "Rea2", "Serie", null , null, null);
		ListMovies<Movie> test = new ListMovies<Movie>(p);
		test.addMovie(d);
		String json = "{\"barycentre\":"+p.toJson()+",\"list\":["+d.toJson()+"]}";
		assertTrue("List avec barycentre initialisée",test.toJson().equals(json));
		test = new ListMovies<Movie>();
		test.addMovie(p);
		test.addMovie(d);
		json = "{\"barycentre\":"+"\"null\""+",\"list\":["+p.toJson()+","+d.toJson()+"]}";
		assertTrue("List sans barycentre initialisée",test.toJson().equals(json));
	}
	
	@Test
	public final void test_getBarycentre(){
		ListMovies<Movie> temp = new ListMovies<Movie>();
		Movie test = temp.getBarycentre(); 
		assertTrue("Barycentre d'une ListMovies<Movie> vide",test == null);
		temp = new ListMovies<Movie>(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		test = temp.getBarycentre();
		assertTrue("Barycentre d'une ListMovies<Movie> initialisée à un film", test != null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null));
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		test = temp.getBarycentre();
		assertTrue("Barycentre d'une ListMovies<Movie> a plusieurs films", test != null);
	}
	
	@Test 
	public final void test_getList(){
		ListMovies<Movie> temp = new ListMovies<Movie>();
		ArrayList<Movie> test = temp.getList(); 
		assertTrue("Liste d'une ListMovies<Movie> vide",test.isEmpty());
		temp = new ListMovies<Movie>(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		test = temp.getList();
		assertTrue("Liste d'une ListMovies<Movie> initialisée à un film", test.isEmpty());
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null));
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		test = temp.getList();
		assertTrue("Liste d'une ListMovies<Movie> a plusieurs films", !test.isEmpty());
	}
	
	@Test
	public final void test_addMovie(){
		ListMovies<Movie> temp = new ListMovies<Movie>();
		assertTrue("Liste vide sans barycentre",temp.getList().size() == 0 && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Ajout d'un film sans barycentre",temp.getList().size() == 1 && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Ajout de deux films sans barycentre",temp.getList().size() == 2 && temp.getBarycentre() == null);
		temp = new ListMovies<Movie>(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Liste vide avec barycentre",temp.getList().size() == 0 && temp.getBarycentre() != null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Ajout d'un film avec barycentre",temp.getList().size() == 1 && temp.getBarycentre() != null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Ajout de deux films avec barycentre",temp.getList().size() == 2 && temp.getBarycentre() != null);
	
	}
	
	@Test
	public final void test_getRowCount(){
		ListMovies<Movie> temp = new ListMovies<Movie>();
		assertTrue("Taille d'une ListMovies<Movie> vide",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Taille d'une ListMovies<Movie> de 1 film",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Taille d'une ListMovies<Movie> de 2 films",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
	}
	
	@Test
	public final void test_getColumnCount(){
		ListMovies<Movie> temp = new ListMovies<Movie>();
		assertTrue("Taille de head de liste sans barycentre",temp.getColumnCount() == 9);
		temp = new ListMovies<Movie>(new Movie());
		assertTrue("Taille de head de liste avec barycentre",temp.getColumnCount() == 9);
	}
	
	@Test
	public final void test_getColumnName(){
		ListMovies<Movie> temp = new ListMovies<Movie>();
		assertTrue("Obtenir Id",temp.getColumnName(0) == "Id");
		assertTrue("Obtenir Titre",temp.getColumnName(1) == "Titre");
		assertTrue("Obtenir Année",temp.getColumnName(2) == "Année");
		assertTrue("Obtenir Durée",temp.getColumnName(3) == "Durée");
		assertTrue("Obtenir Réalisateur",temp.getColumnName(4) == "Réalisateur");
		assertTrue("Obtenir Type",temp.getColumnName(5) == "Type");
		assertTrue("Obtenir Acteurs",temp.getColumnName(6) == "Acteurs");
		assertTrue("Obtenir Genre",temp.getColumnName(7) == "Genre");
		assertTrue("Obtenir Description",temp.getColumnName(8) == "Description");
	}
	
	
	@Test
	public final void test_trier_dist_barycentre(){
		ArrayList<String> genre= new ArrayList<String>();
		genre.add("Genre1");
		ListMovies<Movie> temp = new ListMovies<Movie>(new Movie(0, "Film", 0, 0, "Rea2", "test", null , genre, null));
		Movie test = new Movie(1, "Film", 0, 0, "Rea1", "test", null , genre, null);
		genre.remove("Genre1");
		genre.add("Genre2");
		Movie test_bis = new Movie(2, "Film", 0, 0, "Rea2", "test", null , genre, null);
		temp.addMovie(test_bis);
		temp.addMovie(test);
		assertTrue("", temp.getBarycentre() != null);
		temp.trier_dist_barycentre();
		//assertTrue("Test du test le plus proche",temp.getList().get(0).equals(test));
		//assertTrue("Test du second le plus proche",temp.getList().get(1).equals(test_bis));

	}
	
	@Test 
	public final void test_getPropositions(){
		
	}

}
