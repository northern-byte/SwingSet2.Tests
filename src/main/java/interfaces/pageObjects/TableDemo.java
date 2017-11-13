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

}

