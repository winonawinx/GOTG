package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Calculation {

	private double currWeight;
	private double addedWeight;
	private double subtractedWeight = 0;
	private double remainingWeight;
	private double totalAmtSubtracted;
	private double result;
	private double oldProbabilities[][];
	private double currProbabilities[][] = new double[2][10];
	private double otherProbabilities[][] = new double[10][10];
	private double otherAddedWeight;
	private double columnWeightRemaining = 0;
	
	public double[][] computeStats(Player player, Piece pumatay)
	{
		Piece killer = null;
		int killerindex = -1;
		Iterator iterator = player.getPieces();
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		while(iterator.hasNext())
		{
			pieces.add((Piece)iterator.next());
		}
		
		// looking for which piece is the killer
		for(int x = 0; x < pieces.size();x++)
		{
			if(pieces.get(x).equals(pumatay))
			{
				killer = pieces.get(x);
				killerindex = x;
			}
				
		}
		
		oldProbabilities = killer.getProbability();
		//comparePieces(killer);
		
		
		// comparepieces start here
		
		totalAmtSubtracted = 0;
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
				if(kList.get(kList.size()-1).getType() > killerprobability[0][x])
				{
					totalAmtSubtracted += killerprobability[1][x];
					killerprobability[1][x] = 0;
				}
			}
		}
		else
		{
			for(int x = 0; x < 10; x++)
			{
				if(kList.get(kList.size()-1).getType() %2 == 0)
				{
					totalAmtSubtracted += killerprobability[1][x];
					killerprobability[1][x] = 0;
				}
			}
		}
			
		// whatever is left of the weights in that row (piece probability)
		remainingWeight = 1-totalAmtSubtracted;
		
		//actual computation
		//note: kulang yung if else stamenet para sa total subtracted or kung total amount added gagamitin sa computatuion.
		for(int x = 0; x<10; x++)
		{
			if(killerprobability[1][x] != 0)
			{
				currWeight = killerprobability[1][x];
				addedWeight = totalAmtSubtracted*currWeight/remainingWeight;
				result = currWeight + addedWeight;
			}
		}
		
		player.getPiecesArraylist().get(killerindex).setProbabilities(killerprobability[1]);
		
		currProbabilities = player.getPiecesArraylist().get(killerindex).getProbability();
		
		//solving for the probs in the remaining cells 
		
		
		for(int x = 0; x < 10; x++) // columns
		{
			columnWeightRemaining = 1 - currProbabilities[killerindex][x];
			for(int y = 0; y < 10; y++) // rows
			{
				if(y != killerindex)
				{
					otherProbabilities = player.getPiecesArraylist().get(y).getProbability();
					if(oldProbabilities[1][y] < currProbabilities[1][y])
					{
						addedWeight = currProbabilities[1][y] - otherProbabilities[1][y];
					}
					if(oldProbabilities[1][y] > currProbabilities[1][y])
					{
						subtractedWeight = oldProbabilities[1][y] - currProbabilities[1][y];
					}
					otherAddedWeight = addedWeight * otherProbabilities[1][y]/columnWeightRemaining;
					otherProbabilities[1][y] = otherProbabilities[1][y] + otherAddedWeight;
				}
			}
						
		}
		
		return oldProbabilities;
	}
	
	public void comparePieces(Piece killer)
	{
		
	}
	
}
