package com.yuan.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.yuan.activity.MainActivity;
import com.yuan.base.BaseDetailPager;
import com.yuan.base.BasePager;
import com.yuan.bean.NewsTitleBean;
import com.yuan.fagment.LeftMenuFragment;
import com.yuan.service.NewsService;
import com.yuan.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuan
 */
public class NewsPager extends BasePager {

    ArrayList<BaseDetailPager> detailPagers;

    List<NewsTitleBean.DataBean> datas;

    public NewsTitleBean newsTitleBean;

    public NewsPager(Context context) {
        super(context);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        ib_leftmenu.setVisibility(View.VISIBLE);
        initData();
    }

    @Override
    public void initData() {
        // 设置标题
        tv_title.setText("新闻");

        // 绑定数据
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);

        getData();
    }

    private void getData() {
        // 网络请求
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsTitleBean newsTitleBean = new NewsTitleBean();
        NewsService newsService = retrofit.create(NewsService.class);
        Call<NewsTitleBean> call = newsService.getInitialInfo();
        call.enqueue(new Callback<NewsTitleBean>() {
            @Override
            public void onResponse(Call<NewsTitleBean> call, Response<NewsTitleBean> response) {
                NewsTitleBean newsTitleBean = response.body();
                if (newsTitleBean != null) {
                    processData(newsTitleBean);
                }
            }
            @Override
            public void onFailure(Call<NewsTitleBean> call, Throwable t) {}
        });

    }

    private void processData(NewsTitleBean newsTitleBean) {
        datas = newsTitleBean.getDatas();

        // 添加详情页面
        detailPagers = new ArrayList<>();
        detailPagers.add(new NewsDetailPager(context, datas.get(0)));
        detailPagers.add(new TopicDetailPager(context));
        detailPagers.add(new PhotoDetailPager(context));
        detailPagers.add(new InteractionDetailPager(context));

        // 设置左侧边栏
        setLeftMenuTopics(datas);
    }

    private void setLeftMenuTopics(List<NewsTitleBean.DataBean> datas) {
        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setTopics(datas);
        leftMenuFragment.initData();
    }

    public void switchPager(int position) {
        if (position < detailPagers.size()) {
            // 设置标题
            tv_title.setText(datas.get(position).getTitle());
            // 移除内容
            fl_content.removeAllViews();
            // 添加内容
            BaseDetailPager pager = detailPagers.get(position);
            View view = pager.rootView;
            pager.initData();

            fl_content.addView(view);
        } else {
            Toast.makeText(context, "未启动页面", Toast.LENGTH_SHORT).show();
        }
    }
}
