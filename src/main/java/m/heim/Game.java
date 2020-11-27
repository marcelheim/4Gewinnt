package m.heim;

public class Game {
    private final Board board;
    private Player player;
    private Player winner;
    private boolean gameOver;

    private Player evaluateWinnerFromPlacement(Point positionLastPlaced) {
        boolean win = false;
        int[] countedLengths = getCountedLengths(positionLastPlaced);
        int[] countedLengthsAcross = {
                countedLengths[0] + countedLengths[6],
                countedLengths[2] + countedLengths[5],
                countedLengths[3] + countedLengths[4],
                countedLengths[1]};
        for (int i = 0; i < 4; i++) {
            if (countedLengthsAcross[i] >= 3) win = true;
        }
        if (win) return this.board.get(positionLastPlaced);
        else return Player.UNDEFINED;
    }

    private int[] getCountedLengths(Point position) {
        int[] countedLengths = new int[7];
        int dirX, dirY;
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0 -> {
                    dirX = -1;
                    dirY = -1;
                }
                case 1 -> {
                    dirX = 0;
                    dirY = -1;
                }
                case 2 -> {
                    dirX = 1;
                    dirY = -1;
                }
                case 3 -> {
                    dirX = -1;
                    dirY = 0;
                }
                case 4 -> {
                    dirX = 1;
                    dirY = 0;
                }
                case 5 -> {
                    dirX = -1;
                    dirY = 1;
                }
                case 6 -> {
                    dirX = 1;
                    dirY = 1;
                }
                default -> throw new IllegalStateException("Unexpected value: " + i);
            }
            countedLengths[i] = countLengthInDir(dirX, dirY, position);
        }
        return countedLengths;
    }

    private int countLengthInDir(int dirX, int dirY, Point position) {
        boolean end = false;
        int lengthInDir = 0;
        int positionDirX = position.getX() + dirX;
        int positionDirY = position.getY() + dirY;
        Player player = this.board.get(position);
        do {
            if (positionDirX < 0 || positionDirY < 0
                    || positionDirX >= this.board.getBoardWidth()
                    || positionDirY >= this.board.getBoardHeight()
            ) end = true;
            else if (player == this.board.get(new Point(positionDirX, positionDirY))) {
                positionDirX += dirX;
                positionDirY *= dirY;
                lengthInDir += 1;
            } else end = true;
        } while (!end);
        return lengthInDir;
    }

    public Game(int sizeX, int sizeY) {
        this.board = new Board(sizeX, sizeY);
        this.player = Player.PLAYER1;
        this.winner = Player.UNDEFINED;
        this.gameOver = false;
    }

    public boolean place(int x) {
        if (!this.gameOver && this.board.place(this.player, x)) {
            Point position = new Point(x, board.getPlacedHeight(x) - 1);
            this.winner = evaluateWinnerFromPlacement(position);
            if (this.winner != Player.UNDEFINED
                    || this.board.getNumberOfStones() >= this.board.getBoardWidth() * this.board.getBoardHeight())
                gameOver = true;
            else {
                if (this.player == Player.PLAYER1) this.player = Player.PLAYER2;
                else this.player = Player.PLAYER1;
            }
            return true;
        } else return false;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
