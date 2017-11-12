package implementations.fixtures;

import interfaces.fixtures.PrepareFixture;
import interfaces.pageObjects.View;

public class TableDemoPrepareFixture implements PrepareFixture{
    @Override
    public void prepair(View view) {
        view.getDemoSwitcher().goToTableDemo();
    }
}
