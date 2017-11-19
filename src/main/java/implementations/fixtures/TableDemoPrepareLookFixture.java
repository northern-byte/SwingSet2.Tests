package implementations.fixtures;

import interfaces.fixtures.PrepareLookFixture;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;

public class TableDemoPrepareLookFixture implements PrepareLookFixture<TableDemo> {
    @Override
    public TableDemo prepair(View view, String lookAndFeelText) {
        view.getMenu().selectLookAndFeel(lookAndFeelText);
        return view.getDemoSwitcher().goToTableDemo();
    }
}
