package utility.helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ObjectTypeConverterTest {

    private ResourceReader resourceReader;
    private ObjectTypeConverter converter;
    private File originalImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.resourceReader = new ResourceReader();
        this.converter      = new ObjectTypeConverter();
        this.originalImage  = resourceReader.readFile("images/canonical.jpg");
    }

    @After
    @SuppressWarnings("ConstantConditions")
    public void tearDown() throws Exception {
        Arrays.stream(resourceReader.readFile("images/").listFiles())
                .filter(file -> ("canonical_GEN.jpg".equals(file.getName())))
                .forEach(file -> System.out.println(file.delete()));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void inputStreamFromBytes() throws Exception {
        assertTrue(converter.inputStream(new byte[1]) instanceof InputStream);
    }

    @Test
    public void inputStreamFromBufferedImage() throws Exception {
        assertNotNull(converter.inputStream(ImageIO.read(originalImage)));
    }

    @Test
    public void inputStreamFromBufferedImageException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.inputStream((BufferedImage) null));
    }

    @Test
    public void bufferedImageFromBytes() throws Exception {
        assertNotNull(converter.bufferedImage(Files.readAllBytes(Paths.get(originalImage.getAbsolutePath()))));
    }

    @Test
    public void bufferedImageFromBytesException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.bufferedImage((byte[]) null));
    }

    @Test
    public void bufferedImageFromFile() throws Exception {
        assertNotNull(converter.bufferedImage(originalImage));
    }

    @Test
    public void bufferedImageFromFileException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.bufferedImage((File) null));
    }
}