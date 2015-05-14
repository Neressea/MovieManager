package view;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyTextList extends JPanel{
	private JPanel list;
	private ArrayList<JTextField> fields;

	public MyTextList(){
		setLayout(new BorderLayout());
		fields = new ArrayList<JTextField>();

		JButton add = new JButton("+");

		list = new JPanel();
		list.setLayout(new GridLayout(0, 1, 30, 30));
		fields.add(new JTextField(25));
		list.add(fields.get(fields.size()-1));
		JScrollPane jsp = new JScrollPane(list,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(40, 70));
		add(jsp, BorderLayout.CENTER);
		add(add, BorderLayout.EAST);

		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				fields.add(new JTextField(25));
				list.add(fields.get(fields.size()-1));
				list.revalidate();
				list.repaint();
			}
		});
	}

	public static void main(String[] args) {
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		p.add(new JScrollPane(p1));

		p1.add(new JTextField(25));

		JFrame f = new JFrame();
		f.setContentPane(p);
		f.pack();
		f.setVisible(true);

		p1.add(new JTextField(25));
		//p.repaint();
	}
}