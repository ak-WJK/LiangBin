package com.wjk2288.liangbin.activity.shop.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.dao.UserDAO;
import com.wjk2288.liangbin.activity.shop.adapter.CartAdapter;
import com.wjk2288.liangbin.activity.shop.bean.CartBean;
import com.wjk2288.liangbin.activity.shop.view.WrapContentLinearLayoutManager;
import com.wjk2288.liangbin.activity.utils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class CartActivity extends AppCompatActivity {

    @Bind(R.id.ib_cart_back)
    ImageButton ibCartBack;
    @Bind(R.id.tv_shoppingcart)
    TextView tvShoppingcart;
    @Bind(R.id.ib_cart_edit)
    TextView ibCartEdit;
    @Bind(R.id.jianman)
    TextView jianman;
    @Bind(R.id.zhekou)
    TextView zhekou;
    @Bind(R.id.baozhuang)
    TextView baozhuang;
    @Bind(R.id.baoyou)
    TextView baoyou;
    @Bind(R.id.cb_cart_select)
    CheckBox cbCartSelect;
    @Bind(R.id.tv_cart_totalprice)
    TextView tvCartTotalprice;
    @Bind(R.id.btn_jiesuan)
    Button btnJiesuan;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.ll)
    LinearLayout ll;
    private CartAdapter adapter;
    private ArrayList<CartBean> cartGoods;

    boolean isShowItem = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        ibCartEdit.setText("编辑");

        adapter = new CartAdapter(this, tvCartTotalprice, cbCartSelect, ibCartEdit);
        recyclerview.setAdapter(adapter);

        //设置item的间距
        int pixelSize = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerview.addItemDecoration(new SpaceItemDecoration(pixelSize));


        recyclerview.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //查询数据库
        cartGoods = UserDAO.getInstance().getCartGoods();

        adapter.refreshData(cartGoods);

        adapter.showTotalPrice();

        initListener();


    }

    private void initListener() {
        adapter.setOnClickItemListener(new CartAdapter.onLongClickItemListener() {
            @Override
            public void onLongItemClick(View itemView, int position) {

                showShare(position);

            }
        });


    }


    @OnClick({R.id.ib_cart_back, R.id.ib_cart_edit, R.id.cb_cart_select, R.id.btn_jiesuan, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_cart_back:
                finish();
                break;
            case R.id.ib_cart_edit:

                isShowItem = !isShowItem;
//
                if (isShowItem) {
                    ibCartEdit.setText("编辑");
                    btnJiesuan.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.GONE);

                    boolean checked = true;
                    adapter.allCheckNone(true);
                    cbCartSelect.setChecked(checked);
                    adapter.notifyDataSetChanged();

                } else {
                    ibCartEdit.setText("完成");
                    btnJiesuan.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.VISIBLE);

                    boolean checked = false;
                    adapter.allCheckNone(checked);
                    cbCartSelect.setChecked(checked);
                    adapter.notifyDataSetChanged();
                }


                break;
            case R.id.cb_cart_select:

                boolean checked = cbCartSelect.isChecked();
                adapter.allCheckNone(checked);
                adapter.showTotalPrice();
                break;
            case R.id.btn_jiesuan:


                break;
            case R.id.btn_delete:
                LogUtils.e("TAG", "delete=== ");
                adapter.deleteItem();

                break;
        }
    }

    //设置item的间距
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }


    private void showShare(int position) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        String imageUrl = cartGoods.get(position).getImageUrl();

        oks.setTitleUrl(imageUrl);
        // text是分享文本，所有平台都需要这个字段
        String goodsContent = cartGoods.get(position).getGoodsContent();

        oks.setText(goodsContent);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(imageUrl);

        // 启动分享GUI
        oks.show(this);
    }

}