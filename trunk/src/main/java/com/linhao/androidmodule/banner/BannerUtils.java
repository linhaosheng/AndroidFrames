package com.linhao.androidmodule.banner;

/**
 * Created by reeman on 2017/10/18.
 */

public class BannerUtils {


    /**
     * 使用步骤:  https://github.com/youth5201314/banner
     *
     1 . 在布局文件中添加Banner

     <com.youth.banner.Banner
     xmlns:app="http://schemas.android.com/apk/res-auto"
     android:id="@+id/banner"
     android:layout_width="match_parent"
     android:layout_height="高度自己设置" />

     2. 重写图片加载器

     //
     /**
     注意：
     1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
     2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
     传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
     切记不要胡乱强转！


     public class GlideImageLoader extends ImageLoader {
    @Override public void displayImage(Context context, Object path, ImageView imageView) {
    //Glide 加载图片简单用法
    Glide.with(context).load(path).into(imageView);

    //Picasso 加载图片简单用法
    Picasso.with(context).load(path).into(imageView);

    //用fresco加载图片简单用法，记得要写下面的createImageView方法
    Uri uri = Uri.parse((String) path);
    imageView.setImageURI(uri);
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override public ImageView createImageView(Context context) {
    //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
    SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
    return simpleDraweeView;
    }
    }


     3.  在Activity或者Fragment中配置Banner

     --------------------------简单使用-------------------------------
     @Override protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
     Banner banner = (Banner) findViewById(R.id.banner);
     //设置图片加载器
     banner.setImageLoader(new GlideImageLoader());
     //设置图片集合
     banner.setImages(images);
     //banner设置方法全部调用完毕时最后调用
     banner.start();
     }
     --------------------------详细使用-------------------------------
     @Override protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
     Banner banner = (Banner) findViewById(R.id.banner);
     //设置banner样式
     banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
     //设置图片加载器
     banner.setImageLoader(new GlideImageLoader());
     //设置图片集合
     banner.setImages(images);
     //设置banner动画效果
     banner.setBannerAnimation(Transformer.DepthPage);
     //设置标题集合（当banner样式有显示title时）
     banner.setBannerTitles(titles);
     //设置自动轮播，默认为true
     banner.isAutoPlay(true);
     //设置轮播时间
     banner.setDelayTime(1500);
     //设置指示器位置（当banner模式中有指示器时）
     banner.setIndicatorGravity(BannerConfig.CENTER);
     //banner设置方法全部调用完毕时最后调用
     banner.start();
     }


     -----------------当然如果你想偷下懒也可以这么用--------------------
     @Override protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
     Banner banner = (Banner) findViewById(R.id.banner);
     banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
     }


     4  （可选）增加体验

     //如果你需要考虑更好的体验，可以这么操作
     @Override protected void onStart() {
     super.onStart();
     //开始轮播
     banner.startAutoPlay();
     }

     @Override protected void onStop() {
     super.onStop();
     //结束轮播
     banner.stopAutoPlay();
     }


     */
}
