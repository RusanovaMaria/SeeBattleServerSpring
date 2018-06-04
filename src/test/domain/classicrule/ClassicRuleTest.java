package domain.classicrule;

import com.seebattleserver.domain.gameobject.Kind;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;
import junit.framework.TestCase;

public class ClassicRuleTest extends TestCase {

    public void testCountQuantityOfObjects_whenParameterIsSingleDeckShip_return4() {
        Rule rule = new ClassicRule();
        int result = rule.countQuantityOfObjects(Kind.SINGLE_DECKED);
        assertEquals(4, result);
    }

    public void testCountQuantityOfObjects_whenParameterIsTwoDeckShip_return3() {
        Rule rule = new ClassicRule();
        int result = rule.countQuantityOfObjects(Kind.DOUBLE_DECKED);
        assertEquals(3, result);
    }

    public void testCountQuantityOfObjects_whenParameterIsThreeDeckShip_return2() {
        Rule rule = new ClassicRule();
        int result = rule.countQuantityOfObjects(Kind.THREE_DECKED);
        assertEquals(2, result);
    }

    public void testCountQuantityOfObjects_whenParameterIsFourDeckShip_return1() {
        Rule rule = new ClassicRule();
        int result = rule.countQuantityOfObjects(Kind.FOUR_DECKED);
        assertEquals(1, result);
    }
}