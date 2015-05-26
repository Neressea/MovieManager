package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import model.*;

public class TestMovie {
	
	@Test
	public final void test_toJson(){
		Movie m = new Movie(1, "Titre", 2015, 66, "Mr. Director", "Movie", null, null, "Best. Movie. Ever.");
		//Test d'un film sans genres ni acteurs
		String json = m.toJson();
		String expected="{\"title\":\"Titre\",\"description\":\"Best. Movie. Ever.\",\"director\":\"Mr. Director\",\"duration\":\"66\",\"year\":\"2015\",\"type\":\"Movie\",\"id\":\"1\",\"kinds\":[],\"actors\":[]}";
		assertTrue("Le premier élément json n'est pas bon", json.equals(expected));
		
		//Test d'un film avec acteurs
		m.setActors(new ArrayList<String>(Arrays.asList(new String[]{"Acteur 1", "Acteur 2", "Acteur 3"})));
		json = m.toJson();
		expected="{\"title\":\"Titre\",\"description\":\"Best. Movie. Ever.\",\"director\":\"Mr. Director\",\"duration\":\"66\",\"year\":\"2015\",\"type\":\"Movie\",\"id\":\"1\",\"kinds\":[],\"actors\":[\"Acteur 1\",\"Acteur 2\",\"Acteur 3\"]}";
		assertTrue("Le second élément json n'est pas bon", json.equals(expected));
		
		//Test d'un film avec genres et acteurs
		m.setKinds(new ArrayList<String>(Arrays.asList(new String[]{"Comédie", "Drame", "Tranche de vie"})));
		json = m.toJson();
		expected="{\"title\":\"Titre\",\"description\":\"Best. Movie. Ever.\",\"director\":\"Mr. Director\",\"duration\":\"66\",\"year\":\"2015\",\"type\":\"Movie\",\"id\":\"1\",\"kinds\":[\"Comédie\",\"Drame\",\"Tranche de vie\"],\"actors\":[\"Acteur 1\",\"Acteur 2\",\"Acteur 3\"]}";
		assertTrue("Le troisième élément json n'est pas bon", json.equals(expected));
	}
	
	@Test
	public final void test_fromJson(){
		//Test d'un film sans genres ni acteurs
		
		//Test d'un film avec genres
				
		//Test d'un film avec genres et acteurs
	}
	
	@Test
	public final void test_toString(){
		//Test d'un film sans genres ni acteurs
		
		//Test d'un film avec genres
						
		//Test d'un film avec genres et acteurs
	}
	
	@Test
	public final void test_dist(){
		ArrayList<String> actors1 = new ArrayList<String>(Arrays.asList(new String[]{"Timothy Olyphant",  "Nick Searcy",  "Joelle Carter"}));
		ArrayList<String> kinds1 = new ArrayList<String>(Arrays.asList(new String[]{"Crime", "Drama", "Thriller"}));

		ArrayList<String> actors2 = new ArrayList<String>(Arrays.asList(new String[]{"Timothy Olyphant",  "Nick Searcy",  "Joelle Carter"}));
		ArrayList<String> kinds2 = new ArrayList<String>(Arrays.asList(new String[]{"Crime", "Drama", "Thriller"}));
		
		ArrayList<String> actors4 = new ArrayList<String>(Arrays.asList(new String[]{"Tom Cruise",  "Jeremy Renner",  "Simon Pegg"})); 
		ArrayList<String> kinds4 = new ArrayList<String>(Arrays.asList(new String[]{"Action","Adventure","Thriller"}));

		Movie m1 = new Movie(98, "Justified", 2010, 44, null, "TV Series", kinds1, actors1, "Old-school U.S. Marshal Raylan Givens is reassigned from Miami to his childhood home in the poor, rural coal-mining towns in Eastern Kentucky.");
		Movie m2 = new Movie(95, "Justified", 2010, 44, null, "TV Series", kinds2, actors2, "Old-school U.S. Marshal Raylan Givens is reassigned from Miami to his childhood home in the poor, rural coal-mining towns in Eastern Kentucky.");
		Movie m3;
		Movie m4 = new Movie(96, "Mission impossible 5", 2015, 0, "Christopher McQuarrie", "Movie",kinds4, actors4, "Ethan and team take on their most impossible mission yet, eradicating the Syndicate - an International rogue organization as highly skilled as they are, committed to destroying the IMF.");
		
		//Distance entre deux films identiques 
		assertTrue("Deux films identiques n'ont pas une distance de 0", m1.dist(m2) == 0);
		
		//Distance entre deux films normaux
		
		//Distance maximale entre deux films les plus éloignés possibles
		assertTrue("La distance maximale n'est pas de 155 mais de : "+m1.dist(m4), m1.dist(m4) == 155);
	}
	
	@Test
	public final void test_toObjectTable(){
		ArrayList<String> kinds = new ArrayList<String>(Arrays.asList(new String[]{"Comédie", "Drame", "Tranche de vie"}));
		ArrayList<String> actors = new ArrayList<String>(Arrays.asList(new String[]{"Acteur 1", "Acteur 2", "Acteur 3"}));
		Movie m = new Movie(1, "Titre", 2015, 66, "Mr. Director", "Movie", kinds, actors, "Best. Movie. Ever.");
		
		Object[] expected = new Object[]{new Integer(1), "Titre", new Integer(2015), new Integer(66), "Mr. Director", "Movie", "Acteur 1, Acteur 2, Acteur 3", "Comédie, Drame, Tranche de vie", "Best. Movie. Ever."};
		
		for (int i = 0; i < expected.length; i++) {
			assertTrue("Erreur dans toObjectTable() au champ "+i, m.toObjectTable()[i].equals(expected[i]));
		}
	}

}
