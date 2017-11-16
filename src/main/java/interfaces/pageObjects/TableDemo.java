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
}

