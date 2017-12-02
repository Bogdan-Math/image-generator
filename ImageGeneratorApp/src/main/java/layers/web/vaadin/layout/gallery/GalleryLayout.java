package layers.web.vaadin.layout.gallery;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import layers.repository.GalleryImageRepository;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

import static java.lang.Integer.min;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.iterate;

@SpringComponent
@Scope("session")
public class GalleryLayout extends VerticalLayout {

    private static final String GALLERY_STYLE = "gallery-image";

    private GalleryImageRepository galleryImageRepository;

    private int columnsCount;

    @PostConstruct
    public void postConstruct() {
        this.columnsCount = 4;
        addComponent(allImagesLayout());
    }

    private VerticalLayout allImagesLayout() {
        VerticalLayout allImagesLayout = new VerticalLayout();
        allImagesLayout.setSizeFull();

        pagedGallery(galleryImageRepository.getAll()
                .stream()
                .map(galleryImage -> new Image() {{
                    setSource(new StreamResource(() -> new ByteArrayInputStream(galleryImage.getBytes()), "1"));
                    setStyleName(GALLERY_STYLE);
                }})
                .collect(toList())).forEach(list -> allImagesLayout.addComponent(addNewLine(list)));

        return allImagesLayout;
    }

    private List<List<Image>> pagedGallery(List<Image> list) {
        return iterate(0, i -> i + columnsCount).limit((list.size() + columnsCount - 1) / columnsCount)
                                                  .boxed()
                                                  .map(i -> list.subList(i, min(i + columnsCount, list.size())))
                                                  .collect(toList());
    }

    private HorizontalLayout addNewLine(List<Image> images) {
        HorizontalLayout newLine = new HorizontalLayout();
        images.forEach(image -> {
            newLine.addComponent(image);
            newLine.setComponentAlignment(image, Alignment.TOP_CENTER);
        });
        newLine.setSizeFull();
        return newLine;
    }

    @Resource(name = "galleryImageRepository")
    public void setGalleryImageRepository(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }
}
