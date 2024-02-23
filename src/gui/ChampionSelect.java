package gui;

import engine.*;
import model.abilities.Ability;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ChampionSelect extends JPanel implements ActionListener {
	private Main main;
	private JLabel n1;
	private Game game;
	private int[] team1 = new int[] {99,99,99,99};
	private int[] team2 = new int[] {99,99,99,99};
	private JButton[] Champions1;
	private JTextArea info;
	private String ChampInfo = "";
	private JTextArea team;
	private JLabel currChamp;
	private JLabel teamPlayer;//I Stopped here
	private JButton Lock;
	private JButton LockLeader;
	JButton start;
	
	private int chosen = 98;
	private int counter = 0;
	private int chosenind = 0;
	private JButton[] LeaderChoose;
	private boolean Locked1;
	private boolean Locked2;
	private boolean LockedLeader1;
	private boolean LockedLeader2;
	//add the current champion selected and your team

	public ChampionSelect(Main main) {

		this.main = main;

		this.setLayout(null);
		n1 = new JLabel(main.player1Name + " please select :))");
		// this.setBackground(Color.red);
		n1.setFont(new Font("Comic Sans", Font.BOLD, 50));
		n1.setBounds(700, 60, 600, 100);
		this.add(n1);
		String[] Champions = new String[Game.getAvailableChampions().size()];
		Champions1 = new JButton[Champions.length];
		LeaderChoose = new JButton[3];
		int k = 0;
		int j = 0;
		for (int i = 0; i < Game.getAvailableChampions().size(); i++) {
			Champions1[i] = new JButton(Game.getAvailableChampions().get(i).getName());
			Champions1[i].setBounds(126 * (1 + j), 600 + k, 150, 25);
			j++;
			if (i == 7) {
				k = 25;
				j = 0;
			}
			Champions1[i].addActionListener(this);
			this.add(Champions1[i]);
		}
		info = new JTextArea();
		info.setEditable(false);
		info.setBounds(750, 200, 400, 300);
		this.add(info);
		currChamp=new JLabel("Current selected champion:");
		currChamp.setBounds(750, 150, 200, 50);
		this.add(currChamp);
		team = new JTextArea("");
		team.setEditable(false);
		team.setBounds(1500, 250, 100, 150);
		this.add(team);
		teamPlayer=new JLabel("team:");
		teamPlayer.setBounds(1500, 200, 100, 50);
		this.add(teamPlayer);
		Lock = new JButton("Lock");
		Lock.setBounds(1000, 650, 120, 25);
		Lock.addActionListener(this);
		LockLeader = new JButton("Lock Leader");
		LockLeader.setBounds(1000, 550, 120, 25);
		LockLeader.addActionListener(this);
		this.add(Lock);
		start=new JButton("Start game");
		start.setBounds(950, 850, 125, 25);
		start.addActionListener(this);
		this.add(start);
		start.setVisible(false);
		
	}

	public String getType(Champion c) {
		if (c instanceof Hero)
			return "Hero";
		if (c instanceof Villain)
			return "Villain";
		if (c instanceof AntiHero)
			return "AntiHero";
		return "";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = 0;

		while (i < Champions1.length) {
			if (e.getSource() == Champions1[i]) {
				Champion c = Game.getAvailableChampions().get(i);
				ChampInfo = "Name: " + c.getName() + "\n" + "Type: " + getType(c) + "\n" + "Health: " + c.getMaxHP()
						+ "\n" + "Mana: " + c.getMana() + "\n" + "Maximum action point per turn: "
						+ c.getMaxActionPointsPerTurn() + "\n" + "Speed: " + c.getSpeed() + "\n" + "Range: "
						+ c.getAttackRange() + "\n" + "Attack damage: " + c.getAttackDamage();
				String ability = "Abilites: " + "\n";
				info.setText(ChampInfo + "\n");
				for (int a = 0; a < 3; a++) {
					ability = ability + c.getAbilities().get(a).getName() + "\n";
				}
				info.setText(info.getText() + ability);
				chosen = i;
			}
			i++;
		}
		// System.out.println(chosen);
		if (e.getSource() == Lock && !Locked1) {
			
			if (counter == 3) {
				
				JOptionPane.showMessageDialog(this, "You can only choose 3 champions", "Error",
						JOptionPane.ERROR_MESSAGE);
				for (int t = 0; t < Champions1.length; t++) {
					Champions1[t].setVisible(false);
				}
				Lock.setVisible(false);
				for (int s = 0; s < 3; s++) {
					LeaderChoose[s] = new JButton(game.getAvailableChampions().get(team1[s]).getName());
					LeaderChoose[s].setBounds(126 * (1 + s), 650, 150, 25);
					LeaderChoose[s].addActionListener(this);
					this.add(LeaderChoose[s]);
				}
				Locked1 = true;
				n1.setText("Select your Leader");
				this.add(LockLeader);
				chosen=98;
			    counter=0;
			    return;
				
				
//		
			} else {
				for(int m=0;m<team1.length;m++) {
					if(chosen==team1[m] || chosen==team2[m])
						return;
				}
				if(chosen==98)
					return;
				team.setText(team.getText() + "\n" + Champions1[chosen].getText());
				
				Champions1[chosen].setEnabled(false);
				
				team1[counter] = chosen;
				counter++;
			}
			// System.out.println(team1[2]);
		}

		if (e.getSource() == Lock && Locked1 && !Locked2) {
		
			if (counter == 3) {
				JOptionPane.showMessageDialog(this, "You can only choose 3 champions", "Error",
						JOptionPane.ERROR_MESSAGE);
				for (int t = 0; t < Champions1.length; t++) {
					Champions1[t].setVisible(false);
				}
				Lock.setVisible(false);
				for (int s = 0; s < 3; s++) {
					LeaderChoose[s] = new JButton(game.getAvailableChampions().get(team2[s]).getName());
					LeaderChoose[s].setBounds(126 * (1 + s), 650, 150, 25);
					LeaderChoose[s].addActionListener(this);
					this.add(LeaderChoose[s]);
				}
				n1.setText("Select your Leader");
				Locked2 = true;
				chosen=98;
				this.add(LockLeader);

			} else {
				for(int m=0;m<team2.length;m++) {
					if(chosen==team2[m] || chosen==team1[m])
						return;
				}
				Champions1[chosen].setEnabled(false);
				
				team.setText(team.getText() + "\n" + Champions1[chosen].getText());
				
				team2[counter] = chosen;
				counter++;
			}
			// System.out.println(team1[2]);
		}

		i = 0;

		while (i < LeaderChoose.length) {
			if (e.getSource() == LeaderChoose[i] && !LockedLeader1) {
				Champion c = Game.getAvailableChampions().get(team1[i]);
				ChampInfo = "Name: " + c.getName() + "\n" + "Type: " + getType(c) + "\n" + "Health: " + c.getMaxHP()
						+ "\n" + "Mana: " + c.getMana() + "\n" + "Maximum action point per turn: "
						+ c.getMaxActionPointsPerTurn() + "\n" + "Speed: " + c.getSpeed() + "\n" + "Range: "
						+ c.getAttackRange() + "\n" + "Attack damage: " + c.getAttackDamage();
				String ability = "Abilites: " + "\n";
				info.setText(ChampInfo + "\n");
				for (int a = 0; a < 3; a++) {
					ability = ability + c.getAbilities().get(a).getName() + "\n";
				}
				info.setText(info.getText() + ability);
				chosen = team1[i];
				chosenind = i;
			}
			if (e.getSource() == LeaderChoose[i] && LockedLeader1 && Locked2) {
				Champion c = Game.getAvailableChampions().get(team2[i]);
				ChampInfo = "Name: " + c.getName() + "\n" + "Type: " + getType(c) + "\n" + "Health: " + c.getMaxHP()
						+ "\n" + "Mana: " + c.getMana() + "\n" + "Maximum action point per turn: "
						+ c.getMaxActionPointsPerTurn() + "\n" + "Speed: " + c.getSpeed() + "\n" + "Range: "
						+ c.getAttackRange() + "\n" + "Attack damage: " + c.getAttackDamage();
				String ability = "Abilites: " + "\n";
				info.setText(ChampInfo + "\n");
				for (int a = 0; a < 3; a++) {
					ability = ability + c.getAbilities().get(a).getName() + "\n";
				}
				info.setText(info.getText() + ability);
				chosen = team2[i];
				chosenind = i;
			}
			i++;
		}
		if (e.getSource() == LockLeader && !Locked2) {
			if (!LockedLeader1) {
				
				if(chosen>=15) {
					JOptionPane.showMessageDialog(this, "please select a leader first", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				team1[3] = chosen;
				LeaderChoose[chosenind].setEnabled(false);
				LockedLeader1 = true;
			} else {
				JOptionPane.showMessageDialog(this, "You can only choose 1 Leader", "Error", JOptionPane.ERROR_MESSAGE);
				this.remove(LockLeader);
				for (int s = 0; s < 3; s++) {
					this.remove(LeaderChoose[s]);
				}
				this.validate();
				this.repaint();
				n1.setText(main.player2Name + " please select :))");

//				int k = 0;
//				int j = 0;
				for (int l = 0; l < Game.getAvailableChampions().size(); l++) {
//					Champions1[l] = new JButton(Game.getAvailableChampions().get(l).getName());
//					Champions1[l].setBounds(126 * (1 + j), 600 + k, 150, 25);
//					j++;
//					if (l == 7) {
//						k = 25;
//						j = 0;
//					}
//					Champions1[l].addActionListener(this);
					Champions1[l].setVisible(true);
				}
				Lock.setVisible(true);
				counter = 0;

				team.setText("");
				this.validate();
				this.repaint();
			}

		}
		if (e.getSource() == LockLeader && Locked2) {
			if (!LockedLeader2) {
				
				if(chosen>=15) {
					JOptionPane.showMessageDialog(this, "please select a leader first", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				LeaderChoose[chosenind].setEnabled(false);
				//System.out.print(chosen);
				team2[3] = chosen;
				LockedLeader2 = true;
			} else {
				JOptionPane.showMessageDialog(this, "You can only choose 1 Leader", "Error", JOptionPane.ERROR_MESSAGE);

				this.remove(LockLeader);

				for (int s = 0; s < 3; s++) {
					this.remove(LeaderChoose[s]);
				}
				
				n1.setText("LET THE GAMES BEGIN!");
				this.remove(team);

				info.setText("LESSGGGOOOOOOO");
				start.setVisible(true);
//				System.out.println(team1[3]);
//				System.out.println(team2[3]);
				
				this.validate();
				this.repaint();
			}

		}
		if(e.getSource()==start) {
//			System.out.println("the game is starting");
			main.SwitchToGame(team1,team2);
			this.validate();
			this.repaint();
		}
	}

}
