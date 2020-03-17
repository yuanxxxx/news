package com.yuan.pager.tabpager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yuan.R;
import com.yuan.bean.NewsTitleBean;
import com.yuan.bean.TabDetailBean;
import com.yuan.service.NewsService;
import com.yuan.ui.HorizontalScrollViewPager;
import com.yuan.ui.RefreshListView;
import com.yuan.utils.Constant;
import com.yuan.utils.DensityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

/**
 * @author yuan
 */
public class TabTopicPager {

    public View rootView;

    public Context context;

    public NewsTitleBean.DataBean.ChildrenBean newsData;

    public RefreshListView listView;
    private HorizontalScrollViewPager viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private List<TabDetailBean.Data.TopNews> topnews;
    private List<TabDetailBean.Data.News> news;
    private int prePosition;
    private boolean isLoadMore = false;
    private String moreUrl;
    private TabDetailListAdapter adapter;

    public TabTopicPager(Context context, NewsTitleBean.DataBean.ChildrenBean newsData) {
        this.context = context;
        this.newsData = newsData;
    }

    public void initView() {
        View view = View.inflate(context, R.layout.tab_details, null);
        listView = view.findViewById(R.id.lv_tabdetail);
        View topnews = View.inflate(context, R.layout.topnews, null);
        viewpager = (HorizontalScrollViewPager) topnews.findViewById(R.id.viewpager);
        tv_title = (TextView) topnews.findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) topnews.findViewById(R.id.ll_point_group);
        listView.addHeaderTopNewsView(topnews);
        listView.setOnRefreshListener(new RefreshListView.onRefreshListener() {
            @Override
            public void onDropDownRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                initData();
            }
        });
        rootView = listView;
        initData();
    }

    public void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsService newsService = retrofit.create(NewsService.class);
        if (!isLoadMore) {
            // 正常请求
            String url = newsData.getUrl();
            Call<TabDetailBean> call = newsService.tabDetails(url.substring(1));
            call.enqueue(new Callback<TabDetailBean>() {
                @Override
                public void onResponse(Call<TabDetailBean> call, Response<TabDetailBean> response) {
                    TabDetailBean tabDetailBean = response.body();
                    processData(tabDetailBean);
                    listView.onRefreshFinish(true);
                }

                @Override
                public void onFailure(Call<TabDetailBean> call, Throwable t) {
                    listView.onRefreshFinish(false);
                }
            });

        } else {
            // 加载更多
            Call<TabDetailBean> call = newsService.moreDetails(moreUrl);
            call.enqueue(new Callback<TabDetailBean>() {
                @Override
                public void onResponse(Call<TabDetailBean> call, Response<TabDetailBean> response) {
                    TabDetailBean tabDetailBean = response.body();
                    processData(tabDetailBean);
                    listView.onRefreshFinish(false);
                }

                @Override
                public void onFailure(Call<TabDetailBean> call, Throwable t) {
                    listView.onRefreshFinish(false);
                }
            });
        }
    }

    private void processData(TabDetailBean bean) {

        if (TextUtils.isEmpty(bean.getData().getMore())) {
            moreUrl = "";
        } else {
            moreUrl = bean.getData().getMore().substring(1);
        }

        if (!isLoadMore) {
            topnews = bean.getData().getTopnews();
            news = bean.getData().getNews();
            addPoint();
            tv_title.setText(topnews.get(0).getTitle());
            viewpager.setAdapter(new TabDetailPagerAdapter());
            viewpager.addOnPageChangeListener(new MyPagerChangeListener());
            adapter = new TabDetailListAdapter();
            listView.setAdapter(adapter);
        } else {
            isLoadMore = false;
            news.addAll(bean.getData().getNews());
            adapter.notifyDataSetChanged();
        }

    }

    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            ll_point_group.getChildAt(position).setEnabled(true);
            tv_title.setText(topnews.get(position).getTitle());
            prePosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class TabDetailListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_tabdetail_pager, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (SimpleDraweeView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TabDetailBean.Data.News newsData = news.get(position);
            String imageUrl = Constant.BASE_URL + newsData.getListimage();
            viewHolder.iv_icon.setImageURI(imageUrl);
            viewHolder.tv_title.setText(newsData.getTitle());
            viewHolder.tv_time.setText(newsData.getPubdate());

            return convertView;
        }
    }

    static class ViewHolder {
        SimpleDraweeView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    class TabDetailPagerAdapter extends PagerAdapter {
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            simpleDraweeView.setBackgroundResource(R.drawable.home_scroll_default);
            simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(simpleDraweeView);
            String imageUrl = Constant.BASE_URL + topnews.get(position).getTopimage();
            simpleDraweeView.setImageURI(imageUrl);

            return simpleDraweeView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    private void addPoint() {
        ll_point_group.removeAllViews();//移除所有的红点

        for (int i = 0; i < topnews.size(); i++) {

            ImageView imageView = new ImageView(context);
            //设置背景选择器
            imageView.setBackgroundResource(R.drawable.point_selector);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtils.dip2px(context, 5), DensityUtils.dip2px(context, 5));

            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtils.dip2px(context, 8);
            }


            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);

        }
    }
}
