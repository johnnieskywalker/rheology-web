package optimization.nonlinear.unconstrained.testes;

import optimization.nonlinear.unconstrained.dataloaders.TextToListReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TextToArrayListReaderTest {
    List<Double> fileReadAsDoubles = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        TextToListReader textToArrayListReader = new TextToListReader();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("optimizationTasksData/kperzyns_aproksymacja_data/taskData/experimentalDeformation").getFile());

        fileReadAsDoubles = textToArrayListReader.readFileToDoubles(file);

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void shouldBeSize37() {
        assertEquals(new Integer(37), new Integer(fileReadAsDoubles.size()));
    }

    @Test
    public void firstElementOfListEqualsFirstValueFromFile() {
        assertEquals(0.000903, new Double(fileReadAsDoubles.get(0)));
    }

    @Test
    public void lastElementEqualsLastValueFromFile() {
        assertEquals(0.3571, new Double(fileReadAsDoubles.get(36)));
    }

}