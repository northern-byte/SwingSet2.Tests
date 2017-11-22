package interfaces.fixtures;

import interfaces.pageObjects.View;

public interface PrepareFixture<T> {
    T prepare(View view);
}
