package dk.oister.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @SuppressWarnings("unchecked")
    public static <L, R> Either<L, R> fromOptional(Optional<R> optional, Supplier<L> errorSupplier) {
        return (Either<L, R>) optional
            .map(value -> Either.pure(value))
            .orElseGet(() -> Either.left(errorSupplier.get()));
    }

    public static <L, R> Either<L, R> fromTryCatch(SupplierWithException<R> supplier, Function<Throwable, L> errorMapper) {
        try {
            return Either.pure(supplier.supply());
        } catch (Throwable e) {
            return Either.left(errorMapper.apply(e));
        }
    }

    public abstract <R2> Either<L, R2> map(
        Function<? super R, ? extends R2> mapper
    );

    public abstract <R2> Either<L, R2> flatMap(
        Function<? super R, ? extends Either<? extends L, ? extends R2>> flatMapper
    );

    public abstract <L2> Either<L2, R> mapLeft(
        Function<? super L, ? extends L2> mapper
    );

    public abstract <L2> Either<L2, R> flatMapLeft(
        Function<? super L, ? extends Either<? extends L2, ? extends R>> mapper
    );

    public abstract <U> U fold(
        Function<? super L, ? extends U> leftMapper,
        Function<? super R, ? extends U> rightMapper
    );

}
