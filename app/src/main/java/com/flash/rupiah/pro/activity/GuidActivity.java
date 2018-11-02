package com.flash.rupiah.pro.activity;

import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.flash.rupiah.pro.R;

import java.util.ArrayList;

/**
 * @author BloodFragance
 * @ClassName GuidActivity
 * @time 2017/2/20 17:49
 * @desc
 */
public class GuidActivity extends BasicActivity {

    /**
     * 装分页显示的view的数组
     */
    private ArrayList<View> pageViews;

    private ViewPager viewPager;
    /**
     * 立即体验
     */
    private TextView guide_vp_bt;

    private int[] imageRes = {R.mipmap.guide_one, R.mipmap.guide_two, R.mipmap.guide_three};

    /**
     * 当前图片的索引号
     */
    private int currentItem = 0;

    /**
     * 互动翻页所需滚动的长度是当前屏幕宽度的1/3
     */
    private int flaggingWidth;

    /**
     * 用户滑动
     */
    private GestureDetector gestureDetector;
    Handler handler;

    @Override
    protected int getResourceId() {
        return R.layout.activity_guid;
    }


    @Override
    protected void init() {
        pageViews = new ArrayList<>();
        for (int image : imageRes) {
            ImageView vew = new ImageView(this);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                    .MATCH_PARENT);
            vew.setLayoutParams(params);
            vew.setImageResource(image);
            vew.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pageViews.add(vew);
        }
        handler = new Handler();
        handler.postDelayed(new TimerRunnable(), 5000);
    }

    class TimerRunnable implements Runnable {

        @Override
        public void run() {
            int curItem = viewPager.getCurrentItem();
            if (pageViews != null && curItem < pageViews.size()) {
                viewPager.setCurrentItem(curItem + 1);
                if (handler != null) {
                    handler.postDelayed(this, 5000);
                }
            }

        }
    }

    @Override
    protected void initView() {
        // 创建imageviews数组，大小是要显示的图片的数量
        viewPager = (ViewPager) findViewById(R.id.guidePages);
        guide_vp_bt = (TextView) findViewById(R.id.guide_vp_bt);
    }

    @Override
    protected void initData() {
        // 设置viewpager的适配器和监听事件
        viewPager.setAdapter(new GuidePageAdapter());
        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;
        gestureDetector = new GestureDetector(new GuideViewTouch());
    }

    @Override
    protected void initListener() {
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        guide_vp_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = null;
                startNewActivity();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }


    private class GuideViewTouch extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (currentItem == (pageViews.size())) {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY()) && (e1.getX
                        () - e2.getX() <= (-flaggingWidth) || e1.getX() - e2.getX() >=
                        flaggingWidth)) {
                    if (e1.getX() - e2.getX() >= flaggingWidth) {
                        GoToMainActivity();
                        return true;
                    }
                }
            }
            return false;
        }

    }

    class GuidePageAdapter extends PagerAdapter {

        /**
         * 销毁position位置的界面
         */
        @Override
        public void destroyItem(View v, int position, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) v).removeView(pageViews.get(position));

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        /**
         * 获取当前窗体界面数
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pageViews.size();
        }

        /**
         * 初始化position位置的界面
         */
        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager) v).addView(pageViews.get(position));

            return pageViews.get(position);
        }

        /**
         * 判断是否由对象生成界面
         */
        @Override
        public boolean isViewFromObject(View v, Object arg1) {
            // TODO Auto-generated method stub
            return v == arg1;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            currentItem = position;
            if (currentItem < pageViews.size() - 1) {
                guide_vp_bt.setVisibility(View.GONE);
            } else {
                guide_vp_bt.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pageViews != null && pageViews.size() > 0) {
            pageViews.clear();
            pageViews = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}