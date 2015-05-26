package test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.*;

public class TestDuo {

	@Test
	public final void test(){
		Movie a = new Movie();
		List<Movie> temp = new List<Movie>(a);
		Duo<Movie,List<Movie>> test = new Duo<Movie,List<Movie>>(temp);
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
