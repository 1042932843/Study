package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.FragmentAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Base.DuskyApp;
import com.dy.ustudyonline.Design.imagePicker.SelectDialog;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.User;
import com.dy.ustudyonline.Module.fragment.HomePageTab1Fragment;
import com.dy.ustudyonline.Module.fragment.HomePageTab2Fragment;
import com.dy.ustudyonline.Module.fragment.HomePageTab3Fragment;
import com.dy.ustudyonline.Module.fragment.HomePageTab4Fragment;
import com.dy.ustudyonline.Module.fragment.HomePageTab5Fragment;
import com.dy.ustudyonline.Net.ApiConstants;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.CommonUtil;
import com.dy.ustudyonline.Utils.PgyUpdateManagerListener;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.dy.ustudyonline.Base.DuskyApp.optionsRoundedCircle;


public class MainActivity extends BaseActivity {
    protected ProgressDialog pdialog;
    public static final String openDrawer = "drawer";
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> selImageList=new ArrayList<>(); //当前选择的所有图片
    private int maxImgCount = 1;               //允许选择图片最大数
    List<File> files=new ArrayList<>();
    public static final int RESULT_CODE_FP = 1042;
    ArrayList<ImageItem> images = null;

    @OnClick(R.id.zhsz)
    public void goAccountSet(){
        startActivity(new Intent(MainActivity.this,AccountSetActivity.class));
    }
    @OnClick(R.id.pwdset)
    public void gopwdSet(){
        startActivity(new Intent(MainActivity.this,PwResetActivity.class));
    }
    @OnClick(R.id.def)
    public void defTerrace(){
        startActivity(new Intent(MainActivity.this,AccountSetActivity.class));
    }
    @OnClick(R.id.change)
    public void changeTerrace(){
        startActivity(new Intent(MainActivity.this,AccountSetActivity.class));
    }

    @BindView(R.id.drawer)
    DrawerLayout drawer;

    @BindView(R.id.ver)
    TextView ver;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.user_head)
    ImageView userhead;

    @BindView(R.id.name)
    TextView name;

    @OnClick(R.id.user_head)
    public void changeAvatar() {
        if (!DuskyApp.getInstance().isLogged()) {
            CommonUtil.goLogin(this);
        } else {
            List<String> names = new ArrayList<>();
            names.add("拍照");
            names.add("相册");
            showDialog(new SelectDialog.SelectDialogListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0: // 直接调起相机
                            /**
                             * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                             *
                             * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                             *
                             * 如果实在有所需要，请直接下载源码引用。
                             */
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount);
                            Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                            break;
                        case 1:
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount);
                            Intent intent1 = new Intent(MainActivity.this, ImageGridActivity.class);
                            /* 如果需要进入选择的时候显示已经选中的图片，
                             * 详情请查看ImagePickerActivity
                             * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                            startActivityForResult(intent1, REQUEST_CODE_SELECT);
                            break;
                        default:
                            break;
                    }

                }
            }, names);
        }

    }

    @OnClick(R.id.tc)
    public void logout() {
        CommonUtil.logOut(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String cmd) {
        switch (cmd) {
            case openDrawer:
                openDrawer();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if(!DuskyApp.getInstance().isLogged()){
            CommonUtil.goLogin(this);
        }
    }

    public void openDrawer() {
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START);
        } else {
            drawer.openDrawer(Gravity.START);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        update();
        initBottomNavigationBar();
        initFragments();
        ver.setHint("版本号 "+DuskyApp.getInstance().getAppVersionName(this));
        name.setText(PreferenceUtil.getStringPRIVATE("realName","未登录"));
        if(!TextUtils.isEmpty(PreferenceUtil.getStringPRIVATE("imageUrl",""))){
            Glide.with(MainActivity.this).load(ApiConstants.Base_URL+PreferenceUtil.getStringPRIVATE("imageUrl","")).apply(optionsRoundedCircle).into(userhead);
        }

    }


    private void initFragments() {
        List<String> titles = new ArrayList<>();
        List<Fragment> mFragments = new ArrayList<>();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, titles);

        mFragments.add(new HomePageTab1Fragment());
        mFragments.add(new HomePageTab2Fragment());
        mFragments.add(new HomePageTab3Fragment());
        mFragments.add(new HomePageTab4Fragment());
        mFragments.add(new HomePageTab5Fragment());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bottomNavigationBar.selectTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.syh2x, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.xkh2x, "选课"))
                .addItem(new BottomNavigationItem(R.drawable.xxh2x, "学习"))
                .addItem(new BottomNavigationItem(R.drawable.zxh2x, "资讯"))
                .addItem(new BottomNavigationItem(R.drawable.gdh2x, "更多"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void update() {
        try {
            PgyUpdateManager.register(this, new PgyUpdateManagerListener(this));
        } catch (Exception e) {
            PgyCrashManager.reportCaughtException(this, e);
            ToastUtil.ShortToast("检查更新失败");
        }
    }


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    Log.d("reg", "images:" + images.get(0).path);
                    selImageList=images;
                    files.clear();
                    File file = new File(images.get(0).path);
                    Glide.with(MainActivity.this).load(file).apply(optionsRoundedCircle).into(userhead);
                    pdialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
                    pdialog.setMessage("头像上传中...");
                    pdialog.show();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getPath(), options);
                    if (options.outWidth > 720 || options.outHeight > 720) {
                        options.inSampleSize = Math.max(options.outWidth, options.outHeight) / 720;
                    }
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if (null != bitmap) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                        bitmap.recycle();
                        RetrofitHelper.gethomePageTab1API()
                                .uploadPicture(PreferenceUtil.getStringPRIVATE("id",""),Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT))
                                .compose(this.bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(bean -> {
                                    String a=bean.string();
                                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                                    String state = apiMsg.getState();
                                    switch (state){
                                        case "0000":
                                            ToastUtil.ShortToast(apiMsg.getMessage());
                                            PreferenceUtil.putStringPRIVATE("imageUrl",apiMsg.getImgPath());
                                            break;
                                        case "-1":
                                        case "-2":
                                        default:
                                            ToastUtil.ShortToast(apiMsg.getMessage());
                                            break;
                                    }

                                    pdialog.dismiss();
                                }, throwable -> {
                                    pdialog.dismiss();
                                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                                });
                    }


                }
            }
        }
    }

}
