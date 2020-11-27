import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {

    static ArrayList<Integer> player1Positions = new ArrayList<Integer>();
    static ArrayList<Integer> player2Positions = new ArrayList<Integer>();
    public static void main(String[] args) {
        char[][] initial = {{' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {'-', '|', '-', '|', '-'},
                    {'-', '+', '-', '+', '-'},
                    {'-', '|', '-', '|', '-'}};
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {'-', '|', '-', '|', '-'},
                    {'-', '+', '-', '+', '-'},
                    {'-', '|', '-', '|', '-'}};
        char[][] temp;
        printGameBoard(gameBoard);
        
        while(true){
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter your placement (1-9):");
            int pos = scan.nextInt();
            while(player1Positions.contains(pos) || player2Positions.contains(pos)){
                System.out.println(pos + " Already entered, pick again (1-9):");
                pos = scan.nextInt();
            }
            player1Positions.add(pos);
            System.out.println(pos);

            placePiece(gameBoard, pos, "player 1");
            printGameBoard(gameBoard);
            System.out.println(checkWinner());
            
            if(!isEnd()){
                bestMove(gameBoard);
                printGameBoard(gameBoard);
                System.out.println(checkWinner());
            } else{
                temp = initial;
                gameBoard = temp;;
                player1Positions.clear();
                player2Positions.clear();
                System.out.println("New Game!");
                printGameBoard(gameBoard);
            }
            
            System.out.println(player1Positions.size()+player2Positions.size());
        }

    }

    public static void printGameBoard(char[][] gameBoard){
        for(char[] row : gameBoard) {
            for(char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placePiece(char[][] gameBoard, int pos, String user){
        char symbol;
        if(user.equals("player 1")){
            symbol = 'X';
        } else {
            symbol = 'O';
        }
        switch(pos) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
        }
    }

    public static String checkWinner() {
        List topRow = Arrays.asList(1,2,3);
        List midRow = Arrays.asList(4,5,6);
        List botRow = Arrays.asList(7,8,9);
        List leftCol = Arrays.asList(1,4,7);
        List midCol = Arrays.asList(2,5,8);
        List rightCol = Arrays.asList(3,6,9);
        List cross1 = Arrays.asList(1,5,9);
        List cross2 = Arrays.asList(7,5,3);

        List<List> winning = new ArrayList<List>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);

        for(List l : winning) {
            if(player1Positions.containsAll(l)){
                return "Player 1 wins";
            } else if(player2Positions.containsAll(l)){
                return "Player 2 wins";
            }
        }
        if(isEnd()){
            return "Tie game";
        }
        return "";
    }

    public static int[] minimax(int depth, boolean isMaximising){
        int[] score = {0,depth};
        String result = checkWinner();
        //If we have a winner return whether it was a win or a loss
        if(result != "") {
            if(result.equals("Player 1 wins")){
                score[0] = -10;
                return score;
            } else if( result.equals("Player 2 wins")){
                score[0] = 10;
                return score;
            } else {
                return score;
            }
        }

        //evaluate CPU's potential move
        if(isMaximising) {
            int[] bestScore = {-1000, 100};
            for(int i = 1; i < 10 ; i++) {
                if(!player2Positions.contains(i) && !player1Positions.contains(i)){
                    player2Positions.add(i);
                    score = minimax(depth + 1, false);
                    player2Positions.remove((Integer)i);
                    bestScore[0] = Math.max(score[0], bestScore[0]);
                    bestScore[1] = Math.min(score[1], bestScore[1]);
                }
                
            } 
            return bestScore;
        } else { //Evaluate Players potential move
            int[] bestScore = {1000, -100};
            for(int i = 1; i < 10; i++){
                if(!player1Positions.contains(i) && !player2Positions.contains(i)) {
                    player1Positions.add(i);
                    score = minimax(depth + 1, true);
                    player1Positions.remove((Integer)i);
                    
                    bestScore[0] = Math.min(score[0], bestScore[0]);
                    bestScore[1] = Math.max(score[1], bestScore[1]);
                }
            }
            return bestScore;
        }
    }

    public static void bestMove(char[][] gameBoard){
        int[] bestScore = {-1000, 99};
        int move = 0;
        int[] score = {-1000, 99};
        for(int i = 1; i < 10; i++){
            //Evalute what the best move is for remaining spaces
            if(!player1Positions.contains(i) && !player2Positions.contains(i)){
                player2Positions.add(i);
                score = minimax(0, false);
                System.out.println("The score for position: " + i + " is... " + score[0]);
                System.out.println("The depth for position: " + i + " is... " + score[1]);
                System.out.println("The bestScore for position: " + i + " is... " + bestScore[0]);
                System.out.println("The bestScore depth for position: " + i + " is... " + bestScore[1]);
                player2Positions.remove((Integer)i);
                if(score[0] > bestScore[0] && score[1] <= bestScore[1] || score[0] > bestScore[0]){
                    bestScore = score;
                    move = i;
                }
            }
        }
        System.out.println("CPU wants to pick this pos: " + move);
        
        player2Positions.add(move);
        placePiece(gameBoard, move, "CPU");

    }

    public static boolean isEnd(){
        if(player1Positions.size() + player2Positions.size() >= 9){
            return true;
        }

        return false;
    }

    
}