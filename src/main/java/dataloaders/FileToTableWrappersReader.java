package dataloaders;

import view.wrappers.TableRowWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileToTableWrappersReader {

    public static List<TableRowWrapper> readFileToTableWrappers(File file) throws FileNotFoundException {
        Scanner equationDataFile = new Scanner(file);
        List<TableRowWrapper> tableWrappers = new ArrayList<>();

        while (equationDataFile.hasNextLine()) {
            String line = equationDataFile.nextLine();

            String[] rowValues = line.split(" ");
            double deformation = Double.valueOf(rowValues[0]);
            double experimentalStress = Double.valueOf(rowValues[1]);
            tableWrappers.add(new TableRowWrapper(deformation,experimentalStress));
        }

        equationDataFile.close();
        return tableWrappers;
    }

}
