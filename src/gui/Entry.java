package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Entry extends JPanel implements ActionListener{
private Main main;
private JButton start;
	public Entry(Main main) {
		this.main=main;
		ImageIcon image1= new ImageIcon("BG.png");
		JLabel img=new JLabel("");
		img.setIcon(image1);
		img.setBounds(0, 0, 1900, 1000);
		img.setOpaque(false);
		this.add(img);
		this.setLayout(null);
		start= new JButton("Start");
		start.setBounds(950, 500, 125, 25);
		start.addActionListener(this);
		this.add(start);
		
		this.repaint();
		this.revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start) {
			main.SwitchToPlayerSelectPanel();
		}
		
	}

}
