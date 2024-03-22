package dk.oister;

import java.lang.Runtime.Version;

import dk.oister.util.Either;

public class Main {
    public static void main(String[] args) {
        Either<?, String> right = Either.right("Hello World, from Right!");
        System.out.println(right);
        Version javaVersion = Runtime.version();
        System.out.println(javaVersion);
        System.out.println(Either.left(null));
    }
}