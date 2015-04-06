package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Calculation {

	private double currWeight;
	private double addedWeight;
	private double remainingWeight;
	private double totalAmtSubtracted;
	private double result;
	private double newProbabilities[][];
	
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
		
		for(int x = 0; x < pieces.size();x++)
		{
			if(pieces.get(x).equals(pumatay))
			{
				killer = pieces.get(x);
				killerindex = x;
			}
				
		}
		
		newProbabilities = killer.getProbability();
		comparePieces(killer);
		
		//newProbabilities = player;
		
		
		
		return newProbabilities;
	}
	
	/*public double[][] computeStats(Piece player)
	{
		newProbabilities = player.getProbability();
		
		
		
		return null;
	}*/
	
	
	public void comparePieces(Piece killer)
	{
		totalAmtSubtracted = 0;
		Iterator iterator = killer.getKills();
		ArrayList<Piece> kList = new ArrayList<Piece>();
		double[][] killerprobability = killer.getProbability();
		while(iterator.hasNext())
		{
			kList.add((Piece)iterator.next());
		}
		
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
			
		addedWeight = 1-totalAmtSubtracted;
		
		//actual computation
		//note: kulang yung if else stamenet para sa total subtracted or kung total amount added gagamitin sa computatuion.
		for(int x = 0; x<10; x++)
		{
			if(killerprobability[1][x] != 0)
			{
				currWeight = killerprobability[1][x];
				
				
				
				
			}
		}
	}
	
}
