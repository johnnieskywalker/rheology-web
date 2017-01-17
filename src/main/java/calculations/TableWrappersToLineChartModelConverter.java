package calculations;

import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import view.wrappers.TableRowWrapper;

import java.util.ArrayList;
import java.util.List;

public class TableWrappersToLineChartModelConverter {

    private List<TableRowWrapper> tableRowWrappers = new ArrayList<>();

    private LineChartModel lineModel = new LineChartModel();

    public LineChartModel buildLineChartModelFromTableData() {

        LineChartSeries experimentalStressSeries = loadExperimentalStressSeries();
        LineChartSeries calculatedStressSeries = loadCalculatedStressSeries();

        lineModel.addSeries(experimentalStressSeries);
        lineModel.addSeries(calculatedStressSeries);

        return lineModel;
    }

    private LineChartSeries loadCalculatedStressSeries() {
        LineChartSeries calculatedStressSeries = new LineChartSeries();
        calculatedStressSeries.setLabel("Naprężenie wyliczone");
        for (TableRowWrapper tableRowWrapper : tableRowWrappers) {
            calculatedStressSeries.set(tableRowWrapper.getDeformation(),tableRowWrapper.getCalculatedStress());
        }
        return calculatedStressSeries;
    }

    private LineChartSeries loadExperimentalStressSeries() {
        LineChartSeries experimentalStressSeries = new LineChartSeries();
        experimentalStressSeries.setLabel("Naprężenie zmierzone");
        for (TableRowWrapper tableRowWrapper : tableRowWrappers) {
            experimentalStressSeries.set(tableRowWrapper.getDeformation(),tableRowWrapper.getExperimentalStress());
        }
        return experimentalStressSeries;
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers) {
        this.tableRowWrappers = tableRowWrappers;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
}
