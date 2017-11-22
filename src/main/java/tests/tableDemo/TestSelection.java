package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareLookFixture;
import interfaces.fixtures.PrepareLookFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import org.pmw.tinylog.Logger;
import utils.Platform;
import utils.Specification;

import java.awt.*;
import java.util.function.Consumer;

public class TestSelection {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareLookFixture<TableDemo> prepareDemo = new TableDemoPrepareLookFixture();
    private final Specification spec = new Specification("java");
    private TableDemo demo;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").Int());

    @Before
    public void Setup() {
        demo = prepareDemo.prepair(setupFixture.init(), spec.get("menu.lookAndFeel").String());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    private class TestData {
        final String selectionModeSingle = spec.get("tableDemo.selectionModeSingle").String();
        final String selectionModeOneRange = spec.get("tableDemo.selectionModeOneRange").String();
        final String selectionModeMultipleRanges = spec.get("tableDemo.selectionModeMultipleRanges").String();

        final int rowToStartSelect = spec.get("tableDemo.rowToStartSelect").Int();
        final int columnToStartSelect = spec.get("tableDemo.columnToStartSelect").Int();
        final int rowToEndSelect = spec.get("tableDemo.rowToEndSelect").Int();
        final int columnToEndSelect = spec.get("tableDemo.columnToEndSelect").Int();
        final String columnToSkip = spec.get("tableDemo.columnToSkip").String();
        final Color selectionColor = spec.get("tableDemo.selectionColor").Color();
        final int rowToSelectFirst = spec.get("tableDemo.rowToSelectFirst").Int();
        final int rowToSelectSecond = spec.get("tableDemo.rowToSelectSecond").Int();
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "Single"
     * Enable row selection
     * Disable column selection
     * Drag from one cell to another(in another row)
     * Check that initially selected row is now not selected
     * Check that the second row is selected
     */
    @Test
    public void TestRowSelectionModeSingle() {
        TestData testData = new TestData();
        demo.selectSelectionMode(testData.selectionModeSingle);
        rowSelectionDrag(testData);

        checkCells(testData, cellInfo -> {
            if (cellInfo.row != testData.rowToEndSelect) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "Single"
     * Enable column selection
     * Disable row selection
     * Drag from one cell to another(in another column)
     * Check that initially selected column is now not selected
     * Check that the second column is selected
     */
    @Test
    public void TestColumnSelectionModeSingle() {
        TestData testData = new TestData();
        demo.selectSelectionMode(testData.selectionModeSingle);
        columnSelectionDrag(testData);

        checkCells(testData, cellInfo -> {
            if (cellInfo.column != testData.columnToEndSelect) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "Single"
     * Enable row selection
     * Enable column selection
     * Drag from one cell to another(in another column and row)
     * Check that initially selected column and row are now not selected
     * Check that only one final cell in the second column is selected
     */
    @Test
    public void TestRowAndColumnSelectionModeSingle() {
        TestData testData = new TestData();
        demo.selectSelectionMode(testData.selectionModeSingle);
        rowAndColumnSelectionDrag(testData);

        checkCells(testData, cellInfo -> {
            if (cellInfo.column != testData.columnToEndSelect || cellInfo.row != testData.rowToEndSelect) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "One range"
     * Enable row selection
     * Disable column selection
     * Drag from one cell to another(in another row)
     * Check that both rows are now selected
     */
    @Test
    public void TestRowSelectionModeOneRange() {
        TestData testData = new TestData();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        rowSelectionDrag(testData);

        checkCells(testData, cellInfo -> {
            if ((cellInfo.row != testData.rowToStartSelect && cellInfo.row != testData.rowToEndSelect)) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "One range"
     * Enable column selection
     * Disable row selection
     * Drag from one cell to another(in another column)
     * Check that both columns are now selected
     */
    @Test
    public void TestColumnSelectionModeOneRange() {
        TestData testData = new TestData();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        columnSelectionDrag(testData);

        checkCells(testData, cellInfo -> {
            if (cellInfo.column != testData.columnToStartSelect && cellInfo.column != testData.columnToEndSelect) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "One range"
     * Enable row selection
     * Enable column selection
     * Drag from one cell to another(in another column and row)
     * Check that only cells that are result of intersection is selected
     */
    @Test
    public void TestRowAndColumnSelectionModeOneRange() {
        TestData testData = new TestData();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        rowAndColumnSelectionDrag(testData);

        checkCells(testData, cellInfo -> {
            if ((cellInfo.column != testData.columnToStartSelect && cellInfo.column != testData.columnToEndSelect)
                    || (cellInfo.row != testData.rowToStartSelect && cellInfo.row != testData.rowToEndSelect)) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "Multiple ranges"
     * Enable row selection
     * Enable column selection
     * Try to select two rows that are separate from each other
     * Check that only the cells in rows we chose and first columns are selected
     */
    @Test
    public void TestRowAndColumnSelectionModeMultipleRanges() {
        TestData testData = new TestData();
        demo.enableRowSelection();
        demo.enableColumnSelection();
        demo.selectSelectionMode(testData.selectionModeMultipleRanges);
        demo.selectRows(testData.rowToSelectFirst, testData.rowToSelectSecond);

        checkCells(testData, cellInfo -> {
            if ((cellInfo.row != testData.rowToSelectFirst && cellInfo.row != testData.rowToSelectSecond)
                    || cellInfo.column != 0) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "One range"
     * Enable row selection
     * Try to select two rows that are separate from each other
     * Check that only the second one is selected
     */
    @Test
    public void TestOneRangeSelection() {
        TestData testData = new TestData();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        demo.enableRowSelection();
        demo.selectRows(testData.rowToSelectFirst, testData.rowToSelectSecond);

        checkCells(testData, cellInfo -> {
            if (cellInfo.row != testData.rowToSelectSecond) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    /**
     * Go to Table Demo
     * Select SelectionMode "Multiple ranges"
     * Enable row selection
     * Try to select two rows that are separate from each other
     * Check that both rows are selected
     */
    @Test
    public void TestMultipleRangesSelection() {
        TestData testData = new TestData();
        demo.enableRowSelection();
        demo.selectSelectionMode(testData.selectionModeMultipleRanges);
        demo.selectRows(testData.rowToSelectFirst, testData.rowToSelectSecond);

        checkCells(testData, cellInfo -> {
            if ((cellInfo.row != testData.rowToSelectFirst && cellInfo.row != testData.rowToSelectSecond)) {
                Assert.assertNotEquals(testData.selectionColor, cellInfo.actualColor);
            } else {
                Assert.assertEquals(testData.selectionColor, cellInfo.actualColor);
            }
        });
    }

    private void rowSelectionDrag(TestData testData) {
        demo.enableRowSelection();
        demo.disableColumnSelection();

        demo.drapAndDrop(demo.getCellPoint(testData.rowToStartSelect, testData.columnToStartSelect),
                demo.getCellPoint(testData.rowToEndSelect, testData.columnToEndSelect));
    }

    private void columnSelectionDrag(TestData testData) {
        demo.enableColumnSelection();
        demo.disableRowSelection();

        demo.drapAndDrop(demo.getCellPoint(testData.rowToStartSelect, testData.columnToStartSelect),
                demo.getCellPoint(testData.rowToEndSelect, testData.columnToEndSelect));
    }

    private void rowAndColumnSelectionDrag(TestData testData) {
        demo.enableRowSelection();
        demo.enableColumnSelection();

        demo.drapAndDrop(demo.getCellPoint(testData.rowToStartSelect, testData.columnToStartSelect),
                demo.getCellPoint(testData.rowToEndSelect, testData.columnToEndSelect));
    }

    private void checkCells(TestData testData, Consumer<CellInfo> condition) {
        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        int columnToSkip = demo.getColumnIndex(testData.columnToSkip);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (j == columnToSkip) {
                    continue;
                }
                Logger.info(String.format("Cell at (%d, %d)", i, j));
                Color actualColor = demo.getCellBackgroundColor(i, j);
                CellInfo cellInfo = new CellInfo(i, j, actualColor);
                condition.accept(cellInfo);
            }
        }
    }

    private class CellInfo {
        private final int row;
        private final int column;
        private final Color actualColor;

        public CellInfo(int row, int column, Color actualColor) {
            this.row = row;
            this.column = column;
            this.actualColor = actualColor;
        }
    }
}
