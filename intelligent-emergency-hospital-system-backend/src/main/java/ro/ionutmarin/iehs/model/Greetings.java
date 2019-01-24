package ro.ionutmarin.iehs.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Greetings {
    String content;

    public Greetings(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
