package implementations.fixtures;

import interfaces.fixtures.PrepareFixture;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;

public class TableDemoPrepareFixture implements PrepareFixture<TableDemo>{
    @Override
    public TableDemo prepair(View view) {
        return view.getDemoSwitcher().goToTableDemo();
    }
}
