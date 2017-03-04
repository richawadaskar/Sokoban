package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import controller.Controller;

public class View {
	//Attributes
	public JFrame window;

	JPanel mainPanel;
	MyDrawingPanel drawingPanel;
	JLabel backgroundImage;
	JLabel screenTitle;
	
	JMenuBar menuBar;
	JMenu gameMenu;
	JMenu optionsMenu;
	JMenu helpMenu;
	JMenuItem gameItem;
	JMenuItem exitItem;
	JMenuItem levelItem;
	JMenuItem howItem;
	JMenuItem aboutItem;
	
	JPanel timerPanel;
	JLabel timer;
	int time = 0;
	
	JPanel stepPanel;
	JLabel countStep;
	int stepsTaken = 0;
	
	JPanel timerStepPanel;

	JPanel GameMenuPanel;
	JButton startButton;
	JButton exitButton;
	
	JPanel storyPanel;
	
	Controller puzzleController;
	
	public View(Controller control){
		puzzleController = control;
		
		//Create the window
		window = new JFrame("Sokoban - Aladdin");
		window.setBounds(200, 75, 600, 710);
		window.setMinimumSize(new Dimension(1200, 740));  
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		try {
			backgroundImage = new JLabel(new ImageIcon(ImageIO.read(new File("images/Background_Tile.png"))));
			screenTitle = new JLabel(new ImageIcon(ImageIO.read(new File("images/Aladdin_Title.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		window.setContentPane(backgroundImage);  
		
		backgroundImage.setLayout(null);
		
		//Create GUI elements
		
		//Drawing Panel
		drawingPanel = new MyDrawingPanel(puzzleController);
		drawingPanel.setPreferredSize(new Dimension((int)(16.75*30), (int)(16.75*30)));  
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());
		
		//Menu Bar
		menuBar = new JMenuBar();
		
		//Menu 
		gameMenu = new JMenu("Game");
		optionsMenu = new JMenu("Options");
		helpMenu = new JMenu("Help");
		
		//Menu Items
		gameItem = new JMenuItem("New Game");
		exitItem = new JMenuItem("Exit");
		levelItem = new JMenuItem("Change Level");
		howItem = new JMenuItem("How to Play");
		howItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
		
		aboutItem = new JMenuItem("About");
		
		//Add items to menu 
		gameMenu.add(gameItem);
		gameMenu.add(exitItem);
		optionsMenu.add(levelItem);
		helpMenu.add(howItem);
		helpMenu.add(aboutItem);
		
		//Add menus to bar
		menuBar.add(gameMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		
		//Add menuBar to window
		window.setJMenuBar(menuBar);
		
		//Add GUI elements to window Content pane
		mainPanel = new JPanel(); 
		mainPanel.setBounds(650, 10, (int)(16.75*30), (int)(16.75*30));
		
		timerStepPanel = new JPanel();
		timerStepPanel.setBounds(700, 520, 400, 55);
		
		timerPanel = new JPanel();
		timerPanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createLineBorder(Color.blue, 2), 
	             "Time Elasped (Sec)", TitledBorder.LEFT, TitledBorder.TOP));
		timerPanel.setBounds(700, 520, 150, 50);   
		timerPanel.setPreferredSize(new Dimension(150, 50));  
		timer = new JLabel(" ");  
		timerPanel.add(timer);
		
		stepPanel = new JPanel();
		stepPanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createLineBorder(Color.blue, 2), 
	             "Steps Taken", TitledBorder.LEFT, TitledBorder.TOP)); 
		stepPanel.setBounds(870, 520, 150, 100);
		stepPanel.setPreferredSize(new Dimension(150, 50));
		countStep = new JLabel(" ");
		stepPanel.add(countStep);

		GameMenuPanel = new JPanel();
		GameMenuPanel.setPreferredSize(new Dimension(440, 450)); 
		GameMenuPanel.setBounds(50, 150, 440, 250);
		
		try {
			startButton = new JButton(new ImageIcon(ImageIO.read(new File("images/menuStartButton.png"))));
			exitButton = new JButton(new ImageIcon(ImageIO.read(new File("images/menuExitButton.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startButton.setBounds(50, 150, 440, 25);
		exitButton.setBounds(50, 200, 440, 25);
		
		GameMenuPanel.add(startButton);
		GameMenuPanel.add(exitButton);
		
		mainPanel.add(drawingPanel);
		
		timerStepPanel.add(timerPanel);
		timerStepPanel.add(stepPanel);
		
		screenTitle.setPreferredSize(new Dimension(350, 115));  
		screenTitle.setBounds(50, 20, 350, 115);  
		
		storyPanel = new JPanel();
		storyPanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createLineBorder(Color.blue, 2), 
	             "Story Panel", TitledBorder.LEFT, TitledBorder.TOP));
		storyPanel.setBounds(50, 450, 440, 200); 
		
		backgroundImage.add(screenTitle);
		backgroundImage.add(GameMenuPanel);
		backgroundImage.add(storyPanel); 
		backgroundImage.add(timerStepPanel);
		backgroundImage.add(mainPanel);
		
		//Set window to visible
		window.setVisible(true);  
	}

	public void addKeyListeners(KeyListener key){
		//window.addKeyListener(key);
		//window.requestFocus();
		drawingPanel.addKeyListener(key); 
		drawingPanel.requestFocus();  
	}
	
	public void addActionListeners(ActionListener a){
		howItem.addActionListener(a);
		aboutItem.addActionListener(a);
		gameItem.addActionListener(a);
		exitItem.addActionListener(a);
		levelItem.addActionListener(a);    
		startButton.addActionListener(a);
		exitButton.addActionListener(a);
	}
	
	public void repaintBoard(){
		//repaint game board.
		//The controller has a getTile method that returns a buffered image 
		//which can be used to get the images for each coordinate point. 
		drawingPanel.repaint();
	}
	
	public void resetTimer(){
		timer.setText(" "); 
		timerPanel.repaint();
		time = 0;
	}
	
	public void updateTime() {
		// TODO Auto-generated method stub
		timer.setText(" "  + getTime());
		timerPanel.repaint();
	}

	private String getTime() {
		// TODO Auto-generated method stub
		time += 1;
		return Integer.toString(time);
	}
	
	public void resetSteps(){
		countStep.setText(" ");
		stepPanel.repaint();
		stepsTaken = 0;
	}
	
	public void updateSteps(){
		countStep.setText(" " + getStep());
		stepPanel.repaint();
	}

	private String getStep() {
		// TODO Auto-generated method stub
		stepsTaken += 1;
		return Integer.toString(stepsTaken);  
	}
	
	public JButton getStart(){
		return startButton;
	}
	
	public JButton getExit(){
		return exitButton;
	}
	
	public void giveFocus(){
		drawingPanel.requestFocus();
	}
}