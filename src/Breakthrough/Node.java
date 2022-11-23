package Breakthrough;

import javax.swing.text.Position;
import java.util.ArrayList;

public class Node
{
    private Node parentNode;
    private Board board;
    private ArrayList<Node> player1Moves;
    private ArrayList<Node> player2Moves;

    /* This Constructor is for the player headNode */
    public Node(Board board)
    {
        this.board = board;
        this.player1Moves = new ArrayList<>();
        this.player2Moves = new ArrayList<>();
    }

    public Node(Board board, Node parentNode)
    {
        this.parentNode = parentNode;
        this.board = board;
        this.player1Moves = new ArrayList<>();
        this.player2Moves = new ArrayList<>();
    }

    public Node getParentNode() {return this.parentNode;}
    public Board getBoard() { return this.board; }
    public int[][] getBoardArray() {return this.board.getBoardArray();}
    public ArrayList<Node> getPlayer1Moves() { return this.player1Moves; }
    public Node getPlayer1Move(int index) { return this.player1Moves.get(index); }
    public ArrayList<Node> getPlayer2Moves() { return this.player2Moves; }
    public Node getPlayer2Move(int index) { return this.player2Moves.get(index); }
    public void addPlayer1Moves(Node node) {this.player1Moves.add(node);}
    public void addPlayer2Moves(Node node) {this.player2Moves.add(node);}
    public void removeAllPlayer1Moves()
    {
        for (int i = 0; i < this.player1Moves.size(); i++)
            this.player1Moves.remove(i);
    }

    public void removeAllPlayer2Moves()
    {
        for (int i = 0; i < this.player2Moves.size(); i++)
            this.player2Moves.remove(i);
    }

    public int getRemainingPlayer1Pieces()
    {
        int num = 0;
        for (int i = 0; i < board.getRowSize(); i++)
            for (int j = 0; j < board.getColumnSize(); j++)
                if (board.getPosition(i,j) == 1)
                    num++;
        return num;
    }

    public int getRemainingPlayer2Pieces()
    {
        int num = 0;
        for (int i = 0; i < board.getRowSize(); i++)
            for (int j = 0; j < board.getColumnSize(); j++)
                if (board.getPosition(i,j) == 2)
                    num++;
        return num;
    }

    public int getDefensiveHeuristic() { return (int) (2 * (getRemainingPlayer1Pieces() + Math.random())); }
    public int getOffensiveHeuristic(){ return (int) (2 * (getRemainingPlayer2Pieces() + Math.random())); }

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
        if (getRemainingPlayer1Pieces() == 0 || getRemainingPlayer2Pieces() == 0)
            return true;


        boolean player1remains = false;
        boolean player2remains = false;
        for (int i = 0; i < board.getRowSize(); i++)
        {
            for (int j = 0; j < board.getColumnSize(); j++)
            {
                if (board.getPosition(i,j) == 1) player1remains = true;
                else if (board.getPosition(i,j) == 2) player2remains = true;
            }
            if (player1remains && player2remains)
                return false;
        }
        return true;
    }

    public void checkPlayer1Moves(Node parentNode)
    {
        this.removeAllPlayer1Moves();
        for (int i = 0; i < board.getRowSize(); i++)
        {
            for (int j = 0; j < board.getColumnSize(); j++)
            {
                // If position is held by player 1
                if (this.board.getPosition(i,j) == 1)
                {
                    // Check for moving straight up
                    if (i - 1 >= 0 && board.getPosition(i-1,j) == 0)
                        this.player1Moves.add(new Node(new Board(this.board,i,j,i-1,j), parentNode));
                    // Check for up and to the right
                    if (i-1 >= 0 && j + 1 < 8 && board.getPosition(i-1,j+1) != 1)
                        this.player1Moves.add(new Node(new Board(this.board,i,j,i-1,j+1), parentNode));
                    // Check for up and to the left
                    if (i-1 >= 0 && j - 1 >= 0 && board.getPosition(i-1,j-1) != 1)
                        this.player1Moves.add(new Node(new Board(this.board,i,j,i-1,j-1), parentNode));
                }
            }
        }
    }

    public void checkPlayer2Moves(Node parentNode)
    {
        this.removeAllPlayer2Moves();
        for (int i = 0; i < board.getRowSize(); i++)
        {
            for (int j = 0; j < board.getColumnSize(); j++)
            {
                // If position is held by player 2
                if (this.board.getPosition(i,j) == 2)
                {
                    // Check for moving straight down
                    if (i + 1 < 8 && board.getPosition(i+1,j) == 0)
                        this.player2Moves.add(new Node(new Board(this.board,i,j,i+1,j), parentNode));
                    // Check for down and to the right
                    if (i + 1 < 8 && j + 1 < 8 && board.getPosition(i+1,j+1) != 2)
                        this.player2Moves.add(new Node(new Board(this.board,i,j,i+1,j+1), parentNode));
                    // Check for down and to the left
                    if (i + 1 < 8 && j - 1 >= 0 && board.getPosition(i+1,j-1) != 2)
                        this.player2Moves.add(new Node(new Board(this.board,i,j,i+1,j-1), parentNode));
                }
            }
        }
    }




}
