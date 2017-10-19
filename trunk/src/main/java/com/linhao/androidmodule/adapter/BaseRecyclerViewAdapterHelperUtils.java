package com.linhao.androidmodule.adapter;

/**
 * Created by reeman on 2017/10/19.
 */

public class BaseRecyclerViewAdapterHelperUtils {
    //使用指南http://www.jianshu.com/p/b343fcff51b0

    /**
     * 使用:
     * 首先需要继承BaseQuickAdapter,然后BaseQuickAdapter<Status, BaseViewHolder>第一个泛型Status是数据实体类型，第二个BaseViewHolder是ViewHolder其目的是为了支持扩展ViewHolder。
     *
     public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
     public HomeAdapter(int layoutResId, List data) {
     super(layoutResId, data);
     }

     @Override
     protected void convert(BaseViewHolder helper, HomeItem item) {
     helper.setText(R.id.text, item.getTitle());
     helper.setImageResource(R.id.icon, item.getImageResource());
     // 加载网络图片
     Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) viewHolder.getView(R.id.iv));
     }
     }

     赋值:
     可以直接使用viewHolder对象点相关方法通过传入viewId和数据进行，方法支持链式调用。如果是加载网络图片或自定义view可以通过viewHolder.getView(viewId)获取该控件。

     Item的点击事件:
     adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
     @Override
     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
     Log.d(TAG, "onItemClick: ");
     Toast.makeText(ItemClickActivity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
     }
     });

     Item的长按事件:
     adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
     @Override
     public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
     Log.d(TAG, "onItemLongClick: ");
     Toast.makeText(ItemClickActivity.this, "onItemLongClick" + position, Toast.LENGTH_SHORT).show();
     return false;
     }
     });

     添加列表加载动画:
     开启动画(默认为渐显效果)
     adapter.openLoadAnimation();
     默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
     public static final int ALPHAIN = 0x00000001;

      // Use with {@link #openLoadAnimation}

    public static final int SCALEIN = 0x00000002;

     // Use with {@link #openLoadAnimation}

    public static final int SLIDEIN_BOTTOM = 0x00000003;

    //Use with {@link #openLoadAnimation}

    public static final int SLIDEIN_LEFT = 0x00000004;

     // Use with {@link #openLoadAnimation}

    public static final int SLIDEIN_RIGHT = 0x00000005;

     切换动画
     quickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

     自定义动画:
     quickAdapter.openLoadAnimation(new BaseAnimation() {
     @Override
     public Animator[] getAnimators(View view) {
     return new Animator[]{
     ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
     ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
     };
     }
     });

     动画默认只执行一次,如果想重复执行可设置
     mQuickAdapter.isFirstOnly(false);

     设置不显示动画数量
     adapter.setNotDoAnimationCount(count);

     首次到界面的item都依次执行加载动画
     @Override
     protected void startAnim(Animator anim, int index) {
     super.startAnim(anim, index);
     if (index < count)
     anim.setStartDelay(index * 150);
     }

     添加头部、尾部:
     mQuickAdapter.addHeaderView(getView());
     mQuickAdapter.addFooterView(getView());

     删除指定view
     mQuickAdapter.removeHeaderView(getView);
     mQuickAdapter.removeFooterView(getView);

     自动加载
     上拉加载
     // 滑动最后一个Item的时候回调onLoadMoreRequested方法
     setOnLoadMoreListener(RequestLoadMoreListener);

     回调处理代码
     mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
     @Override public void onLoadMoreRequested() {
     mRecyclerView.postDelayed(new Runnable() {
     @Override
     public void run() {
     if (mCurrentCounter >= TOTAL_COUNTER) {
     //数据全部加载完毕
     mQuickAdapter.loadMoreEnd();
     } else {
     if (isErr) {
     //成功获取更多数据
     mQuickAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
     mCurrentCounter = mQuickAdapter.getData().size();
     mQuickAdapter.loadMoreComplete();
     } else {
     //获取更多数据失败
     isErr = true;
     Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
     mQuickAdapter.loadMoreFail();

     }
     }
     }

     }, delayMillis);
     }
     }, mReyclerView);

     加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
     mQuickAdapter.loadMoreComplete();

     加载失败
     mQuickAdapter.loadMoreFail();

     加载结束
     mQuickAdapter.loadMoreEnd();

     设置自定义加载布局
     mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());
     public final class CustomLoadMoreView extends LoadMoreView {

     @Override public int getLayoutId() {
     return R.layout.view_load_more;
     }

     /**
      * 如果返回true，数据全部加载完毕后会隐藏加载更多
      * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局

    @Override public boolean isLoadEndGone() {
        return true;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    /**
     * isLoadEndGone()为true，可以返回0
     * isLoadEndGone()为false，不能返回0

    @Override protected int getLoadEndViewId() {
        return 0;
    }
}

     <FrameLayout
     xmlns:android="http://schemas.android.com/apk/res/android"
     android:layout_width="match_parent"
     android:layout_height="@dimen/dp_40">

     <LinearLayout
     android:id="@+id/load_more_loading_view"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:gravity="center"
     android:orientation="horizontal">

     <ProgressBar
     android:id="@+id/loading_progress"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     style="?android:attr/progressBarStyleSmall"
     android:layout_marginRight="@dimen/dp_4"
     android:indeterminateDrawable="@drawable/sample_footer_loading_progress"/>

     <TextView
     android:id="@+id/loading_text"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     android:layout_marginLeft="@dimen/dp_4"
     android:text="@string/loading"
     android:textColor="#0dddb8"
     android:textSize="@dimen/sp_14"/>
     </LinearLayout>

     <FrameLayout
     android:id="@+id/load_more_load_fail_view"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:visibility="gone">


     <TextView
     android:id="@+id/tv_prompt"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     android:layout_gravity="center"
     android:textColor="#0dddb8"
     android:text="@string/load_failed"/>

     </FrameLayout>

     </FrameLayout>


     多布局
     实体类必须实现MultiItemEntity，在设置数据的时候，需要给每一个数据设置itemType
     public class MultipleItem implements MultiItemEntity {
     public static final int TEXT = 1;
     public static final int IMG = 2;
     private int itemType;

     public MultipleItem(int itemType) {
     this.itemType = itemType;
     }

     @Override
     public int getItemType() {
     return itemType;
     }
     }

     在构造里面addItemType绑定type和layout的关系
     public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

     public MultipleItemQuickAdapter(List data) {
     super(data);
     addItemType(MultipleItem.TEXT, R.layout.text_view);
     addItemType(MultipleItem.IMG, R.layout.image_view);
     }

     @Override
     protected void convert(BaseViewHolder helper, MultipleItem item) {
     switch (helper.getItemViewType()) {
     case MultipleItem.TEXT:
     helper.setImageUrl(R.id.tv, item.getContent());
     break;
     case MultipleItem.IMG:
     helper.setImageUrl(R.id.iv, item.getContent());
     break;
     }
     }

     }

     如果考虑到在GridLayoutManager复用item问题可以配置
     multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
     @Override
     public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
     return data.get(position).getSpanSize();
     }
     });

     拖拽和滑动删除的回调方法
     OnItemDragListener onItemDragListener = new OnItemDragListener() {
     @Override
     public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos){}
     @Override
     public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {}
     @Override
     public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {}
     }

     OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
     @Override
     public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {}
     @Override
     public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {}
     @Override
     public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {}
     };

     adapter需要继承BaseItemDraggableAdapter
     public class ItemDragAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
     public ItemDragAdapter(List data) {
     super(R.layout.item_draggable_view, data);
     }

     @Override
     protected void convert(BaseViewHolder helper, String item) {
     helper.setText(R.id.tv, item);
     }
     }

     Activity使用代码

     mAdapter = new ItemDragAdapter(mData);

     ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
     ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
     itemTouchHelper.attachToRecyclerView(mRecyclerView);

     // 开启拖拽
     mAdapter.enableDragItem(itemTouchHelper, R.id.textView, true);
     mAdapter.setOnItemDragListener(onItemDragListener);

     // 开启滑动删除
     mAdapter.enableSwipeItem();
     mAdapter.setOnItemSwipeListener(onItemSwipeListener);

     默认不支持多个不同的 ViewType 之间进行拖拽，如果开发者有所需求：

     重写ItemDragAndSwipeCallback里的onMove()方法，return true即可

     树形列表
     例子：三级菜单
     // if you don't want to extent a class, you can also use the interface IExpandable.
     // AbstractExpandableItem is just a helper class.
     public class Level0Item extends AbstractExpandableItem<Level1Item> {...}
     public class Level1Item extends AbstractExpandableItem<Person> {...}
     public class Person {...}

     adapter需要继承BaseMultiItemQuickAdapter
     public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
     public ExpandableItemAdapter(List<MultiItemEntity> data) {
     super(data);
     addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
     addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
     addItemType(TYPE_PERSON, R.layout.item_text_view);
     }
     @Override
     protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
     switch (holder.getItemViewType()) {
     case TYPE_LEVEL_0:
     ....
     //set view content
     holder.itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
     int pos = holder.getAdapterPosition();
     if (lv0.isExpanded()) {
     collapse(pos);
     } else {
     expand(pos);
     }
     }});
     break;
     case TYPE_LEVEL_1:
     // similar with level 0
     break;
     case TYPE_PERSON:
     //just set the content
     break;
     }
     }

     开启所有菜单：
     adapter.expandAll();

     删除某一个item（添加和修改的思路是一样的）
     // 获取当前父级位置
     int cp = getParentPosition(person);
     // 通过父级位置找到当前list，删除指定下级
     ((Level1Item)getData().get(cp)).removeSubItem(person);
     // 列表层删除相关位置的数据
     getData().remove(holder.getLayoutPosition());
     // 更新视图
     notifyDataSetChanged();

     自定义ViewHolder
     需要继承BaseViewHolder
     public class MovieViewHolder extends BaseViewHolder

     然后修改adapter的第二个泛型为自定义的ViewHolder
     public class DataBindingUseAdapter extends BaseQuickAdapter<Movie, DataBindingUseAdapter.MovieViewHolder>





     */
}
