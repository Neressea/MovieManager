package test;


import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.Test;
import model.*;

public class TestList{
	
	@Test
	public final void test_findBarycentre(){
		Movie p = new Movie(0, "Film1", 0, 0, "Rea1", "TV", null , null, null);
		Movie d = new Movie(0, "Film1", 0, 0, "Rea2", "Serie", null , null, null);
		Movie e = new Movie(0, "Film2", 0, 0, "Rea2", "Serie", null , null, null);
		Movie f = new Movie(0, "Film3", 0, 0, "Rea2", "Serie", null , null, null);
		List<Movie> test = new List<Movie>(p);
		test.addMovie(d);
		test.addMovie(e);
		test.addMovie(f);
		test.findBarycentre();
		assertTrue("List avec barycentre initialisée",test.getBarycentre() == d);
		test = new List<Movie>();
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
		List<Movie> test = new List<Movie>(a);
		test.addMovie(b);
		test.addMovie(c);
		test.addMovie(d);
		assertTrue("List avec barycentre initialisée avec nombre correct de classe",test.sort(4,1,4) == null);
		assertTrue("List avec barycentre initialisée avec nombre incorrect de classe",test.sort(4,0,1) == null);
		assertTrue("List avec barycentre initialisée avec nombre incorrect de film total",test.sort(3,1,4) == null);
		test = new List<Movie>();
		test.addMovie(a);
		test.addMovie(b);
		test.addMovie(c);
		test.addMovie(d);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe",test.sort(4,1,4).size() == 4);
		assertTrue("List sans barycentre initialisée avec nombre incorrect de classe",test.sort(4,1,3) == null);
		assertTrue("List sans barycentre initialisée avec nombre incorrect de film total",test.sort(3,1,4) == null);
		ArrayList<List<Movie>> temp = test.sort(4,2,2);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.size() == 2);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(0).getList().size() == 1);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(1).getList().size() == 1);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(0).getBarycentre() != null);
		assertTrue("List sans barycentre initialisée avec nombre correct de classe et liste non vide",temp.get(1).getBarycentre() != null);

    } 
	
	@Test
	public final void test_getBarycentre(){
		List<Movie> temp = new List<Movie>();
		Movie test = temp.getBarycentre(); 
		assertTrue("Barycentre d'une List<Movie> vide",test == null);
		temp = new List<Movie>(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		test = temp.getBarycentre();
		assertTrue("Barycentre d'une List<Movie> initialisée à un film", test != null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null));
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		test = temp.getBarycentre();
		assertTrue("Barycentre d'une List<Movie> a plusieurs films", test != null);
	}
	
	@Test 
	public final void test_getList(){
		List<Movie> temp = new List<Movie>();
		ArrayList<Movie> test = temp.getList(); 
		assertTrue("Liste d'une List<Movie> vide",test.isEmpty());
		temp = new List<Movie>(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		test = temp.getList();
		assertTrue("Liste d'une List<Movie> initialisée à un film", test.isEmpty());
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null));
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		test = temp.getList();
		assertTrue("Liste d'une List<Movie> a plusieurs films", !test.isEmpty());
	}
	
	@Test
	public final void test_addMovie(){
		List<Movie> temp = new List<Movie>();
		assertTrue("Liste vide sans barycentre",temp.getList().size() == 0 && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Ajout d'un film sans barycentre",temp.getList().size() == 1 && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Ajout de deux films sans barycentre",temp.getList().size() == 2 && temp.getBarycentre() == null);
		temp = new List<Movie>(new Movie(0, "Film", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Liste vide avec barycentre",temp.getList().size() == 0 && temp.getBarycentre() != null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Ajout d'un film avec barycentre",temp.getList().size() == 1 && temp.getBarycentre() != null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Ajout de deux films avec barycentre",temp.getList().size() == 2 && temp.getBarycentre() != null);
	
	}
	
	@Test
	public final void test_getRowCount(){
		List<Movie> temp = new List<Movie>();
		assertTrue("Taille d'une List<Movie> vide",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Film", 0, 0, "Rea2", null, null , null, null) );
		assertTrue("Taille d'une List<Movie> de 1 film",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
		temp.addMovie(new Movie(0, "Filmautre", 0, 0, "Rea1", null, null , null, null));
		assertTrue("Taille d'une List<Movie> de 2 films",temp.getList().size() == temp.getRowCount() && temp.getBarycentre() == null);
	}
	
	@Test
	public final void test_getColumnCount(){
		List<Movie> temp = new List<Movie>();
		assertTrue("Taille de head de liste sans barycentre",temp.getColumnCount() == 9);
		temp = new List<Movie>(new Movie());
		assertTrue("Taille de head de liste avec barycentre",temp.getColumnCount() == 9);
	}
	
	@Test
	public final void test_getColumnName(){
		List<Movie> temp = new List<Movie>();
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
		List<Movie> temp = new List<Movie>(new Movie(0, "Film", 0, 0, "Rea2", "test", null , genre, null));
		Movie test = new Movie(1, "Film", 0, 0, "Rea1", "test", null , genre, null);
		genre.remove("Genre1");
		genre.add("Genre2");
		Movie test_bis = new Movie(2, "Film", 0, 0, "Rea2", "test", null , genre, null);
		temp.addMovie(test_bis);
		temp.addMovie(test);
		assertTrue("Barycentre non vide", temp.getBarycentre() != null);
		temp.trier_dist_barycentre();
		assertTrue("Test du test le plus proche",temp.getList().get(1).equals(test));
		assertTrue("Test du second le plus proche",temp.getList().get(0).equals(test_bis));

	}
	
	@Test 
	public final void test_getPropositions(){
		
	}

}
