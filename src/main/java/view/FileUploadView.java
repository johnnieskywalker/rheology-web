package view;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import utils.SystemPaths;
import view.cache.ViewsDataController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@ManagedBean
@SessionScoped
public class FileUploadView {

    private UploadedFile uploadedFile;

    private ViewsDataController viewsDataController;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        viewsDataController
                = (ViewsDataController)facesContext.getApplication()
                .createValueBinding("#{viewsDataController}").getValue(facesContext);
    }

    public void fileUploadListener(FileUploadEvent event) {
        uploadedFile = event.getFile();
        System.out.println("Uploaded File Name Is :: " + uploadedFile.getFileName() + " :: Uploaded File Size :: " + uploadedFile.getSize());
        if (uploadedFile != null) {
            processUploadedFile();
        }
    }

    private void processUploadedFile() {
        try {
            InputStream upladedFileInput = uploadedFile.getInputstream();

            Path uploadedFilePath = createFilePath();

            Files.copy(upladedFileInput, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Uploaded uploadedFile successfully saved in " + uploadedFilePath);

            displayMessageToUser();

            putUploadedFileToCache(uploadedFilePath);
            reloadAllViews();
        } catch (IOException exception) {
            exception.printStackTrace();
            FacesMessage message = new FacesMessage(exception.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    private Path createFilePath() throws IOException {
        Path folder = Paths.get(SystemPaths.TOMCAT_UPLOAD_FOLDER.toString());
        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        return Files.createTempFile(folder, filename + "-", "." + extension);
    }

    private void displayMessageToUser() {
        FacesMessage message = new FacesMessage("Succesful", uploadedFile.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void putUploadedFileToCache(Path uploadedFilePath) {
        viewsDataController.setLastUploadedFilePath(uploadedFilePath);
    }

    private void reloadAllViews(){
        viewsDataController.reload();
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
