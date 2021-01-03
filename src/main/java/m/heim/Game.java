package m.heim;

public class Game {
    private final Board board;
    private Player player;
    private Player playerLastTurn;
    private Player winner;
    private boolean gameOver;

    private boolean evaluateWinnerFromPlayer(Player player) {
        // horizontalCheck
        for (int j = 0; j < this.board.getBoardHeight() - 3; j++) {
            for (int i = 0; i < this.board.getBoardWidth(); i++) {
                if (this.board.get(new Point(i, j)) == player
                        && this.board.get(new Point(i, j+1)) == player
                        && this.board.get(new Point(i, j+2)) == player
                        && this.board.get(new Point(i, j+3)) == player
                ) return true;
            }
        }
        // verticalCheck
        for (int i = 0; i < this.board.getBoardWidth()-3; i++) {
            for (int j = 0; j < this.board.getBoardHeight(); j++) {
                if (this.board.get(new Point(i, j)) == player
                        && this.board.get(new Point(i+1, j)) == player
                        && this.board.get(new Point(i+2, j)) == player
                        && this.board.get(new Point(i+3, j)) == player
                ) return true;
            }
        }
        // ascendingDiagonalCheck
        for (int i = 3; i < this.board.getBoardWidth(); i++) {
            for (int j = 0; j < this.board.getBoardHeight()-3; j++) {
                if (this.board.get(new Point(i, j)) == player
                        && this.board.get(new Point(i-1, j+1)) == player
                        && this.board.get(new Point(i-2, j+2)) == player
                        && this.board.get(new Point(i-3, j+3)) == player
                ) return true;
            }
        }
        // descendingDiagonalCheck
        for (int i = 3; i < this.board.getBoardWidth(); i++) {
            for (int j = 3; j < this.board.getBoardHeight(); j++) {
                if (this.board.get(new Point(i, j)) == player
                        && this.board.get(new Point(i-1, j-1)) == player
                        && this.board.get(new Point(i-2, j-2)) == player
                        && this.board.get(new Point(i-3, j-3)) == player
                ) return true;
            }
        }
        return false;
    }

    public Game(int sizeX, int sizeY) {
        this.board = new Board(sizeX, sizeY);
        this.player = Player.PLAYER1;
        this.playerLastTurn = Player.UNDEFINED;
        this.winner = Player.UNDEFINED;
        this.gameOver = false;
    }

    public boolean place(int x) {
        if (!this.gameOver && this.board.place(this.player, x)) {
            this.playerLastTurn = this.player;
            if(evaluateWinnerFromPlayer(this.player)) this.winner = this.player;
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

    public Player getPlayerLastTurn() {
        return playerLastTurn;
    }
}
