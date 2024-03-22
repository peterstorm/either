package dk.oister.util;

import java.util.Objects;
import java.util.Optional;

public record Left<L, R>(L value) implements Either<L, R> {

    public Left {
        Objects.requireNonNull(value);
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public Optional<L> getLeft() {
        return Optional.of(value);
    }

    @Override
    public Optional<R> getRight() {
        return Optional.empty();
    }
    
}
