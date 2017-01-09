package optimization.nonlinear.unconstrained.core;

import optimization.nonlinear.unconstrained.dataloaders.TextToListReader;

import java.io.File;
import java.io.FileNotFoundException;

public class ApproximationFromFileTask {

    SumMeanSquaredErrors sumMeanSquaredErrors = new SumMeanSquaredErrors();

    TextToListReader textToArrayListReader = new TextToListReader();

    public void loadExperimentalDataFromFiles(File experimentalStressFile, File deformationsFile){
        try {
            loadExperimentalStressFromFile(experimentalStressFile);
            loadDeformationsFromFile(deformationsFile);
        }
        catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }
    }

    public void loadExperimentalDataFromFilesPaths(String experimentalStressFilePath, String deformationsFilePath){
        ClassLoader classLoader = getClass().getClassLoader();
        File experimentalStressFile = new File(classLoader.getResource
                (experimentalStressFilePath).getFile());
        File deformationFile = new File(classLoader.getResource
                (deformationsFilePath).getFile());
        try {
            loadExperimentalStressFromFile(experimentalStressFile);
            loadDeformationsFromFile(deformationFile);
        }
        catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }
    }

    public void loadExperimentalStressFromFile(File file) throws FileNotFoundException{
     sumMeanSquaredErrors.setExperimentalStresses(textToArrayListReader.readFileToDoubles(file));
    }


    public void loadDeformationsFromFile(File file) throws FileNotFoundException{
        sumMeanSquaredErrors.setDeformations(textToArrayListReader.readFileToDoubles(file));
    }

    public SumMeanSquaredErrors getSumMeanSquaredErrors() {
        return sumMeanSquaredErrors;
    }

    public void setSumMeanSquaredErrors(SumMeanSquaredErrors sumMeanSquaredErrors) {
        this.sumMeanSquaredErrors = sumMeanSquaredErrors;
    }
}
