package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import controller.Controller;


public class MyDrawingPanel extends JPanel{
	//Attributes
	Controller puzzleController;
	int tileWidth = 50;
	int tileHeight = 50;
	int drawRow;
	int drawCol;
		
	public MyDrawingPanel(Controller controller) {
		this.puzzleController = controller;
		drawRow = puzzleController.getNumRows();
		drawCol = puzzleController.getNumCols();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); 
		
		drawImages(g);
	}

	private void drawImages(Graphics g) {
		// TODO Auto-generated method stub
		
		for (int row = 0; row < drawRow; row++) {
			for (int col = 0; col < drawCol; col++) {
				
				if (puzzleController.isTransparent(row, col)) {
					if (puzzleController.singleLayer(row, col)) {
						g.drawImage(puzzleController.getBackground(row, col, 0), col*tileWidth, 
								row*tileHeight, tileWidth, tileHeight, null);
					} else {
						g.drawImage(puzzleController.getBackground(row, col, 2), col*tileWidth, 
								row*tileHeight, tileWidth, tileHeight, null);
						g.drawImage(puzzleController.getBackground(row, col, 1), col*tileWidth, 
								row*tileHeight, tileWidth, tileHeight, null);
					}
				}
				
				g.drawImage(puzzleController.getImage(row, col), col*tileWidth, 
						row*tileHeight, tileWidth, tileHeight, null);
			}
		}
	}
}