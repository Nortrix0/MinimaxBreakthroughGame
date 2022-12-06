package Breakthrough;

public class Board
{
    private int[][] boardArray;
    private int rowSize;
    private int columnSize;

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
        {
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
    }

    public int[][] makeMove(int fromRow, int fromColumn, int toRow, int toColumn)
    {
        int[][] newBoardArray = new int[this.rowSize][this.columnSize];
        for (int i = 0; i < this.rowSize; i++)
        {
            for (int j = 0; j < this.columnSize; j++)
            {
                if (i == fromRow && j == fromColumn)
                    newBoardArray[i][j] = 0;
                else if (i ==toRow && j == toColumn)
                    newBoardArray[i][j] = this.boardArray[fromRow][fromColumn];
                else
                    newBoardArray[i][j] = this.boardArray[i][j];
            }
        }
        return newBoardArray;
    }

    public void setPosition(int row, int column, int player){this.boardArray[row][column] = player;}
    public int getPosition(int row, int column) {return boardArray[row][column];}
    public int[][] getBoardArray() { return boardArray; }
    public int getRowSize(){return this.rowSize;}
    public int getColumnSize(){return this.columnSize;}


    public void printArray()
    {
        System.out.println();
        for (int i = 0; i < this.rowSize; i++)
            for (int j = 0; j < this.columnSize; j++)
                System.out.print(this.boardArray[i][j] + " ");
            System.out.println();
        System.out.println();
    }

    public void printBoard()
    {
        String[] blackWithP1Piece = {"     "," (+) ","     "};
        String[] blackWithP1PieceNewMove = {"     ",ConsoleColors.GREEN+" (+) " + ConsoleColors.RESET,"     "};
        String[] whiteWithP1Piece = {"#####","#(+)#","#####"};
        String[] whiteWithP1PieceNewMoves = {"#####","#"+ConsoleColors.GREEN+"(+)"+ConsoleColors.RESET+"#","#####"};
        String[] blackWithP2Piece = {"     "," (-) ","     "};
        String[] blackWithP2PieceNewMove = {"     ",ConsoleColors.GREEN+" (-) " + ConsoleColors.RESET,"     "};
        String[] whiteWithP2Piece = {"#####","#(-)#","#####"};
        String[] whiteWithP2PieceNewMoves = {"#####","#"+ConsoleColors.GREEN+"(-)"+ConsoleColors.RESET+"#","#####"};
        String[] blackSquare = {"     ","     ","     "};
        String[] whiteSquare = {"#####","#####","#####",};

        System.out.println();
        for (int i = 0; i < columnSize; i++)
        {
            for (int j = 0; j < blackWithP1Piece.length; j++)
            {
                for (int k = 0; k < rowSize; k++)
                {
                    if ((i % 2 == 0 && k % 2 == 0) || (i % 2 == 1 && k % 2 == 1))
                    {
                        if (boardArray[i][k] == 0)
                            System.out.print(blackSquare[j]);
                        else
                            System.out.print(boardArray[i][k] == 1 ? blackWithP1Piece[j] : blackWithP2Piece[j]);
                    }
                    else
                    {
                        if (boardArray[i][k] == 0)
                            System.out.print(whiteSquare[j]);
                        else
                            System.out.print(boardArray[i][k] == 1 ? whiteWithP1Piece[j] : whiteWithP2Piece[j]);
                    }
                }
                System.out.println();
            }
        }
        System.out.println('\n');
    }
}
