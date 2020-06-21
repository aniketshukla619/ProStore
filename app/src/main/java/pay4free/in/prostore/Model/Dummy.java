package pay4free.in.prostore.Model;

/**
 * Created by AAKASH on 09-10-2017.
 */

public class Dummy {
    private String Name;
    private String Image;
    public Dummy()
    {

    }

    public Dummy(String name, String image) {
        Name = name;
        Image = image;

    }



    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }
}
