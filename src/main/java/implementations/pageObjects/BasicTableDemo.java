package implementations.pageObjects;

import abstracts.PageObject;
import implementations.helpers.HeaderDragAndDrop;
import implementations.wrappers.Lazy;
import interfaces.pageObjects.TableDemo;
import org.fest.swing.fixture.*;
import utils.ResourceManager;

import java.awt.*;

public class BasicTableDemo extends PageObject implements TableDemo {

    public BasicTableDemo(FrameFixture frame) {
        super(frame);
    }

    //region Helpers
    protected HeaderDragAndDrop dragAndDrop = new HeaderDragAndDrop(frame.robot);
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

    @Override
    public void clickTable() {
        tableView.get().click();
        scrollpane.get().verticalScrollBar().scrollBlockDown(6);
        isColumnReorderingAllowedCheckBox.get().uncheck();
        isColumnReorderingAllowedCheckBox.get().click();
        showHorizontalLinesCheckBox.get().click();
        showVerticalLinesCheckBox.get().click();
        interCellSpacingLabel.get().click();
        rowHeightLabel.get().click();
        isColumnReorderingAllowedCheckBox.get().click();
        isColumnSelectionAllowedCheckBox.get().click();
        isRowSelectionAllowedCheckBox.get().click();
        interCellSpacingSlider.get().slideToMaximum();
        rowHeightSlider.get().slideToMinimum();
        selectionModeComboBox.get().selectItem(0);
        resizeModeComboBox.get().selectItem(0);
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
}
