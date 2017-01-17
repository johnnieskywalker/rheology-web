package view.cache;

import calculations.TableWrappersToLineChartModelConverter;
import dataloaders.FileToTableWrappersReader;
import optimization.nonlinear.unconstrained.examples.ApproximationFromTableWrappersTask;
import org.primefaces.model.chart.LineChartModel;
import view.TableView;
import view.wrappers.TableRowWrapper;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "viewsDataController")
@SessionScoped
public class ViewsDataController {

    private Path lastUploadedFilePath;

    private FacesContext facesContext;

    private TableView tableViewBean;

//    private ChartView chartViewBean;

    private ApproximationFromTableWrappersTask approximationFromTableWrappersTask = new
            ApproximationFromTableWrappersTask();

    @PostConstruct
    public void initializeViewBeans() {
        facesContext = FacesContext.getCurrentInstance();
        tableViewBean
                = (TableView) facesContext.getApplication()
                .createValueBinding("#{tableView}").getValue(facesContext);

//        chartViewBean
//                = (ChartView) facesContext.getApplication()
//                .createValueBinding("#{chartView}").getValue(facesContext);
    }

    public void reloadDataFromLastFile() {
        tableViewBean.setTableRowWrappers(prepareTableWrappersFromFile());
    }

    public List<TableRowWrapper> prepareTableWrappersFromFile() {
        try {
            return FileToTableWrappersReader.readFileToTableWrappers(lastUploadedFilePath.toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void loadResultsToViews() {
        runCalculationsForTableWrappers();

        fillTableWithResultRows();

        updateParameterValuesDisplayedBelowTable();

//        loadDataFromTableToChart();
    }

    private void runCalculationsForTableWrappers() {
        approximationFromTableWrappersTask.setTableRowWrappers(tableViewBean.getTableRowWrappers());

        approximationFromTableWrappersTask.run();
    }

    private void fillTableWithResultRows() {
        tableViewBean.setTableRowWrappers(approximationFromTableWrappersTask.loadResultTableRowWrappers());
    }

    private void updateParameterValuesDisplayedBelowTable() {
        tableViewBean.setOptimizedParameterK(approximationFromTableWrappersTask.getOptimizedParameterKValue());
        tableViewBean.setOptimizedParameterN(approximationFromTableWrappersTask.getOptimizedParameterNValue());
        tableViewBean.setSumMeanSquaredError(approximationFromTableWrappersTask.getSumMeanSquaredErrorsValue());
    }

    public LineChartModel loadDataFromTableToChart() {
        TableWrappersToLineChartModelConverter tableWrappersToLineChartModelConverter = new
                TableWrappersToLineChartModelConverter();
        tableWrappersToLineChartModelConverter.setTableRowWrappers(tableViewBean.getTableRowWrappers());

//        tableWrappersToLineChartModelConverter.setLineModel(chartViewBean.getLineModel());
        return tableWrappersToLineChartModelConverter.buildLineChartModelFromTableData();
    }

    public Path getLastUploadedFilePath() {
        return lastUploadedFilePath;
    }

    public void setLastUploadedFilePath(Path lastUploadedFilePath) {
        this.lastUploadedFilePath = lastUploadedFilePath;
    }
}
