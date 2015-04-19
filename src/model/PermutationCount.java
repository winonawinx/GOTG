/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package permutationcount;

public class PermutationCount {
    
    public static final int GENERAL_OF_THE_ARMY = 16;
    public static final int LIEUTENTANT_GENERAL = 14;
    public static final int COLONEL = 12;
    public static final int CAPTAIN = 10;
    public static final int FIRST_LIEUTENANT = 8;
    public static final int PRIVATE = 3;
    public static final int SPY = 2;
    public static final int FLAG = 0;
    
    public static final int[] rankMapping = {SPY, GENERAL_OF_THE_ARMY, LIEUTENTANT_GENERAL, COLONEL, CAPTAIN, FIRST_LIEUTENANT, PRIVATE, FLAG}; 

    public boolean[][] possibilities1 = {
                                        {true, true, true, true, false, false, false, false},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true}
                                        };
    
    public boolean[][] possibilities2 = {
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true}
                                        };
    
    public static void main(String[] args) {
        PermutationCount count = new PermutationCount();
        System.out.println(count.calcPermutations(0, 1, 0));
    }
    
    public int getCount(int player, int piece){
        // Assumes player is 1
        // This function is in no way legit
        
        if(piece == 0){
            return 4;
        }
        
        else{
            return 10;
        }
    }
    
    public int calcPermutations(int player, int piece, int rank){
        // For now this is assuming the rank is just 0-9
        
        boolean[][] possibilities;
        int total = 1;
        int removedNonRank = 0;
        
        if(player == 0){
            possibilities = possibilities1;
        }
        
        else{
            possibilities = possibilities2;
        }
        
        for(int i = 0; i < 10; i++){
            // If we are currently at the piece being checked
            if(i == piece){
                total *= 1; // In other words, useless
                System.out.printf(" * 1");
            }
            // If the piece has a possibility to be the current rank
            else if(possibilities[i][rank]){
                total *= getCount(player, i) - 1 - removedNonRank;
                System.out.printf(" ** " + (getCount(player, i) - 1 - removedNonRank));
                removedNonRank++;
            }
            else{
                total *= getCount(player, i) - removedNonRank;
                System.out.printf(" *** " + (getCount(player, i) - removedNonRank));
                removedNonRank++;
            }
            
        }
        
        System.out.println("");
        
        // If the piece is a private
        if(piece == 7){
            return total * 3;
        }
        
        else{
            return total;
        }
    }
}
