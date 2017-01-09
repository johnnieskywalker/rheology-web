package optimization.nonlinear.unconstrained.dataloaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextToListReader {

    public List<Double> readFileToDoubles(String filePath) throws FileNotFoundException {
        return readFileToDoubles(new File(filePath));
    }

    public List<Double> readFileToDoubles(File file) throws FileNotFoundException {
        Scanner equationDataFile = new Scanner(file);
        List<Double> list = new ArrayList<>();

        while (equationDataFile.hasNextLine()) {
            String line = equationDataFile.nextLine();

            list.add(new Double(line));
        }

        equationDataFile.close();

        equationDataFile.close();
        return list;
    }

}
