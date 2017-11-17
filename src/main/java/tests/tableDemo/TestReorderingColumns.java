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

public class TestReorderingColumns {

    SetupFixture setupFixture = new AppletSetupFixture();
    PrepareFixture<TableDemo> prepairDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification();
    TableDemo demo;

    @Before
    public void Setup() {
        demo = prepairDemo.prepair(setupFixture.init());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Get index of the column we want to drag
     * Get index of column which place we'll occupy
     * Load number of the row we'll use as for a check
     * Get an original value of a cell in the column we are going to drag and specified row
     * Get points where to drag from and to
     * Drag and drop column to a new place
     * Get value from the cell we projected our original value to be in
     * Check if the values match
     */
    @Test
    public void DragColumnToNewPosition() {
        String columnToDrag = spec.get("tableDemo.columnToDrag").String();
        String columnWhereToDrop = spec.get("tableDemo.columnWhereToDrag").String();

        demo.allowReordering();

        int startIndex = demo.getColumnIndex(columnToDrag);
        int endIndex = demo.getColumnIndex(columnWhereToDrop);

        int rowNumber = spec.get("tableDemo.reorderingRowNumber").Int();

        String originalCellValue = demo.getStringFromTableCell(rowNumber, startIndex);

        Point dragFrom = demo.getColumnHeaderPoint(columnToDrag);
        Point dropTo = demo.getColumnHeaderPoint(columnWhereToDrop);

        demo.drapAndDrop(dragFrom, dropTo);

        String newCellValue = demo.getStringFromTableCell(rowNumber, endIndex);

        Assert.assertEquals(originalCellValue, newCellValue);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Create a new point based on the first one(subtract from y coordinate to make it higher)
     * Get original index of column to be dragged
     * Try to drag column up
     * Get index of the column again
     * Check that column is in the same place(same index)
     */
    @Test
    public void DragColumnUp() {
        String columnToDrag = spec.get("tableDemo.columnToDrag").String();

        demo.allowReordering();

        Point dragFrom = demo.getColumnHeaderPoint(columnToDrag);
        Point dropTo = new Point(dragFrom.x, dragFrom.y - 100);

        int startIndex = demo.getColumnIndex(columnToDrag);

        demo.drapAndDrop(dragFrom, dropTo);

        int endIndex = demo.getColumnIndex(columnToDrag);

        Assert.assertEquals(startIndex, endIndex);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Create a new point based on where we want to drag and subtract from y coordinate to make it higher
     * Get original index of column to be dragged
     * Try to drag column up and in desired direction
     * Get index of the column again
     * Check that column is in the desired place(by index)
     */
    @Test
    public void DragColumnUpAndToTheSide() {
        String columnToDrag = spec.get("tableDemo.columnWhereToDrag").String();
        String columnWhereToDrop = spec.get("tableDemo.columnToDrag").String();

        demo.allowReordering();

        Point dragFrom = demo.getColumnHeaderPoint(columnToDrag);
        Point dropTo = demo.getColumnHeaderPoint(columnWhereToDrop);
        dropTo.y -= 100;

        int whereToDropIndex = demo.getColumnIndex(columnWhereToDrop);

        demo.drapAndDrop(dragFrom, dropTo);

        int currentIndex = demo.getColumnIndex(columnToDrag);

        Assert.assertEquals(currentIndex, whereToDropIndex);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Get the point from where to drag cell
     * Get the point where to drop the cell
     * Try to drag cell
     * Get position of the cell again
     * Check that cell's position didn't change
     */
    @Test
    public void TryDragCellUpAndToTheSide() {
        String cellToDrag = spec.get("tableDemo.cellToDrag").String();
        String cellWhereToDrop = spec.get("tableDemo.cellWhereToDrop").String();

        demo.allowReordering();

        Point dragFrom = demo.getCellPoint(cellToDrag);
        Point dropTo = demo.getCellPoint(cellWhereToDrop);

        demo.drapAndDrop(dragFrom, dropTo);

        Point newPosition = demo.getCellPoint(cellToDrag);

        Assert.assertEquals(dragFrom.x, newPosition.x);
        Assert.assertEquals(dragFrom.y, newPosition.y);
    }

    @Test //Prototype
    public void Prototype() {
        demo.clickTable();
    }
}
