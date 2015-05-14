package view;

import javax.swing.*;
import controller.*;
import model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class GraphicalView extends JFrame{

	private PopupAddMovie popup;
	private ListMovies<Movie> movies_base;
	private ListMovies<Movie> movies_perso;
	private ListMovies<Movie> movies_propose;
	private JTable my_movies;
	private JTable my_propositions;
	private JTable base;
	private JTabbedPane table;

	public GraphicalView(){
		this(500, 600);
	}

	public GraphicalView(int width, int height){
		super();
		setTitle("Movie Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		table = new JTabbedPane(SwingConstants.TOP);
		createMenu();
		
		table.addChangeListener(new PropositionController());

		movies_base = null;
		try{
			movies_base = ListMovies.load("films.txt");
		}catch(Exception e){
			System.out.println("Erreur durant la lecture du fichier");
			e.printStackTrace();
		}

		movies_perso = new ListMovies<Movie>();
		movies_propose = new ListMovies<Movie>();

		my_movies = new JTable(movies_perso){
			//Implement table cell tool tips.           
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = "<html><p width=\"500\">"+getValueAt(rowIndex, colIndex).toString()+"</p></html>";
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }

                return tip;
            }
		};

		my_propositions = new JTable(movies_propose){
			//Implement table cell tool tips.           
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = "<html><p width=\"500\">"+getValueAt(rowIndex, colIndex).toString()+"</p></html>";
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }

                return tip;
            }
		};

		base = new JTable(movies_base){
			//Implement table cell tool tips.           
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = "<html><p width=\"500\">"+getValueAt(rowIndex, colIndex).toString()+"</p></html>";
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }

                return tip;
            }
		};

		JPanel personnel_movies = new JPanel(new BorderLayout());
		JButton add_movie = new JButton("Ajouter un film");
		add_movie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
	       		popup.setVisible(true);
    		}
		});

		personnel_movies.add(add_movie, BorderLayout.NORTH);
		personnel_movies.add(new JScrollPane(my_movies), BorderLayout.CENTER);

		table.addTab("Mes films", personnel_movies);
		table.addTab("Mes recommandations", new JScrollPane(my_propositions));
		table.addTab("Base de films", new JScrollPane(base));

		this.setContentPane(table);

		popup = new PopupAddMovie(this);

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
		json.addActionListener(new JSONController(this));

		final JMenuItem import_base = new JMenuItem("Importer un fichier de films");
		import_base.addActionListener(new BaseController());

		table.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if(table.getSelectedIndex() == 1){
					import_base.setEnabled(false);
				}else{
					import_base.setEnabled(true);
				}
			}
		});

		file.add(json);
		file.add(import_base);

		JMenuItem about = new JMenuItem("A propos");
		about.addActionListener(new AboutController());

		help.add(about);

		this.setJMenuBar(barre);
	}

	public ListMovies<Movie> getSelectedList(){
		int ind = table.getSelectedIndex();
		if(ind == 2)
			return movies_base;

		if(ind == 0)
			return movies_perso;

		return movies_propose; 
	}

	public ListMovies<Movie> getMoviesPerso(){
		return movies_perso;
	}

	public JTabbedPane getTable(){
		return table;
	}

	public JTable getTablePerso(){
		return my_movies;
	}

	public static void main(String[] args) {
		GraphicalView v = new GraphicalView();
	}
}