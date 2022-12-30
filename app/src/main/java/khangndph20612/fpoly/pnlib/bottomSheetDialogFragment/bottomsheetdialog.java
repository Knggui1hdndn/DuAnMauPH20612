package khangndph20612.fpoly.pnlib.bottomSheetDialogFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
 import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import khangndph20612.fpoly.pnlib.DAO.ThanhVienDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.ThanhVien;
import khangndph20612.fpoly.pnlib.quanli.quanlithanhvien;

public class bottomsheetdialog extends BottomSheetDialogFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)
        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

    }

    ThanhVien thanhVien = null;
    TextInputLayout edtMaTV, edtTenTV, edtSoDT;
    Button btnSave, btnHuy;
    LottieAnimationView lottie;
    @Nullable
    ThanhVienDAO sachDAO;
    ImageView imgAvatar, imgSelected, imgCam, img;
    quanlithanhvien quanlinguoidung;
    TextView t;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_nguoi_dung, null);
        initView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            t = view.findViewById(R.id.t);
            img = view.findViewById(R.id.img);
            t.setText("Xem chi tiết");
            thanhVien = (ThanhVien) bundle.getSerializable("thanhvien");
            if (thanhVien.getImg() != null) {
                img.setVisibility(View.VISIBLE);
                img.setImageBitmap(BitmapFactory.decodeByteArray(thanhVien.getImg(), 0, thanhVien.getImg().length));
                imgAvatar.setImageBitmap(BitmapFactory.decodeByteArray(thanhVien.getImg(), 0, thanhVien.getImg().length));
            }
            edtMaTV.getEditText().setText(thanhVien.getMaTV());
            edtTenTV.getEditText().setText(thanhVien.getTenTV());
            edtSoDT.getEditText().setText(thanhVien.getSoDT());
        }
        imgCam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 2);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
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
        view.findViewById(R.id.dissmis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAvatar.setImageBitmap(null);
                imgAvatar.setVisibility(View.GONE);
                edtMaTV.getEditText().setText("");
                edtTenTV.getEditText().setText("");
                edtSoDT.getEditText().setText("");
                imgAvatar.setVisibility(View.GONE);
                if (thanhVien != null) {
                    dismiss();
                }
            }
        });
        btnSave.setOnClickListener(v -> {
            int x4 = check(edtMaTV);
            int x1 = check(edtTenTV);
            int x2 = check(edtSoDT);
            if (x1 == 0 && x2 == 0 && x4 == 0) {
                if (thanhVien == null) {
                    sachDAO.addThanhVien(new ThanhVien(edtMaTV.getEditText().getText().toString(),
                            edtTenTV.getEditText().getText().toString(),
                            edtSoDT.getEditText().getText().toString(),
                            convertBitmapToBytes(imgAvatar)));
                } else {
                    sachDAO.updateThanhVien(new ThanhVien(edtMaTV.getEditText().getText().toString(),
                            edtTenTV.getEditText().getText().toString(),
                            edtSoDT.getEditText().getText().toString(),
                            convertBitmapToBytes(imgAvatar)), thanhVien.getMaTV());
                }
                imgAvatar.setImageResource(0);
                imgAvatar.setVisibility(View.GONE);
                edtMaTV.getEditText().setText("");
                edtTenTV.getEditText().setText("");
                edtSoDT.getEditText().setText("");
                imgAvatar.setVisibility(View.GONE);
                lottie.setScaleX(0.8f);
                lottie.setScaleY(0.8f);
                lottie.setAnimation(R.raw.successful1);
                lottie.playAnimation();
                quanlinguoidung = (quanlithanhvien) getActivity();
                quanlinguoidung.loadRcy();
                if (thanhVien != null) {
                    dismiss();
                }
            } else {
                lottie.setScaleX(0.5f);
                lottie.setScaleY(0.5f);
                lottie.setAnimation(R.raw.failed);
                lottie.playAnimation();
            }
        });
        return view;
    }

    private byte[] convertBitmapToBytes(ImageView view) {
        if (view.getDrawable() == null) {
            return null;
        }
        BitmapDrawable bitmap = (BitmapDrawable) view.getDrawable();

        Bitmap bitmapDrawable = bitmap.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapDrawable.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
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
        if (inputLayout.equals(edtMaTV)) {
            for (ThanhVien thuThu : sachDAO.getThanhVien()) {
                if (thanhVien != null) {
                    if (thuThu.getMaTV().equals(inputLayout.getEditText().getText().toString())
                            &&
                            !inputLayout.getEditText().getText().toString().equals(thanhVien.getMaTV())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã thành viên đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                } else {
                    if (thuThu.getMaTV().equals(inputLayout.getEditText().getText().toString())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã thành viên đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                }
            }
        }
        return x[0];
    }

    private void initView(View view) {
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgSelected = view.findViewById(R.id.imgSelected);
        imgCam = view.findViewById(R.id.imgCam);
        btnSave = view.findViewById(R.id.btnSave);
        btnHuy = view.findViewById(R.id.btnHuy);
        lottie = view.findViewById(R.id.lottie);
        sachDAO = new ThanhVienDAO(view.getContext());
        edtMaTV = view.findViewById(R.id.edtMaTV);
        edtTenTV = view.findViewById(R.id.edtTenTV);
        edtSoDT = view.findViewById(R.id.edtSoDT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d("kkkkkkkk", "onActivityResult: " + data.getData());
            try {
                if (requestCode == 2) {
                    if (data.getExtras().get("data") != null) {
                        imgAvatar.setVisibility(View.VISIBLE);
                        if (thanhVien != null) {
                            img.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        }
                        img.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        imgAvatar.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    }
                } else {
                    imgAvatar.setVisibility(View.VISIBLE);
                    if (thanhVien != null) {
                        img.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()));
                    }
                    imgAvatar.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
