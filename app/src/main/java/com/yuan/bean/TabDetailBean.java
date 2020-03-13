package com.yuan.bean;

import java.util.List;

/**
 * @author yuan
 * @date 2020/3/11 14:05
 */
public class TabDetailBean {

    public int retcode;
    public Data data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        public String title;
        public String countcommenturl;
        public String more;
        public List<String> topic;
        public List<News> news;
        public List<TopNews> topnews;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public List<String> getTopic() {
            return topic;
        }

        public void setTopic(List<String> topic) {
            this.topic = topic;
        }

        public List<News> getNews() {
            return news;
        }

        public void setNews(List<News> news) {
            this.news = news;
        }

        public List<TopNews> getTopnews() {
            return topnews;
        }

        public void setTopnews(List<TopNews> topnews) {
            this.topnews = topnews;
        }

        public static class TopNews {

            /**
             * comment : true
             * commentlist : /static/api/news/10007/53/147253/comment_1.json
             * commenturl : /client/user/newComment/147253
             * id : 147253
             * pubdate : 2015-10-19 07:18
             * title : 市教委：中高考英语试卷结构不变
             * topimage : /static/images/2015/10/19/36/1053274969EORV.jpg
             * type : news
             * url : /static/html/2015/10/19/714C6E504A6F1B7869277C42.html
             */

            private boolean comment;
            private String commentlist;
            private String commenturl;
            private int id;
            private String pubdate;
            private String title;
            private String topimage;
            private String type;
            private String url;

            public boolean isComment() {
                return comment;
            }

            public void setComment(boolean comment) {
                this.comment = comment;
            }

            public String getCommentlist() {
                return commentlist;
            }

            public void setCommentlist(String commentlist) {
                this.commentlist = commentlist;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTopimage() {
                return topimage;
            }

            public void setTopimage(String topimage) {
                this.topimage = topimage;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class News {
            /**
             * comment : true
             * commentlist : /static/api/news/10007/61/147261/comment_1.json
             * commenturl : /client/user/newComment/147261
             * id : 147261
             * listimage : /static/images/2015/10/19/34/1987564164OD60.jpg
             * pubdate : 2015-10-19 07:44
             * title : 河北“大头娃娃”出生即患脑积水 与母蜗居30年
             * type : news
             * url : /static/html/2015/10/19/75486F51496C197A6F227844.html
             */

            private boolean comment;
            private String commentlist;
            private String commenturl;
            private int id;
            private String listimage;
            private String pubdate;
            private String title;
            private String type;
            private String url;

            public boolean isComment() {
                return comment;
            }

            public void setComment(boolean comment) {
                this.comment = comment;
            }

            public String getCommentlist() {
                return commentlist;
            }

            public void setCommentlist(String commentlist) {
                this.commentlist = commentlist;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getListimage() {
                return listimage;
            }

            public void setListimage(String listimage) {
                this.listimage = listimage;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }


}
