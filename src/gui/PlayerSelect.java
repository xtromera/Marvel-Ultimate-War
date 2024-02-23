package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PlayerSelect extends JPanel implements ActionListener{

		Main main;
		
		private JLabel l1;
		private JLabel l2;
		
		private JTextField name1;
		private JTextField name2;
		
		private JButton next;
		
	
	
	
		public PlayerSelect(Main main) {
			
			this.main = main;
			
			this.setLayout(null);
			this.setVisible(true);
			
			l1 = new JLabel("Player 1 Name");
			l1.setBounds(30, 30, 120, 25);
			this.add(l1);
			
			name1 = new JTextField();
			name1.setBounds(150, 30, 150, 25);
			this.add(name1);
			
			l2 = new JLabel("Player 2 Name");
			l2.setBounds(330, 30, 120, 25);
			this.add(l2);
			
			name2 = new JTextField();
			name2.setBounds(450, 30, 150, 25);
			this.add(name2);
			
			next = new JButton("Next >>");
			next.setBounds(250, 100, 120, 30);
			next.addActionListener(this);
			this.add(next);
			
					
					
			
		}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == next) {
			if(name1.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "pls choose a name for player 1", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else if(name2.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "pls choose a name for player 2", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				main.SwitchToChampSelectPanel(name1.getText(), name2.getText());
			}
		}
		
	}

}
