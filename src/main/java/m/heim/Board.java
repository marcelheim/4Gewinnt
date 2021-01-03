package m.heim;

public class Board {
    private final Player[][] boardMatrix;
    private final int[] maxPlacedHeight;
    private final int boardWidth;
    private final int boardHeight;
    private int numberOfStones;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.boardMatrix = new Player[boardWidth][boardHeight];
        this.maxPlacedHeight = new int[boardWidth];
        this.numberOfStones = 0;

        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                boardMatrix[x][y] = Player.UNDEFINED;
            }
        }
    }

    public boolean place(Player player, int x) {
        if (maxPlacedHeight[x] < boardHeight) {
            this.boardMatrix[x][maxPlacedHeight[x]] = player;
            this.numberOfStones += 1;
            this.maxPlacedHeight[x] += 1;
            return true;
        } else return false;
    }

    public Player get(Point position) {
        return this.boardMatrix[position.getX()][position.getY()];
    }

    public int getPlacedHeight(int x) {
        return maxPlacedHeight[x];
    }

    public int getBoardWidth() {
        return this.boardWidth;
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }

    public int getNumberOfStones() {
        return this.numberOfStones;
    }
}
