package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import implementations.wrappers.TestImage;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Platform;
import utils.Specification;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class TestSorting {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private final static Specification spec = new Specification();
    private TableDemo demo;

    private String columnName;
    private Object expectedAsc;
    private Object expectedDesc;
    private Class<?> contentType;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").asInt());

    @Before
    public void Setup() {
        demo = prepareDemo.prepare(setupFixture.init());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    private static class TestData {
        private static String textColumn = spec.get("tableDemo.textColumn").asString();
        private static String firstText = spec.get("tableDemo.firstText").asString();
        private static String lastText = spec.get("tableDemo.lastText").asString();

        private static String numberColumn = spec.get("tableDemo.numberColumn").asString();
        private static Double firstNumber = spec.get("tableDemo.firstNumber").asDouble();
        private static Double lastNumber = spec.get("tableDemo.lastNumber").asDouble();

        private static String colorColumn = spec.get("tableDemo.colorColumn").asString();
        private static Color firstColor = spec.get("tableDemo.firstColor").asColor();
        private static Color lastColor = spec.get("tableDemo.lastColor").asColor();

        private static String imageColumn = spec.get("tableDemo.imageColumn").asString();
        private static TestImage firstImage = spec.get("tableDemo.firstImage").asTestImage();
        private static TestImage lastImage = spec.get("tableDemo.lastImage").asTestImage();
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {TestData.imageColumn, TestData.firstImage, TestData.lastImage, TestImage.class},
                {TestData.textColumn, TestData.firstText, TestData.lastText, String.class},
                {TestData.numberColumn, TestData.firstNumber, TestData.lastNumber, Double.class},
                {TestData.colorColumn, TestData.firstColor, TestData.lastColor, Color.class}
        });
    }

    public TestSorting(String columnName, Object expectedAsc, Object expectedDesc, Class<?> contentType) {
        this.contentType = contentType;
        this.columnName = columnName;
        this.expectedAsc = expectedAsc;
        this.expectedDesc = expectedDesc;
    }

    /**
     * Get column, expected result and result type from parameters
     * Go to Table Demo
     * Click on column we want to sort, to sort it in asc order
     * Check that actual content of first cell in that column corresponds to expected
     */
    @Test
    public void TestSortingAsc() {
        demo.clickColumnHeader(columnName);
        int columnIndex = demo.getColumnIndex(columnName);
        Object cast = getCast(demo.getValueFromCell(0, columnIndex), contentType);
        Assert.assertNotNull(cast);
        Assert.assertEquals((expectedAsc), cast);
    }

    /**
     * Get column, expected result and result type from parameters
     * Go to Table Demo
     * Click on column we want to sort twice, to sort it in desc order
     * Check that actual content of first cell in that column corresponds to expected
     */
    @Test
    public void TestSortingDesc() {
        demo.clickColumnHeader(columnName);
        demo.clickColumnHeader(columnName);
        int columnIndex = demo.getColumnIndex(columnName);
        Object cast = getCast(demo.getValueFromCell(0, columnIndex), contentType);
        Assert.assertNotNull(cast);
        Assert.assertEquals((expectedDesc), cast);
    }

    private Object getCast(Object object, Class<?> type) {
        if (type.isAssignableFrom(object.getClass())) {
            return object;
        }
        try {
            return type.getDeclaredConstructor(object.getClass()).newInstance(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
