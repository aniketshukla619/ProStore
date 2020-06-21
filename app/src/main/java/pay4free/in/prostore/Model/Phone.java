package pay4free.in.prostore.Model;

/**
 * Created by AAKASH on 07-06-2018.
 */

public class Phone {
    private String Image,Name,amazon,data,Flipkart,urlf,urla,price;

    public Phone() {
    }

    public Phone(String image, String name, String amazon, String data, String flipkart, String flipkarturl, String amazonurl,String price) {
        Image = image;
        Name = name;
        this.amazon = amazon;
        this.data = data;
        this.price=price;
        Flipkart = flipkart;
        this.urlf = flipkarturl;
        this.urla = amazonurl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrlf() {
        return urlf;
    }

    public void setUrlf(String urlf) {
        this.urlf = urlf;
    }

    public String getUrla() {
        return urla;
    }

    public void setUrla(String urla) {
        this.urla = urla;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmazon() {
        return amazon;
    }

    public void setAmazon(String amazon) {
        this.amazon = amazon;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFlipkart() {
        return Flipkart;
    }

    public void setFlipkart(String flipkart) {
        Flipkart = flipkart;
    }
}
