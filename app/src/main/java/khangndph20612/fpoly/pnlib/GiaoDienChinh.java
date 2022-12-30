package khangndph20612.fpoly.pnlib;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.SachDAO;
import khangndph20612.fpoly.pnlib.DAO.ThuThuDAO;
import khangndph20612.fpoly.pnlib.ThucThe.ThuThu;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialogselectedimg;
import khangndph20612.fpoly.pnlib.taiKhoan.doimatkhau;
import khangndph20612.fpoly.pnlib.taiKhoan.taoTaiKhoan;
import me.relex.circleindicator.CircleIndicator3;

public class GiaoDienChinh extends AppCompatActivity implements editHeader {
    RecyclerView rcy;
    RecyclerView rcy2;
    adapter adapter;
    adapter adapter1;
    CircleIndicator3 circleIndicator3;
    ViewPager2 viewPager2;
    Button button;
    SearchView searchView;
    List<obj> objs;
    ImageView setting, imgAvatar;
    LinearLayout mL;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView header;
    Handler handler = new Handler(Looper.getMainLooper());
    int i = 0;
    TextView t;
    ThuThu thuThu1;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            i = viewPager2.getCurrentItem();
            if (i == 2) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(i + 1);
            }
            Log.d("sss", "run: " + i);
        }
    };

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_chinh);
        navigationView = findViewById(R.id.navigationView);
        View HeaderView = navigationView.getHeaderView(0);
        imgAvatar = HeaderView.findViewById(R.id.imgAvatar);
        header = HeaderView.findViewById(R.id.txtHeader);
        button = findViewById(R.id.button);
        mL = findViewById(R.id.mL);
        t = findViewById(R.id.t);
        setting = findViewById(R.id.setting);
        drawerLayout = findViewById(R.id.drawerLayout);
        rcy2 = findViewById(R.id.rcy2);
        searchView = findViewById(R.id.search);
        circleIndicator3 = findViewById(R.id.circleIndicator3);
        rcy = findViewById(R.id.rcy1);
        viewPager2 = findViewById(R.id.slide);
        preferences = getSharedPreferences("login", MODE_PRIVATE);
        new SachDAO(this).top10SachMuonNhieu();



        findViewById(R.id.a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mL.setVisibility(View.GONE);
                rcy2.setVisibility(View.VISIBLE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mL.setVisibility(View.GONE);
                rcy2.setVisibility(View.VISIBLE);
            }
        });
        setting.setOnClickListener(view -> {
            drawerLayout.openDrawer(Gravity.LEFT);
        });
        if (preferences.getString("login", "").equals("admin")) {
            navigationView.getMenu().findItem(R.id.editName).setTitle("Chỉnh sửa tên(thủ thư)");
            navigationView.getMenu().findItem(R.id.editAvatar).setTitle("Chỉnh sửa Avatar(thủ thư)");
        } else {
            navigationView.getMenu().findItem(R.id.addTK).setVisible(false);

        }
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.logout:
                    startActivity(new Intent(GiaoDienChinh.this, MainActivity.class));
                    finish();
                    break;

                case R.id.editName:
                    if (!preferences.getString("login", null).equals("admin")) {
                        Intent intent=new Intent(GiaoDienChinh.this,  EditName.class);
                        intent.putExtra("ma",getIntent().getStringExtra("tk"));
                        startActivity(intent);

                    }
                    break;


                case R.id.editPass:

                    startActivity(new Intent(GiaoDienChinh.this, doimatkhau.class));
                    break;

                case R.id.editAvatar:
                    if (!preferences.getString("login", null).equals("admin")) {
                        ActivityCompat.requestPermissions(GiaoDienChinh.this, new String[]{Manifest.permission.CAMERA}, 1);

                    }
                    ;
                    break;
                case R.id.addTK:
                    startActivity(new Intent(GiaoDienChinh.this, taoTaiKhoan.class));
                    break;

            }
            return true;
        });
        adapter = new adapter(0);
        adapter.setData(setData());
        rcy.setLayoutManager(new GridLayoutManager(this, 3));
        rcy.setAdapter(adapter);


        adapter1 = new adapter(1);
        adapter1.setData(setData());
        rcy2.setLayoutManager(new GridLayoutManager(this, 3));
        rcy2.setAdapter(adapter1);

        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                slide slide = new slide();
                Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        bundle.putInt("img", R.drawable.img_11);
                        slide.setArguments(bundle);
                        return slide;
                    case 1:
                        bundle.putInt("img", R.drawable.img_10);
                        slide.setArguments(bundle);
                        return slide;
                    case 2:
                        bundle.putInt("img", R.drawable.img_9);
                        slide.setArguments(bundle);
                        return slide;
                }
                return slide;
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });
        circleIndicator3.setViewPager(viewPager2);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mL.setVisibility(View.GONE);
                rcy2.setVisibility(View.VISIBLE);
                if (newText.length() == 0) {
                    mL.setVisibility(View.VISIBLE);
                    rcy2.setVisibility(View.GONE);
                }
                List<obj> objs1 = new ArrayList<>();
                for (obj s : objs) {
                    if (s.getName().contains(newText)) {
                        objs1.add(s);
                    }
                }
                Log.d("onQueryTextChange", "onQueryTextChange: " + objs1.size());
                adapter1.setData(objs1);
                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String[] s = {"Tìm kiếm dịch vụ nhà sách Phương Nam "};
                while (true) {
                    searchView.post(new Runnable() {
                        @Override
                        public void run() {
                            searchView.setQueryHint(s[0].substring(s[0].length() - 1) + TextUtils.substring(
                                    s[0],
                                    0,
                                    s[0].length() - 1));
                            s[0] = (String) searchView.getQueryHint();
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private List<obj> setData() {
        objs = new ArrayList<>();
        objs.add(new obj("Quản lí Phiếu Mượn", R.drawable.img_7));
        objs.add(new obj("Quản lí Sách", R.drawable.bookicon));
        objs.add(new obj("Quản lí Loại Sách", R.drawable.b));
        objs.add(new obj("Quản lí Thành Viên", R.drawable.emthree));
        objs.add(new obj("10 sách mượn nhiều nhất", R.drawable.cateicon));
        objs.add(new obj("Doanh thu", R.drawable.money_icon));
        objs.add(new obj("Thêm thành viên", R.drawable.img_4));
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        if (preferences.getString("login", null).equals("admin")) {
            objs.add(new obj("Tạo tài khoản", R.drawable.img_12));
        }
        objs.add(new obj("Đổi mật khẩu", R.drawable.img_5));
        objs.add(new obj("Đăng xuất", R.drawable.img_6));
        return objs;
    }

    @Override
    public void onBackPressed() {
        if (!mL.isInLayout()) {
            mL.setVisibility(View.VISIBLE);
            rcy2.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            bottomsheetdialogselectedimg bottomsheetdialogselectedimg = new bottomsheetdialogselectedimg(this);
            bottomsheetdialogselectedimg.show(getSupportFragmentManager(), "tag");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!getIntent().getStringExtra("tk").equals("admin")) {
            thuThu1 = new ThuThuDAO(this).getThuThu(getIntent().getStringExtra("tk"));
            imgAvatar.setImageBitmap(BitmapFactory.decodeByteArray(thuThu1.getImg(), 0, thuThu1.getImg().length));
            header.setText(thuThu1.getTenTT() + "");
        }

    }

    @Override
    public void editInfo(Bitmap bitmap) throws IOException {
        if (bitmap != null) {
            imgAvatar.setImageBitmap(bitmap);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatar.getDrawable();
            Bitmap bitmap1 = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            ThuThuDAO thuThuDAO = new ThuThuDAO(GiaoDienChinh.this);
            thuThu1.setImg(stream.toByteArray());
            stream.close();
            thuThuDAO.updateThuThu(thuThu1);
        }
    }
}