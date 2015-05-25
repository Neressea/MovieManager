package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.jdatepicker.impl.*;
import java.util.*;
import model.*;

public class PopupAddMovie extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GraphicalView owner;

	private JPanel container;
	private JTextField field_title;
	private JDatePickerImpl field_year;
	private JTextField field_director;
	private JFormattedTextField field_duration;
	private JComboBox<String> box_type;
	private MyTextList list_actors;
	private MyTextList list_kinds;
	private JTextArea field_description;
	private JButton ok;

	public PopupAddMovie(GraphicalView view){
		owner = view;
		container = new JPanel();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); 
		c.fill = GridBagConstraints.HORIZONTAL;

		field_title = new JTextField(40);
		c.gridx=0;
		c.gridy=0;
		add("Titre", field_title, c);

		Properties p = new Properties();
		p.put("text.today", "Aujourd'hui");
		p.put("text.month", "Mois");
		p.put("text.year", "Année");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl date_panel = new JDatePanelImpl(model, p);
		field_year = new JDatePickerImpl(date_panel, new DateLabelFormatter());
		c.gridy=1;
		c.insets = new Insets(10,0,0,0);
		add("Année", field_year, c);

		field_duration=new JFormattedTextField(new Integer(0));
		c.gridy=2;
		add("Durée", field_duration, c);

		field_director = new JTextField(40);
		c.gridy=3;
		add("Réalisateur", field_director, c);

		box_type = new JComboBox<String>(new String[]{"Film", "Série télé"});
		c.gridy=4;
		add("Type", box_type, c);

		list_actors = new MyTextList();
		c.gridy=5;
		add("Acteurs", list_actors, c);

		list_kinds = new MyTextList();
		c.gridy=6;
		add("Genres", list_kinds, c);

		field_description = new JTextArea();
		c.gridy=7;
		c.ipady = 40;
		add("Description", field_description, c);

		ok = new JButton("Ajouter");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ListMovies<Movie> l = owner.getMoviesPerso();
				l.addMovie(createMovie(l.getRowCount()+1));
				
				setVisible(false);
				erased();
			}
		});

		c.gridy=9;
		c.ipady=0;
		container.add(ok, c);

		setContentPane(container);
		pack();
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public Movie createMovie(int id){
		String title = field_title.getText();
		String description = field_description.getText();
		String director = field_director.getText();
		int duration = (Integer) field_duration.getValue();
		int year = field_year.getModel().getYear();
		String type = (String) box_type.getSelectedItem();
		ArrayList<String> actors = list_actors.toList();
		ArrayList<String> kinds = list_kinds.toList();

		return new Movie(id, title, year, duration, director, type, kinds, actors, description);
	}

	public void erased(){
		field_description.setText("");
		field_title.setText("");
		field_director.setText("");
		field_duration.setValue(new Integer(0));
		Calendar today = Calendar.getInstance();
		field_year.getModel().setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
		box_type.setSelectedIndex(0);
		list_kinds.init();
		list_actors.init();
	}

	public void add(String label, JComponent component, GridBagConstraints c){
		JPanel new_row = new JPanel();
		new_row.setLayout(new BorderLayout());
		
		new_row.add(new JLabel(label), BorderLayout.WEST);
		new_row.add(component, BorderLayout.CENTER);
		
		container.add(new_row, c);
	}
}