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
}
