package com.ptyl.uang.flash.bean;

import java.util.List;

/**
 * Created by heise on 2018/6/13.
 */

public class UrlBean {

    private List<FilesBean> files;

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class FilesBean {
        /**
         * fileType : KTP_PHOTO
         * url : http://localhost/uploadfile/100006/180524
         * /1527133235570_KTP_PHOTO_14024766391647748.jpg
         */

        private String fileType;
        private String url;

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
