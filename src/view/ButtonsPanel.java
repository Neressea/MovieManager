package view;

import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ButtonsPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
				if(movies != null)
					my_propositions.setModel(movies);
				else{
					//Il n'y a pas de films entrés
					JOptionPane.showMessageDialog((JButton) arg0.getSource(), "Entrez des films pour recevoir des propositions !", "Erreur durant le processus", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		this.add(combo_nb);
		this.add(launch);
	}
}