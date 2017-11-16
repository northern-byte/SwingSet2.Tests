package interfaces.fixtures;

import interfaces.pageObjects.View;

public interface PrepareFixture<T> {
    T prepair(View view);
}
