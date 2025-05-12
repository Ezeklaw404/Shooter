import com.neumont.engine.Game;
import com.neumont.engine.Window;
import com.neumont.game.ShooterGame;

public class Main {
    public static void main(String[] args) {
        Game game = new ShooterGame(400, 600, 60);
        Window window = new Window(game,"Pew Pew", 100 ,100, false);

        game.start();
    }
}
