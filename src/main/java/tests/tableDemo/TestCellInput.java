package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import utils.Platform;
import utils.Specification;

import java.awt.*;

public class TestCellInput {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification();
    private TableDemo demo;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").Int());

    @Before
    public void Setup() {
        demo = prepareDemo.prepare(setupFixture.init());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    private class TestData {
        final String doubleColumn = spec.get("tableDemo.numberColumn").String();
        final String doubleInput = spec.get("tableDemo.inputDouble").String();
        final String doubleExpected = spec.get("tableDemo.inputDoubleExpected").String();

        final int colorColumn = demo.getColumnIndex(spec.get("tableDemo.colorColumn").String());
        final String colorToChoose = spec.get("tableDemo.inputColor").String();
        final Color colorExpected = spec.get("tableDemo.inputColorExpected").Color();
    }

    /**
     * Go to Table Demo
     * Input a value greater then a max double
     * Check that value in the cell equals infinite
     */
    @Test
    public void TestInputDoubleInfinity() {
        TestData testData = new TestData();

        String value = demo.inputValueToCell(0, demo.getColumnIndex(testData.doubleColumn), testData.doubleInput);
        Assert.assertEquals(testData.doubleExpected, value);
    }

    /**
     * Go to Table Demo
     * Input a text, representing color
     * Check that cell's background is now of the expected color
     */
    @Test
    public void TestChooseColor() {
        TestData testData = new TestData();

        demo.inputValueToCell(0, testData.colorColumn, testData.colorToChoose);
        Color value = demo.getCellBackgroundColor(0, testData.colorColumn);
        Assert.assertEquals(testData.colorExpected, value);
    }

    /**
     * Go to Table Demo
     * Input a value greater then a max double
     * Sort column desc
     * Check that infinite value is at the top
     */
    @Test
    public void TestInputDoubleInfinityIsSorted() {
        TestData testData = new TestData();

        demo.inputValueToCell(0, demo.getColumnIndex(testData.doubleColumn), testData.doubleInput);
        demo.clickColumnHeader(testData.doubleColumn);
        demo.clickColumnHeader(testData.doubleColumn);
        String value = String.valueOf(demo.getValueFromCell(0, demo.getColumnIndex(testData.doubleColumn)));
        Assert.assertEquals(testData.doubleExpected, value);
    }
}
