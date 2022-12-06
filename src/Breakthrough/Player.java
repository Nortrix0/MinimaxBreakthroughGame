package Breakthrough;

public class Player
{
    private final int playerNumber;
    private Node headNode;

    public Player(int playerNumber, Board board)
    {
        this.playerNumber = playerNumber;
        headNode = new Node(board);
    }

    public int getPlayerNumber() { return this.playerNumber; }
    public void setHeadNode(Board board) {this.headNode = new Node(board);}
    public Node getHeadNode() { return this.headNode; }

}
