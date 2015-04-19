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
	private static int turn = 1;
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
		System.out.println("TURN: "+ turn);
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

		double[][] kp = killer.getProbability();				
		for(int y = 0; y < 2; y++)
		{
			for(int x = 0; x < 10; x++)
			{
				killerOldProbabilities[y][x] = kp[y][x];
			}
		}

		double[][] temporary = new double[2][10];
		//placing the pieces' probabilities in the 10x10 array
		
		for(int x = 0; x < 10; x++) // row
		{
			double[][] temp = player.getPiecesArraylist().get(x).getProbability();
			for(int z = 0 ; z < 2; z++)
			{
				for(int y = 0; y < 10; y++)
				{
					temporary[z][y] = temp[z][y];
				}
			}
		
			for(int y = 0; y < 10; y++)
			{
				currProbabilities[x][y] = temporary[1][y];
			}
		}
		
		System.out.println("BEFORE CALCULATION RECOPYING the 10X10 array probabilities: ");
		// recopying the 10x10
		for(int x = 0; x < 10; x++)
		{
			System.out.printf("%d ", x);
			for(int y = 0; y < 10; y++)
			{
				newProbabilities[x][y] = currProbabilities[x][y];
				System.out.printf("%.2f ", newProbabilities[x][y] );
			}
			System.out.println();
		}
		
		
		//Compare killed and killer to know which columns to 0 out
		killerTotalAmtSubtracted = 0;
		iterator = killer.getKills();
		ArrayList<Piece> kList = new ArrayList<Piece>();
		//double[][] killerprobability = killer.getProbability();
		
		
		double[][] killerprobability = new double[2][10];
		double[][] killp = killer.getProbability();
		for(int x = 0; x < 2; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				killerprobability[x][y] = killp[x][y];
			}
		}
		
		
		while(iterator.hasNext())
		{
			kList.add((Piece)iterator.next());
		}
		
		//kung same YUNG PIECES
		if(killer.getType() == kList.get(kList.size()-1).getType())
		{
			for(int x = 0; x < 10; x++)
			{
				if(kList.get(kList.size()-1).getType() != killerprobability[0][x])
				{
					killerTotalAmtSubtracted += killerprobability[1][x];
					killerprobability[1][x] = 0.0;
				}
			}
		}
		else
		{
			System.out.println("KILLED: " + kList.get(kList.size()-1).getName());
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
					else if(killerprobability[0][x] == 2 && kList.get(kList.size()-1).getType() % 2 != 0)
					{
						killerTotalAmtSubtracted += killerprobability[1][x];
						killerprobability[1][x] = 0;
					}
				}
			}
			else if(kList.get(kList.size()-1).getType() == 2)
			{

				for(int x = 0; x < 10; x++)
				{
					if(killerprobability[0][x] != 3)
					{
						killerTotalAmtSubtracted += killerprobability[1][x];
						killerprobability[1][x] = 0;
					}
				}
			}
			
		}
		

		//loop to place killer's new probs in 10x10
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(x == killerindex)
				{
					currProbabilities[x][y] = killerprobability[1][y];
				}
			}
		}
		
		
		int isAllZero = 0;
		int isOddx = -1;
		int isOddy = -1;
		int isCalculate = 0;		
		
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
		for(int x = 0; x < 2; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				killerNewProbabilities[x][y] = killerprobability[x][y];
			}
		}
		
		//Placing those current values in the 10x10 array
		for(int x = 0; x < 10; x++)
		{
			currProbabilities[killerindex][x] = killerprobability[1][x];
		}
		
		// Distribute the probabilities per column

		for(int y = 0; y < 10; y++) // columns
		{
			for(int x = 0; x < 10; x++) //rows
			{
				if(x != killerindex) //if it isn't in the killer's row
				{
					columnWeightRemaining = 1.0 - newProbabilities[killerindex][y];
					System.out.println("Old killer prob["+y+"] : "+ killerOldProbabilities[1][y]+" NEW killer PROB ["+ y + "]"+ killerNewProbabilities[1][y]);
					if(killerOldProbabilities[1][y] < killerNewProbabilities[1][y]) // addition in the probability of the killer occured
					{
						killerAddedWeight = killerOldProbabilities[1][y] - currProbabilities[killerindex][y]; //will subtract in the others
					}
					else if(killerOldProbabilities[1][y] > killerNewProbabilities[1][y]) // subtraction in the probability of the killer occured
					{
						killerAddedWeight = currProbabilities[killerindex][y] - killerOldProbabilities[1][y]*-1; // will add in the others
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
	
	
		System.out.println("After killer computation");
		System.out.println("Distribute probabilities per column: ");
		for(int x = 0; x < 10; x++)
		{
			System.out.printf("%d ", x);
			for(int y = 0; y < 10; y++)
			{
				newProbabilities[x][y] = currProbabilities[x][y];
				System.out.printf("%.2f ", newProbabilities[x][y] );
			}
			System.out.println();
		}
		

		

/*******************************************************************************************************/
		System.out.println("KILLER NEW PROBABILITIES");
		
		for(int x = 1; x<2; x++)
			for(int i = 0; i<10; i++)
			{
				System.out.printf("%.2f ",killerNewProbabilities[x][i]);
			}
System.out.println();
System.out.println("*>*>*>*>**>*>*>*>*>*>*>*>*>*>*>*>*>*>**>*>>*>**>*>*>>*>*>*>*>*>*>*>*>*>*>*>>*>*>*>*>*>*>*>**>*>*>*>**>");
 turn++;
 
 
/*-----------------------------------------------------------------------------------------*/

 
 /*************************************************************************************************************/
	System.out.println("AFTER SETTING ZEROES TO PIECES IT CAN't BE");
	System.out.println("CHECK ZERORESS PARA AUTOMATIC 1");
	int rowIndex = -1;
	double rowOldProb[] = new double[10];
	double rowNewProb[] = new double[10];
	double totalAmtSubtracted = 0;
	for(int x = 0; x<10; x++)
	{
		System.out.printf("%d ", x);
		for(int y = 0; y<10; y++)
		{
			
			if(currProbabilities[x][y] == 0 && killerindex != x)
			{
				isAllZero++;
			}
			else if (currProbabilities[x][y] != 0 && killerindex != x)
			{
				totalAmtSubtracted += currProbabilities[x][y];
			}
			rowNewProb[y] = currProbabilities[x][y];
			rowOldProb[y] = currProbabilities[x][y];
			System.out.printf("%.2f ", currProbabilities[x][y]);
		}
		
		if(isAllZero == 9)
		{
			computeRowStat(rowNewProb, totalAmtSubtracted);
			for(int i = 0; i<10; i++)
				currProbabilities[x][i] = rowNewProb[i];
			calculate(player, rowNewProb, rowOldProb, x);
		}
			isAllZero = 0;
		
			
			//I think we have to recalculate here. 
			
		System.out.println();
	}

	 /*************************************************************************************************************/

 
 
 /*******************************************************************************************************/
 
	System.out.println("ENDING: ");
	// recopying the 10x10
	for(int x = 0; x < 10; x++)
	{
		System.out.printf("%d ", x);
		for(int y = 0; y < 10; y++)
		{
			newProbabilities[x][y] = currProbabilities[x][y];
			System.out.printf("%.2f ", newProbabilities[x][y]);
		}
		System.out.println();
	}

 
	for(int x = 0; x < 10; x++)
	{   
		for(int y = 0; y < 10; y++)
		{
			if(currProbabilities[y][x] == 1 && y != killerindex)
			{
				for(int z = 0; z < 10; z++)
				{
					if(z != x)
					{
						currProbabilities[y][z] = 0;
					}
				}
			}
		}
	}
 
	// recopying the 10x10
	for(int x = 0; x < 10; x++)
	{
		System.out.printf("%d ", x);
		for(int y = 0; y < 10; y++)
		{
			if(Double.isNaN(currProbabilities[x][y]) || currProbabilities[x][y] < 0)
				newProbabilities[x][y] = 0;
			else{
				newProbabilities[x][y] = currProbabilities[x][y];}
			System.out.printf("%.2f ", newProbabilities[x][y]);
		}
		System.out.println();
	} 
 
	// updating each of the pieces' probabilities
	for(int x = 0; x < 10; x++)
	{
		player.getPiecesArraylist().get(x).setProbabilities(newProbabilities[x]);
	}
/*-----------------------------------------------------------------------------------------*/
 
	return killerNewProbabilities;
		
	}
	
	
	public void calculate(Player player, double newProb[], double oldProb[], int indexChange)
	{
		/*******************************************************************************************************/		
		// Distribute the probabilities per column
				double remWeight;
				double addedWeight = 0;
				double tempAddedWeight;
				System.out.println("\n Distribute probabilities per column VERSION 2: ");
				
				
				for(int y = 0; y < 10; y++) // columns
				{
					for(int x = 0; x < 10; x++) //rows
					{
						if(x != indexChange && x != killerindex) //if it isn't in the killer's row
						{
							remWeight = 1.0 - newProb[y];
							System.out.println("NEW PROB["+y+"]" + " "+ newProb[y]);
							System.out.println("REM WeIGHT: "+ remWeight);
							System.out.println("Curr Probabilities["+indexChange+"]["+y+"]" + " "+ currProbabilities[indexChange][y]);
							System.out.println("[X][Y]Curr Probabilities["+x+"]["+y+"]" + " "+ currProbabilities[x][y]);
							System.out.println("Old prob["+y+"] : "+ oldProb[y]+"NEW PROB ["+ y + "]"+ newProb[y]);
							
							if(newProb[y] != 0)
							{
								currProbabilities[x][y] = 0;
							}
							else
							{
								if(oldProb[y] < newProb[y]) // addition in the probability of the killer occured
								{
									addedWeight = newProb[y] - oldProb[y]; //will subtract in the others
									System.out.println("Added weight (Added) WeIGHT: "+ addedWeight);
								}
								else if(oldProb[y] > newProb[y]) // subtraction in the probability of the killer occured
								{
									addedWeight = oldProb[y]-newProb[y]*-1; // will add in the others
									System.out.println("Added weight (Subtracted) WeIGHT: "+ addedWeight);
								}
								
								tempAddedWeight = addedWeight * currProbabilities[x][y]/remWeight;
								currProbabilities[x][y] = currProbabilities[x][y] + tempAddedWeight;
								System.out.println("CurrProability: "+ currProbabilities[x][y]);
							}
						}
					}
						
					System.out.println("::::::::::::::::::::::::::::::::::::::::::");
				}
				
				// updating each of the pieces' probabilities
				for(int x = 0; x < 10; x++)
				{
					player.getPiecesArraylist().get(x).setProbabilities(currProbabilities[x]);
				}
			
				System.out.println("))))))))))))))))))))))))))))))))))CHECK IF SAME NEW PROBABILIY((((((((((((((((((((((((((((((((((((((((((((((");
				for(int x = 0; x < 10; x++)
				{
					System.out.printf("%d ", x);
					for(int y = 0; y < 10; y++)
					{
						newProbabilities[x][y] = currProbabilities[x][y];
						System.out.printf("%.2f ", newProbabilities[x][y] );
					}
					System.out.println();
				}
	}
	
	public void computeRowStat(double prob[], double totalAmtSubtracted)
	{
		double currWeight;
		double addedWeight;
		double remWeight;
		double result;
		
		System.out.println("\n COMPUTE ROW STAT");
		for(int x = 0; x<10; x++)
		{
			if(prob[x] != 0)
			{
				currWeight = prob[x];
				remWeight = 1 - totalAmtSubtracted;
				addedWeight = totalAmtSubtracted*currWeight/remWeight;
				result = currWeight + addedWeight;
				prob[x] = result;
			}
			
			System.out.printf("%.2f ", prob[x]);
		}
	
	}
	
}
