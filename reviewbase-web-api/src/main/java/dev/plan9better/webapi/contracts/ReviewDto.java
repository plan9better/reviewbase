package dev.plan9better.webapi.contracts;

import dev.plan9better.data.model.enums.Constructiveness;

public class ReviewDto {
    private long id;
    private long author_id;
    private String content;
    private Constructiveness constructiveness;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Constructiveness getConstructiveness() {
        return constructiveness;
    }

    public void setConstructiveness(Constructiveness constructiveness) {
        this.constructiveness = constructiveness;
    }
}
