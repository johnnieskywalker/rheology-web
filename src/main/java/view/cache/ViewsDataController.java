package view.cache;

import dataloaders.FileToTableWrappersReader;
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

@ManagedBean(name= "viewsDataController")
@SessionScoped
public class ViewsDataController {

    private Path lastUploadedFilePath;

    private TableView tableViewBean;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        tableViewBean
                = (TableView)facesContext.getApplication()
                .createValueBinding("#{tableView}").getValue(facesContext);
    }

    public void reload(){
        tableViewBean.setTableRowWrappers(prepareTableWrappers());
    }

    public List<TableRowWrapper> prepareTableWrappers() {
        try {
            return FileToTableWrappersReader.readFileToTableWrappers(lastUploadedFilePath.toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Path getLastUploadedFilePath() {
        return lastUploadedFilePath;
    }

    public void setLastUploadedFilePath(Path lastUploadedFilePath) {
        this.lastUploadedFilePath = lastUploadedFilePath;
    }
}
