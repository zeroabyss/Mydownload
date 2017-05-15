package zero.okhttp_piccaso_recyclerview.database;

import org.litepal.crud.DataSupport;

/**
 * Created by Aiy on 2017/5/15.
 */

public class My_Like extends DataSupport {
    private int id;
    private String name;
    private String url;
    private Boolean love;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getLove() {
        return love;
    }

    public void setLove(Boolean love) {
        this.love = love;
    }
}
