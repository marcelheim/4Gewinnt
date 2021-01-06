package m.heim;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
    private Game game;
    private final int boardSizeX = 7;
    private final int boardSizeY = 6;
    private double sceneSizeX;
    private double sceneSizeY;
    private List<Disk> diskList = new ArrayList<>();
    @FXML
    private Pane rootPane;
    @FXML
    private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSizeX = rootPane.getPrefWidth();
        sceneSizeY = rootPane.getPrefHeight();
        initGame();
        initDraw();
        updateStatusLabel();
    }

    private void initGame(){
        game = new Game(this.boardSizeX, this.boardSizeY);
    }

    private void initDraw(){
        rootPane.setStyle("-fx-background-color: #212121");
        rootPane.getChildren().clear();
        rootPane.getChildren().add(makeGridShape());
        rootPane.getChildren().addAll(makeColumnShapeList());
    }

    @FXML
    private void restartAction(){
        initGame();
        initDraw();
        debugPrint();
        updateStatusLabel();
    }

    private void place(int x){
        if(game.place(x)) {
            debugPrint();
            drawDisk(new Point(x, game.getBoard().getPlacedHeight(x)));
        }
        updateStatusLabel();
    }

    private void updateStatusLabel(){
        if (!game.isGameOver()){
            statusLabel.setText("Spieler: " + (game.getPlayer() == Player.PLAYER1 ? "Blau" : "Grün"));
        }
        else if(game.getWinner() == Player.UNDEFINED) statusLabel.setText("Unentschieden");
        else statusLabel.setText("Sieger: " + (game.getPlayer() == Player.PLAYER1 ? "Blau" : "Grün"));
    }

    private Shape makeGridShape(){
        double tileSize = sceneSizeX / boardSizeX;
        double diskSize = sceneSizeX / boardSizeX * 0.8f;
        Shape gridShape = new Rectangle(boardSizeX * tileSize, boardSizeY * tileSize);

        for (int x = 0; x < boardSizeX; x++) {
            for (int y = 0; y < boardSizeY; y++) {
                Circle circle = new Circle(diskSize / 2);
                circle.setSmooth(true);
                circle.setCenterX(diskSize / 2);
                circle.setCenterY(diskSize / 2);
                circle.setTranslateX(x * tileSize + (tileSize - diskSize) / 2);
                circle.setTranslateY(y * tileSize + (tileSize - diskSize) / 2);
                gridShape = Shape.subtract(gridShape, circle);
            }
        }

        gridShape.setSmooth(true);
        gridShape.setFill(Color.web("000000"));

        return gridShape;
    }

    private List<Rectangle> makeColumnShapeList(){
        double tileSize = sceneSizeX / boardSizeX;

        List<Rectangle> columnShapeList = new ArrayList<>();

        for (int x = 0; x < boardSizeX; x++) {
            Rectangle rect = new Rectangle(tileSize, tileSize * boardSizeY);
            rect.setTranslateX(x * tileSize);

            rect.setFill(Color.TRANSPARENT);
            rect.setOnMouseEntered(e -> {
                Paint color = Color.web("2196F3", 0.3);
                if(game.isGameOver()) color = Color.TRANSPARENT;
                else if(game.getPlayerLastTurn() == Player.PLAYER1) color = Color.web("1DE9B6", 0.3);
                rect.setFill(color);
            });
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            int finalX = x;
            rect.setOnMouseClicked(e -> {
                place(finalX);
            });

            columnShapeList.add(rect);
        }

        return columnShapeList;
    }

    private void drawDisk(Point position){
        double tileSize = sceneSizeX / boardSizeX;
        double diskSize = sceneSizeX / boardSizeX * 0.8f;
        position = new Point(position.getX(), boardSizeY - position.getY());
        Paint color = Color.web("2196F3");
        if(game.getPlayerLastTurn() == Player.PLAYER2) color = Color.web("1DE9B6");
        Disk disk = new Disk(diskSize / 2, color, position, game.getPlayerLastTurn());
        disk.setCenterX(diskSize / 2);
        disk.setCenterY(diskSize / 2);
        disk.setTranslateX(position.getX() * tileSize + (tileSize - diskSize) / 2);
        disk.setTranslateY(position.getY() * tileSize + (tileSize - diskSize) / 2);
        diskList.add(disk);
        rootPane.getChildren().add(disk);
    }

    private void debugPrint(){
        System.out.printf("Player: %d Stones: %d Winner: %d GameOver: %B\n", game.getPlayer().ordinal(), game.getBoard().getNumberOfStones(), game.getWinner().ordinal(), game.isGameOver());
        for (int y = 5; y >= 0; y--) {
            for (int x = 0; x < 7; x++) {
                System.out.printf("[%d]", game.getBoard().get(new Point(x, y)).ordinal());
            }
            System.out.println();
        }
    }
}
