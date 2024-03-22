import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.oister.util.Either;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

public class EitherPropertyTest {

    record Value(String value1, String value2) {}

    @Property
    void rightEqualityProperty(@ForAll String value) {
        Either<?, String> firstRight = Either.right(value);
        Either<?, String> secondRight = Either.right(value);
        assertEquals(firstRight, secondRight);
    }

    @Property
    void leftEqualityProperty(@ForAll String value) {
        Either<String, ?> firstLeft = Either.left(value);
        Either<String, ?> secondLeft = Either.left(value);
        assertEquals(firstLeft, secondLeft);
    }

    @Provide
    Arbitrary<Value> generateDifferentStrings() {
        Arbitrary<String> strings = Arbitraries.strings();
        return Combinators
            .combine(strings, strings)
            .filter((first, second) -> !first.equals(second))
            .as((s1, s2) -> new Value(s1, s2));
    }

    @Property
    void rightInequlityProperty(@ForAll("generateDifferentStrings") Value value) {
        Either<?, String> firstRight = Either.right(value.value1);
        Either<?, String> secondRight = Either.right(value.value2);
        assertNotEquals(firstRight, secondRight);
    }

    @Property
    void leftInequlityProperty(@ForAll("generateDifferentStrings") Value value) {
        Either<String, ?> firstLeft = Either.left(value.value1);
        Either<String, ?> secondLeft = Either.left(value.value2);
        assertNotEquals(firstLeft, secondLeft);
    }

    @Property
    void toStringProperty(@ForAll String value) {
        assertEquals(("Right[value=" + value + "]"), Either.right(value).toString());
        assertEquals(("Left[value=" + value + "]"), Either.left(value).toString());
    }

}
