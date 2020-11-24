import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int actionCount = in.nextInt(); // the number of spells and recipes in play
            int[][] cast_result = new int[actionCount][5];
            for(int j = 0; j < actionCount; j++){
                cast_result[j][0] = -10000;
                cast_result[j][1] = -10000;
                cast_result[j][2] = -10000;
                cast_result[j][3] = -10000;
                cast_result[j][4] = -10000;
            }
            int best = -1;
            String best_action_type = "";
            int best_price = 0;
            int[] best_ing = new int[4];

            for (int i = 0; i < actionCount; i++) {
                int actionId = in.nextInt(); // the unique ID of this spell or recipe
                String actionType = in.next(); // in the first league: BREW; later: CAST, OPPONENT_CAST, LEARN, BREW
                int delta0 = in.nextInt(); // tier-0 ingredient change
                int delta1 = in.nextInt(); // tier-1 ingredient change
                int delta2 = in.nextInt(); // tier-2 ingredient change
                int delta3 = in.nextInt(); // tier-3 ingredient change
                int price = in.nextInt(); // the price in rupees if this is a potion
                int tomeIndex = in.nextInt(); // in the first two leagues: always 0; later: the index in the tome if this is a tome spell, equal to the read-ahead tax; For brews, this is the value of the current urgency bonus
                int taxCount = in.nextInt(); // in the first two leagues: always 0; later: the amount of taxed tier-0 ingredients you gain from learning this spell; For brews, this is how many times you can still gain an urgency bonus
                boolean castable = in.nextInt() != 0; // in the first league: always 0; later: 1 if this is a castable player spell
                boolean repeatable = in.nextInt() != 0; // for the first two leagues: always 0; later: 1 if this is a repeatable player spell

                if(actionType.compareTo("CAST") == 0 && castable){
                    cast_result[i][0] = delta0;
                    cast_result[i][1] = delta1;
                    cast_result[i][2] = delta2;
                    cast_result[i][3] = delta3;
                    cast_result[i][4] = actionId;
                }

                if(price > best_price){
                    best = actionId;
                    best_action_type = actionType;
                    best_ing[0] = delta0;
                    best_ing[1] = delta1;
                    best_ing[2] = delta2;
                    best_ing[3] = delta3;
                }
            }
            for (int i = 0; i < 2; i++) {
                int inv0 = in.nextInt(); // tier-0 ingredients in inventory
                int inv1 = in.nextInt();
                int inv2 = in.nextInt();
                int inv3 = in.nextInt();
                int score = in.nextInt(); // amount of rupees

                if(i == 0){
                    if(inv0 < Math.abs(best_ing[0]) || inv1 <  Math.abs(best_ing[1]) || inv2 <  Math.abs(best_ing[2]) || inv3 <  Math.abs(best_ing[3])){
                        best_action_type = "REST";
                        // int random_number = ((int)Math.random())%4;
                        // if(inv3 >)
                        for(int j = actionCount-1; j >= 0; j--){
                            //System.err.println(j + " " + cast_result[j][0]);
                            if(cast_result[j][0] != -10000 && inv0+inv1+inv2+inv3+cast_result[j][0]+cast_result[j][1]+cast_result[j][2]+cast_result[j][3] < 11){
                                if(cast_result[j][0] >= 0 && cast_result[j][1] >= 0 && cast_result[j][2] >= 0 && cast_result[j][3] >= 0){
                                    best_action_type = "CAST";
                                    best = cast_result[j][4];
                                    break;
                                }
                                if(cast_result[j][0] < 0 && inv0 >= Math.abs(cast_result[j][0]) ){
                                    best_action_type = "CAST";
                                    best = cast_result[j][4];
                                    break;
                                }

                                if(cast_result[j][1] < 0 && inv1 >= Math.abs(cast_result[j][1]) && inv2 < 2  ){
                                    best_action_type = "CAST";
                                    best = cast_result[j][4];
                                    break;
                                }

                                if(cast_result[j][2] < 0 && inv2 >= Math.abs(cast_result[j][2]) && inv3 < 2 ){
                                    best_action_type = "CAST";
                                    best = cast_result[j][4];
                                    break;
                                }

                                if(cast_result[j][3] < 0 && inv3 >= Math.abs(cast_result[j][3]) ){
                                    best_action_type = "CAST";
                                    best = cast_result[j][4];
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            //
            
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // in the first league: BREW <id> | WAIT; later: BREW <id> | CAST <id> [<times>] | LEARN <id> | REST | WAIT
            if(best_action_type == "REST"){
                System.out.println( "REST" );
            }else{
                System.out.println( best_action_type + " " + best );
            }
        }
    }
}
