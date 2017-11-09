package interfaces.fixtures;

public interface Fixture<T> {
    T Init();
    void Dispose();
}
