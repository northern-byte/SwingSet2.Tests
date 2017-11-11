package implementations.wrappers;

import java.util.function.Supplier;

public class Lazy<T> {
    private Supplier<T> action;

    public Lazy(Supplier<T> action) {
        this.action = action;
    }

    public synchronized T get() {
        return action.get();
    }
}