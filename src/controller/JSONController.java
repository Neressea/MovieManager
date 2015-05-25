package controller;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import view.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class JSONController implements ActionListener{
	private GraphicalView owner;

	public JSONController(GraphicalView gv){
		owner = gv;
	}

	public void actionPerformed(ActionEvent arg0) {

		String json = owner.getSelectedList().toJson();

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers json", "json");
		chooser.setFileFilter(filter);
		int verif = chooser.showSaveDialog(owner);
		if(verif == JFileChooser.APPROVE_OPTION){

			try{
				String dest = chooser.getSelectedFile().getAbsolutePath();
				dest = dest.concat(".json");
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dest)));

				bw.write(json);

				bw.close();

			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}