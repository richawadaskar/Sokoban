package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import model.Model;
import model.Movable;
import model.Tile;
import view.View;

public class Controller implements KeyListener, ActionListener{

	Model sokobanModel;
	View sokobanView;
	BufferedImage ALADDIN, ABU, GENIE, IAGO, JASMINE, RAJAH, MAGIC_CARPET, 
					FLOOR, TARGET, BLOCK, WALL, DARK, SEMIDARK, PEDDLER;
	Timer time = new Timer(1000, this); 
	JEditorPane helpContent;
	JEditorPane aboutContent;
	JScrollPane helpPane;
	JScrollPane aboutPane;
	
	/**
	 * Start with the how to play page. Ask the user to press space to begin the game.
	 * Once the user presses the spacebar, the controller sets the level to 0, and 
	 * model loads the test level. View needs to have a method that can accept a 
	 * buffered image and can draw it at a given coordinate. 
	 * 
	 */
	public Controller(){
		sokobanModel = new Model();
		
		try{
			ALADDIN = ImageIO.read(new File("images/aladdin.png"));
			ABU = ImageIO.read(new File("images/abu.png"));
			IAGO = ImageIO.read(new File("images/iago.png"));
			JASMINE = ImageIO.read(new File("images/jasmine.png"));
			RAJAH = ImageIO.read(new File("images/rajah.png"));
			MAGIC_CARPET = ImageIO.read(new File("images/magic_carpet.png"));
			FLOOR = ImageIO.read(new File("images/floor.png"));
			TARGET = ImageIO.read(new File("images/target.png"));
			GENIE = ImageIO.read(new File("images/genie.png"));
			BLOCK = ImageIO.read(new File("images/block.png"));
			WALL = ImageIO.read(new File("images/wall.png"));
			DARK = ImageIO.read(new File("images/dark.png"));
			SEMIDARK = ImageIO.read(new File("images/semiDark.png"));
			PEDDLER = ImageIO.read(new File("images/peddler.png"));
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
		
		sokobanModel.setLevel(1);
		sokobanView = new View(this);
		sokobanView.addKeyListeners(this);
		sokobanView.addActionListeners(this);
		
		sokobanView.repaintBoard();
	}
	
	/**
	 * Method that returns whether the tile at the position is transparent and
	 * it's background should also be drawn
	 * 
	 * Returns 0 if background is a floor tile, 1 if it is a target and the background
	 * for the target should also be drawn, and -1 if it isn't transparent.
	 */
	public boolean isTransparent(int row, int col) {
		Tile current = sokobanModel.getTile(row,col);
		
		if (current.getType() == Tile.getTarget()) {
			return true;
		} else if (current.getType() == Tile.getCharacter()) {
			return true;
		} else if (current.getDarkness() == Tile.getSemiDark()) {
			return true;
		} else if (current.getRescue()) {
			return true;
		}
		
		return false;
	}
	
	public boolean singleLayer(int row, int col) {
		Tile current = sokobanModel.getTile(row,col);
		
		if (current.getType() == Tile.getTarget() || current.getRescue()) {
			return !(current.getDarkness() == Tile.getSemiDark());
		} else if (current.getType() == Tile.getCharacter()) {
			Movable character = (Movable)(current);
			Tile underneath = character.getCovering();
			
			if(underneath.getType() == Tile.getFloor()){
				return true;
			} else if(underneath.getType() == Tile.getTarget()){
				return false;
			}
		}
		
		if (current.getDarkness() == Tile.getSemiDark()) {
			return true;
		}
		
		return false;
	}

	/**
	 * Method that returns a buffered image to the view
	 */
	public BufferedImage getImage(int row, int col) {
		if(sokobanModel.getTile(row,col).getDarkness() == Tile.getDark()) {
			return DARK;
		}
		
		if(sokobanModel.getTile(row,col).getDarkness() == Tile.getSemiDark()) {
			return SEMIDARK;
		}
		
		return getTile(row, col);
	}
	
	public BufferedImage getTile(int row, int col) {
		Tile current = sokobanModel.getTile(row, col);
		
		return getTile(current);
	}
	
	private BufferedImage getTile(Tile current) {
		if(current.getType() == Tile.getCharacter()){
			//load character
			return ALADDIN;
		} else if(current.getType() == Tile.getWall()){
			//load wall
			return WALL;
		} else if(current.getType() == Tile.getFloor()){
			//load floor tile
			return FLOOR;
		} else if(current.getType() == Tile.getBlock()){
			//load box
			return BLOCK;
		} else if(current.getType() == Tile.getTarget()){
			//load target
			return TARGET;
		} else if(current.getType() == Tile.getMagicCarpet()){
			//load Magic carpet
			return MAGIC_CARPET;
		} else if(current.getType() == Tile.getAbu()){
			//load Abu
			return ABU;
		} else if(current.getType() == Tile.getIago()){
			//load Iago
			return IAGO;
		} else if(current.getType() == Tile.getRajah()){
			//load Rajah
			return RAJAH;
		} else if(current.getType() == Tile.getGenie()){
			//load Genie
			return GENIE;
		} else if(current.getType() == Tile.getJasmine()){
			//load Jasmine
			return JASMINE;
		} else if(current.getType() == Tile.getPeddler()){
			//load Peddler
			return PEDDLER;
		}
		
		return null;
	}
	
	public BufferedImage getBackground(int row, int col, int layer) {
		Tile current = sokobanModel.getTile(row,col);
		
		if (layer == 0) {
			if (current.getDarkness() == Tile.getSemiDark()) {
				return getTile(row, col);
			} else if (current.getType() == Tile.getCharacter()) {
				return FLOOR;
			} else if (current.getType() == Tile.getTarget()) {
				return FLOOR;
			} else if (current.getRescue()){
				return FLOOR;
			}
		} else if (layer == 1){
			if (current.getType() == Tile.getCharacter()) {
				Movable character = (Movable)current;
				return getTile(character.getCovering());
			} else {
				return getTile(row, col);
			}
		} else if (layer == 2) {
			return FLOOR;
		}
		
		return null;
	}

	//@Override
	public void keyReleased(KeyEvent e) {
		 if(sokobanModel.isPlayMode()){
			//Start timer when one of the arrow keys is pressed
			time.start();
			if (e.getKeyCode() == KeyEvent.VK_UP){
				sokobanModel.movePlayer(sokobanModel.getUp());
				sokobanView.updateSteps();
				sokobanView.repaintBoard();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN){
				sokobanModel.movePlayer(sokobanModel.getDown());
				sokobanView.updateSteps();
				sokobanView.repaintBoard();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				sokobanModel.movePlayer(sokobanModel.getRight());
				sokobanView.updateSteps();
				sokobanView.repaintBoard();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT){
				sokobanModel.movePlayer(sokobanModel.getLeft());
				sokobanView.updateSteps();
				sokobanView.repaintBoard();
			} else if (sokobanModel.isTransition() && e.getKeyCode() == KeyEvent.VK_SPACE){
				System.out.println(sokobanModel.getMessage());
			}
			//check if winGame or lostGame. How to check if player lost? 
			//Can edit the format of the way the game is played through adding a yes/no option to the replay 
			//pop up window, which will then restart the game rather than having the newGame boolean. 
			if(sokobanModel.gameWon()){
				time.stop();
				int n = JOptionPane.showConfirmDialog(
				    sokobanView.window,
				    "Would you like to play again?",
				    "Congrats you won!! :)",
				    JOptionPane.YES_NO_OPTION);
					if(n == 0){
						resetGame();
					} else {
						//Go to a "Home Screen" 
					}
			} else if(sokobanModel.levelCompleted()){
				time.stop();
//				String levelFinished = "You completed level " + sokobanModel.getLevel() + ". Press OK to continue.";
//				JOptionPane.showMessageDialog(
//			    sokobanView.window,
//			    levelFinished,
//			    "Level Completed",
//			    JOptionPane.PLAIN_MESSAGE, null);
				if (!sokobanModel.getRescueStage() && !sokobanModel.isTransition()) {
					sokobanModel.setLevel(-1);
				} else {
					sokobanModel.setLevel(sokobanModel.getLevel() + 0.5);
				}
				time.start();
				sokobanView.repaintBoard();
			}
		} 
	}
	
	public void resetGame(){
		sokobanModel.setNewGame(true);
		sokobanModel.setLevel(1);
		sokobanView.repaintBoard();
		sokobanView.resetTimer();
		sokobanView.resetSteps();
		playWithDarkness();
	}

	//@Override
	public void keyTyped(KeyEvent e) {}
	//@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == time){
			sokobanView.updateTime();  
		}
		
		if (e.getActionCommand() != null){
			if(e.getActionCommand().equals("New Game")){
				resetGame();
			} else if(e.getActionCommand().equals("Change Level")){
				String num = JOptionPane.showInputDialog("Set level to", 1);
				int a = Integer.parseInt(num);
				//Do not restart game but just set level to __ and update board.
				sokobanModel.setLevel(a);
				sokobanView.repaintBoard();
			} else if(e.getActionCommand().equals("How to Play")){
				//System.out.println("How to Play");
				//need to display the page itself after it has been created
				//add the howToPlay
				time.stop();
				loadHowToPlayPage();
				time.start();
			} else if(e.getActionCommand().equals("About")){
				//System.out.println("About");
				//need to display the about page
				time.stop();
				loadAboutPage();
				time.start();
			} else if(e.getActionCommand().equals("Exit")){
				sokobanView.window.dispose();
			} else if(e.getSource() == sokobanView.getStart()){
				startGame();
			} else if(e.getSource() == sokobanView.getExit()){
				sokobanView.window.dispose();
			}
		}
		
	}
	
	private void startGame() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(
	    sokobanView.window,
	    "Welcome to Aladdin's world. Press OK to begin the game. " + '\n'
	    + "Use the arrow keys to move Aladdin.",
	    "Aladdin's World",
	    JOptionPane.PLAIN_MESSAGE, null);

        playWithDarkness();

        sokobanView.giveFocus();  
        sokobanModel.setPlayMode(true);
        
	}

	public void loadAboutPage(){
		try {
			aboutContent = new JEditorPane(new URL("file:images/about.html"));
			aboutPane = new JScrollPane(aboutContent);  
			
			JOptionPane.showMessageDialog(null, aboutPane, "How To Play", JOptionPane.PLAIN_MESSAGE, null);
			
		} catch (MalformedURLException i) {
			// TODO Auto-generated catch block
			i.printStackTrace();
		} catch (IOException i) {
			// TODO Auto-generated catch block
			i.printStackTrace();
		}
	}
	
	public void loadHowToPlayPage(){
		try {
			helpContent = new JEditorPane(new URL("file:images/howToPlay.html"));
			helpPane = new JScrollPane(helpContent);

			helpPane.setMinimumSize(new Dimension(400, 300));
			helpPane.setPreferredSize(new Dimension(400, 300)); 
			helpPane.setMaximumSize(new Dimension(400, 300));   
			
			JOptionPane.showMessageDialog(null, helpPane, "How To Play", JOptionPane.PLAIN_MESSAGE, null);
			
		} catch (MalformedURLException i) {
			// TODO Auto-generated catch block
			i.printStackTrace();
		} catch (IOException i) {
			// TODO Auto-generated catch block
			i.printStackTrace();
		}
	}
	
	public int getNumRows(){
		return sokobanModel.getRow();
	}
	
	public int getNumCols(){
		return sokobanModel.getCol();
	}
	
	private void playWithDarkness(){
		int darkness = JOptionPane.showConfirmDialog(
				sokobanView.window,
			    "Do you want to play with darkness enabled?",
			    "Darkness",
			    JOptionPane.YES_NO_OPTION);
		
		if (darkness == JOptionPane.YES_OPTION) {
			sokobanModel.setDarkness(true);
		} else {
			sokobanModel.setDarkness(false);
		}
	}
}
