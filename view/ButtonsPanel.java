package view;

import model.*;

import javax.swing.*;
import controller.*;
import model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class ButtonsPanel extends JPanel{
	JButton launch;
	JComboBox<Integer> combo_nb;

	public ButtonsPanel(final JTable my_propositions, final ListMovies<Movie> movies_base, final ListMovies<Movie> movies_viewed, final int nb_max){

		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i=1; i<=movies_base.getList().size(); i++) {
			numbers.add(i);
		}

		launch = new JButton("Proposer !");
		combo_nb = new JComboBox<Integer>(numbers.toArray(new Integer[numbers.size()]));

		launch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ListMovies<Movie> movies = ListMovies.getPropositions(movies_base, movies_viewed, (Integer) combo_nb.getSelectedItem(), 0);
				my_propositions.setModel(movies);
			}
		});

		this.add(combo_nb);
		this.add(launch);
	}
}