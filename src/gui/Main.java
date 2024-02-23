package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.swing.*;

import engine.Game;
import engine.Player;
import model.world.Champion;

public class Main extends JFrame {
	Game game;
	Entry entry;
	PlayerSelect playerselect;
	String player1Name;
	String player2Name;
	ChampionSelect championselect;
	BoardPanel panel;

	public Main() throws IOException {
		entry = new Entry(this);
		this.getContentPane().add(entry);
		this.setSize(1900, 1000);
		this.setVisible(true);
		this.setTitle("Marvel War :))");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//	this.pack();
//		ImageIcon background = new ImageIcon("BG.jpg");
//		Image img = background.getImage();
//		Image temp = img.getScaledInstance(1900, 1000, Image.SCALE_SMOOTH);
//		background = new ImageIcon(temp);
//		JLabel back = new JLabel(background);
//		back.setLayout(null);
//		back.setBounds(0, 0, 1900, 1000);
//		this.add(back);
//		ImageIcon image1= new ImageIcon("BG.jpg");
//		this.setContentPane(new BasicBackgroundPanel("BG.jpg"));
		ImageIcon image2= new ImageIcon("icon.png");
		this.setIconImage(image2.getImage());

	}

	public void SwitchToPlayerSelectPanel() {
		this.getContentPane().remove(entry);
		playerselect = new PlayerSelect(this);
		this.getContentPane().add(playerselect);

		this.setSize(650, 200);

	}

	public void SwitchToChampSelectPanel(String text1, String text2) {

		player1Name = text1;
		player2Name = text2;

		try {
			Game.loadAbilities("Abilities.csv");
			Game.loadChampions("Champions.csv");

			this.setSize(1900, 1000);
			this.getContentPane().remove(playerselect);

			championselect = new ChampionSelect(this);
			this.getContentPane().add(championselect);

		} catch (IOException e) {

			e.printStackTrace();
		}

		this.validate();
		this.repaint();

	}

	public static void main(String[] args) throws IOException {

		Main myFrame = new Main();

	}


	public void SwitchToGame(int[] team1, int[] team2) {
		Player p1=new Player(player1Name);
		Player p2=new Player(player2Name);
		Champion c=Game.getAvailableChampions().get(team1[0]);
		p1.getTeam().add((Champion)Game.getAvailableChampions().get(team1[0]));
		p1.getTeam().add((Champion)Game.getAvailableChampions().get(team1[1]));
		p1.getTeam().add((Champion)Game.getAvailableChampions().get(team1[2]));
		p1.setLeader((Champion)Game.getAvailableChampions().get(team1[3]));
		
		p2.getTeam().add((Champion)Game.getAvailableChampions().get(team2[0]));
		p2.getTeam().add((Champion)Game.getAvailableChampions().get(team2[1]));
		p2.getTeam().add((Champion)Game.getAvailableChampions().get(team2[2]));
		p2.setLeader((Champion)Game.getAvailableChampions().get(team2[3]));
		game=new Game(p1,p2);
		this.getContentPane().remove(championselect);
		panel= new BoardPanel(this);
		this.add(panel);
		this.validate();
		this.repaint();
		
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public PlayerSelect getPlayerselect() {
		return playerselect;
	}

	public void setPlayerselect(PlayerSelect playerselect) {
		this.playerselect = playerselect;
	}

	public String getPlayer1Name() {
		return player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public ChampionSelect getChampionselect() {
		return championselect;
	}

	public void setChampionselect(ChampionSelect championselect) {
		this.championselect = championselect;
	}

	public BoardPanel getPanel() {
		return panel;
	}

	public void setPanel(BoardPanel panel) {
		this.panel = panel;
	}

}
