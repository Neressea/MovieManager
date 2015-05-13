package view;

import javax.swing.*;
import controller.*;
import model.*;
import java.awt.Dimension;

public class GraphicalView extends JFrame{

	public GraphicalView(){
		this(500, 600);
	}

	public GraphicalView(int width, int height){
		super();
		setTitle("Movie Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createMenu();

		JTabbedPane table = new JTabbedPane(SwingConstants.TOP);
		table.addChangeListener(new PropositionController());

		ListMovies<Movie> movies = null;
		try{
			movies = Movie.load("films.txt");
		}catch(Exception e){
			System.out.println("Erreur durant la lecture du fichier");
			e.printStackTrace();
		}

		JTable my_movies = new JTable(new Object[][]{}, ListMovies.head);
		JTable my_propositions = new JTable(new Object[][]{}, ListMovies.head);
		JTable base = new JTable(movies);

		table.addTab("Mes films", new JScrollPane(my_movies));
		table.addTab("Mes recommandations", new JScrollPane(my_propositions));
		table.addTab("Base de films", new JScrollPane(base));

		this.setContentPane(table);

		setPreferredSize(new Dimension(width, height));
		pack();
		setVisible(true);
	}

	public void createMenu(){
		JMenuBar barre = new JMenuBar();

		JMenu file = new JMenu("Fichier");
		JMenu help = new JMenu("?");

		barre.add(file);
		barre.add(help);

		JMenuItem json = new JMenuItem("Exporter la liste courante en JSON");
		json.addActionListener(new JSONController());

		JMenuItem import_base = new JMenuItem("Importer un fichier de films");
		import_base.addActionListener(new BaseController());

		file.add(json);
		file.add(import_base);

		JMenuItem about = new JMenuItem("A propos");
		about.addActionListener(new AboutController());

		help.add(about);

		this.setJMenuBar(barre);
	}

	public static void main(String[] args) {
		GraphicalView v = new GraphicalView();
	}
}