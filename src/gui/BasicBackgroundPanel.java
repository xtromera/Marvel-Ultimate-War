package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;

public class BasicBackgroundPanel extends JComponent {

	  private Image backgroundImage;

	  // Some code to initialize the background image.
	  // Here, we use the constructor to load the image. This
	  // can vary depending on the use case of the panel.
	  public BasicBackgroundPanel(String fileName) throws IOException {
	    backgroundImage = new ImageIcon("BG.jpg").getImage();
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, this);
	  }
	}