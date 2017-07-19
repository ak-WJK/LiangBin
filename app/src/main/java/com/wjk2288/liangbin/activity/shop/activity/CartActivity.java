package com.wjk2288.liangbin.activity.shop.activity;

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
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.ll)
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        CartAdapter adapter = new CartAdapter(this);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //查询数据库
        CartBean cartGoods = UserDAO.getInstance().getCartGoods();
        adapter.refreshData(cartGoods);



    }

    @OnClick({R.id.ib_cart_back, R.id.ib_cart_edit, R.id.cb_cart_select, R.id.btn_jiesuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_cart_back:
                finish();
                break;
            case R.id.ib_cart_edit:

                break;
            case R.id.cb_cart_select:

                break;
            case R.id.btn_jiesuan:


                break;
        }
    }
}