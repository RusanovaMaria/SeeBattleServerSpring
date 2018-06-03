package test.domain.classicplayingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectposition.StandardGameObjectPosition;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class ClassicPlayingFieldTest extends TestCase {

    @Test
    public void testFindCage_whenCageIsExists_returnCage() {
        PlayingField playingField = new ClassicPlayingField(new StandardGameObjectPosition());
        int x = 0;
        Cage cage = playingField.findCage(x, 'a');
        int result = cage.getX();
        assertEquals(result, x);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindCage_whenCageIsNotExists_returnException() {
        PlayingField playingField = new ClassicPlayingField(new StandardGameObjectPosition());
        int x = 0;
        Cage cage = playingField.findCage(x, 'a');
        int result = cage.getX();
        assertEquals(result, x);
    }

    @Test
    public void testIsAllObjectsDied_whenAllObjectsAlive_returnFalse() {
        PlayingField playingField = new ClassicPlayingField(new StandardGameObjectPosition());
        boolean result = playingField.isAllObjectsDied();
        assertFalse(result);
    }

    @Test
    public void testIsAllObjectsDied_whenNotAllObjectsAlive_returnFalse() {
        PlayingField playingField = new ClassicPlayingField(new StandardGameObjectPosition());
        List<GameObject> gameObjects = playingField.getGameObjects();
        GameObject gameObject = gameObjects.get(0);
        gameObject.setStatus(Status.KILLED);
        boolean result = playingField.isAllObjectsDied();
        assertFalse(result);
    }

    @Test
    public void testIsAllObjectsDied_whenAllObjectsDied_returnTrue() {
        PlayingField playingField = new ClassicPlayingField(new StandardGameObjectPosition());
        List<GameObject> gameObjects = playingField.getGameObjects();

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            gameObject.setStatus(Status.KILLED);
        }

        boolean result = playingField.isAllObjectsDied();
        assertTrue(result);
    }
}