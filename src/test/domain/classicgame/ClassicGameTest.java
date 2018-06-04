package domain.classicgame;

import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.player.Player;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class ClassicGameTest extends TestCase {

    @Test
    public void testIsEnd_whenNotEnd_returnFalse() {
        Game game = new ClassicGame(new Player(null), new Player(null));
        boolean result = game.isEnd();
        assertFalse(result);
    }

    @Test
    public void testFire_whenGameObjectIsMissed_returnMissed() {
        Player firstPlayer = new Player(null);
        Game game = new ClassicGame(firstPlayer, new Player(null));
        Result result = game.fire(firstPlayer, 2, 'b');
        assertEquals(Result.MISSED, result);
    }

    @Test
    public void testFire_whenGameObjectIsGot_returnGot() {
        Player firstPlayer = new Player(null);
        Game game = new ClassicGame(firstPlayer, new Player(null));
        Result result = game.fire(firstPlayer, 3, 'a');
        assertEquals(Result.GOT, result);
    }

    @Test
    public void testFire_whenGameObjectIsKilled_returnKilled() {
        Player firstPlayer = new Player(null);
        Game game = new ClassicGame(firstPlayer, new Player(null));
        Result result = game.fire(firstPlayer, 0, 'a');
        assertEquals(Result.KILLED, result);
    }
}