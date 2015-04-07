package model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Piece{
	public static final int GENERAL_OF_THE_ARMY = 16;
	public static final int LIEUTENTANT_GENERAL = 14;
	public static final int COLONEL = 12;
	public static final int CAPTAIN = 10;
	public static final int FIRST_LIEUTENANT = 8;
	public static final int PRIVATE = 3;
	public static final int SPY = 2;
	public static final int FLAG = 1;
	
	private ImageIcon onPlay;
	private ImageIcon offPlay;
	private ArrayList<Piece> kills;
	private boolean isAlive;
	private String name;
	private int type;
	private boolean isSelected;
	private Point loc;
	private Player master;
	private boolean isPlaying;
	
	private Piece killer;
	
	
	//WINONA
	private double probability[][];
	
	public double[][] getProbability() {
		return probability;
	}

	public void setProbability(double[][] probability) {
		this.probability = probability;
	}

	public Piece(String onPath, String name, int type, Point loc, Player master)
	{
		//WINONA
		probability = new double[2][10];
		/*probability[0][0] = 1;
		probability[0][1] = 2;
		probability[0][2] = 3;
		probability[0][3] = 3;
		probability[0][4] = 3;
		probability[0][5] = 8;
		probability[0][6] = 10;
		probability[0][7] = 12;
		probability[0][8] = 14;
		probability[0][9] = 16;*/
		
		probability[0][0] = 16;
		probability[0][1] = 14;
		probability[0][2] = 12;
		probability[0][3] = 10;
		probability[0][4] = 8;
		probability[0][5] = 3;
		probability[0][6] = 3;
		probability[0][7] = 3;
		probability[0][8] = 2;
		probability[0][9] = 1;
		
		probability[1][0] = (double) 0.1;
		probability[1][1] = (double) 0.1;
		probability[1][2] = (double) 0.1;
		probability[1][3] = (double) 0.1;
		probability[1][4] = (double) 0.1;
		probability[1][5] = (double) 0.1;
		probability[1][6] = (double) 0.1;
		probability[1][7] = (double) 0.1;
		probability[1][8] = (double) 0.1;
		probability[1][9] = (double) 0.1;
		
		
		kills=new ArrayList<Piece>();
		isAlive=true;
		isSelected=false;
		isPlaying=false;
		this.loc=loc;
		this.name=name;
		this.type=type;
		this.master=master;
		
		
		ImageIcon ii = new ImageIcon(
				onPath);
		Image img = ii.getImage();
		Image newimg = img.getScaledInstance((int) (ii.getIconWidth() * 2),
				(int) (ii.getIconHeight() * 2), java.awt.Image.SCALE_SMOOTH);
		onPlay=new ImageIcon(newimg);
		
		ii = new ImageIcon(
				"C:\\Users\\Weh\\workspace\\GOTG\\src\\hidden.png");
		img = ii.getImage();
		newimg = img.getScaledInstance((int) (ii.getIconWidth() * 2),
				(int) (ii.getIconHeight() * 2), java.awt.Image.SCALE_SMOOTH);
		offPlay=new ImageIcon(newimg);
	}
	
	public Player getMaster(){
		return master;
	}
	
	public void setDead(Piece killer){
		isAlive=false;
		this.killer=killer;
	}
	
	public Piece getKiller(){
		return killer;
	}

	public boolean isAlive() {
		return isAlive;
	}
	public void setPlay(boolean status){
		isPlaying=status;
	}
	
	public ImageIcon getImg(){
		if(isPlaying) return onPlay;
		return offPlay;
	}
	
	public void addKill(Piece pc){
		kills.add(pc);
	}
	
	public Iterator getKills(){
		return kills.iterator();
	}

	public String getName(){
		return name;
	}
	
	public int getType(){
		return type;
	}
	
	public void toggleSelected(){
		isSelected=!isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public void move(Point point){
		this.loc=point;
	}
	
	public Point getLocation(){
		return loc;
	}
	
	public void setLocation(Point p){
		loc=p;
	}
	
	public String getStats(){
		CalculateStats calc = new CalculateStats();
		System.out.println("Shayane ");
		probability = calc.computeStats(master, killer);
		String st="";
		st+= "General of the Army: "+ probability[1][0]+"%\n";
		st+= "Lieutenant General: "+probability[1][1] +"%\n";
		st+= "Colonel: "+ probability[1][2]+"%\n";
		st+= "Captain: "+probability[1][3]+"%\n";
		st+= "First Lieutenant: "+probability[1][4]+"%\n";
		st+= "Spy: "+probability[1][8]+"%\n";
		st+= "Private: "+probability[1][5] + probability[1][6] + probability[1][7]+"%\n";
		st+= "Flag: "+probability[1][9]+"%\n";
		return st;
	}
	
	public abstract boolean challenge(Piece enemy);
	
	public void setProbabilities(double[] prob) 
	{
		for(int x = 0; x < 10; x++)
			probability[1][x] = prob[x];
	}
	
}
