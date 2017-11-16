package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Specification;

public class TestReorderingColumns {

    SetupFixture setupFixture = new AppletSetupFixture();
    PrepareFixture<TableDemo> prepairDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification();
    TableDemo demo;

    @Before
    public void Setup(){
        demo = prepairDemo.prepair(setupFixture.init());
    }

    @After
    public void Close(){
        setupFixture.dispose();
    }
    
    @Test //Prototype
    public void Prototype(){
        demo.clickTable();
    }
}
