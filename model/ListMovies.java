package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ListMovies<K extends Movie> extends AbstractTableModel implements Barycentrable<K>{
	public static String[] head = new String[]{"Id", "Titre", "Année", "Durée", "Type", "Acteurs", "Genre", "Description"};

	private ArrayList<K> list;
	private K barycentre;

	public ListMovies(){
		super();
		list = new ArrayList<K>();
		barycentre = null;
	}

	public ListMovies(K movie){
		super();
		ArrayList<K> list = new ArrayList<K>();
		barycentre = movie;
	}

	public void findBarycentre(){
		int min = 20; // i.e. here 20 mean infinity
		K temp;		

		for (int i =0 ; i < this.list.size(); i++){
			int dist = 0;			
			for (int j = 0; j < this.list.size(); j++){
				dist += list.get(i).dist(list.get(j));
			}

			if (dist < min){
				temp = this.barycentre;
				barycentre = this.list.get(i);
				this.list.add(i,temp);
			}
		}

	}

	public K getBarycentre(){
		return this.barycentre;
	}

	public void addMovie(K newmovie){
		this.list.add(newmovie);
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

}
