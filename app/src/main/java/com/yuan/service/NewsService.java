package com.yuan.service;

import com.yuan.bean.NewsTitleBean;
import com.yuan.bean.TabDetailBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface NewsService {

    @GET("static/api/news/categories.json")
    Call<NewsTitleBean> getInitialInfo();

    @GET()
    Call<TabDetailBean> tabDetails(@Url String url);
}
