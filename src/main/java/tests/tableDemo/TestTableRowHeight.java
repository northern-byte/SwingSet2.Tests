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

public class TestTableRowHeight {
    private SetupFixture setupFixture = new AppletSetupFixture();
    private PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
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

    /**
     * Go to the Table Demo
     * Change table's row height to maximum via controller
     * Check that actual maximum values of row height correspond to those from specification
     */
    @Test
    public void TestRowHeightChangesToMax() {
        demo.setRowHeightToMax();
        int newHeight = demo.getTableRowHeight();

        int maxHeight = spec.get("tableDemo.maxRowHeight").Int();

        Assert.assertTrue(newHeight == maxHeight);
    }

    /**
     * Go to the Table Demo
     * Change table's row height to minimum via controller
     * Check that actual minimum values of row height correspond to those from specification
     */
    @Test
    public void TestRowHeightChangesToMin() {
        demo.setRowHeightToMin();
        int newHeight = demo.getTableRowHeight();

        int minHeight = spec.get("tableDemo.minRowHeight").Int();

        Assert.assertTrue(newHeight == minHeight);
    }

    /**
     * Go to the Table Demo
     * Change table's inter-cell spacing to maximum via controller
     * Calculate table's to controller's values ration
     * Get minimum value from controller
     * Decrease row height using controller, until we hit minimum value
     * At each value, check that table's row height changed in a linear manner in comparison to control
     */
    @Test
    public void TestRowHeightScalesLinearly() {
        int position = demo.setRowHeightToMax();
        int maxHeight = demo.getTableRowHeight();
        double heightRatio = maxHeight / position;

        int min = demo.getRowHeightMin();

        position -= 5;
        while (position > min) {
            demo.setRowHeightTo(position);
            double height = demo.getTableRowHeight();
            Assert.assertTrue(height == position * heightRatio);
            position -= 5;
        }
    }
}
