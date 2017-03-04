package model;

public class Movable extends Tile{

	Tile covering;
	
	public Movable(int r, int c, int type, Tile covered) {
		super(r, c, type);
		
		covering = covered;
	}
	
	public void setPosition(int r, int c){
		myRow = r;
		myCol = c;
	}
	
	public void setRow(int r) {
		myRow = r;
	}
	public void setCol(int c) {
		myCol = c;
	}
	
	public Tile getCovering(){
		return covering;
	}
	
	public void setCovering(Tile covered){
		covering = covered;
	}

}
