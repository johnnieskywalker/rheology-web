package view;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import utils.SystemPaths;
import view.cache.GlobalCache;

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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void fileUploadListener(FileUploadEvent event) {
        // Get uploaded file from the FileUploadEvent
        this.uploadedFile = event.getFile();
        // Print out the information of the file
        System.out.println("Uploaded File Name Is :: " + uploadedFile.getFileName() + " :: Uploaded File Size :: " + uploadedFile.getSize());
        if (uploadedFile != null) {
            try {
                InputStream upladedFileInput = uploadedFile.getInputstream();

                Path folder = Paths.get(SystemPaths.TOMCAT_UPLOAD_FOLDER.toString());
                String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
                String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
                Path uploadedFilePath = Files.createTempFile(folder, filename + "-", "." + extension);

                Files.copy(upladedFileInput, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Uploaded uploadedFile successfully saved in " + uploadedFilePath);

                FacesMessage message = new FacesMessage("Succesful", uploadedFile.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);

                putUploadedFileToCache(uploadedFilePath);
            } catch (IOException exception) {
                exception.printStackTrace();
                FacesMessage message = new FacesMessage(exception.getStackTrace().toString());
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    private void putUploadedFileToCache(Path uploadedFilePath) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        GlobalCache globalCache
                = (GlobalCache)facesContext.getApplication()
                .createValueBinding("#{globalCache}").getValue(facesContext);
        globalCache.setLastUploadedFilePath(uploadedFilePath);
    }
}
