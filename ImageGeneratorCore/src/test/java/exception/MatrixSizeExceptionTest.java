package exception;

import domain.InformationalImage;
import core.BasicImageGenerator;
import core.BasicSettings;
import core.ImageGenerator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static domain.InformationalImage.TYPE_INT_RGB;

public class MatrixSizeExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionMessage() {

        thrown.expect(MatrixSizeException.class);
        thrown.expectMessage("Count of expected columns (is 10) could not be more than image width (is 1)");

        ImageGenerator imageGenerator = new BasicImageGenerator();
        imageGenerator.setSettings(new BasicSettings() {{
            setIncomeImage(new InformationalImage(1, 1, TYPE_INT_RGB));
            setExpectedColumnsCount(10);
        }});

        imageGenerator.generateImage();
    }
}