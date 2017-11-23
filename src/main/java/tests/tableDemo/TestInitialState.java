package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.LocaleFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Platform;
import utils.ResourceManager;
import utils.Specification;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

@RunWith(value = Parameterized.class)
public class TestInitialState {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private final LocaleFixture localeFixture = new implementations.fixtures.LocaleFixture();
    private final Specification spec = new Specification();
    private TableDemo demo;
    private Locale locale;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").Int());

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Locale> data() {
        return Arrays.asList(Locale.JAPANESE);
    }

    @Before
    public void Setup() {
        localeFixture.SetLocale(locale);
        demo = prepareDemo.prepare(setupFixture.init());
    }

    public TestInitialState(Locale locale) {
        this.locale = locale;
    }

    @After
    public void Close() {
        setupFixture.dispose();
        localeFixture.resetDefault();
    }

    private class TestData {
        boolean reordering = spec.get("tableDemo.initialReordering").Boolean();
        boolean horizLines = spec.get("tableDemo.initialHorizLines").Boolean();
        boolean vertLines = spec.get("tableDemo.initialVertLines").Boolean();
        boolean rowSelection = spec.get("tableDemo.initialRowSelection").Boolean();
        boolean columnSelection = spec.get("tableDemo.initialColumnSelection").Boolean();
        int intercellSpacing = spec.get("tableDemo.initialIntercellSpacing").Int();
        int rowHeight = spec.get("tableDemo.initialRowHeight").Int();
        String selectionMode = ResourceManager.getResString("TableDemo.multiple_ranges");
        String autoresizeMode = ResourceManager.getResString("TableDemo.subsequent_columns");
    }

    /**
     * Go to Table Demo
     * Check that all controls are set as described in specification
     */
    @Test
    public void TestInitialState(){
        TestData testData = new TestData();
        boolean reordering = demo.isReorderingAllowed();
        boolean horizLines = demo.isHorizontalLinesControlEnabled();
        boolean vertLines = demo.isVerticalLinesControlEnabled();
        boolean rowSelection = demo.isRowSelectionEnabled();
        boolean columnSelection = demo.isColumnSelectionEnabled();
        int intercellSpacing = demo.getIntercellSpacingValue();
        int rowHeight = demo.getRowHeightValue();
        String selectionMode = demo.getSelectionMode();
        String autoresizeMode = demo.getAutoresizeMode();

        Assert.assertEquals(reordering,testData.reordering);
        Assert.assertEquals(horizLines, testData.horizLines);
        Assert.assertEquals(vertLines, testData.vertLines);
        Assert.assertEquals(rowSelection, testData.rowSelection);
        Assert.assertEquals(columnSelection, testData.columnSelection);
        Assert.assertEquals(intercellSpacing, testData.intercellSpacing);
        Assert.assertEquals(rowHeight, testData.rowHeight);
        Assert.assertEquals(selectionMode, testData.selectionMode);
        Assert.assertEquals(autoresizeMode, testData.autoresizeMode);
    }
}
