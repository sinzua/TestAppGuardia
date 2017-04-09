package com.kvprasad.zbarbarcodescanner;

/**
 * Created by JUMBO on 03/04/2017.
 */

class SearchObject {
    private String url =null;
    private String id = null;
    private String imageUrl;
    private String title;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
