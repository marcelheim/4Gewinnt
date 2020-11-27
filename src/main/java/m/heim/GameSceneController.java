package m.heim;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable {
    Game game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game(7, 6);
    }

    @FXML
    public void onPlaceButtonClick(ActionEvent event){
        Button btn = (Button) event.getSource();
        String btnId = btn.getId();
        int x;
        switch (btnId){
            case "placeButton1" -> x = 0;
            case "placeButton2" -> x = 1;
            case "placeButton3" -> x = 2;
            case "placeButton4" -> x = 3;
            case "placeButton5" -> x = 4;
            case "placeButton6" -> x = 5;
            case "placeButton7" -> x = 6;
            default -> throw new IllegalStateException("Unexpected value: " + btnId);
        }
        game.place(x);
        debugPrint();
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
