package Breakthrough;
public class Solution
{
    public static void main(String[] args) {
        Board board = new Board(8,8);
        board.printBoard();
        board.printArray();
        Player player1 = new Player(1,board);
        Player player2 = new Player(2, board);
        while (!player2.getHeadNode().winCondition())
        {
            board = offMinimaxSearch(board,player1).getBoard();
            System.out.println("Player one's move");
            board.printBoard();
            board = offMinimaxSearch(board,player2).getBoard();
            System.out.println("Player two's move");
            board.printBoard();
        }
    }
    public static Node offMinimaxSearch(Board board, Player player)
    {
        int me = player.getPlayerNumber();
        int opponent = player.getPlayerNumber() % 2 + 1;
        player.setHeadNode(board); // Set players head node to our new board
        if (player.getHeadNode().winCondition())
            return player.getHeadNode();
        player.getHeadNode().checkPlayerMoves(me); // check for moves player can make
        player.getHeadNode().getPlayerMove(me).checkPlayerMoves(opponent);
        player.getHeadNode().getPlayerMove(me).getPlayerMove(opponent).checkPlayerMoves(me);
        Node bestMyFirstMove = player.getHeadNode().getPlayerMove(me);
        Node bestOpponentFirstMove = player.getHeadNode().getPlayerMove(me).getPlayerMove(opponent);
        Node bestMySecondMove = player.getHeadNode().getPlayerMove(me).getPlayerMove(opponent).getPlayerMove(me);
        for (Node myPlayerMove1: player.getHeadNode().getPlayerMoves(me)) // for all player moves
        {
            // if player's move results in less opponent pieces, that's a good move. Maybe the best move
            if (myPlayerMove1.getOffensiveHeuristic(opponent) < bestMyFirstMove.getOffensiveHeuristic(opponent))
                bestMyFirstMove = myPlayerMove1;
            bestMyFirstMove.checkPlayerMoves(opponent); // check for moves opponent can make.
            for (Node opponentPlayerMove1: myPlayerMove1.getPlayerMoves(opponent)) // for all opponent moves
                if (opponentPlayerMove1.getOffensiveHeuristic(me) < bestOpponentFirstMove.getOffensiveHeuristic(me))
                    bestOpponentFirstMove = opponentPlayerMove1;  //if opponent move results in less player pieces, good move
            bestOpponentFirstMove.checkPlayerMoves(me); // Check for moves specifically from best opp move
            for (Node myPlayerMove2: bestOpponentFirstMove.getPlayerMoves(me)) // for all player 1 moves from best opp move
                if (myPlayerMove2.getOffensiveHeuristic(opponent) < bestMySecondMove.getOffensiveHeuristic(opponent))
                    bestMySecondMove = myPlayerMove2;
        }
        if (bestMySecondMove.getRemainingPlayerPieces(opponent) < bestMyFirstMove.getRemainingPlayerPieces(opponent))
            return bestMySecondMove.getParentNode().getParentNode();
        else
            return bestMyFirstMove;
    }
}
