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


}