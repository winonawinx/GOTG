package control;

import java.util.ArrayList;

import model.Black;
import model.Player;
import model.White;
import view.Gui;

public class Driver {
	public static void main(String[] args){
//		new Gui();
		System.out.println("Starting game ");
		Player players[]={new Black(), new White()};
		new Control();
	}
	
	
	/*public static void main(String[] args)
	{
		ArrayList<ArrayList<Float>> p = new ArrayList<ArrayList<Float>>();
		ArrayList p2 = new ArrayList<Float>();
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		p2.add(0.1);
		
		p.add(p2);
		p.add(p2);
		p.add(p2);
		p.add(p2);
		p.add(p2);
		p.add(p2);
		p.add(p2);
		p.add(p2);
		p.add(p2);
		p.add(p2);
		
		
		
		System.out.println("In p + " + p.get(0).get(1));
	}*/
}
