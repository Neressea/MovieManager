package view;

import javax.swing.*;

import controller.*;
import model.*;
import model.List;

import java.awt.*;
import java.awt.event.*;

import javax.swing.event.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class GraphicalView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PopupAddMovie popup;
	private List<Movie> movies_base;
	private List<Movie> movies_perso;
	private List<Movie> movies_propose;
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
			movies_base = List.load("films.txt");
		}catch(Exception e){
			System.out.println("Erreur durant la lecture du fichier");
			e.printStackTrace();
		}

		movies_perso = new List<Movie>();
		movies_propose = new List<Movie>();

		my_movies = new JTable(movies_perso){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			//Implement table cell tool tips.           
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = "<html><p width=\"500\">"+getValueAt(rowIndex, colIndex).toString()+"</p></html>";
                }catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }
                return tip;
            }
		};

		my_propositions = new JTable(movies_propose){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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

		JPanel proposition_panel = new JPanel(new BorderLayout());
		proposition_panel.add(new JScrollPane(my_propositions), BorderLayout.CENTER);
		ButtonsPanel panel_buttons = new ButtonsPanel(my_propositions, movies_base, movies_perso, movies_base.getList().size());
		proposition_panel.add(panel_buttons, BorderLayout.NORTH);

		base = new JTable(movies_base){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		
		JPanel base_panel = new JPanel(new BorderLayout());
		JButton add_list_button = new JButton("Ajouter la sélection à ma liste");
		base_panel.add(add_list_button, BorderLayout.NORTH);
		base_panel.add(new JScrollPane(base), BorderLayout.CENTER);

		final JPanel personnel_movies = new JPanel(new BorderLayout());
		JButton add_movie = new JButton("Ajouter un film");
		add_movie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
	       		popup.setVisible(true);
    		}
		});

		personnel_movies.add(add_movie, BorderLayout.NORTH);
		personnel_movies.add(new JScrollPane(my_movies), BorderLayout.CENTER);
		
		add_list_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int [] indexes = base.getSelectedRows();
				for (int i = 0; i < indexes.length; i++) {
					Movie m = movies_base.getList().get(indexes[i]);
					movies_perso.addMovie(m);
				}
				table.setSelectedComponent(personnel_movies);
			}
		});

		table.addTab("Mes films", personnel_movies);
		table.addTab("Mes recommandations", proposition_panel);
		table.addTab("Base de films", base_panel);

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
		import_base.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//We get the Json file
	       		JFileChooser chooser = new JFileChooser();
	       		FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fichiers JSON", "json");
	       		chooser.setFileFilter(filtre);
				int verif = chooser.showOpenDialog(import_base);
				String  json="";

				if(verif == JFileChooser.APPROVE_OPTION){
					String file = (chooser.getSelectedFile()).getAbsolutePath();
					try{
						String line;
						BufferedReader bfr = new BufferedReader(new FileReader(new File(file))); 
						while((line=bfr.readLine()) != null) json += line; //On gère le cas où le json est sur plusieurs lignes
						bfr.close();
					}catch(Exception e){
						//Si le fichier n'a pas été ouverte
						JOptionPane.showMessageDialog(import_base, "Le fichier n'a pu être lu", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}

	       		List<Movie> list = List.fromJson(json);
	       		switch(table.getSelectedIndex()){
	       		case 1:
	       			movies_perso = list;
	       			my_movies.setModel(movies_perso);
	       			break;
	       		case 3:
	       			movies_base = list;
	       			base.setModel(movies_base);
	       			break;
	       		}
    		}
		});

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

	public List<Movie> getSelectedList(){
		int ind = table.getSelectedIndex();
		if(ind == 2)
			return movies_base;

		if(ind == 0)
			return movies_perso;

		return movies_propose; 
	}

	public List<Movie> getMoviesPerso(){
		return movies_perso;
	}

	public JTabbedPane getTable(){
		return table;
	}

	public JTable getTablePerso(){
		return my_movies;
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		GraphicalView v = new GraphicalView();
	}
}