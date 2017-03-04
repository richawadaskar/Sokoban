package model;

public class Tile extends Model{
	
	private static final int CHARACTER = 0;
	private static final int WALL = 1;
	private static final int FLOOR = 2;
	private static final int BLOCK = 3;
	private static final int TARGET = 4;
	private static final int MAGIC_CARPET = 5;
	private static final int ABU = 6;
	private static final int IAGO = 7;
	private static final int RAJAH = 8;
	private static final int GENIE = 9;
	private static final int JASMINE = 10;
	private static final int PEDDLER = 11;
	
	private static final int LIGHT = 1;
	private static final int SEMI_DARK = 0;
	private static final int DARK = -1;
	
	
	protected boolean isWalkable;
	protected int myRow, myCol;
	protected int myType;
	protected int darkness = LIGHT;
	protected boolean toRescue;
	
	public Tile(int r, int c, int type){
		myRow = r;
		myCol = c;
		myType = type;
		
		if (myType == FLOOR || myType == TARGET) {
			isWalkable = true;
		} else {
			isWalkable = false;
		}
		
		toRescue = isRescued();
	}
	
	private boolean isRescued(){
		return (myType == MAGIC_CARPET || myType == ABU || 
				myType == IAGO || myType == RAJAH || 
				myType == GENIE || myType == JASMINE ||
				myType == PEDDLER);
	}

	public int getRow() {
		return myRow;
	}
	public int getCol() {
		return myCol;
	}
	
	public int getType(){
		return myType;
	}
	
	public boolean getWalkable(){
		return isWalkable;
	}
	
	public int getDarkness(){
		return darkness;
	}
	
	public void setDarkness(int dark) {
		darkness = dark;
	}
	
	public boolean getRescue() {
		return toRescue;
	}
	
	//getters for controller for checking what type of tile
	public static int getCharacter() {
		return CHARACTER;
	}

	public static int getWall() {
		return WALL;
	}

	public static int getFloor() {
		return FLOOR;
	}

	public static int getBlock() {
		return BLOCK;
	}

	public static int getTarget() {
		return TARGET;
	}
	
	public static int getMagicCarpet(){
		return MAGIC_CARPET;
	}
	
	public static int getAbu(){
		return ABU;
	}
	
	public static int getIago(){
		return IAGO;
	}
	
	public static int getRajah(){
		return RAJAH;
	}
	
	public static int getGenie(){
		return GENIE;
	}
	
	public static int getJasmine(){
		return JASMINE;
	}
	
	public static int getPeddler(){
		return PEDDLER;
	}
		
	public static int getSemiDark() {
		return SEMI_DARK;
	}
	
	public static int getDark() {
		return DARK;
	}
	
	public static int getLight() {
		return LIGHT;
	}
}
