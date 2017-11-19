package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Specification;

import java.awt.*;

public class TestSelection {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification("mac");
    private TableDemo demo;

    @Before
    public void Setup() {
        demo = prepareDemo.prepair(setupFixture.init());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    private class TestData {
        private String selectionModeSingle;
        private String selectionModeOneRange;
        private String selectionModeMultipleRanges;

        private int rowToStartSelect;
        private int columnToStartSelect;
        private int rowToEndSelect;
        private int columnToEndSelect;
        private String columnToSkip;
        private Color selectionColor;
        private int rowToSelectFirst;
        private int rowToSelectSecond;

        TestData invoke() {
            selectionModeSingle = spec.get("tableDemo.selectionModeSingle").String();
            selectionModeOneRange = spec.get("tableDemo.selectionModeOneRange").String();
            selectionModeMultipleRanges = spec.get("tableDemo.selectionModeMultipleRanges").String();

            rowToStartSelect = spec.get("tableDemo.rowToStartSelect").Int();
            columnToStartSelect = spec.get("tableDemo.columnToStartSelect").Int();
            rowToEndSelect = spec.get("tableDemo.rowToEndSelect").Int();
            columnToEndSelect = spec.get("tableDemo.columnToEndSelect").Int();
            columnToSkip = spec.get("tableDemo.columnToSkip").String();
            selectionColor = spec.get("tableDemo.selectionColor").Color();
            rowToSelectFirst = spec.get("tableDemo.rowToSelectFirst").Int();
            rowToSelectSecond = spec.get("tableDemo.rowToSelectSecond").Int();

            return this;
        }
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
        TestData testData = new TestData().invoke();
        demo.selectSelectionMode(testData.selectionModeSingle);
        rowSelectionDrag(testData);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        int columnToSkip = demo.getColumnIndex(testData.columnToSkip);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if (i != testData.rowToEndSelect || j == columnToSkip) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.selectSelectionMode(testData.selectionModeSingle);
        columnSelectionDrag(testData);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if (j != testData.columnToEndSelect) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.selectSelectionMode(testData.selectionModeSingle);
        rowAndColumnSelectionDrag(testData);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if (j != testData.columnToEndSelect || i != testData.rowToEndSelect) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        rowSelectionDrag(testData);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        int columnToSkip = demo.getColumnIndex(testData.columnToSkip);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if ((i != testData.rowToStartSelect && i != testData.rowToEndSelect) || j == columnToSkip) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        columnSelectionDrag(testData);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if (j != testData.columnToStartSelect && j != testData.columnToEndSelect) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        rowAndColumnSelectionDrag(testData);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if ((j != testData.columnToStartSelect && j != testData.columnToEndSelect)
                        || (i != testData.rowToStartSelect && i != testData.rowToEndSelect)) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.enableRowSelection();
        demo.enableColumnSelection();
        demo.selectSelectionMode(testData.selectionModeMultipleRanges);
        demo.selectRows(testData.rowToSelectFirst, testData.rowToSelectSecond);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if ((i != testData.rowToSelectFirst && i != testData.rowToSelectSecond) || j != 0) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.selectSelectionMode(testData.selectionModeOneRange);
        demo.enableRowSelection();
        demo.selectRows(testData.rowToSelectFirst, testData.rowToSelectSecond);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        int columnToSkip = demo.getColumnIndex(testData.columnToSkip);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if (i != testData.rowToSelectSecond || j == columnToSkip) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
        TestData testData = new TestData().invoke();
        demo.enableRowSelection();
        demo.selectSelectionMode(testData.selectionModeMultipleRanges);
        demo.selectRows(testData.rowToSelectFirst, testData.rowToSelectSecond);

        int rowCount = demo.getRowCount();
        int columnCount = demo.getColumnCount();
        int columnToSkip = demo.getColumnIndex(testData.columnToSkip);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Color actualColor = demo.getCellBackgroundColor(i, j);
                if ((i != testData.rowToSelectFirst && i != testData.rowToSelectSecond) || j == columnToSkip) {
                    Assert.assertNotEquals(testData.selectionColor, actualColor);
                } else {
                    Assert.assertEquals(testData.selectionColor, actualColor);
                }
            }
        }
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
}
