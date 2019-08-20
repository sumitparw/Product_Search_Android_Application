package edu.sumitusc.productsearch;

public class SearchItem {

    private String title;
    private String img;
    private String condition;
    private String shipping;
    private String productId;
    private String zip;
    private String price;
    private String shortname;

    public SearchItem() {

    }
    public SearchItem(String title,String shortname, String productId, String img,String zip,String shipping,String condition,String price) {
        this.title = title;
        this.img = img;
        this.zip = zip;
        this.productId = productId;
        this.shipping = shipping;
        this.condition = condition;
        this.price=price;
        this.shortname=shortname;
    }

    public String getTitle() {
        return title;
    }

    public String getShipping() {
        return shipping;
    }

    public String getImg() {
        return img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}

