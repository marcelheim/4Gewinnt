package m.heim;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Disk extends Circle {
    private Point position;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Disk(double radius, Paint fill, Point position, Player player) {
        super(radius, fill);
        this.position = position;
        this.player = player;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
