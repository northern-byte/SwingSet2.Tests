package interfaces.fixtures;

import interfaces.pageObjects.View;

public interface PrepareLookFixture<T> {
    T prepair(View view, String lookAndFeelText);
}
