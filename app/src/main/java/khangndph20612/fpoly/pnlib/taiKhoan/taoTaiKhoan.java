package khangndph20612.fpoly.pnlib.taiKhoan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
 import com.google.android.material.textfield.TextInputLayout;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import khangndph20612.fpoly.pnlib.DAO.ThuThuDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.ThuThu;

public class taoTaiKhoan extends AppCompatActivity {
    TextInputLayout edtSDT, edtMaThuThu, edtTenThuThu, edtMatKhau,edtNlMatKhau;
    Button btnCreate;
    ThuThuDAO thuThuDAO;
    ImageView imageView,imgCam,imgSelected;
    VideoView vd;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);
        setToolBar();
        initView();

//        vd.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.successful));
//        vd.start();
        imgCam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 2);
                }else {
                    ActivityCompat.requestPermissions(taoTaiKhoan.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3);
                }

            }
        });
        imgSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);

            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x4 = check(edtMaThuThu);
                int x1 = check(edtMatKhau);
                int x2 = check(edtTenThuThu);
                int x3 = check(edtSDT);
                int x5 = check(edtNlMatKhau);
                if (x1 == 0 && x2 == 0 && x3 == 0 && x4 == 0&&x5==0) {
                    if(!edtNlMatKhau.getEditText().getText().toString().equals(edtNlMatKhau.getEditText().getText().toString())){
                         edtNlMatKhau.setErrorEnabled(true);
                        edtNlMatKhau.setError("Mật khẩu nhập lại không khớp");
                        lottie.setScaleX(0.5f);
                        lottie.setScaleY(0.5f);
                        lottie.setAnimation(R.raw.failed);
                        lottie.playAnimation();
                    }else{
                        thuThuDAO.addThuThu(new ThuThu(edtMaThuThu.getEditText().getText().toString(),
                                edtTenThuThu.getEditText().getText().toString(),
                                edtSDT.getEditText().getText().toString(),
                                edtMatKhau.getEditText().getText().toString(),
                                convertBitmapToBytes(imageView)));
                        edtMaThuThu.getEditText().setText("");
                        edtMatKhau.getEditText().setText("");
                        edtTenThuThu.getEditText().setText("");
                        edtSDT.getEditText().setText("");
                        lottie.setScaleX(0.8f);
                        lottie.setScaleY(0.8f);
                        lottie.setAnimation(R.raw.successful1);
                        lottie.playAnimation();
                        imageView.setImageResource(0);
                        imageView.setVisibility(View.GONE);
                    }
                } else {
                    lottie.setScaleX(0.5f);
                    lottie.setScaleY(0.5f);
                    lottie.setAnimation(R.raw.failed);

                    lottie.playAnimation();
                }
            }
        });
    }

    private byte[] convertBitmapToBytes(ImageView view) {
        if (view.getDrawable() == null) {
            return null;
        }
        BitmapDrawable bitmap = (BitmapDrawable) view.getDrawable();

        Bitmap bitmapDrawable = bitmap.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapDrawable.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    private void initView() {
        edtNlMatKhau = findViewById(R.id.edtNlMatKhau);
        lottie = findViewById(R.id.lottie);
        edtMaThuThu = findViewById(R.id.edtMaThuThu);
        edtTenThuThu = findViewById(R.id.edtTenThuThu);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnCreate = findViewById(R.id.btnCreate);
        edtSDT = findViewById(R.id.edtSDT);
        thuThuDAO = new ThuThuDAO(this);
        imageView = findViewById(R.id.imgAvatar);
        imgSelected =  findViewById(R.id.imgSelected);
        imgCam = findViewById(R.id.imgCam);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d("kkkkkkkk", "onActivityResult: " + data.getData());
            try {
                if (requestCode == 2) {
                    if (data.getExtras().get("data") != null) {
                        imageView.setVisibility(View.VISIBLE);

                        imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    }
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(MediaStore.Images.Media.getBitmap( getContentResolver(), data.getData()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private int check(TextInputLayout inputLayout) {
        final int[] x = {0};
        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayout.setErrorEnabled(false);
                    x[0] = 0;

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (inputLayout.getEditText().getText().toString().length() == 0) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Không để trống " + inputLayout.getHint().toString().toLowerCase(Locale.ROOT));
            x[0] = 1;

        }
        if (inputLayout.equals(edtMaThuThu)) {
            for (ThuThu thuThu : thuThuDAO.getThuThu()) {
                if (thuThu.getMaTT().equals(inputLayout.getEditText().getText().toString())) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError("Mã thủ thư đã tồn tại");
                    x[0] = 1;
                    break;
                }
            }
        }
        return x[0];
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_245);//set icon menu
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.batdau1, R.anim.ketthucfrag);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.batdau1, R.anim.ketthucfrag);
        }
        return true;
    }
}