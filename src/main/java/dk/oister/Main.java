package dk.oister;

import java.lang.Runtime.Version;
import java.util.Optional;
import java.util.function.Function;

import dk.oister.util.Either;
import dk.oister.util.Left;
import dk.oister.util.Right;

public class Main {

    interface Error {};
    record SomeError() implements Error {};
    record SomeOtherError(String errorMessage) implements Error {};

    public static void main(String[] args) {
        Either<String, String> right = Either
          .right("Hello World, from Right!");

        Either<Error, Integer> fromTryCatch = Either
          .fromTryCatch(
              () -> (Integer) Integer.parseInt("Hello"),
              e -> new SomeOtherError(e.getMessage())
          );

        Either<Error, Integer> fromTryCatchRight = Either
          .fromTryCatch(
              () -> (Integer) Integer.parseInt("23"),
              e -> new SomeOtherError(e.getMessage())
          );

        System.out.println("Testing fromTryCatch");
        System.out.println(fromTryCatch);
        System.out.println(fromTryCatchRight);
        System.out.println(right);
        Version javaVersion = Runtime.version();
        System.out.println(javaVersion);

        testPatterMatching(right);


        System.out.println("I'm an Optional.empty");
        Optional<String> optional = Optional.empty();
        Either<Error, String> either = Either
          .fromOptional(optional, SomeError::new);
        System.err.println(either);
    }

    public static <L, R> void testPatterMatching(Either<L, R> either) {
        switch (either) {
            case Left<L, ?> value -> System.out.println(value);
            case Right<?, R> value -> System.out.println(value);
        }

    }
    static Function<String, Either<String, Integer>> safeParseInt = s -> {
        try {
            Integer integer = Integer.parseInt(s);
            return Either.right(integer);
        } catch (Exception e) {
            return Either.left("Cannot parse to integer");
        }
    };

}
