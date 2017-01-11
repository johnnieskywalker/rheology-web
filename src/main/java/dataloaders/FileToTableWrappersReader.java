package dataloaders;

import view.wrappers.TableWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileToTableWrappersReader {

    public List<TableWrapper> readFileToTableWrappers(File file) throws FileNotFoundException {
        Scanner equationDataFile = new Scanner(file);
        List<TableWrapper> tableWrappers = new ArrayList<>();

        while (equationDataFile.hasNextLine()) {
            String line = equationDataFile.nextLine();

            String[] rowValues = line.split(" ");
            double deformation = Double.valueOf(rowValues[0]);
            double experimentalStress = Double.valueOf(rowValues[1]);
            tableWrappers.add(new TableWrapper(deformation,experimentalStress));
        }

        equationDataFile.close();
        return tableWrappers;
    }

}
