package layers.service;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import layers.repository.PatternImageRepository;
import model.PatternImage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import system.ResourceReader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

import static domain.PatternType.*;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CachedPatternsServiceTest {

    @Autowired
    private CachedPatternsService patternsService;

    @Autowired
    private PatternImageRepository repository;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));

        final byte[] bytes = readAllBytes(get(new ResourceReader()
                .readFile("images/plains/white.jpg")
                .getAbsolutePath()));

        when(repository.getCommons()).thenReturn(new ArrayList<PatternImage>() {{
            add(new PatternImage() {{
                setBytes(bytes);
            }});
            add(new PatternImage() {{
                setBytes(bytes);
            }});
            add(new PatternImage() {{
                setBytes(bytes);
            }});
        }});

        when(repository.getFlags()).thenReturn(new ArrayList<PatternImage>() {{
            add(new PatternImage() {{
                setBytes(bytes);
            }});
            add(new PatternImage() {{
                setBytes(bytes);
            }});
        }});

        when(repository.getPlains()).thenReturn(new ArrayList<PatternImage>() {{
            add(new PatternImage() {{
                setBytes(bytes);
            }});
        }});

    }

    @Test
    public void cacheAndGetAllPatterns() throws Exception {
        patternsService.cacheAllPatterns();
        Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns = patternsService.getAllPatterns();

        assertThat(allPatterns.size(), is(3));
        assertThat(allPatterns.get(COMMONS).size(), is(1));
        assertThat(allPatterns.get(FLAGS).size(),   is(1));
        assertThat(allPatterns.get(PLAINS).size(),  is(1));
    }

    @Test
    public void twoSameAveragedColors() throws Exception {
        patternsService.cacheAllPatterns();
        Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns = patternsService.getAllPatterns();

        assertNotNull(allPatterns);
        assertThat(outContent.toString(), containsString("Two same averaged colors:"));
    }

}