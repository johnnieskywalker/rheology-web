package view;

import utils.SystemPaths;
import utils.TerminalCommands;

import javax.faces.bean.ManagedBean;
import java.io.File;
import java.util.Arrays;
import java.util.Date;

@ManagedBean
public class TerminalView {

    String commandList = "";

    public String handleCommand(String command, String[] params) {
        if (command.equals(TerminalCommands.GREET.toString())) {
            return handleGreet(params);
        } else if (command.equals(TerminalCommands.HELP.toString())) {

            return handleHelp();
        } else if (command.equals(TerminalCommands.DATE.toString())) {
            return new Date().toString();
        } else if (command.equals(TerminalCommands.LIST_FILES.toString())) {
            return handleListFiles();
        } else{
            return command + " not found";
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