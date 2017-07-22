package com.wjk2288.liangbin.activity.shop.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.dao.UserDAO;
import com.wjk2288.liangbin.activity.shop.adapter.CartAdapter;
import com.wjk2288.liangbin.activity.shop.bean.CartBean;
import com.wjk2288.liangbin.activity.shop.view.WrapContentLinearLayoutManager;
import com.wjk2288.liangbin.activity.utils.LogUtils;
import com.wjk2288.liangbin.activity.utils.PayKeys;
import com.wjk2288.liangbin.activity.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

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
                //集成支付宝
//                Intent intent = new Intent(CartActivity.this, PayDemoActivity.class);
//                startActivity(intent);

                showPay(0.01);

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


    //分享
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


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(CartActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(CartActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(CartActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(CartActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    //调起支付宝
    private void showPay(double money) {
        // 订单
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", money + "");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(CartActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, false);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    //商户PID
    public static final String PARTNER = PayKeys.DEFAULT_PARTNER;
    //商户收款账号
    public static final String SELLER = PayKeys.DEFAULT_SELLER;
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = PayKeys.PRIVATE;
    //支付宝公钥
    public static final String RSA_PUBLIC = PayKeys.PUBLIC;


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }


    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


}