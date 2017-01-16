package view.cache;

import dataloaders.FileToTableWrappersReader;
import optimization.nonlinear.unconstrained.examples.ApproximationFromTableWrappersTask;
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

    private ApproximationFromTableWrappersTask approximationFromTableWrappersTask = new
            ApproximationFromTableWrappersTask();

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

    public void findResultsForTableWrappers() {
        runCalculationsForTableWrappers();

        fillTableWithResultRows();
    }

    private void runCalculationsForTableWrappers() {
        approximationFromTableWrappersTask.setTableRowWrappers(tableViewBean.getTableRowWrappers());

        approximationFromTableWrappersTask.run();
    }

    private void fillTableWithResultRows() {
        tableViewBean.setTableRowWrappers(approximationFromTableWrappersTask.loadResultTableRowWrappers());
    }

    public Path getLastUploadedFilePath() {
        return lastUploadedFilePath;
    }

    public void setLastUploadedFilePath(Path lastUploadedFilePath) {
        this.lastUploadedFilePath = lastUploadedFilePath;
    }
}
