package test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.*;

public class TestTable {
	
	@Test
	public final void test_addValue(){
		Table<Movie, List<Movie>> t = new Table<Movie, List<Movie>>();
		
		//Empty table
		t.addValue(new Duo<Movie, List<Movie>>(new List<Movie>()));
		assertTrue("Addiction d'un Duo dans une table dans une table vide", t.getListe().size() == 1);
		
		//With one element
		t.addValue(new Duo<Movie, List<Movie>>(new List<Movie>()));
		assertTrue("Addiction d'un Duo dans une table avec un element", t.getListe().size() == 2);
	}
	
	@Test
	public final void test_addList(){
		Table<Movie, List<Movie>> t = new Table<Movie, List<Movie>>();
		t.addList(new List<Movie>());
		assertTrue("Addiction d'un Duo dans une table dans une table vide", t.getListe().size() == 1);
		t.addList(new List<Movie>());
		assertTrue("Addiction d'un Duo dans une table avec un element", t.getListe().size() == 2);
	}
	
	
	@Test
	public final void test_getListe(){
		Table<Movie, List<Movie>> t = new Table<Movie, List<Movie>>();
		Movie a = new Movie();
		List<Movie> b = new List<Movie>(a); 
		Duo<Movie, List<Movie>> newduo = new Duo<Movie, List<Movie>>(b);
		t.addValue(newduo);
		assertTrue("Obtenir la liste de duo d'une table", t.getListe().contains(newduo));
		
	}
	
	@Test
	public final void test_getBarycentre(){
		Table<Movie, List<Movie>> t = new Table<Movie, List<Movie>>();
		Movie a = new Movie();
		List<Movie> b = new List<Movie>(a); 
		Duo<Movie, List<Movie>> newduo = new Duo<Movie, List<Movie>>(b);
		t.addValue(newduo);
		assertTrue("Obtenir la liste de duo d'une table", t.getBarycentre().equals(a));
	}

}
