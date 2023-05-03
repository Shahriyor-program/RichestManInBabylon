package tjk.programs.richestmaninbabylon.model;
import java.util.HashMap;
import java.util.Map;
public class Root {
    private String link;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}