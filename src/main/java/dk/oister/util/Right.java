package dk.oister.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public <R2> Either<L, R2> map(Function<? super R, ? extends R2> mapper) {
        return new Right<L, R2>(mapper.apply(value));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R2> Either<L, R2> flatMap(Function<? super R, ? extends Either<? extends L, ? extends R2>> flatMapper) {
        return (Either<L, R2>) flatMapper.apply(value);
    }
    
}
