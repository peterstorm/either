package dk.oister.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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

    @SuppressWarnings("unchecked")
    @Override
    public <R2> Either<L, R2> map(Function<? super R, ? extends R2> mapper) {
        return (Either<L, R2>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R2> Either<L, R2> flatMap(Function<? super R, ? extends Either<? extends L, ? extends R2>> flatMapper) {
        return (Either<L, R2>) this;
    }
    
}
