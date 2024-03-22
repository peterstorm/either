package dk.oister.util;

import java.util.Optional;
import java.util.function.Function;

public sealed interface Either<L, R> permits Left, Right {
    
    public static <L, R> Either<L, R> left(L value) {
        return new Left<L, R>(value);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Right<L, R>(value);
    }

    public static <L, R> Either<L, R> pure(R value) {
        return new Right<L,R>(value);
    }

    public abstract boolean isLeft();

    public default boolean isRight() {
        return !isLeft();
    }

    public abstract Optional<L> getLeft();

    public abstract Optional<R> getRight();

    public abstract <R2> Either<L, R2> map(
        Function<? super R, ? extends R2> mapper
    );

    public abstract <R2> Either<L, R2> flatMap(
        Function<? super R, ? extends Either<? extends L, ? extends R2>> flatMapper
    );

}

