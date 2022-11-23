package Breakthrough;

public class Solution
{
    public static void main(String[] args)
    {
        //startOffMinimaxGame();
        startDefMinimaxGame();
    }

    public static void startOffMinimaxGame()
    {
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

    public static void startDefMinimaxGame()
    {
        Board board = new Board(8,8);
        board.printBoard();
        board.printArray();
        Player player1 = new Player(1,board);
        Player player2 = new Player(2, board);
        while (!player2.getHeadNode().winCondition())
        {
            board = defMinimaxSearch(board,player1).getBoard();
            System.out.println("Player one's move");
            board.printBoard();
            board = defMinimaxSearch(board,player2).getBoard();
            System.out.println("Player two's move");
            board.printBoard();
        }
    }

    public static Node offMinimaxSearch(Board board, Player player)
    {
        player.setHeadNode(board); // Set players head node to our new board
        if (player.getPlayerNumber() == 1)
        {
            player.getHeadNode().checkPlayer1Moves(player.getHeadNode()); // check for moves player 1 can make
            player.getHeadNode().getPlayer1Move(0).checkPlayer2Moves(player.getHeadNode().getPlayer1Move(0));
            player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0).checkPlayer1Moves(player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0));
            Node bestP1FirstMove = player.getHeadNode().getPlayer1Move(0);
            Node bestP2FirstMove = player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0);
            Node bestP1SecondMove = player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0).getPlayer1Move(0);
            for (Node player1Move1: player.getHeadNode().getPlayer1Moves()) // for all player 1 moves
            {
                // if player 1's move results in less opponent pieces, thats a good move. Maybe the best move
                if (player1Move1.getOffensiveHeuristic() < bestP1FirstMove.getOffensiveHeuristic())
                {
                    /*
                    System.out.println("Best first move changed from");
                    bestP1FirstMove.getBoard().printArray();
                    System.out.println("to");
                    player1Move1.getBoard().printArray();
                    System.out.println("because " + player1Move1.getRemainingPlayer2Pieces() + " < " + bestP1FirstMove.getRemainingPlayer2Pieces());
                     */
                    bestP1FirstMove = player1Move1;
                }

                player1Move1.checkPlayer2Moves(player1Move1); // check for moves player 2 can make.
                for (Node player2Move1: player1Move1.getPlayer2Moves()) // for all player 2 moves
                {
                    if (player2Move1.getOffensiveHeuristic() < bestP2FirstMove.getOffensiveHeuristic())
                    {
                        /*
                        System.out.println("Best player2 first move changed from");
                        bestP2FirstMove.getBoard().printArray();
                        System.out.println("to");
                        player2Move1.getBoard().printArray();
                        System.out.println("because " + player2Move1.getRemainingPlayer1Pieces() + " < " + bestP2FirstMove.getRemainingPlayer1Pieces());
                         */
                        bestP2FirstMove = player2Move1;  //if player 2 move results in less player 1 pieces, good move
                    }

                }
                bestP2FirstMove.checkPlayer1Moves(bestP2FirstMove); // Check for moves specifically from best opp move
                for (Node player1Move2: bestP2FirstMove.getPlayer1Moves()) // for all player 1 moves from best opp move
                {
                    //System.out.println("First player: Possible third move from best second move");
                    //player1Move2.getBoard().printArray();
                    if (player1Move2.getOffensiveHeuristic() < bestP1SecondMove.getOffensiveHeuristic())
                    {
                        /*
                        System.out.println("Best second move changed from");
                        bestP1SecondMove.getBoard().printArray();
                        System.out.println("to");
                        player1Move2.getBoard().printArray();
                        System.out.println("because " + player1Move2.getRemainingPlayer2Pieces() + " < " + bestP1SecondMove.getRemainingPlayer2Pieces());
                         */
                        bestP1SecondMove = player1Move2;
                    }
                }
            }
            if (bestP1SecondMove.getRemainingPlayer2Pieces() < bestP1FirstMove.getRemainingPlayer2Pieces())
                return bestP1SecondMove.getParentNode().getParentNode();
            else
                return bestP1FirstMove;
        }
        else
        {
            player.getHeadNode().checkPlayer2Moves(player.getHeadNode());
            player.getHeadNode().getPlayer2Move(0).checkPlayer1Moves(player.getHeadNode().getPlayer2Move(0));
            player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0).checkPlayer2Moves(player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0));
            Node bestP2FirstMove = player.getHeadNode().getPlayer2Move(0);
            Node bestP1FirstMove = player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0);
            Node bestP2SecondMove = player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0).getPlayer2Move(0);
            for (Node player2Move1: player.getHeadNode().getPlayer2Moves())
            {
                // if player 2's move results in less opponent pieces, thats a good move. Maybe the best move
                if (player2Move1.getOffensiveHeuristic() < bestP2FirstMove.getOffensiveHeuristic())
                {
                    /*
                    System.out.println("Best first move changed from");
                    bestP2FirstMove.getBoard().printArray();
                    System.out.println("to");
                    player2Move1.getBoard().printArray();
                    System.out.println("because " + player2Move1.getRemainingPlayer1Pieces() + " < " + bestP2FirstMove.getRemainingPlayer1Pieces());
                     */
                    bestP2FirstMove = player2Move1;
                }
                player2Move1.checkPlayer1Moves(player2Move1);
                for (Node player1Move1: player2Move1.getPlayer1Moves()) // for all player 2 moves
                {
                    if (player1Move1.getOffensiveHeuristic() < bestP1FirstMove.getOffensiveHeuristic())
                    {
                        /*
                        System.out.println("Best player1 first move changed from");
                        bestP1FirstMove.getBoard().printArray();
                        System.out.println("to");
                        player1Move1.getBoard().printArray();
                        System.out.println("because " + player1Move1.getRemainingPlayer2Pieces() + " < " + bestP1FirstMove.getRemainingPlayer2Pieces());
                         */
                        bestP1FirstMove = player1Move1;  //if player 2 move results in less player 1 pieces, good move
                    }
                }
                bestP1FirstMove.checkPlayer2Moves(bestP1FirstMove); // Check for moves specifically from best opp move
                for (Node player2Move2: bestP1FirstMove.getPlayer2Moves()) // for all player 1 moves from best opp move
                {
                    if (player2Move2.getOffensiveHeuristic() < bestP2SecondMove.getOffensiveHeuristic())
                    {
                        /*
                        System.out.println("Best second move changed from");
                        bestP2SecondMove.getBoard().printArray();
                        System.out.println("to");
                        player2Move2.getBoard().printArray();
                        System.out.println("because " + player2Move2.getRemainingPlayer2Pieces() + " < " + bestP2SecondMove.getRemainingPlayer2Pieces());
                         */
                        bestP2SecondMove = player2Move2;
                    }

                }
            }
            if (bestP2SecondMove.getRemainingPlayer1Pieces() < bestP2FirstMove.getRemainingPlayer1Pieces())
                return bestP2SecondMove.getParentNode().getParentNode();
            else
                return bestP2FirstMove;
        }
    }

    public static Node defMinimaxSearch(Board board, Player player)
    {
        player.setHeadNode(board); // Set players head node to our new board
        if (player.getPlayerNumber() == 1)
        {
            player.getHeadNode().checkPlayer1Moves(player.getHeadNode()); // check for moves player 1 can make
            player.getHeadNode().getPlayer1Move(0).checkPlayer2Moves(player.getHeadNode().getPlayer1Move(0));
            player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0).checkPlayer1Moves(player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0));
            Node bestP1FirstMove = player.getHeadNode().getPlayer1Move(0);
            Node bestP2FirstMove = player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0);
            Node bestP1SecondMove = player.getHeadNode().getPlayer1Move(0).getPlayer2Move(0).getPlayer1Move(0);
            for (Node player1Move1: player.getHeadNode().getPlayer1Moves()) // for all player 1 moves
            {
                // if player 1's move results in less opponent pieces, thats a good move. Maybe the best move
                if (player1Move1.getDefensiveHeuristic() < bestP1FirstMove.getDefensiveHeuristic())
                {
                    /*
                    System.out.println("Best first move changed from");
                    bestP1FirstMove.getBoard().printArray();
                    System.out.println("to");
                    player1Move1.getBoard().printArray();
                    System.out.println("because " + player1Move1.getRemainingPlayer2Pieces() + " < " + bestP1FirstMove.getRemainingPlayer2Pieces());
                     */
                    bestP1FirstMove = player1Move1;
                }

                player1Move1.checkPlayer2Moves(player1Move1); // check for moves player 2 can make.
                for (Node player2Move1: player1Move1.getPlayer2Moves()) // for all player 2 moves
                {
                    if (player2Move1.getDefensiveHeuristic() < bestP2FirstMove.getDefensiveHeuristic())
                    {
                        /*
                        System.out.println("Best player2 first move changed from");
                        bestP2FirstMove.getBoard().printArray();
                        System.out.println("to");
                        player2Move1.getBoard().printArray();
                        System.out.println("because " + player2Move1.getRemainingPlayer1Pieces() + " < " + bestP2FirstMove.getRemainingPlayer1Pieces());
                         */
                        bestP2FirstMove = player2Move1;  //if player 2 move results in less player 1 pieces, good move
                    }

                }
                bestP2FirstMove.checkPlayer1Moves(bestP2FirstMove); // Check for moves specifically from best opp move
                for (Node player1Move2: bestP2FirstMove.getPlayer1Moves()) // for all player 1 moves from best opp move
                {
                    //System.out.println("First player: Possible third move from best second move");
                    //player1Move2.getBoard().printArray();
                    if (player1Move2.getDefensiveHeuristic() < bestP1SecondMove.getDefensiveHeuristic())
                    {
                        /*
                        System.out.println("Best second move changed from");
                        bestP1SecondMove.getBoard().printArray();
                        System.out.println("to");
                        player1Move2.getBoard().printArray();
                        System.out.println("because " + player1Move2.getRemainingPlayer2Pieces() + " < " + bestP1SecondMove.getRemainingPlayer2Pieces());
                         */
                        bestP1SecondMove = player1Move2;
                    }
                }
            }
            if (bestP1SecondMove.getRemainingPlayer2Pieces() < bestP1FirstMove.getRemainingPlayer2Pieces())
                return bestP1SecondMove.getParentNode().getParentNode();
            else
                return bestP1FirstMove;
        }
        else
        {
            player.getHeadNode().checkPlayer2Moves(player.getHeadNode());
            player.getHeadNode().getPlayer2Move(0).checkPlayer1Moves(player.getHeadNode().getPlayer2Move(0));
            player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0).checkPlayer2Moves(player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0));
            Node bestP2FirstMove = player.getHeadNode().getPlayer2Move(0);
            Node bestP1FirstMove = player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0);
            Node bestP2SecondMove = player.getHeadNode().getPlayer2Move(0).getPlayer1Move(0).getPlayer2Move(0);
            for (Node player2Move1: player.getHeadNode().getPlayer2Moves())
            {
                // if player 2's move results in less opponent pieces, thats a good move. Maybe the best move
                if (player2Move1.getDefensiveHeuristic() < bestP2FirstMove.getDefensiveHeuristic())
                {
                    /*
                    System.out.println("Best first move changed from");
                    bestP2FirstMove.getBoard().printArray();
                    System.out.println("to");
                    player2Move1.getBoard().printArray();
                    System.out.println("because " + player2Move1.getRemainingPlayer1Pieces() + " < " + bestP2FirstMove.getRemainingPlayer1Pieces());
                     */
                    bestP2FirstMove = player2Move1;
                }
                player2Move1.checkPlayer1Moves(player2Move1);
                for (Node player1Move1: player2Move1.getPlayer1Moves()) // for all player 2 moves
                {
                    if (player1Move1.getDefensiveHeuristic() < bestP1FirstMove.getDefensiveHeuristic())
                    {
                        /*
                        System.out.println("Best player1 first move changed from");
                        bestP1FirstMove.getBoard().printArray();
                        System.out.println("to");
                        player1Move1.getBoard().printArray();
                        System.out.println("because " + player1Move1.getRemainingPlayer2Pieces() + " < " + bestP1FirstMove.getRemainingPlayer2Pieces());
                         */
                        bestP1FirstMove = player1Move1;  //if player 2 move results in less player 1 pieces, good move
                    }
                }
                bestP1FirstMove.checkPlayer2Moves(bestP1FirstMove); // Check for moves specifically from best opp move
                for (Node player2Move2: bestP1FirstMove.getPlayer2Moves()) // for all player 1 moves from best opp move
                {
                    if (player2Move2.getDefensiveHeuristic() < bestP2SecondMove.getDefensiveHeuristic())
                    {
                        /*
                        System.out.println("Best second move changed from");
                        bestP2SecondMove.getBoard().printArray();
                        System.out.println("to");
                        player2Move2.getBoard().printArray();
                        System.out.println("because " + player2Move2.getRemainingPlayer2Pieces() + " < " + bestP2SecondMove.getRemainingPlayer2Pieces());
                         */
                        bestP2SecondMove = player2Move2;
                    }

                }
            }
            if (bestP2SecondMove.getRemainingPlayer1Pieces() < bestP2FirstMove.getRemainingPlayer1Pieces())
                return bestP2SecondMove.getParentNode().getParentNode();
            else
                return bestP2FirstMove;
        }
    }


}
