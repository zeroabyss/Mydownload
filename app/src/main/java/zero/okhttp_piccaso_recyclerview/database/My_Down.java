package zero.okhttp_piccaso_recyclerview.database;

import org.litepal.crud.DataSupport;

/**
 * Created by Aiy on 2017/5/11.
 */

public class My_Down extends DataSupport {
    private String name;
    private String url;

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
}
