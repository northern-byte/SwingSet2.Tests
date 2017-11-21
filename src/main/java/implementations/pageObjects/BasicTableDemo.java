package implementations.pageObjects;

import abstracts.PageObject;
import implementations.helpers.DragAndDropHelper;
import implementations.helpers.ImageHelper;
import implementations.helpers.TableHeaderHelper;
import implementations.helpers.TextMatcherHelper;
import implementations.wrappers.Lazy;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.data.TableCell;
import org.fest.swing.fixture.*;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import org.junit.Assert;
import utils.Platform;
import utils.ResourceManager;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BasicTableDemo extends PageObject implements TableDemo {

    public BasicTableDemo(FrameFixture frame, View view) {
        super(frame, view);
    }

    //region Helpers
    protected DragAndDropHelper dragAndDrop = new DragAndDropHelper(frame.robot);
    protected TableHeaderHelper headerHelper = new TableHeaderHelper();
    protected TextMatcherHelper textMatcherHelper = new TextMatcherHelper();
    protected ImageHelper imageHelper = new ImageHelper();
    //endregion

    //region Expected Texts
    private final String reorderingText = ResourceManager.getResString("TableDemo.reordering_allowed");
    private final String horzText = ResourceManager.getResString("TableDemo.horz_lines");
    private final String vertText = ResourceManager.getResString("TableDemo.vert_lines");
    private final String columnSelText = ResourceManager.getResString("TableDemo.column_selection");
    private final String rowSelText = ResourceManager.getResString("TableDemo.row_selection");
    private final String intercellColonText = ResourceManager.getResString("TableDemo.intercell_spacing_colon");
    private final String rowHeightColonText = ResourceManager.getResString("TableDemo.row_height_colon");
    private final String intercellSliderName = ResourceManager.getResString("TableDemo.intercell_spacing");
    private final String rowHeightSliderName = ResourceManager.getResString("TableDemo.row_height");
    private final String selectionDefaultValue = ResourceManager.getResString("TableDemo.multiple_ranges");
    private final String resizeDefaultValue = ResourceManager.getResString("TableDemo.subsequent_columns");
    //endregion

    //region Components
    protected Lazy<JTableFixture> tableView = wait.lazy(() -> frame.table());
    protected Lazy<JScrollPaneFixture> scrollpane = wait.lazy(() -> frame.scrollPane());

    protected Lazy<JCheckBoxFixture> isColumnReorderingAllowedCheckBox = wait.lazy(() -> frame.checkBox(getCheckBoxTextMatcher(reorderingText)));
    protected Lazy<JCheckBoxFixture> showHorizontalLinesCheckBox = wait.lazy(() -> frame.checkBox(getCheckBoxTextMatcher(horzText)));
    protected Lazy<JCheckBoxFixture> showVerticalLinesCheckBox = wait.lazy(() -> frame.checkBox(getCheckBoxTextMatcher(vertText)));

    protected Lazy<JCheckBoxFixture> isColumnSelectionAllowedCheckBox = wait.lazy(() -> frame.checkBox(getCheckBoxTextMatcher(columnSelText)));
    protected Lazy<JCheckBoxFixture> isRowSelectionAllowedCheckBox = wait.lazy(() -> frame.checkBox(getCheckBoxTextMatcher(rowSelText)));

    protected Lazy<JLabelFixture> interCellSpacingLabel = wait.lazy(() -> frame.label(getLabelTextMatcher(intercellColonText)));
    protected Lazy<JLabelFixture> rowHeightLabel = wait.lazy(() -> frame.label(getLabelTextMatcher(rowHeightColonText)));

    protected Lazy<JSliderFixture> interCellSpacingSlider = wait.lazy(() -> frame.slider(getSliderAccessNameMatcher(intercellSliderName)));
    protected Lazy<JSliderFixture> rowHeightSlider = wait.lazy(() -> frame.slider(getSliderAccessNameMatcher(rowHeightSliderName)));

    protected Lazy<JComboBoxFixture> selectionModeComboBox = wait.lazy(() -> frame.comboBox(getComboBoxSelectedMatcher(selectionDefaultValue)));
    protected Lazy<JComboBoxFixture> resizeModeComboBox = wait.lazy(() -> frame.comboBox(getComboBoxSelectedMatcher(resizeDefaultValue)));
    //endregion

    //TODO remove this temporary sandbox code
    @Override
    public void clickTable() {
        JTableFixture table = tableView.get();
//        table.click();
//        table.drag(TableCell.row(0).column(0));
//        table.drop(TableCell.row(0).column(3));
//        table.tableHeader().clickColumn(0);
//
//        scrollpane.get().verticalScrollBar().scrollBlockDown(6);
//        isColumnReorderingAllowedCheckBox.get().uncheck();
//        isColumnReorderingAllowedCheckBox.get().click();
//        showHorizontalLinesCheckBox.get().click();
//        showVerticalLinesCheckBox.get().click();
//        interCellSpacingLabel.get().click();
//        rowHeightLabel.get().click();
//        isColumnReorderingAllowedCheckBox.get().click();
//        isColumnSelectionAllowedCheckBox.get().click();
//        isRowSelectionAllowedCheckBox.get().click();
//        interCellSpacingSlider.get().slideToMaximum();
//        rowHeightSlider.get().slideToMinimum();
//        selectionModeComboBox.get().selectItem(0);
//        resizeModeComboBox.get().selectItem(0);
        JTableHeader header = table.tableHeader().target;
        Point from = headerHelper.pointAtName(header, textMatcherHelper.exactText("First Name"));
        Point to = headerHelper.pointAtName(header, textMatcherHelper.containsText("Movie"));
        dragAndDrop.drag(header, from);
        dragAndDrop.drop(header,to);

        Assert.assertTrue(table.cell("Mike").column == 2);

        Point b1 = headerHelper.rightBorder(header, textMatcherHelper.containsText("First Name"));
        Point to2 = headerHelper.pointAtName(header, textMatcherHelper.containsText("Movie"));
        dragAndDrop.dragAndDrop(header, b1, to2);

        Point b2 = headerHelper.leftBorder(header, textMatcherHelper.containsText("Movie"));
        Point to3 = headerHelper.pointAtName(header, textMatcherHelper.containsText("Last"));
        dragAndDrop.dragAndDrop(header, b2, to3);

        Dimension originalSpacing = table.target.getIntercellSpacing();
        interCellSpacingSlider.get().slideToMaximum();
        Dimension newSpacing = table.target.getIntercellSpacing();
        Assert.assertTrue(originalSpacing.height < newSpacing.height && originalSpacing.width < newSpacing.width);

        interCellSpacingSlider.get().slideToMinimum();
        newSpacing = table.target.getIntercellSpacing();
        Assert.assertTrue(originalSpacing.height > newSpacing.height && originalSpacing.width > newSpacing.width);

        Integer originalHeight = table.target.getRowHeight();
        rowHeightSlider.get().slideToMaximum();
        Integer newHeight = table.target.getRowHeight();
        Assert.assertTrue(originalHeight < newHeight);

        originalHeight = table.target.getRowHeight();
        rowHeightSlider.get().slideToMinimum();
        newHeight = table.target.getRowHeight();
        Assert.assertTrue(originalHeight > newHeight);

        String value = table.valueAt(TableCell.row(0).column(5));
        Assert.assertTrue(value.isEmpty());

        BufferedImage expected = imageHelper.loadBufferedImageFromRes("images/ImageClub/food/strawberry.jpg");
        int imageType = expected.getType();
        ImageIcon image = (ImageIcon)table.target.getValueAt(0, 5);

        BufferedImage bimage = imageHelper.convertToBufferedImage(image.getImage(), imageType);
        int bh = bimage.getHeight();
        int bw = bimage.getWidth();
        int eh = expected.getHeight();
        int ew = expected.getWidth();
        Assert.assertEquals(bh, eh);
        Assert.assertEquals(bw, ew);
        Assert.assertTrue(imageHelper.imagesAreEqual(bimage, expected));
    }

    @Override
    public int getIntercellSpacingMax() {
        return interCellSpacingSlider.get().target.getMaximum();
    }

    @Override
    public int getIntercellSpacingMin() {
        return interCellSpacingSlider.get().target.getMinimum();
    }

    @Override
    public int getIntercellSpacingValue() {
        return interCellSpacingSlider.get().target.getValue();
    }

    @Override
    public int setIntercellSpacingToMax() {
        JSliderFixture slider = interCellSpacingSlider.get().slideToMaximum();
        return slider.target.getValue();
    }

    @Override
    public int setIntercellSpacingToMin() {
        JSliderFixture slider = interCellSpacingSlider.get().slideToMinimum();
        return slider.target.getValue();
    }

    @Override
    public int setIntercellSpacingTo(int value) {
        JSliderFixture slider = interCellSpacingSlider.get().slideTo(value);
        return slider.target.getValue();
    }

    @Override
    public Dimension getTableIntercellSpacing() {
        return tableView.get().target.getIntercellSpacing();
    }

    @Override
    public int getRowHeightMax() {
        return rowHeightSlider.get().target.getMaximum();
    }

    @Override
    public int getRowHeightMin() {
        return rowHeightSlider.get().target.getMinimum();
    }

    @Override
    public int setRowHeightToMax() {
        JSliderFixture slider = rowHeightSlider.get().slideToMaximum();
        return slider.target.getValue();
    }

    @Override
    public int getTableRowHeight() {
        return tableView.get().target.getRowHeight();
    }

    @Override
    public int setRowHeightToMin() {
        JSliderFixture slider = rowHeightSlider.get().slideToMinimum();
        return slider.target.getValue();
    }

    @Override
    public int setRowHeightTo(int value){
        JSliderFixture slider = rowHeightSlider.get().slideTo(value);
        return slider.target.getValue();
    }

    @Override
    public int getColumnIndex(String columnName) {
        return tableView.get().columnIndexFor(columnName);
    }

    @Override
    public int getColumnCount(){
        return tableView.get().target.getColumnModel().getColumnCount();
    }

    @Override
    public int getRowCount() {
        return tableView.get().rowCount();
    }

    @Override
    public int getColumnWidth(int index){
        return tableView.get().target.getColumnModel().getColumn(index).getWidth();
    }

    @Override
    public int getColumnWidth(String columnName){
        int index = getColumnIndex(columnName);
        return getColumnWidth(index);
    }

    @Override
    public String getStringFromTableCell(int row, int column) {
        return (String)tableView.get().target.getValueAt(row, column);
    }

    @Override
    public void drapAndDrop(Point from, Point to) {
        JTableHeader header = tableView.get().tableHeader().target;
        dragAndDrop.dragAndDrop(header, from, to);
    }

    @Override
    public void drapAndDrop(Point from, List<Point> to) {
        JTableHeader header = tableView.get().tableHeader().target;
        dragAndDrop.dragAndDrop(header, from, to);
    }

    @Override
    public Point getColumnHeaderPoint(String columnName) {
        JTableHeader header = tableView.get().tableHeader().target;
        return headerHelper.pointAtName(header, textMatcherHelper.exactText(columnName));
    }

    @Override
    public Point getColumnHeaderRightBorderPoint(String columnName) {
        JTableHeader header = tableView.get().tableHeader().target;
        return headerHelper.rightBorder(header, textMatcherHelper.exactText(columnName));
    }

    @Override
    public void allowReordering() {
        isColumnReorderingAllowedCheckBox.get().check();
    }

    @Override
    public void forbidReordering() {
        isColumnReorderingAllowedCheckBox.get().uncheck();
    }

    @Override
    public Point getCellPoint(String cellText) {
        TableCell cell = tableView.get().cell(cellText);
        Rectangle rectangle = tableView.get().target.getCellRect(cell.row, cell.column, true);
        return new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height);
    }

    @Override
    public Point getCellPoint(int row, int column) {
        TableCell cell = TableCell.row(row).column(column);
        Rectangle rectangle = tableView.get().target.getCellRect(cell.row, cell.column, true);
        return new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height);
    }

    @Override
    public void selectCell(String cellText) {
        TableCell cell = tableView.get().cell(cellText);
        tableView.get().click(cell, MouseButton.LEFT_BUTTON);
    }

    @Override
    public void doubleClickCell(String cellText) {
        TableCell cell = tableView.get().cell(cellText);
        tableView.get().click(cell, MouseClickInfo.button(MouseButton.LEFT_BUTTON).times(2));
    }

    @Override
    public void selectResizeMode(String resizeModeName) {
        resizeModeComboBox.get().selectItem(resizeModeName);
    }

    @Override
    public void selectCell(int row, int column) {
        TableCell cell = TableCell.row(row).column(column);
        tableView.get().click(cell, MouseButton.LEFT_BUTTON);
    }

    @Override
    public void selectSelectionMode(String mode) {
        selectionModeComboBox.get().selectItem(mode);
    }

    @Override
    public int[] getSelectedRows() {
        return tableView.get().target.getSelectedRows();
    }

    @Override
    public int[] getSelectedColumns() {
        return tableView.get().target.getSelectedColumns();
    }

    @Override
    public void enableRowSelection() {
        isRowSelectionAllowedCheckBox.get().check();
    }

    @Override
    public void enableColumnSelection() {
        isColumnSelectionAllowedCheckBox.get().check();
    }

    @Override
    public void disableRowSelection() {
        isRowSelectionAllowedCheckBox.get().uncheck();
    }

    @Override
    public void disableColumnSelection() {
        isColumnSelectionAllowedCheckBox.get().uncheck();
    }

    public Color getCellBackgroundColor(int row, int column) {
        TableCellRenderer r = tableView.get().target.getCellRenderer(row, column);
        Component component = tableView.get().target.prepareRenderer(r, row, column);
        return component.getBackground();
    }

    @Override
    public void selectRows(int... rows) {
        tableView.get().selectRows(rows);
    }

    @Override
    public void enableHorizontalLines() {
        showHorizontalLinesCheckBox.get().check();
    }

    @Override
    public void enableVerticalLines() {
        showVerticalLinesCheckBox.get().check();
    }

    @Override
    public void disableHorizontalLines() {
        showHorizontalLinesCheckBox.get().uncheck();
    }

    @Override
    public void disableVerticalLines() {
        showVerticalLinesCheckBox.get().uncheck();
    }

    @Override
    public boolean horizontalLinesEnabled() {
        return tableView.get().target.getShowHorizontalLines();
    }

    @Override
    public boolean verticalLinesEnabled() {
        return tableView.get().target.getShowVerticalLines();
    }

    @Override
    public Color getGridColor() {
        return tableView.get().target.getGridColor();
    }

    @Override
    public void clickColumnHeader(String columnName) {
        JTableHeader header = tableView.get().tableHeader().target;
        Point columnPoint = headerHelper.pointAtName(header, textMatcherHelper.exactText(columnName));
        frame.robot.click(header, columnPoint);
    }

    @Override
    public Object getValueFromCell(int row, int column){
        return tableView.get().target.getValueAt(row, column);
    }

    @Override
    public void maximizeWindow() {
        frame.maximize();
        Pause.pause(new Condition("Wait until maximized") {
            @Override
            public boolean test() {
                return frame.target.getSize().width
                        == Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            }
        }, Timeout.timeout(Platform.getConfigProp("timeout").Int()));
    }

    @Override
    public void resizeWindowTo(Dimension size) {
        frame.resizeTo(size);
        Pause.pause(new Condition("Wait until resized") {
            @Override
            public boolean test() {
                return frame.target.getSize().width
                        == size.width;
            }
        }, Timeout.timeout(Platform.getConfigProp("timeout").Int()));
    }

    @Override
    public String inputValueToCell(int row, int column, String value) {
        tableView.get().enterValue(TableCell.row(row).column(column), value);
        return String.valueOf(getValueFromCell(row, column));
    }
}
