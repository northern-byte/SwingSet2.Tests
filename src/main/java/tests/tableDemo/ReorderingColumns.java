package tests.tableDemo;

import implementations.fixtures.BasicAppletFixture;
import interfaces.fixtures.Fixture;
import interfaces.pageObjects.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReorderingColumns {

    Fixture fixture = new BasicAppletFixture();
    View view;

    @Before
    public void Setup(){
        view = fixture.Init();
    }

    @After
    public void Close(){
        fixture.Dispose();
    }

    @Test //Prototype
    public void DragColumnsLeft(){
        view.getDemoSwitcher().goToTableDemo();
        view.getTableDemo().clickTable();
    }
}
