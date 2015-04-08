package model;

import java.util.ArrayList;
import java.util.Iterator;

public class CalculateStats
{
	//killer variables
	private double killerCurrentWeight = 0;
	private double killerAddedWeight = 0;
	private double killerSubtractedWeight = 0;
	private double killerRemainingWeight = 0;
	private double killerTotalAmtSubtracted = 0;
	private double killerResult = 0;
	private double killerOldProbabilities[][] = new double[2][10];
	private double killerNewProbabilities[][] = new double[2][10];
	
	//other variables
	private double otherCurrentWeight = 0;
	private double otherAddedWeight = 0;
	private double otherrSubtractedWeight = 0;
	private double otherRemainingWeight = 0;
	private double otherTotalAmtSubtracted = 0;
	private double otherResult = 0;
	private double otherOldProbabilities[][] = new double[2][10];
	private double otherNewProbabilities[][] = new double[2][10];
	
	//10x10 array for the total probabilities of all of the player's stats
	private double currProbabilities[][] = new double[10][10];
	private double newProbabilities[][] = new double[10][10];
	
	//variables for the killer piece and the killer player
	private Piece killer = null;
	private int killerindex = -1;
	private Iterator iterator = null;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	//weight remaining in the column of the 10x10 to be distributed
	private double columnWeightRemaining = 0;
	
	// returns the 10x10 array
	public double[][] computeStats(Player player, Piece pumatay)
	{
		// reset stats
		
		//killer variables
		killerCurrentWeight = 0;
		killerAddedWeight = 0;
		killerSubtractedWeight = 0;
		killerRemainingWeight = 0;
		killerTotalAmtSubtracted = 0;
		killerResult = 0;
		killerOldProbabilities = new double[2][10];
		killerNewProbabilities = new double[2][10];
		
		//other variables
		otherCurrentWeight = 0;
		otherAddedWeight = 0;
		otherrSubtractedWeight = 0;
		otherRemainingWeight = 0;
		otherTotalAmtSubtracted = 0;
		otherResult = 0;
		otherOldProbabilities = new double[2][10];
		otherNewProbabilities = new double[2][10];
		
		//10x10 array for the total probabilities of all of the player's stats
		currProbabilities = new double[10][10];
		newProbabilities = new double[10][10];
		
		//variables for the killer piece and the killer player
		killer = null;
		killerindex = -1;
		iterator = null;
		pieces = new ArrayList<Piece>();
		
		//weight remaining in the column of the 10x10 to be distributed
		columnWeightRemaining = 0;
		
		
		
		
		//transforms the player's pieces (iterator) into arraylist
		iterator = player.getPieces();
		while(iterator.hasNext())
		{
			pieces.add((Piece)iterator.next());
		}
		
		//look for the killer from the player's pieces
		for(int x = 0; x < pieces.size();x++)
		{
			if(pieces.get(x).equals(pumatay))
			{
				killer = pieces.get(x);
				killerindex = x;
			}
		}
		//killer = pumatay;
		killerOldProbabilities = killer.getProbability();
		
		double[][] temporary = new double[2][10];
		//placing the pieces' probabilities in the 10x10 array
		for(int x = 0; x < 10; x++) // row
		{
			temporary = player.getPiecesArraylist().get(x).getProbability();
			currProbabilities[x] = temporary[1];
		}
		
		System.out.println("10x10 is:");
		System.out.println("" + temporary[0][0] + "  "  + temporary[0][1] + "  " + temporary[0][2] + "  " + temporary[0][3] + "  "
				 + temporary[0][4] + "  " + temporary[0][5] + "  " + temporary[0][6] + "  " + temporary[0][7] + "  " + temporary[0][8] + "  " + temporary[0][9] + "  ");
		
		for(int x = 0; x < 10; x++)
		{
			System.out.println("" + currProbabilities[x][0] + "  "  + currProbabilities[x][1] + "  " + currProbabilities[x][2] + "  " + currProbabilities[x][3] + "  "
					 + currProbabilities[x][4] + "  " + currProbabilities[x][5] + "  " + currProbabilities[x][6] + "  " + currProbabilities[x][7] + "  " + currProbabilities[x][8] + "  " + currProbabilities[x][9] + "  ");
		}
		
		// recopying the 10x10
		newProbabilities = currProbabilities;
		
		//Compare killed and killer to know which columns to 0 out
		killerTotalAmtSubtracted = 0;
		iterator = killer.getKills();
		ArrayList<Piece> kList = new ArrayList<Piece>();
		double[][] killerprobability = killer.getProbability();
		
		while(iterator.hasNext())
		{
			kList.add((Piece)iterator.next());
		}
		//sets 0 to pieces it can't be
		if(kList.get(kList.size()-1).getType() != 2)
		{
			for(int x = 0; x < 10; x++)
			{
				if(kList.get(kList.size()-1).getType() >= killerprobability[0][x] && killerprobability[0][x] != 2)
				{
					killerTotalAmtSubtracted += killerprobability[1][x];
					killerprobability[1][x] = 0;
				}
				else if (killerprobability[0][x] == 2 && kList.get(kList.size()-1).getType() % 2 != 0)
				{
					killerTotalAmtSubtracted += killerprobability[1][x];
					killerprobability[1][x] = 0;
				}
			}
		}
		else
		{
			for(int x = 0; x < 10; x++)
			{
				if(kList.get(kList.size()-1).getType() % 2 != 0)
				{
					killerTotalAmtSubtracted += killerprobability[1][x];
					killerprobability[1][x] = 0;
				}
			}
		}
		
		killerRemainingWeight = 1.0 - killerTotalAmtSubtracted;
		
		
		//Actual computation for the killer's new probabilities
		for(int x = 0; x<10; x++)
		{
			if(killerprobability[1][x] != 0)
			{
				killerCurrentWeight = killerprobability[1][x];
				killerAddedWeight = killerTotalAmtSubtracted*killerCurrentWeight/killerRemainingWeight;
				killerResult = killerCurrentWeight + killerAddedWeight;
				killerprobability[1][x] = killerResult;
			}
		}
		//change the probabilities of the killer in the player's pieces list
		player.getPiecesArraylist().get(killerindex).setProbabilities(killerprobability[1]);
	
		//getting the new probabilities of the killer
		killerNewProbabilities = killerprobability;
		
		//Placing those current values in the 10x10 array
		currProbabilities[killerindex] = killerprobability[1];

		// Distribute the probabilities per column

		System.out.println("10x10 is:");
		System.out.println("" + killerprobability[0][0] + "  "  + killerprobability[0][1] + "  " + killerprobability[0][2] + "  " + killerprobability[0][3] + "  "
				 + killerprobability[0][4] + "  " + killerprobability[0][5] + "  " + killerprobability[0][6] + "  " + killerprobability[0][7] + "  " + killerprobability[0][8] + "  " + killerprobability[0][9] + "  ");
		
		for(int x = 0; x < 10; x++)
		{
			System.out.println("" + currProbabilities[x][0] + "  "  + currProbabilities[x][1] + "  " + currProbabilities[x][2] + "  " + currProbabilities[x][3] + "  "
					 + currProbabilities[x][4] + "  " + currProbabilities[x][5] + "  " + currProbabilities[x][6] + "  " + currProbabilities[x][7] + "  " + currProbabilities[x][8] + "  " + currProbabilities[x][9] + "  ");
		}

		for(int y = 0; y < 10; y++) // columns
		{

			for(int x = 0; x < 10; x++) //rows
			{
				if(x != killerindex) //if it isn't in the killer's row
				{
					columnWeightRemaining = 1.0 - currProbabilities[killerindex][y];
					System.out.println("cr = " + columnWeightRemaining);
					if(killerOldProbabilities[1][y] < killerNewProbabilities[1][y]) // addition in the probability of the killer occured
					{
						killerAddedWeight = killerNewProbabilities[1][y] - currProbabilities[killerindex][y] * -1; //will subtract in the others
					}
					else if(killerOldProbabilities[1][y] > killerNewProbabilities[1][y]) // subtraction in the probability of the killer occured
					{
						killerAddedWeight = currProbabilities[killerindex][y] - killerNewProbabilities[1][y]; // will add in the others
					}
					otherAddedWeight = killerAddedWeight * currProbabilities[x][y]/columnWeightRemaining;
					currProbabilities[x][y] = currProbabilities[x][y] + otherAddedWeight;
				}
			}
		
		}
		
		
		/*for(int y = 0; y < 10; y++) // columns
		{

			for(int x = 0; x < 10; x++) //rows
			{
				if(x != killerindex) //if it isn't in the killer's row
				{
					columnWeightRemaining = 1.0 - currProbabilities[killerindex][y];
					System.out.println("cr = " + columnWeightRemaining);
					if(killerOldProbabilities[1][y] < killerNewProbabilities[1][y]) // addition in the probability of the killer occured
					{
						killerAddedWeight = killerNewProbabilities[1][y] - currProbabilities[killerindex][y] * -1; //will subtract in the others
					}
					else if(killerOldProbabilities[1][y] > killerNewProbabilities[1][y]) // subtraction in the probability of the killer occured
					{
						killerAddedWeight = currProbabilities[killerindex][y] - killerNewProbabilities[1][y]; // will add in the others
					}
					otherAddedWeight = killerAddedWeight * currProbabilities[x][y]/columnWeightRemaining;
					currProbabilities[x][y] = currProbabilities[x][y] + otherAddedWeight;
				}
			}
		
		}*/
		
		
		// updating each of the pieces' probabilities
		for(int x = 0; x < 10; x++)
		{
			player.getPiecesArraylist().get(x).setProbabilities(currProbabilities[x]);
		}
	
		return killerNewProbabilities;
		
	}
	
	
}
