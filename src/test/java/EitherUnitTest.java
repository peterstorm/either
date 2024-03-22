import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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

}