package com.emall.vo;

/**
 * Created by Administrator on 2017/11/30 0030.
 */
public class UploadVo {


    private String uri;
    private String url;

    public UploadVo() {
    }

    public UploadVo(String uri, String url) {

        this.uri = uri;
        this.url = url;
    }
    public UploadVo( String uri) {

        this.uri = uri;

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UploadVo{" +

                ", uri='" + uri + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
