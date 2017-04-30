package com.rilevamento;

/**
 * Created by JUMBO on 03/04/2017.
 */

public class SearchObject {
    private String url =null;



    private String ean =null;
    private String id = null; //asin
    private String imageUrl =null;
    private String title =null;
    private String manufacturer =null;
    private String productGroup =null;


    public String getEan() {return ean;}

    public void setEan(String asin) {this.ean= asin;}

    public String getManufacturer() { return manufacturer;}

    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer;}

    public String getProductGroup() {return productGroup;}

    public void setProductGroup(String productGroup) { this.productGroup = productGroup;}

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
        return this.title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {return this.id;}

    public String getUrl() {
        return this.url;
    }


}
/*
    public SearchObject (String url, String id, String imageUrl, String title) {
        this.url = url;
        this.id= id;
        this.imageUrl= imageUrl;
        this.title = title;
    }
*/