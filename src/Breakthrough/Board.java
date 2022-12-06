package Breakthrough;

public class Board
{
    private final int[][] boardArray;
    private final int rowSize;
    private final int columnSize;

    /* This constructor is strictly for making fresh boards */
    public Board(int rowSize, int columnSize)
    {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.boardArray = new int[rowSize][columnSize];
        /* This will initialize the board array to fresh board */
        for (int i = 0; i < rowSize; i++)
        {
            for (int j = 0; j < columnSize; j++)
            {
                if (i < 2)
                    this.boardArray[i][j] = 2;
                else if (i > rowSize - 3)
                    this.boardArray[i][j] = 1;
                else
                    this.boardArray[i][j] = 0;
            }
        }
    }
    public Board(Board board, int fromRow, int fromColumn, int toRow, int toColumn)
    {
        this.rowSize = board.getRowSize();
        this.columnSize = board.getColumnSize();
        this.boardArray = new int[rowSize][columnSize];
        for (int i = 0; i < this.rowSize; i++)
            for (int j = 0; j < this.columnSize; j++)
            {
                if (i == fromRow && j == fromColumn)
                    this.boardArray[i][j] = 0;
                else if (i ==toRow && j == toColumn)
                    this.boardArray[i][j] = board.boardArray[fromRow][fromColumn];
                else
                    this.boardArray[i][j] = board.boardArray[i][j];
            }
    }
    public int getPosition(int row, int column) {return boardArray[row][column];}
    public int getRowSize(){return this.rowSize;}
    public int getColumnSize(){return this.columnSize;}
    public void printArray()
    {
        System.out.println();
        for (int i = 0; i < this.rowSize; i++) {
            for (int j = 0; j < this.columnSize; j++)
                System.out.print(this.boardArray[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
    public void printBoard()
    {
        //Order is Empty Black, Empty White,
        // Player 1 Black, Player 1 White,
        // Player 2 Black, Player 2 White,
        // Player 1 Black New Move, Player 1 White New Move,
        // Player 2 Black New Move, Player 2 White New Move
        String[][] Squares = { {"     ","     ","     "}, {"#####","#####","#####",}, {"     "," (+) ","     "}, {"#####","#(+)#","#####"}, {"     "," (-) ","     "}, {"#####","#(-)#","#####"}, {"     ",ConsoleColors.GREEN+" (+) " + ConsoleColors.RESET,"     "}, {"#####","#"+ConsoleColors.GREEN+"(+)"+ConsoleColors.RESET+"#","#####"}, {"     ",ConsoleColors.GREEN+" (-) " + ConsoleColors.RESET,"     "}, {"#####","#"+ConsoleColors.GREEN+"(-)"+ConsoleColors.RESET+"#","#####"} };
        System.out.println();
        for (int i = 0; i < columnSize; i++)
            for (int j = 0; j < Squares[0].length; j++)
            {
                for (int k = 0; k < rowSize; k++)
                {
                    int blackOrWhite = ((i % 2 == 0 && k % 2 == 0) || (i % 2 == 1 && k % 2 == 1)) ? 1 : 0;
                    int player = boardArray[i][k];
                    int newMove = false ? 6 : 0;
                    System.out.print(Squares[blackOrWhite + (player * 2) + newMove][j]);
                }
                System.out.println();
            }
        System.out.println('\n');
    }
}
