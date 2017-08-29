package layers.web.vaadin.listeners;

import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

@SpringComponent
public class UploadProgressListenerComponent implements UploadProgressListener {

    @Autowired
    private UploadStartedListener startedListener;

    @Resource(name = "notifications")
    private List<String> notifications;

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        int maxSize = 10485760; // 10485760 (Bytes) = 10MB

        if (maxSize < contentLength) {
            startedListener.getUpload().interruptUpload();

            notifications.add("Oh, no! File size can not be more then 10 MB.");
        }
    }
}
