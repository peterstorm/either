package dk.oister;

import java.lang.Runtime.Version;
import java.util.function.Function;

import dk.oister.util.Either;
import dk.oister.util.Left;
import dk.oister.util.Right;

public class Main {
    public static void main(String[] args) {
        Either<String, String> right = Either.right("Hello World, from Right!");
        System.out.println(right);
        Version javaVersion = Runtime.version();
        System.out.println(javaVersion);
        testPatterMatching(right);
    }

    public static <L, R> void testPatterMatching(Either<L, R> either) {
        switch (either) {
            case Left<L, ?> value -> System.out.println(value);
            case Right<?, R> value -> System.out.println(value);
        }
    }
}