package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import org.jdatepicker.impl.*;
import org.jdatepicker.util.*;
import org.jdatepicker.*;
import java.util.Properties;

public class PopupAddMovie extends JFrame{
	private GraphicalView owner;

	private JPanel container;
	private JTextField field_title;
	private JDatePickerImpl field_year;
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
		c.insets = new Insets(10,0,0,0);
		add("Durée", field_duration, c);

		box_type = new JComboBox<String>(new String[]{"Film", "Série télé"});
		c.gridy=3;
		c.insets = new Insets(10,0,0,0);
		add("Type", box_type, c);

		list_actors = new MyTextList();
		c.gridy=4;
		c.insets = new Insets(10,0,0,0);
		add("Acteurs", list_actors, c);

		list_kinds = new MyTextList();
		c.gridy=5;
		c.insets = new Insets(10,0,0,0);
		add("Genres", list_kinds, c);

		field_description = new JTextArea();
		c.gridy=6;
		c.ipady = 40;
		c.insets = new Insets(10,0,0,0);
		add("Description", field_description, c);

		ok = new JButton("Ajouter");
		c.gridy=7;
		c.ipady=0;
		c.insets = new Insets(10,0,0,0);
		container.add(ok, c);

		setContentPane(container);
		pack();
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public void add(String label, JComponent component, GridBagConstraints c){
		JPanel new_row = new JPanel();
		new_row.setLayout(new BorderLayout());
		
		new_row.add(new JLabel(label), BorderLayout.WEST);
		new_row.add(component, BorderLayout.CENTER);
		
		container.add(new_row, c);
	}
}