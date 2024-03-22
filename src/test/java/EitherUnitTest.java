import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dk.oister.util.Either;

public class EitherUnitTest {

    @Test
    @DisplayName("Ensure that construction an Either with null, throws")
    void requireNonNull() {
        assertThrows(NullPointerException.class, () -> Either.left(null));
        assertThrows(NullPointerException.class, () -> Either.right(null));
    }

    @Test
    @DisplayName("Ensure enqualities and inequalities of Left, Right and their values")
    void testEquals() {

        assertEquals(Either.right("1"), Either.right("1"));
        assertEquals(Either.left("2"), Either.left("2"));

        assertEquals(Either.<Object, Object>right("1"), Either.right("1"));
        assertEquals(Either.<Object, Object>left("1"), Either.left("1"));

        assertNotEquals(Either.right("1"), Either.right("2"));
        assertNotEquals(Either.left("1"), Either.left("2"));

        assertNotEquals(Either.<Object, Object>right(1), Either.right("1"));
        assertNotEquals(Either.<Object, Object>left(1), Either.left("1"));
    }

    @Test
    @DisplayName("Should output the correct string")
    void testToString () {
        assertEquals("Right[value=1]", Either.right("1").toString());
        assertEquals("Left[value=1]", Either.left("1").toString());
    }

    @Test
    @DisplayName("Ensure semantics of methods on Left")
    void testGetLeft() {
        Either<String, ?> either = Either.left("1");
        assertTrue(either.isLeft());
        assertFalse(either.isRight());
        assertEquals(Optional.of("1"), either.getLeft());
        assertEquals(Optional.empty(), either.getRight());
    }

    @Test
    @DisplayName("Ensure semantics of methods on Right")
    void testGetRight() {
        Either<?, String> either = Either.right("1");
        assertTrue(either.isRight());
        assertFalse(either.isLeft());
        assertEquals(Optional.of("1"), either.getRight());
        assertEquals(Optional.empty(), either.getLeft());
    }

    @Test
    @DisplayName("Ensures map operations are lawful")
    void testMap() {
        Either<String, ?> left = Either.left("1");
        Either<?, String> right = Either.right("1");
        assertSame(left, left.map(s -> "map does nothing on Left"));
        // identity law
        assertEquals(right, right.map(Function.identity()));
        // preserves function composition
        Function<String, Integer> parseInt = s -> Integer.parseInt(s);
        Function<Integer, String> toString = t -> t.toString();
        Function<String, String> composed = toString.compose(parseInt);
        Either<?, String> composedRight = right.map(parseInt).map(toString);
        assertEquals(composedRight, right.map(composed));
    }

    @Test
    @DisplayName("Ensure flatMap operations are lawful")
    void testFlatMap() {
        Either<String, Integer> left = Either.left("1");
        Either<String, String> right = Either.right("1");
        // Left is left alone
        assertSame(left, left.flatMap(s -> Either.left("flatMap does nothing on Left")));
        Either<String, String> leftResult = safeParseInt
            .apply("awefawfeawf")
            .flatMap(i -> Either.left("flatMap does nothing on Left"));
        assertEquals(Either.left("Cannot parse to integer"), leftResult);
        // Right goes through
        Either<String, String> roundTrip = right
            .flatMap(safeParseInt)
            .flatMap(toString);
        assertEquals(right, roundTrip);
        Either<String, String> rightResult = safeParseInt
            .apply("1")
            .flatMap(toString);
        assertEquals(right, rightResult);
        // left identity
        Either<?, Integer> pureAndFlatMap = Either.pure("1").flatMap(safeParseInt);
        Either<?, Integer> justFunctionCall = safeParseInt.apply("1");
        assertEquals(pureAndFlatMap, justFunctionCall);
        // right identity
        Either<?, String> flatMapAndPure = Either.pure("1").flatMap(s -> Either.pure(s));
        Either<?, String> justContext = Either.pure("1");
        assertEquals(flatMapAndPure, justContext);
        // associativity
        Either<?, String> bracketsToTheLeft = (Either.pure("1").flatMap(safeParseInt)).flatMap(toString);
        Either<?, String> bracketsToTheRight = Either.pure("1").flatMap(s -> (safeParseInt.apply(s).flatMap(toString)));
        assertEquals(bracketsToTheLeft, bracketsToTheRight);

    }

    static Function<String, Either<String, Integer>> safeParseInt = (s) -> {
        try {
            Integer integer = Integer.parseInt(s);
            return Either.right(integer);
        } catch (Exception e) {
            return Either.left("Cannot parse to integer");
        }
    };

    static Function<Integer, Either<String, String>> toString = (i) -> Either.pure(i.toString());


}