package layers.web.vaadin.layout.images;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ImagesLayoutTest {

    @Autowired
    private ImagesLayout imagesLayout;

    @Test
    public void postConstruct() throws Exception {
        assertThat(imagesLayout.getOriginalImageView().getStyleName(), is("bordered-image"));
        assertThat(imagesLayout.getGeneratedImageView().getStyleName(), is("bordered-image"));
    }

}