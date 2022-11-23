package Breakthrough;

public class Player
{
    private Board board;
    private final int playerNumber;
    private Node headNode;

    public Player(int playerNumber, Board board)
    {
        this.playerNumber = playerNumber;
        this.board = board;
        headNode = new Node(board);
    }

    public int getPlayerNumber() { return this.playerNumber; }
    public Board getBoard() { return this.board; }
    public void setHeadNode(Board board) {this.headNode = new Node(board);}
    public Node getHeadNode() { return this.headNode; }

}
