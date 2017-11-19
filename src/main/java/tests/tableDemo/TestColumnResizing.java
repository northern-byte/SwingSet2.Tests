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

public class TestColumnResizing {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification();
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
        private String resizeModeOff;
        private String resizeModeColumnBoundaries;
        private String resizeModeSubsequentColumns;
        private String resizeModeLastColumn;
        private String resizeModeAllColumns;

        private String columnToResizeName;
        private String columnWhereToResizeName;

        TestData invoke() {
            resizeModeOff = spec.get("tableDemo.resizeOff").String();
            resizeModeColumnBoundaries = spec.get("tableDemo.resizeColumnBoundaries").String();
            resizeModeSubsequentColumns = spec.get("tableDemo.resizeSubsequentColumns").String();
            resizeModeLastColumn = spec.get("tableDemo.resizeLastColumn").String();
            resizeModeAllColumns = spec.get("tableDemo.resizeAllColumns").String();

            columnToResizeName = spec.get("tableDemo.columnToResize").String();
            columnWhereToResizeName = spec.get("tableDemo.columnWhereToResize").String();
            return this;
        }
    }

    /**
     * Go to Table Demo
     * Select autoresize mode 'Off'
     * Get original widths of columns
     * Resize desired column
     * Check that only resized column changed width
     */
    @Test
    public void TestAutoResizeOff(){
        TestData testData = new TestData().invoke();

        demo.selectResizeMode(testData.resizeModeOff);
        int[] originalWidths = getColumnWidths();

        int resizeColumnIndex = demo.getColumnIndex(testData.columnToResizeName);

        resizeColumn(testData.columnToResizeName, testData.columnWhereToResizeName);

        for(int i = 0; i < originalWidths.length; i++){
            int currentWidth = demo.getColumnWidth(i);
            if(i != resizeColumnIndex){
                Assert.assertEquals(originalWidths[i],currentWidth);
            } else {
                Assert.assertTrue(currentWidth > originalWidths[i]);
            }
        }
    }

    /**
     * Go to Table Demo
     * Select autoresize mode 'Column boundaries'
     * Get original widths of columns
     * Resize desired column
     * Check that two columns were resized: original got wider and the one next to it got narrower
     */
    @Test
    public void TestAutoResizeColumnBoundaries(){
        TestData testData = new TestData().invoke();

        demo.selectResizeMode(testData.resizeModeColumnBoundaries);
        int[] originalWidths = getColumnWidths();

        int resizeColumnIndex = demo.getColumnIndex(testData.columnToResizeName);

        resizeColumn(testData.columnToResizeName, testData.columnWhereToResizeName);

        for(int i = 0; i < originalWidths.length; i++){
            int currentWidth = demo.getColumnWidth(i);
            if(i == resizeColumnIndex + 1){
                Assert.assertTrue(currentWidth < originalWidths[i]);
            } else if(i != resizeColumnIndex) {
                Assert.assertEquals(originalWidths[i], currentWidth);
            } else {
                Assert.assertTrue(currentWidth > originalWidths[i]);
            }
        }
    }

    /**
     * Go to Table Demo
     * Select autoresize mode 'Subsequent columns'
     * Get original widths of columns
     * Resize desired column
     * Check that originally resized column got wider and all subsequent columns got narrower
     */
    @Test
    public void TestAutoResizeSubsequentColumns(){
        TestData testData = new TestData().invoke();

        demo.selectResizeMode(testData.resizeModeSubsequentColumns);
        int[] originalWidths = getColumnWidths();

        int resizeColumnIndex = demo.getColumnIndex(testData.columnToResizeName);

        resizeColumn(testData.columnToResizeName, testData.columnWhereToResizeName);

        for(int i = 0; i < originalWidths.length; i++){
            int currentWidth = demo.getColumnWidth(i);
            if(i > resizeColumnIndex){
                Assert.assertTrue(currentWidth < originalWidths[i]);
            } else if(i == resizeColumnIndex) {
               Assert.assertTrue(currentWidth > originalWidths[i]);
            } else {
                Assert.assertEquals(originalWidths[i], currentWidth);
            }
        }
    }

    /**
     * Go to Table Demo
     * Select autoresize mode 'Last column'
     * Get original widths of columns
     * Resize desired column
     * Check that originally resized column got wider and only last column got narrower
     */
    @Test
    public void TestAutoResizeLastColumn(){
        TestData testData = new TestData().invoke();

        demo.selectResizeMode(testData.resizeModeLastColumn);
        int[] originalWidths = getColumnWidths();

        int resizeColumnIndex = demo.getColumnIndex(testData.columnToResizeName);

        resizeColumn(testData.columnToResizeName, testData.columnWhereToResizeName);

        for(int i = 0; i < originalWidths.length; i++){
            int currentWidth = demo.getColumnWidth(i);
            if(i != resizeColumnIndex && i != originalWidths.length - 1){
                Assert.assertEquals(originalWidths[i], currentWidth);
            } else if(i == resizeColumnIndex){
                Assert.assertTrue(currentWidth > originalWidths[i]);
            } else {
                Assert.assertTrue(currentWidth < originalWidths[i]);
            }
        }
    }

    /**
     * Go to Table Demo
     * Select autoresize mode 'All columns'
     * Get original widths of columns
     * Resize desired column
     * Check that originally resized column got wider and all other columns got narrower
     */
    @Test
    public void TestAutoResizeAllColumns(){
        TestData testData = new TestData().invoke();

        demo.selectResizeMode(testData.resizeModeAllColumns);
        int[] originalWidths = getColumnWidths();

        int resizeColumnIndex = demo.getColumnIndex(testData.columnToResizeName);

        resizeColumn(testData.columnToResizeName, testData.columnWhereToResizeName);

        for(int i = 0; i < originalWidths.length; i++){
            int currentWidth = demo.getColumnWidth(i);
            if(i != resizeColumnIndex){
                Assert.assertTrue(currentWidth < originalWidths[i]);
            } else {
                Assert.assertTrue(currentWidth > originalWidths[i]);
            }
        }
    }

    /**
     * Go to Table Demo
     * Select autoresize mode 'Column boundaries'
     * Resize desired column
     * Get original widths of columns
     * Select autoresize mode 'All columns'
     * Resize desired column to it's original point
     * Check that autoresizing behaviour changed from previous one
     */
    @Test
    public void TestResizeTwiceWithDifferentModes(){
        TestData testData = new TestData().invoke();

        demo.selectResizeMode(testData.resizeModeColumnBoundaries);

        Point resizePoint = demo.getColumnHeaderRightBorderPoint(testData.columnToResizeName);
        Point resizeToPoint = demo.getColumnHeaderRightBorderPoint(testData.columnWhereToResizeName);
        demo.drapAndDrop(resizePoint, resizeToPoint);
        int[] originalWidths = getColumnWidths();

        demo.selectResizeMode(testData.resizeModeAllColumns);

        Point newResizePoint = demo.getColumnHeaderRightBorderPoint(testData.columnToResizeName);
        demo.drapAndDrop(newResizePoint, resizePoint);

        int[] newWidths = getColumnWidths();

        int resizeColumnIndex = demo.getColumnIndex(testData.columnToResizeName);

        for(int i = 0; i< newWidths.length; i++){
            if(i != resizeColumnIndex){
                Assert.assertTrue(newWidths[i] > originalWidths[i]);
            } else {
                Assert.assertTrue(newWidths[i] < originalWidths[i]);
            }
        }
    }

    private void resizeColumn(String columnToResizeName, String columnWhereToResizeName) {
        Point resizePoint = demo.getColumnHeaderRightBorderPoint(columnToResizeName);
        Point resizeToPoint = demo.getColumnHeaderRightBorderPoint(columnWhereToResizeName);

        demo.drapAndDrop(resizePoint, resizeToPoint);
    }

    private int[] getColumnWidths() {
        int columnCount = demo.getColumnCount();
        int[] originalWidths = new int[columnCount];

        for(int i = 0; i < columnCount; i++){
            originalWidths[i] = demo.getColumnWidth(i);
        }
        return originalWidths;
    }


}
