package dk.oister.util;

import java.util.Objects;
import java.util.Optional;

public record Right<L, R>(R value) implements Either<L, R> {

    public Right {
        Objects.requireNonNull(value);
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public Optional<L> getLeft() {
        return Optional.empty();
    }

    @Override
    public Optional<R> getRight() {
        return Optional.of(value);
    }
    
}
