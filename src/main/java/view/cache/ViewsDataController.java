package view.cache;

import calculations.TableWrappersToLineChartModelConverter;
import dataloaders.FileToTableWrappersReader;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import optimization.nonlinear.unconstrained.core.PowellAlgorithm;
import optimization.nonlinear.unconstrained.core.materialFunctions.CompressedMaterialWithoutRecrystalizationSoftening;
import optimization.nonlinear.unconstrained.core.materialFunctions.SimpleMaterialFunction;
import optimization.nonlinear.unconstrained.examples.ApproximationFromTableWrappersTask;
import org.primefaces.model.chart.LineChartModel;
import view.TableView;
import view.model.OptimizedParameterWrapper;
import view.model.OptimizedParametersWrapperBuilder;
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

    private ApproximationFromTableWrappersTask approximationFromTableWrappersTask = new
            ApproximationFromTableWrappersTask();

    private OptimizedParametersWrapperBuilder optimizedParametersWrapperBuilder = new
            OptimizedParametersWrapperBuilder(approximationFromTableWrappersTask);

    @PostConstruct
    public void initializeViewBeans() {
        facesContext = FacesContext.getCurrentInstance();
        tableViewBean
                = (TableView) facesContext.getApplication()
                .createValueBinding("#{tableView}").getValue(facesContext);
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
    }

    private void runCalculationsForTableWrappers() {
        approximationFromTableWrappersTask.setTableRowWrappers(tableViewBean.getTableRowWrappers());

        approximationFromTableWrappersTask.run();
    }

    private void fillTableWithResultRows() {
        tableViewBean.setTableRowWrappers(approximationFromTableWrappersTask.loadResultTableRowWrappers());
    }

    private void updateParameterValuesDisplayedBelowTable() {
        tableViewBean.setSumMeanSquaredError(approximationFromTableWrappersTask.getObjectiveFunctionValue());
        optimizedParametersWrapperBuilder.reloadOptimizedParameters();
        tableViewBean.setParametersInfo(optimizedParametersWrapperBuilder.getParametersInfo());
    }

    public LineChartModel loadDataFromTableToChart() {
        TableWrappersToLineChartModelConverter tableWrappersToLineChartModelConverter = new
                TableWrappersToLineChartModelConverter();
        tableWrappersToLineChartModelConverter.setTableRowWrappers(tableViewBean.getTableRowWrappers());

        return tableWrappersToLineChartModelConverter.buildLineChartModelFromTableData();
    }

    public void simpleMaterialFunction() {
        approximationFromTableWrappersTask.setMaterialFunction(new SimpleMaterialFunction());
    }

    public void compressedMaterialWithoutRecrystalizationSofteningFunction() {
        approximationFromTableWrappersTask.setMaterialFunction(new CompressedMaterialWithoutRecrystalizationSoftening());
    }

    public void setHookeJeevesAlgorithm() {
        approximationFromTableWrappersTask.setSearchMethod(new HookeAlgorithm());
    }

    public void setPowellAlgorithm() {
        double startingK = 140.0;
        double startingN = 10.0;
        double[] startPoint = {startingK, startingN};
        approximationFromTableWrappersTask.setStartPoint(startPoint);
        approximationFromTableWrappersTask.setSearchMethod(new PowellAlgorithm());
    }

    public void setOptimizedParameterWrappers(List<OptimizedParameterWrapper> optimizedParameterWrappers) {
        optimizedParametersWrapperBuilder.setOptimizedParameterWrappers(optimizedParameterWrappers);
    }

    public List<OptimizedParameterWrapper> getOptimizedParameterWrappers() {
        return optimizedParametersWrapperBuilder.getOptimizedParameterWrappers();
    }

    public Path getLastUploadedFilePath() {
        return lastUploadedFilePath;
    }

    public void setLastUploadedFilePath(Path lastUploadedFilePath) {
        this.lastUploadedFilePath = lastUploadedFilePath;
    }
}
