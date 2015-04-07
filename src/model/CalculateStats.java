package model;

import java.util.ArrayList;
import java.util.Iterator;

public class CalculateStats
{
	private double killerCurrentWeight = 0;
	private double killerAddedWeight = 0;
	private double killerSubtractedWeight = 0;
	private double killerRemainingWeight = 0;
	private double killerTotalAmtSubtracted = 0;
	private double killerResult = 0;
	private double killerOldProbabilities[][] = new double[2][10];
	private double killerNewProbabilities[][] = new double[2][10];
	private double otherCurrentWeight = 0;
	private double otherAddedWeight = 0;
	private double otherrSubtractedWeight = 0;
	private double otherRemainingWeight = 0;
	private double otherTotalAmtSubtracted = 0;
	private double otherResult = 0;
	private double otherOldProbabilities[][] = new double[2][10];
	private double otherNewProbabilities[][] = new double[2][10];
	
	private double currProbabilities[][] = new double[10][10];
	private double newProbabilities[][] = new double[10][10];
	
	private Piece killer = null;
	private int killerindex = -1;
	private Iterator iterator = null;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	private double columnWeightRemaining = 0;
	
	// returns the 10x10 array
	public double[][] computeStats(Player player, Piece pumatay)
	{
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
		System.out.println("Killer is " + pumatay.getName());
		killerOldProbabilities = killer.getProbability();
		
		//placing the pieces' probabilities in the 10x10 array
		for(int x = 0; x < 10; x++) // row
		{
			double[][] temporary = player.getPiecesArraylist().get(x).getProbability();
			currProbabilities[x] = temporary[1];
		}
		newProbabilities = currProbabilities;
		
		//Compare killed and killer to know which columns to 0 out
		killerTotalAmtSubtracted = 0;
		iterator = killer.getKills();
		ArrayList<Piece> kList = new ArrayList<Piece>();
		double[][] killerprobability = killer.getProbability();
		
		System.out.println("killer probs table");
		for(int x = 0; x < 10; x++)
		{
			System.out.println("Weight at " + x +"is " + killerprobability[0][x]);
		}
		
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
		
		killerRemainingWeight = 1 - killerTotalAmtSubtracted;
		
		
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

		for(int y = 0; y < 10; y++) // columns
		{
			columnWeightRemaining = 1 - currProbabilities[killerindex][y];
			for(int x = 0; x < 10; x++) //rows
			{
				if(x != killerindex) //if it isn't in the killer's row
				{
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
		
		
		// updating each of the pieces' probabilities
		for(int x = 0; x < 10; x++)
		{
			player.getPiecesArraylist().get(x).setProbabilities(currProbabilities[x]);
		}
	
		return killerNewProbabilities;
		
	}
	
	
}
