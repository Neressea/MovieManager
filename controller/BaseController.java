package controller;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BaseController implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Base invoquée !");
	}
}