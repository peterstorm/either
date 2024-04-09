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

    @Override
    public <L2> Either<L2, R> mapLeft(Function<? super L, ? extends L2> mapper) {
        return new Left<>(mapper.apply(value));
    }

    @Override
    public <L2> Either<L2, R> flatMapLeft(Function<? super L, ? extends Either<? extends L2, ? extends R>> mapper) {
        @SuppressWarnings("unchecked")
        Either<L2, R> result = (Either<L2, R>) mapper.apply(value);
        return result;
    }

    @Override
    public <U> U fold(Function<? super L, ? extends U> leftMapper, Function<? super R, ? extends U> rightMapper) {
        return leftMapper.apply(value);
    }
    
}
