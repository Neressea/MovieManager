package view;

import javax.swing.*;
import controller.*;
import java.awt.Dimension;

public class GraphicalView extends JFrame{
	private static String[] head = new String[]{"Id", "Titre", "Année", "Durée", "Type", "Acteurs", "Genre", "Description"};

	public GraphicalView(){
		this(500, 600);
	}

	public GraphicalView(int width, int height){
		super();
		setTitle("Movie Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createMenu();

		JTabbedPane table = new JTabbedPane(SwingConstants.TOP);

		JTable my_movies = new JTable(new Object[][]{}, head);
		JTable my_propositions = new JTable(new Object[][]{}, head);
		JTable base = new JTable(new Object[][]{}, head);

		my_movies.setPreferredSize(new Dimension(width, height * 2));
		my_propositions.setPreferredSize(new Dimension(width, height * 2));
		base.setPreferredSize(new Dimension(width, height * 2));

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
		JMenuItem import_base = new JMenuItem("Importer un fichier de films");

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