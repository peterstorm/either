package dk.oister.util;

@FunctionalInterface
public interface SupplierWithException<T> {
    T supply() throws Exception;
}

