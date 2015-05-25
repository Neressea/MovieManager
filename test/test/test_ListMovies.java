package test;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import model.*;

public class test_ListMovies {
	
	@Test
	public final void test_findBarycentre(){
		
	}
	
	@Test
    public final void test_sort() {

        fail("Not yet implemented"); // TODO

    } 
	
	@Test
	public final void test_load(){
		
	}
	
	@Test
	public final void test_fill(){
		
	}
	
	@Test
	public final void toJson(){
		
	}
	
	@Test
	public final void fromJson(){
		
	}
	
	@Test
	public final void test_affiche_solve(){
		
	}
	
	@Test
	public final void test_getBarycentre(){
		ListMovies temp = new ListMovies();
		Movie test = temp.getBarycentre(); 
		assertTrue("Barycentre d'une ListMovies vide",test == null);
		temp = new ListMovies(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		test = temp.getBarycentre();
		assertTrue("Barycentre d'une ListMovies initialisée à un film", test != null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null));
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		test = temp.getBarycentre();
		assertTrue("Barycentre d'une ListMovies a plusieurs films", test != null);
	}
	
	@Test 
	public final void test_getList(){
		ListMovies temp = new ListMovies();
		ArrayList<Movie> test = temp.getList(); 
		assertTrue("Liste d'une ListMovies vide",test.isEmpty());
		temp = new ListMovies(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		test = temp.getList();
		assertTrue("Liste d'une ListMovies initialisée à un film", test.isEmpty());
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null));
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		test = temp.getList();
		assertTrue("Liste d'une ListMovies a plusieurs films", !test.isEmpty());
	}
	
	@Test
	public final void test_addMovie(){
		ListMovies temp = new ListMovies();
		assertTrue("Liste vide sans barycentre",temp.getList().size() == 0 && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Ajout d'un film sans barycentre",temp.getList().size() == 1 && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Ajout de deux films sans barycentre",temp.getList().size() == 2 && temp.getBarycentre() == null);
		temp = new ListMovies(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Liste vide avec barycentre",temp.getList().size() == 0 && temp.getBarycentre() != null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Ajout d'un film avec barycentre",temp.getList().size() == 1 && temp.getBarycentre() != null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Ajout de deux films avec barycentre",temp.getList().size() == 2 && temp.getBarycentre() != null);
	
	}
	
	@Test
	public final void test_getRowCount(){
		ListMovies temp = new ListMovies();
		assertTrue("Taille d'une ListMovies vide",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Taille d'une ListMovies de 1 film",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Taille d'une ListMovies de 2 films",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
	}
	
	@Test
	public final void test_getColumnCount(){
		ListMovies temp = new ListMovies();
		assertTrue("Taille de head de liste sans barycentre",temp.getColumnCount() == 9);
		temp = new ListMovies(new Movie());
		assertTrue("Taille de head de liste avec barycentre",temp.getColumnCount() == 9);
	}
	
	@Test
	public final void test_getColumnName(){
		ListMovies temp = new ListMovies();
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
		ListMovies temp = new ListMovies(new Movie(0, "Film", 0, 0, "Rea2", "test", null , genre, null));
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
