package controller;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutController implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {

		JOptionPane jop = new JOptionPane();
	        String mess = "Bienvenue dans Movie Manager !\n"
	        	+ "Ce logiciel a été codé par Nicolas BEDRINE et Vincent ALBERT dans le cadre d'un projet de Structure de Données.";


	        jop.showMessageDialog(((JMenuItem)arg0.getSource()), mess, "À propos", JOptionPane.INFORMATION_MESSAGE);         
    }
}