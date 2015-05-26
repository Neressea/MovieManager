package test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.*;

public class TestDuo {

	@Test
	public final void test(){
		Movie a = new Movie();
		ListMovies<Movie> temp = new ListMovies<Movie>(a);
		Duo<Movie,ListMovies<Movie>> test = new Duo<Movie,ListMovies<Movie>>(temp);
		assertTrue("Obtenir le barycentre d'une ListMovie vide", test.getK() == a);
		assertTrue("Obtenir la liste d'une ListMovie vide", test.getV().getList().isEmpty());
		Movie b = new Movie();
		Movie c = new Movie();
		temp.addMovie(b);
		temp.addMovie(c);
		assertTrue("Obtenir le barycentre d'une ListMovie de taille 2", test.getK() == a);
		assertTrue("Obtenir la liste d'une ListMovie de taille 2", test.getV().getList().size() == 2);
	}
}
