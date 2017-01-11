package optimization.nonlinear.unconstrained.core;

import dataloaders.FileToDoublesListReader;

import java.io.File;
import java.io.FileNotFoundException;

public class ApproximationFromFileTask {

    SumMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumMeanSquaredErrorsObjectiveFunction();

    FileToDoublesListReader textToArrayListReader = new FileToDoublesListReader();

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
     sumMeanSquaredErrorsObjectiveFunction.setExperimentalStresses(textToArrayListReader.readFileToDoubles(file));
    }


    public void loadDeformationsFromFile(File file) throws FileNotFoundException{
        sumMeanSquaredErrorsObjectiveFunction.setDeformations(textToArrayListReader.readFileToDoubles(file));
    }

    public SumMeanSquaredErrorsObjectiveFunction getSumMeanSquaredErrorsObjectiveFunction() {
        return sumMeanSquaredErrorsObjectiveFunction;
    }

    public void setSumMeanSquaredErrorsObjectiveFunction(SumMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction) {
        this.sumMeanSquaredErrorsObjectiveFunction = sumMeanSquaredErrorsObjectiveFunction;
    }
}
