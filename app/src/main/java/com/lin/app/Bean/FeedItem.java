package com.lin.app.Bean;

public class FeedItem implements FeedItemModelInterface {

    public final static String FAKE_FOOTER_FEED_KEY = "8d777f385d3dfec8815d20f7496026dc";

    private String title;
    private String description;
    private String avatar;
    private String picture;

    @Override
    public void setAuthor(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setAvatar(String thumbnail) {
        this.avatar = thumbnail;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

}
