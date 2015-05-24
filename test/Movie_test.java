package test;

import model.*;
import static org.junit.Assert.*;
import org.junit.Test;

public abstract class Movie_test{

	@Test	
	public final void dist_test(){
		Movie test = new Movie(0, "Film 1", 0, 0, null, null, null, null, null);
		Movie test_bis = new Movie(0, "Film 2", 0, 0, null, null, null, null, null);
		
		assertTrue("test" , 0 == 1 );
	}

	/*public static void main(String[] args){

		Movie_test.dist_test();

	}*/
}
