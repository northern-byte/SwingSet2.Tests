package interfaces.fixtures;

import interfaces.pageObjects.View;

public interface PrepareLookFixture<T> {
    T prepare(View view, String lookAndFeelText);
}
