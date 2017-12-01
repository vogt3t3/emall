package com.emall.vo;

/**
 * Created by Administrator on 2017/11/30 0030.
 */
public class FullUploadVo {


    private String file_path;
    private String msg;
    private String success;

    public FullUploadVo() {
    }

    public FullUploadVo(String file_path, String msg, String success) {
        this.file_path = file_path;
        this.msg = msg;
        this.success = success;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "FullUploadVo{" +
                "file_path='" + file_path + '\'' +
                ", msg='" + msg + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
