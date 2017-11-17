package interfaces.pageObjects;

import java.awt.*;

public interface TableDemo extends Demo {
    void clickTable();

    int getIntercellSpacingMax();

    int getIntercellSpacingMin();

    int getIntercellSpacingValue();

    int setIntercellSpacingToMax();

    int setIntercellSpacingToMin();

    int setIntercellSpacingTo(int value);

    Dimension getTableIntercellSpacing();

    int getRowHeightMax();

    int getRowHeightMin();

    int setRowHeightToMax();

    int getTableRowHeight();

    int setRowHeightToMin();

    int setRowHeightTo(int value);

    int getColumnIndex(String columnName);

    String getStringFromTableCell(int rowNumber, int startIndex);

    void drapAndDrop(Point from, Point to);

    Point getColumnHeaderPoint(String columnName);

    void allowReordering();

    void forbidReordering();

    Point getCellPoint(String cellText);

    void clickCell(String cellText);

    void doubleClickCell(String cellText);
}

