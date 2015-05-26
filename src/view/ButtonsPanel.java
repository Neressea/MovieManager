package view;

import model.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.util.ArrayList;

public class ButtonsPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton launch;
	private JComboBox<Integer> combo_nb;
	private JSpinSlider slider;
	private double max_dist;

	public ButtonsPanel(final JTable my_propositions, final List<Movie> movies_base, final List<Movie> movies_viewed, final int nb_max){

		max_dist = 0.5;
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i=1; i<=movies_base.getList().size(); i++) {
			numbers.add(i);
		}
		
		slider = new JSpinSlider(300, 30, 0.05, 1.0, 0.5, 0.01);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				max_dist = slider.getValue();
			}
		});

		launch = new JButton("Proposer !");
		combo_nb = new JComboBox<Integer>(numbers.toArray(new Integer[numbers.size()]));

		launch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				List<Movie> movies = List.getPropositions(movies_base, movies_viewed, (Integer) combo_nb.getSelectedItem(), 0);
				if(movies != null)
					my_propositions.setModel(movies);
				else{
					//Il n'y a pas de films entrés
					JOptionPane.showMessageDialog((JButton) arg0.getSource(), "Entrez des films pour recevoir des propositions !", "Erreur durant le processus", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		this.add(slider);
		this.add(combo_nb);
		this.add(launch);
	}
}