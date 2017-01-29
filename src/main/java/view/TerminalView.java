package view;

import utils.FileUtils;
import utils.SystemPaths;
import utils.TerminalCommands;
import view.cache.ViewsDataController;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

@ManagedBean
public class TerminalView {

    String commandList = "";

    ViewsDataController viewsDataController;

    public String handleCommand(String command, String[] params) {
        if (command.equals(TerminalCommands.GREET.toString())) {
            return handleGreet(params);
        } else if (command.equals(TerminalCommands.HELP.toString())) {

            return handleHelp();
        } else if (command.equals(TerminalCommands.DATE.toString())) {
            return new Date().toString();
        } else if (command.equals(TerminalCommands.LIST_FILES.toString())) {
            return handleListFiles();
        }
        else if (command.equals(TerminalCommands.READ_FILE.toString())){
            return handleReadFile(params);
        }
        else if(command.equals(TerminalCommands.LAST_FILE.toString())){
            FacesContext facesContext = FacesContext.getCurrentInstance();
            viewsDataController
                    = (ViewsDataController)facesContext.getApplication()
                    .createValueBinding("#{viewsDataController}").getValue(facesContext);
            System.out.println(viewsDataController.getLastUploadedFilePath().getFileName());
            return viewsDataController.getLastUploadedFilePath().getFileName().toString();
        }
        else{
            return command + " not found";
        }
    }

    private String handleReadFile(String[] params) {
        try {
            return FileUtils.readFile(SystemPaths.TOMCAT_UPLOAD_FOLDER.toString()+"\\"+params[0], StandardCharsets
                    .UTF_8);
        }
        catch (IOException exception){
            return exception.getMessage();
        }
    }

    private String handleListFiles() {
        String result = "";
        File[] files = new File(SystemPaths.TOMCAT_UPLOAD_FOLDER.toString()).listFiles();
        Arrays.asList(files);
        for (File file : files) {
            result += file.getName() +"\n";
        }
        return result;
    }

    private String handleHelp() {
        Arrays.asList(TerminalCommands.values()).forEach(commandName -> commandList += "\n" + commandName);
        return commandList;
    }

    private String handleGreet(String[] params) {
        if (params.length > 0)
            return "Witaj " + params[0];
        else
            return "Witaj nieznajomy";
    }
}