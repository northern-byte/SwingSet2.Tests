package interfaces.fixtures;

import interfaces.pageObjects.View;

public interface SetupFixture {
    View init();
    void dispose();
}
