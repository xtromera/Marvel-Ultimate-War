package gui;
import model.abilities.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import engine.Game;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import model.world.Hero;
import model.world.Villain;

public class BoardPanel extends JPanel implements ActionListener, ItemListener{
private Main main;
private JPanel p1;
private JButton[][] board;

private JButton up;
private JButton down;
private JButton left;
private JButton right;
private Direction d;

private JButton attack;
private JButton leaderAbility;
private JButton move;

private JButton castAbility;
private JComboBox<String> abilities;

private JLabel pl1;
private JLabel pl2;
private JLabel currChamp;

private JButton endTurn;

private JTextArea info;
private JLabel turnOrder;

private int x;
private int y;

private JLabel leader1;
private JLabel leader2;
JLabel l1;
JLabel l2;


public BoardPanel(Main main) {
	this.main=main;
	this.setLayout(null);
	p1=new JPanel();
	p1.setLayout(new GridLayout(5,5));
	p1.setBounds(450, 0, 1000, 1000);
	this.add(p1);
	board=new JButton[5][5];
	p1.setVisible(true);
	for(int i=0;i<board.length;i++) {
		for(int j=0;j<board.length;j++) {
			board[i][j]=new JButton();
			board[i][j].setFont(new Font("SansSerif",Font.ITALIC,22));
			board[i][j].addActionListener(this);
			p1.add(board[i][j]);
			
		}
	}
	//adding direction buttons
	up=new JButton("^");
	down=new JButton("v");
	left=new JButton("<");
	right=new JButton(">");
	left.setBounds(1465,915 , 140, 70);
	down.setBounds(1605,915 , 140, 70);
	right.setBounds(1745,915 , 140, 70);
	up.setBounds(1605,845 , 140, 70);
	left.addActionListener(this);
	down.addActionListener(this);
	right.addActionListener(this);
	up.addActionListener(this);
	this.add(left);
	this.add(down);
	this.add(right);
	this.add(up);
	
	attack=new JButton("ATTACK");
	move=new JButton("MOVE");
	attack.setBounds(1475, 760, 150, 75);
	move.setBounds(1725, 760, 150, 75);
	attack.addActionListener(this);
	move.addActionListener(this);
	this.add(attack);
	this.add(move);
	
	castAbility=new JButton("CAST ABILITY");
	castAbility.setBounds(1575, 650, 250, 100);
	abilities=new JComboBox<String>();
	abilities.addItem("SELECT ABILITY");
	abilities.setBounds(1575, 590, 250, 50);
	castAbility.addActionListener(this);
	this.add(abilities);
	this.add(castAbility);
	
	leaderAbility=new JButton("LEADER ABILITY");
	leaderAbility.setBounds(1550, 480, 300, 100);
	leaderAbility.addActionListener(this);
	this.add(leaderAbility);
	
	l1=new JLabel("Player 1 has used his leader ability");
	l2=new JLabel("Player 2 has used his leader ability");
	l1.setBounds(1675, 25, 400, 125);
	l2.setBounds(1700, 160, 200, 50);
	this.add(l1);
	this.add(l2);
	l1.setVisible(false);
	l2.setVisible(false);
	
	endTurn=new JButton("END TURN");
	endTurn.setBounds(1625, 405, 100, 50);
	endTurn.addActionListener(this);
	endTurn.setBackground(Color.red);
	endTurn.setForeground(Color.white);
	this.add(endTurn);
	
	currChamp= new JLabel();
	currChamp.setBounds(1525, 295, 300, 100);
	currChamp.setBackground(Color.BLACK);
	currChamp.setVisible(true);
	this.add(currChamp);
	
	pl2 = new JLabel(main.getPlayer2Name());
	pl2.setFont(new Font("SansSerif", Font.BOLD, 69));
	pl2.setForeground(new Color(0,0,153));
	pl2.setBounds(1475, 160, 400, 125);
	this.add(pl2);
	leader2=new JLabel("Leader: "+main.getGame().getSecondPlayer().getLeader().getName());
	leader2.setBounds(1500, 160, 200, 50);
	this.add(leader2);
	
	pl1 = new JLabel(main.getPlayer1Name());
	pl1.setFont(new Font("SansSerif", Font.BOLD, 69));
	pl1.setForeground(new Color(153,0,0));
	pl1.setBounds(1475, 25, 400, 125);
	this.add(pl1);
	leader1=new JLabel("Leader: "+main.getGame().getFirstPlayer().getLeader().getName());
	leader1.setBounds(1500, 25, 200, 50);
	this.add(leader1);
	
	info=new JTextArea();
	info.setEditable(false);
	JScrollPane p=new JScrollPane(info);
	p.setBounds(25, 400, 400, 500);
	info.setText("");
	this.add(p);
	
	turnOrder=new JLabel();
	//turnOrder.setBounds(10, 10, 350, 50);
	turnOrder.setForeground(Color.DARK_GRAY);
	JScrollPane ex=new JScrollPane(turnOrder);
	ex.setBounds(10, 10, 350, 50);
	this.add(ex);
	
	LoadtheBoard();
	loadAbilities();
	AllInfoChamp();
	infoUpdate();
	
	loadTurnOrder();
	
}



private void infoUpdate() {
	for (int i = 0; i < board.length; i++) {
		for (int j = 0; j < board.length; j++) {
					if(main.getGame().getBoard()[i][j] instanceof Champion) {
					String ChampInfo="";
					Champion c = (Champion) main.getGame().getBoard()[i][j];
					ChampInfo = "Name: " + c.getName() + "\n" + "Type: " + getType(c) + "\n" + "Health: " + c.getCurrentHP()
							+ "\n" + "Mana: " + c.getMana() + "\n" + "Current action point per turn: "
							+ c.getCurrentActionPoints() + "\n" + "Speed: " + c.getSpeed() + "\n" + "Range: "
							+ c.getAttackRange() + "\n" + "Attack damage: " + c.getAttackDamage();
					String ability = "Abilites: " + "\n";
					
					for (int a = 0; a < 3; a++) {
						ability = ability + c.getAbilities().get(a).getName() +"  "+"\n";
					}
					String effects=" Applied Effects:"+ "\n";
					for(int k=0;k<((Champion) main.getGame().getBoard()[i][j]).getAppliedEffects().size();k++) {
					//	System.out.println("da5al");
						effects=effects+((Champion) main.getGame().getBoard()[i][j]).getAppliedEffects().get(k).getName()+"\n"+
							"Duration: "+((Champion) main.getGame().getBoard()[i][j]).getAppliedEffects().get(k).getDuration()+"\n";
						
					}
					ChampInfo=ChampInfo+ability+effects;
			
			
					board[i][j].setToolTipText(ChampInfo);//how to make it on many lines i dont know how
				}else {
					board[i][j].setToolTipText("");
			}
			}
		}
}



private void loadTurnOrder() {
	turnOrder.setText("");
	String s="Current Champion's turn is: "+ main.getGame().getCurrentChampion().getName();
	turnOrder.setText(s);
	if (main.getGame().getTurnOrder().size()>1){
	Champion temp=(Champion) main.getGame().getTurnOrder().remove();
	s=s+" next in turn is: "+((Champion)(main.getGame().getTurnOrder().peekMin())).getName();
	main.getGame().getTurnOrder().insert(temp);
		turnOrder.setText(s);
	}
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
private String abilityType(Ability a) {
	if(a instanceof DamagingAbility)
		return "DamagingAbility";
	else if(a instanceof HealingAbility)
		return "HealingAbility";
	else if(a instanceof CrowdControlAbility)
		return "CrowdControlAbility";
	return "";
}
private void AllInfoChamp() {
	
	String s="";
	Champion c = main.getGame().getCurrentChampion();
	String ChampInfo = "Name: " + c.getName() + "\n" + "Type: " + getType(c) + "\n" + "Health: " + c.getMaxHP()
			+ "\n" + "Mana: " + c.getMana() + "\n" + "Maximum action point per turn: "
			+ c.getMaxActionPointsPerTurn() + "\n"+"Current Action point: "+c.getCurrentActionPoints()+"\n" + "Speed: " + c.getSpeed() + "\n" + "Range: "
			+ c.getAttackRange() + "\n" + "Attack damage: " + c.getAttackDamage();
	String ability = "";
	s=s+ChampInfo+"\n";
	for (int a = 0; a < 3; a++) {
		Ability ab=c.getAbilities().get(a);
		ability=ability+"Abilites: " + "\n"+c.getAbilities().get(a).getName() + "\n"+"Base CoolDown: "+ ab.getBaseCooldown()+"\n"+"Current cooldown: "+ab.getCurrentCooldown()+"\n"+
		"Area Of Effect: "+getArea(ab)+"\n"+ "Type: "+ abilityType(ab)+ "\n"+cc(ab)+ "Cast Range: "+ ab.getCastRange()+"\n"+
		"Mana Cost: "+ab.getManaCost()+"\n"+"Required Action Points: "+ ab.getRequiredActionPoints()+"\n";
		
	}
	s=s+ability;
	String effects="Applied Effects:"+ "\n";
	for(int i=0;i<main.getGame().getCurrentChampion().getAppliedEffects().size();i++) {
		effects=effects+main.getGame().getCurrentChampion().getAppliedEffects().get(i).getName()+"\n"+
			"Duration: "+main.getGame().getCurrentChampion().getAppliedEffects().get(i).getDuration()+"\n";
	}
	s=s+effects;
	info.setText(s);
	
}




private String cc(Ability ab) {
	if(ab instanceof CrowdControlAbility) {
		return"Effect: "+(((CrowdControlAbility) ab).getEffect()).getName()+"\n";
	}
	return null;
}



private String getArea(Ability ab) {
	if(ab.getCastArea()==AreaOfEffect.SINGLETARGET) {
		return "SingleTarget";
	}else if(ab.getCastArea()==AreaOfEffect.DIRECTIONAL) {
		return "Directional";
	}else if(ab.getCastArea()==AreaOfEffect.SELFTARGET) {
		return"SelfTarget";
	}else if(ab.getCastArea()==AreaOfEffect.SURROUND) {
		return "Surround";
	}else if(ab.getCastArea()==AreaOfEffect.TEAMTARGET) {
		return "TeamTarget";
	}
	return "";
}



private void loadAbilities() {
	abilities.removeAllItems();
	abilities.addItem("SELECT ABILITY");
	Champion c= main.getGame().getCurrentChampion();
	for(int i=0;i<c.getAbilities().size();i++) {
		abilities.addItem(c.getAbilities().get(i).getName());
	}
}




private void LoadtheBoard() {
	for(int i=0;i<board.length;i++) {
		for (int j = 0; j < board.length; j++) {
			if(main.getGame().getBoard()[i][j] instanceof Cover) {
				Cover c= (Cover)main.getGame().getBoard()[i][j];
				board[i][j].setText("COVER / "+c.getCurrentHP());
				board[i][j].setBackground(Color.magenta);
				board[i][j].setForeground(Color.white);
				//board[i][j].setEnabled(true);
			}
			else if(main.getGame().getBoard()[i][j] instanceof Champion){
				Champion champ=(Champion)main.getGame().getBoard()[i][j];
				if(main.getGame().getCurrentChampion().getName().equals(champ.getName())) {
					board[i][j].setText(champ.getName()+" / "+champ.getCurrentHP());
					board[i][j].setBackground(Color.yellow);
					board[i][j].setForeground(Color.black);
					//board[i][j].setEnabled(true);
				}
				else if(main.getGame().getFirstPlayer().getTeam().contains(champ)){
					board[i][j].setText(champ.getName()+" / "+champ.getCurrentHP());
					board[i][j].setBackground(new Color(153,0,0));
					board[i][j].setForeground(Color.white);
					//board[i][j].setEnabled(true);
				}
				else if(main.getGame().getSecondPlayer().getTeam().contains(champ)) {
					board[i][j].setText(champ.getName()+" / "+champ.getCurrentHP());
					board[i][j].setBackground(new Color(0,0,153));
					board[i][j].setForeground(Color.white);
					//board[i][j].setEnabled(true);
				}
				
			}
			else {
				board[i][j].setBackground(Color.black);
				board[i][j].setText("EMPTY :))");
				board[i][j].setForeground(Color.white);
				//board[i][j].setEnabled(false);
				
			}
		}
	}
	
}




@Override
public void actionPerformed(ActionEvent e) {
	
	if(e.getSource()==up) {
		d=Direction.DOWN;
	}
if(e.getSource()==down) {
	d=Direction.UP;
	}
if(e.getSource()==left) {
	d=Direction.LEFT;
}
if(e.getSource()==right) {
	d=Direction.RIGHT;
}
	if(e.getSource()==move) {
		try {
			main.getGame().move(d);
			LoadtheBoard();
			loadAbilities();
			AllInfoChamp();
			loadTurnOrder();
			infoUpdate();
			if(main.getGame().checkGameOver()!=null) {
				 JOptionPane.showMessageDialog(this, main.getGame().checkGameOver().getName()+" won!","END",JOptionPane.ERROR_MESSAGE);
				 main.dispose();
				 return;
			}
		} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
			
		}
	}
	if(e.getSource()==attack) {
		try {
			main.getGame().attack(d);
			LoadtheBoard();
			loadAbilities();
			AllInfoChamp();
			loadTurnOrder();
			infoUpdate();
			if(main.getGame().checkGameOver()!=null) {
				 JOptionPane.showMessageDialog(this, main.getGame().checkGameOver().getName()+" won!","END",JOptionPane.ERROR_MESSAGE);
				 main.dispose();
				 return;
			}
		} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}
	if(e.getSource()==endTurn) {
		if(main.getGame().checkGameOver()!=null) {
			 JOptionPane.showMessageDialog(this, main.getGame().checkGameOver().getName()+" won!","END",JOptionPane.ERROR_MESSAGE);
			 main.dispose();
			 return;
		}
		main.getGame().endTurn();
		LoadtheBoard();
		loadAbilities();
		AllInfoChamp();
		loadTurnOrder();
		infoUpdate();
		if(main.getGame().checkGameOver()!=null) {
			 JOptionPane.showMessageDialog(this, main.getGame().checkGameOver().getName()+" won!","END",JOptionPane.ERROR_MESSAGE);
			 main.dispose();
			 return;
		}
	}
	if(e.getSource()==castAbility) {
		if(abilities.getSelectedIndex()==0) {
			JOptionPane.showMessageDialog(this,"Please, select an ability to cast!","END",JOptionPane.ERROR_MESSAGE);
			return;
		}
		int abilityIndex=abilities.getSelectedIndex()-1;
		Ability a=main.getGame().getCurrentChampion().getAbilities().get(abilityIndex);
		if(a.getCastArea()==AreaOfEffect.DIRECTIONAL) {
			try {
				main.getGame().castAbility(a, d);
				LoadtheBoard();
				loadAbilities();
				AllInfoChamp();
				loadTurnOrder();
				infoUpdate();
				
			} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
				
				JOptionPane.showMessageDialog(this, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(a.getCastArea()==AreaOfEffect.SINGLETARGET) {
			try {
			main.getGame().castAbility(a, x, y);
				LoadtheBoard();
				loadAbilities();
				AllInfoChamp();
				loadTurnOrder();
				infoUpdate();
				
			} catch (NotEnoughResourcesException |InvalidTargetException|  AbilityUseException | CloneNotSupportedException e1) {
				
				JOptionPane.showMessageDialog(this, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			try {
			main.getGame().castAbility(a);
			LoadtheBoard();
			loadAbilities();
			AllInfoChamp();
			infoUpdate();
		loadTurnOrder();
			
		} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
			
			JOptionPane.showMessageDialog(this, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
		}
	}
	
	if(e.getSource()==leaderAbility) {
		try {
			if(!main.getGame().isFirstLeaderAbilityUsed() && main.getGame().getFirstPlayer().getTeam().contains(main.getGame().getCurrentChampion())&& main.getGame().getCurrentChampion()==main.getGame().getFirstPlayer().getLeader())
			l1.setVisible(true);//the leader ability need to be reconfigured i dont know how if anyone else but hero sa3at tshtaghal w sa3at laa weirdddddddd
			
			else {
				main.getGame().useLeaderAbility();
				
			}
			if(main.getGame().isSecondLeaderAbilityUsed() && main.getGame().getSecondPlayer().getTeam().contains(main.getGame().getCurrentChampion()) && main.getGame().getCurrentChampion()==main.getGame().getSecondPlayer().getLeader())
				l2.setVisible(true);
				else {
					main.getGame().useLeaderAbility();
					
					
				}
			LoadtheBoard();
			loadAbilities();
			AllInfoChamp();
			loadTurnOrder();
			infoUpdate();
		} catch (LeaderNotCurrentException | LeaderAbilityAlreadyUsedException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}
	for(int m=0;m<board.length;m++) {
		for (int n = 0; n < board.length; n++) {
			if(e.getSource()==board[m][n]) {
			x=m;
			y=n;
			}
		}
	}
}




@Override
public void itemStateChanged(ItemEvent e) {
	
	
}
}
