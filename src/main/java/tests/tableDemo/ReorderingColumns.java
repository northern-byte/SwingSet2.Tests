package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReorderingColumns {

    SetupFixture setupFixture = new AppletSetupFixture();
    PrepareFixture prepairDemo = new TableDemoPrepareFixture();
    View view;

    @Before
    public void Setup(){
        view = setupFixture.init();
        prepairDemo.prepair(view);
    }

    @After
    public void Close(){
        setupFixture.dispose();
    }

    @Test //Prototype
    public void DragColumnsLeft(){
        view.getTableDemo().clickTable();
    }
}
