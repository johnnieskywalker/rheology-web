package view.cache;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.nio.file.Path;

@ManagedBean(name="globalCache")
@SessionScoped
public class GlobalCache {

    Path lastUploadedFilePath;

    public Path getLastUploadedFilePath() {
        return lastUploadedFilePath;
    }

    public void setLastUploadedFilePath(Path lastUploadedFilePath) {
        this.lastUploadedFilePath = lastUploadedFilePath;
    }
}
