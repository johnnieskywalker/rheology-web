package dataloaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileToDoublesListReader {

    public List<Double> readFileToDoubles(Path filePath) throws FileNotFoundException {
        return readFileToDoubles(filePath);
    }

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
        return list;
    }
}
