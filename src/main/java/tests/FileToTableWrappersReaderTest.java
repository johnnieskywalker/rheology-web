package tests;

import dataloaders.FileToTableWrappersReader;
import org.junit.Before;
import org.junit.Test;
import view.wrappers.TableRowWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class FileToTableWrappersReaderTest {
    List<TableRowWrapper> tableWrappers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        FileToTableWrappersReader textToArrayListReader = new FileToTableWrappersReader();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource
                ("optimizationTasksData/kperzyns_aproksymacja_data/taskData" +
                        "/experimetnalDeformationAndStressSpaceSeparated.txt").getFile());

        tableWrappers = textToArrayListReader.readFileToTableWrappers(file);
    }

    @Test
    public void shouldBeSize37() {
        assertEquals(new Integer(37), new Integer(tableWrappers.size()));
    }

    @Test
    public void firstElementOfListEqualsFirstValueFromFile() {
        assertEquals(0.000903, new Double(tableWrappers.get(0).getDeformation()));
    }

    @Test
    public void lastElementEqualsLastValueFromFile() {
        assertEquals(0.3571, new Double(tableWrappers.get(36).getDeformation()));
    }
}
