package Breakthrough;
import java.util.ArrayList;
public class Node
{
    private Node parentNode;
    private Board board;
    private ArrayList<Node>[] playerMoves = new ArrayList[2];
    /* This Constructor is for the player headNode */
    public Node(Board board) { this(board, null); }
    public Node(Board board, Node parentNode)
    {
        this.parentNode = parentNode;
        this.board = board;
        this.playerMoves[0] = new ArrayList<>();
        this.playerMoves[1] = new ArrayList<>();
    }
    public Node getParentNode() {return this.parentNode;}
    public Board getBoard() { return this.board; }
    public int[][] getBoardArray() {return this.board.getBoardArray();}
    public ArrayList<Node> getPlayerMoves(int player) { return this.playerMoves[player - 1]; }
    public Node getPlayerMove(int player) { return this.playerMoves[player - 1].get(0); }
    public Node getPlayerMove(int player, int index) { return this.playerMoves[player - 1].get(index); }
    public void addPlayerMoves(int player, Node node) {this.playerMoves[player - 1].add(node);}
    public void removeAllPlayerMoves(int player)
    {
        for (int i = 0; i < this.playerMoves[player - 1].size(); i++)
            this.playerMoves[player - 1].remove(i);
    }
    public int getRemainingPlayerPieces(int player)
    {
        int num = 0;
        for (int i = 0; i < board.getRowSize(); i++)
            for (int j = 0; j < board.getColumnSize(); j++)
                if (board.getPosition(i,j) == player)
                    num++;
        return num;
    }
    public int getDefensiveHeuristic(int playerNumber)
    {
        return (int) (2 * (getRemainingPlayerPieces(playerNumber) + Math.random()));
    }
    public int getOffensiveHeuristic(int playerNumber)
    {
        return (int) (2 * (getRemainingPlayerPieces(playerNumber) + Math.random()));
    }
    public boolean winCondition()
    {
        for (int i = 0; i < 1; i++)
            for (int j = 0; j < board.getRowSize(); j++)
                if (board.getPosition(i,j) == 1)
                    return true;
        for (int i = board.getRowSize() - 1; i < board.getRowSize(); i++)
            for (int j = 0; j < board.getColumnSize(); j++)
                if (board.getPosition(i,j) == 2)
                    return true;
        if (getRemainingPlayerPieces(1) == 0 || getRemainingPlayerPieces(2) == 0)
            return true;
        boolean player1remains = false;
        boolean player2remains = false;
        for (int i = 0; i < board.getRowSize(); i++)
        {
            for (int j = 0; j < board.getColumnSize(); j++)
            {
                if (board.getPosition(i,j) == 1) player1remains = true;
                else if (board.getPosition(i,j) == 2) player2remains = true;
                if (player1remains && player2remains)
                    return false;
            }
        }
        return true;
    }
    public void checkPlayerMoves(int player)
    {
        this.removeAllPlayerMoves(player);
        for (int i = 0; i < board.getRowSize(); i++)
        {
            for (int j = 0; j < board.getColumnSize(); j++)
            {
                // If position is held by player 1
                if (this.board.getPosition(i,j) == player)
                {
                    int offset = player == 1 ? -1 : 1;
                    // Check for moving straight up
                    if (i+offset >= 0 && i+offset < board.getColumnSize() && board.getPosition(i+offset,j) != player)
                        this.playerMoves[player - 1].add(new Node(new Board(this.board,i,j,i+offset,j), this));
                    // Check for up and to the right
                    if (i+offset >= 0 && i+offset < board.getColumnSize() && j + 1 < 8 && board.getPosition(i+offset,j+1) != player)
                        this.playerMoves[player - 1].add(new Node(new Board(this.board,i,j,i+offset,j+1), this));
                    // Check for up and to the left
                    if (i+offset >= 0 && i+offset < board.getColumnSize() && j - 1 >= 0 && board.getPosition(i+offset,j-1) != player)
                        this.playerMoves[player - 1].add(new Node(new Board(this.board,i,j,i+offset,j-1), this));
                }
            }
        }
    }
}
