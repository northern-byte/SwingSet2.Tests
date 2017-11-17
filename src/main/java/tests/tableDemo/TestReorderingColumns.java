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

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Get index of the column we want to drag
     * Get index of column which place we'll occupy
     * Load number of the row we'll use as for a check
     * Get an original value of a cell in the column we are going to drag and specified row
     * Drag and drop column to a new place
     * Get value from the cell we projected our original value to be in
     * Check if the values match
     */
    @Test
    public void DragColumnToNewPosition(){
        String columnToDrag = spec.get("tableDemo.columnToDrag").String();
        String columnWhereToDrop = spec.get("tableDemo.columnWhereToDrag").String();

        demo.allowReordering();

        int startIndex = demo.getColumnIndex(columnToDrag);
        int endIndex = demo.getColumnIndex(columnWhereToDrop);

        int rowNumber = spec.get("tableDemo.reorderingRowNumber").Int();

        String originalCellValue = demo.getStringFromTableCell(rowNumber, startIndex);

        demo.drapAndDropColumnToColumn(columnToDrag, columnWhereToDrop);

        String newCellValue = demo.getStringFromTableCell(rowNumber, endIndex);

        Assert.assertEquals(originalCellValue, newCellValue);
    }

    @Test //Prototype
    public void Prototype(){
        demo.clickTable();
    }
}
