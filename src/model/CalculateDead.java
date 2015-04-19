package model;

import java.util.ArrayList;
import java.util.Iterator;

public class CalculateDead
{
	//killed variables
	private double killedCurrentWeight = 0;
	private double killedAddedWeight = 0;
	private double killedSubtractedWeight = 0;
	private double killedRemainingWeight = 0;
	private double killedTotalAmtSubtracted = 0;
	private double killedResult = 0;
	private double killedOldProbabilities[][] = new double[2][10];
	private double killedNewProbabilities[][] = new double[2][10];
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
	
	//variables for the killed piece and the killed player
	private Piece killed = null;
	private int killedindex = -1;
	private Iterator iterator = null;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	//weight remaining in the column of the 10x10 to be distributed
	private double columnWeightRemaining = 0;
	
	// returns the 10x10 array
	public double[][] computeStats(Player player, Piece namatay)
	{
		System.out.println("TURN: "+ turn);
		//transforms the player's pieces (iterator) into arraylist
		iterator = player.getPieces();
		while(iterator.hasNext())
		{
			pieces.add((Piece)iterator.next());
		}
		
		//look for the killed from the player's pieces
		for(int x = 0; x < pieces.size();x++)
		{
			if(pieces.get(x).equals(namatay))
			{
				killed = pieces.get(x);
				killedindex = x;
			}
		}

		double[][] kp = killed.getProbability();				
		for(int y = 0; y < 2; y++)
		{
			for(int x = 0; x < 10; x++)
			{
				killedOldProbabilities[y][x] = kp[y][x];
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
//		 recopying the 10x10
		for(int x = 0; x < 10; x++)
		{
//			System.out.printf("%d ", x);
			for(int y = 0; y < 10; y++)
			{
				newProbabilities[x][y] = currProbabilities[x][y];
//				System.out.printf("%.2f ", newProbabilities[x][y] );
			}
//			System.out.println();
		}
		
		
//		Compare killed and killed to know which columns to 0 out
		killedTotalAmtSubtracted = 0;
		Piece killer = killed.getKiller();
		ArrayList<Piece> kList = new ArrayList<Piece>();
//		double[][] killedprobability = killed.getProbability();
		
		
		double[][] killedprobability = new double[2][10];
		double[][] killp = killed.getProbability();
		for(int x = 0; x < 2; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				killedprobability[x][y] = killp[x][y];
			}
		}
	
		
		//kung same YUNG PIECES
		
		if(killed.getType() == killer.getType())
		{
			for(int x = 0; x < 10; x++)
			{
				if(killer.getType() != killedprobability[0][x])
				{
					killedTotalAmtSubtracted += killedprobability[1][x];
					killedprobability[1][x] = 0.0;
				}
			}
		}
		else 
		{
			System.out.println("KILLED: " + killer.getName());
			//sets 0 to pieces it can't be
			if(killer.getType() != 2)
			{
			
				for(int x = 0; x < 10; x++)
				{
					if(killer.getType() <= killedprobability[0][x] && killedprobability[0][x] > 2)
					{
						killedTotalAmtSubtracted += killedprobability[1][x];
						killedprobability[1][x] = 0;
					}
					else if(killedprobability[0][x] == 2 && killer.getType() % 2 == 0)
					{
						killedTotalAmtSubtracted += killedprobability[1][x];
						killedprobability[1][x] = 0;
					}
					else if (killedprobability[0][x] == 1)
					{
						System.out.println("FLAG SET ZERO KA PLEASE: !");
						killedTotalAmtSubtracted += killedprobability[1][x];
						killedprobability[1][x] = 0;
					}
				}
			}
			else if(killer.getType() == 2)
			{
	
				for(int x = 0; x < 10; x++)
				{
					if(killedprobability[0][x] == 3)
					{
						killedTotalAmtSubtracted += killedprobability[1][x];
						killedprobability[1][x] = 0;
					}
				}
			}	
			
		}

		//loop to place killed's new probs in 10x10
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(x == killedindex)
				{
					currProbabilities[x][y] = killedprobability[1][y];
				}
			}
		}
		
		
		int isAllZero = 0;
		int isOddx = -1;
		int isOddy = -1;
		int isCalculate = 0;		
		
		killedRemainingWeight = 1.0 - killedTotalAmtSubtracted;
		
		
		//Actual computation for the killed's new probabilities
		for(int x = 0; x<10; x++)
		{
			if(killedprobability[1][x] != 0)
			{
				killedCurrentWeight = killedprobability[1][x];
				killedAddedWeight = killedTotalAmtSubtracted*killedCurrentWeight/killedRemainingWeight;
				killedResult = killedCurrentWeight + killedAddedWeight;
				killedprobability[1][x] = killedResult;
			}
		}
		//change the probabilities of the killed in the player's pieces list
		player.getPiecesArraylist().get(killedindex).setProbabilities(killedprobability[1]);
	
		//getting the new probabilities of the killed
		for(int x = 0; x < 2; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				killedNewProbabilities[x][y] = killedprobability[x][y];
			}
		}
		
		//Placing those current values in the 10x10 array
		for(int x = 0; x < 10; x++)
		{
			currProbabilities[killedindex][x] = killedprobability[1][x];
		}
		
		// Distribute the probabilities per column

		for(int y = 0; y < 10; y++) // columns
		{
			for(int x = 0; x < 10; x++) //rows
			{
				if(x != killedindex) //if it isn't in the killed's row
				{
					columnWeightRemaining = 1.0 - newProbabilities[killedindex][y];
//					System.out.println("Old killed prob["+y+"] : "+ killedOldProbabilities[1][y]+" NEW killed PROB ["+ y + "]"+ killedNewProbabilities[1][y]);
					if(killedOldProbabilities[1][y] < killedNewProbabilities[1][y]) // addition in the probability of the killed occured
					{
						killedAddedWeight = killedOldProbabilities[1][y] - currProbabilities[killedindex][y]; //will subtract in the others
					}
					else if(killedOldProbabilities[1][y] > killedNewProbabilities[1][y]) // subtraction in the probability of the killed occured
					{
						killedAddedWeight = currProbabilities[killedindex][y] - killedOldProbabilities[1][y]*-1; // will add in the others
					}
					otherAddedWeight = killedAddedWeight * currProbabilities[x][y]/columnWeightRemaining;
					currProbabilities[x][y] = currProbabilities[x][y] + otherAddedWeight;
				}
			}
		}
		
		// updating each of the pieces' probabilities
		for(int x = 0; x < 10; x++)
		{
			player.getPiecesArraylist().get(x).setProbabilities(currProbabilities[x]);
		}
	
	
		System.out.println("After killed computation");
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
		System.out.println("killed NEW PROBABILITIES");
		
		for(int x = 1; x<2; x++)
			for(int i = 0; i<10; i++)
			{
				System.out.printf("%.2f ",killedNewProbabilities[x][i]);
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
			
			if(currProbabilities[x][y] == 0 && killedindex != x)
			{
				isAllZero++;
			}
			else if (currProbabilities[x][y] != 0 && killedindex != x)
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
			if(currProbabilities[y][x] == 1 && y != killedindex)
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
			newProbabilities[x][y] = currProbabilities[x][y];
			System.out.printf("%.2f ", newProbabilities[x][y]);
		}
		System.out.println();
	} 
 
	// updating each of the pieces' probabilities
	for(int x = 0; x < 10; x++)
	{
		player.getPiecesArraylist().get(x).setProbabilities(currProbabilities[x]);
	}
/*-----------------------------------------------------------------------------------------*/
 
	return killedNewProbabilities;
		
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
						if(x != indexChange && x != killedindex) //if it isn't in the killed's row
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
								if(oldProb[y] < newProb[y]) // addition in the probability of the killed occured
								{
									addedWeight = newProb[y] - oldProb[y]; //will subtract in the others
									System.out.println("Added weight (Added) WeIGHT: "+ addedWeight);
								}
								else if(oldProb[y] > newProb[y]) // subtraction in the probability of the killed occured
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

