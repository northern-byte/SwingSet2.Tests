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

    int getColumnCount();

    int getRowCount();

    int getColumnWidth(int index);

    int getColumnWidth(String columnName);

    String getStringFromTableCell(int rowNumber, int startIndex);

    void drapAndDrop(Point from, Point to);

    void drapAndDrop(Point from, java.util.List<Point> to);

    Point getColumnHeaderPoint(String columnName);

    Point getColumnHeaderRightBorderPoint(String columnName);

    void allowReordering();

    void forbidReordering();

    Point getCellPoint(String cellText);

    Point getCellPoint(int row, int column);

    void selectCell(String cellText);

    void doubleClickCell(String cellText);

    void selectResizeMode(String resizeModeName);

    void selectCell(int row, int column);

    void selectSelectionMode(String mode);

    int[] getSelectedRows();

    int[] getSelectedColumns();

    void enableRowSelection();

    void enableColumnSelection();

    void disableRowSelection();

    void disableColumnSelection();

    Color getCellBackgroundColor(int row, int column);

    void selectRows(int... rows);

    void enableHorizontalLines();

    void enableVerticalLines();

    void disableHorizontalLines();

    void disableVerticalLines();

    boolean horizontalLinesEnabled();

    boolean verticalLinesEnabled();

    Color getGridColor();

    void clickColumnHeader(String columnName);

    Object getValueFromCell(int row, int column);

    void maximizeWindow();

    void resizeWindowTo(Dimension size);

    String inputValueToCell(int row, int column, String value);

    boolean isReorderingAllowed();

    boolean areHorizontalLinesEnabled();

    boolean areVerticalLinesEnabled();

    boolean isRowSelectionEnabled();

    boolean isColumnSelectionEnabled();

    int getRowHeightValue();

    String getSelectionMode();

    String getAutoresizeMode();
}
