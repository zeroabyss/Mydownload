package zero.okhttp_piccaso_recyclerview;

import java.util.List;

/**
 * Created by Aiy on 2017/4/18.
 */

public class TestBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {



        private List<WallpaperListInfoBean> WallpaperListInfo;

        public List<WallpaperListInfoBean> getWallpaperListInfo() {
            return WallpaperListInfo;
        }

        public void setWallpaperListInfo(List<WallpaperListInfoBean> WallpaperListInfo) {
            this.WallpaperListInfo = WallpaperListInfo;
        }

        public static class WallpaperListInfoBean {
            /**
             * ID : 10757698
             * PicName : 10757698.jpg
             * pic_path : /picture1/M00/0B/1A/wKiFQ1NRCaKANh-NAAMZZGCkwIk335.jpg
             * BigCategoryId : 1054
             * SecondCategoryId : 2688
             * CreateTime : 2014-04-18 19:13:18
             * passtime : 2014-03-19 19:16:51
             * UserName : sprit_admin
             * DownloadCount : 13
             * GoodCount : 13
             * tags :
             * WallPaperMiddle : http://bzpic.spriteapp.cn/250x445/picture1/M00/0B/1A/wKiFQ1NRCaKANh-NAAMZZGCkwIk335.jpg
             * WallPaperBig : http://bzpic.spriteapp.cn/1080x1920/picture1/M00/0B/1A/wKiFQ1NRCaKANh-NAAMZZGCkwIk335.jpg
             * WallPaperDownloadPath : http://bzpic.spriteapp.cn/1080x1920/picture1/M00/0B/1A/wKiFQ1NRCaKANh-NAAMZZGCkwIk335.jpg
             * WallPaperSource : http://bzpic.spriteapp.cn/744x1392/picture1/M00/0B/1A/wKiFQ1NRCaKANh-NAAMZZGCkwIk335.jpg
             * weixin_url : http://www.budejie.com/bz/bdj-10757698.html?wx.qq.com
             */

            private int ID;
            private String PicName;
            private String tags;

            public String getPicName() {
                return PicName;
            }

            public void setPicName(String picName) {
                PicName = picName;
            }

            private String WallPaperMiddle;
            private String WallPaperBig;
            private String WallPaperDownloadPath;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getWallPaperMiddle() {
                return WallPaperMiddle;
            }

            public void setWallPaperMiddle(String WallPaperMiddle) {
                this.WallPaperMiddle = WallPaperMiddle;
            }

            public String getWallPaperBig() {
                return WallPaperBig;
            }

            public void setWallPaperBig(String WallPaperBig) {
                this.WallPaperBig = WallPaperBig;
            }

            public String getWallPaperDownloadPath() {
                return WallPaperDownloadPath;
            }

            public void setWallPaperDownloadPath(String WallPaperDownloadPath) {
                this.WallPaperDownloadPath = WallPaperDownloadPath;
            }

            @Override
            public String toString() {
                return "WallpaperListInfoBean{" +
                        "ID=" + ID +
                        ", tags='" + tags + '\'' +
                        ", WallPaperMiddle='" + WallPaperMiddle + '\'' +
                        ", WallPaperBig='" + WallPaperBig + '\'' +
                        ", WallPaperDownloadPath='" + WallPaperDownloadPath + '\'' +
                        '}';
            }
        }
    }
}
