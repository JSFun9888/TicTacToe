public class GameBoard {
    char[][] board = {{' ', '|', ' ', '|', ' '},
    {'-', '+', '-', '+', '-'},
    {'-', '|', '-', '|', '-'},
    {'-', '+', '-', '+', '-'},
    {'-', '|', '-', '|', '-'}};

    public GameBoard(){
    }

    public void printGameBoard(char[][] board){
        for(char[] row : board) {
            for(char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public void placePiece(char[][] board, int pos, String user){
        char symbol;
        if(user.equals("player 1")){
            symbol = 'X';
        } else {
            symbol = 'Y';
        }
        switch(pos) {
            case 1:
                board[0][0] = symbol;
                break;
            case 2:
                board[0][2] = symbol;
                break;
            case 3:
                board[0][4] = symbol;
                break;
            case 4:
                board[2][0] = symbol;
                break;
            case 5:
                board[2][2] = symbol;
                break;
            case 6:
                board[2][4] = symbol;
                break;
            case 7:
                board[4][0] = symbol;
                break;
            case 8:
                board[4][2] = symbol;
                break;
            case 9:
                board[4][4] = symbol;
                break;
        }
    }
}
