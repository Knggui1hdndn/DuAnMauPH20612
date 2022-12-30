package khangndph20612.fpoly.pnlib.bottomSheetDialogFragment;

import android.Manifest;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import khangndph20612.fpoly.pnlib.DAO.LoaiSachDAO;
import khangndph20612.fpoly.pnlib.DAO.SachDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.LoaiSach;
import khangndph20612.fpoly.pnlib.ThucThe.Sach;
import khangndph20612.fpoly.pnlib.quanli.quanlisach;

public class bottomsheetdialogSach extends BottomSheetDialogFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)
        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

    }

    TextInputLayout edtMaSach, edtTacGia, edtTieuDe, edtNhaXB, edtGiaBia, edtSoLuong;
    Button btnSave, btnHuy;
    LottieAnimationView lottie;
    @Nullable
    SachDAO sachDAO;
    LoaiSachDAO getSach;
    ImageView imgAvatar, imgSelected, imgCam, img;
    Spinner spnLoaiSach;
    khangndph20612.fpoly.pnlib.quanli.quanlisach quanlisach;
    Sach sach = null;
    private Context context;
    TextView a;

    public bottomsheetdialogSach(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addsach, null);
        initView(view);
        Bundle bundle = getArguments();
        a = view.findViewById(R.id.a);
        if (bundle != null) {
            a.setText("Xem chi tiết");
            img = view.findViewById(R.id.img);
            sach = (Sach) bundle.getSerializable("sach");
            if (sach.getImg() != null) {
                imgAvatar.setVisibility(View.VISIBLE);
                img.setImageBitmap(BitmapFactory.decodeByteArray(sach.getImg(), 0, sach.getImg().length));
                imgAvatar.setImageBitmap(BitmapFactory.decodeByteArray(sach.getImg(), 0, sach.getImg().length));
            }
            edtMaSach.getEditText().setText(sach.getMaSach());
            edtTacGia.getEditText().setText(sach.getTacGia());
            edtTieuDe.getEditText().setText(sach.getTieuDe());
            edtNhaXB.getEditText().setText(sach.getNhaXB());
            edtGiaBia.getEditText().setText(sach.getGiaBan());
            edtSoLuong.getEditText().setText(sach.getSoLuong());

        }
        List<String> list = new ArrayList<>();
        if (sach == null) {
            for (LoaiSach loaiSach : getSach.getLoaiSach()) {
                list.add(loaiSach.getMaLoai());
            }
            list.add(0, "Mã loại sách");
        } else {
            for (LoaiSach loaiSach : getSach.getLoaiSach()) {
                list.add(loaiSach.getMaLoai());
            }
            list.remove(sach.getMaLoai());
            list.add(0, sach.getMaLoai());
        }
        setAdapterSpinner(list);
        view.findViewById(R.id.dissmis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        imgCam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 2);
                }else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3);
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
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sach != null) {
                    dismiss();
                }
                edtMaSach.getEditText().setText("");
                edtTacGia.getEditText().setText("");
                edtTieuDe.getEditText().setText("");
                edtNhaXB.getEditText().setText("");
                edtGiaBia.getEditText().setText("");
                edtSoLuong.getEditText().setText("");
                imgAvatar.setVisibility(View.GONE);
                list.clear();
                for (LoaiSach loaiSach : getSach.getLoaiSach()) {
                    list.add(loaiSach.getMaLoai());
                }  list.add(0, "Mã loại sách");
                setAdapterSpinner(list);
                imgAvatar.setImageBitmap(null);
                imgAvatar.setVisibility(View.GONE);
            }
        });
        btnSave.setOnClickListener(v -> {
            int x = check(edtMaSach);
            int x1 = check(edtTacGia);
            int x2 = check(edtTieuDe);
            int x3 = check(edtNhaXB);
            int x4 = check(edtGiaBia);
            int x5 = check(edtSoLuong);
            if (x1 == 0 && x2 == 0 && x == 0 && x3 == 0 && x4 == 0 && x5 == 0 && !spnLoaiSach.getSelectedItem().toString().equals("Mã loại sách")) {
                if (sach != null) {
                    sachDAO.updateSach(new Sach(edtMaSach.getEditText().getText().toString(),
                            spnLoaiSach.getSelectedItem().toString(),
                            edtTieuDe.getEditText().getText().toString(),
                            edtTacGia.getEditText().getText().toString(),
                            edtNhaXB.getEditText().getText().toString(),
                            edtGiaBia.getEditText().getText().toString(),
                            edtSoLuong.getEditText().getText().toString(),
                            convertBitmapToBytes(imgAvatar)), sach.getMaSach());
                } else {
                    sachDAO.addSach(new Sach(edtMaSach.getEditText().getText().toString(),
                            spnLoaiSach.getSelectedItem().toString(),
                            edtTieuDe.getEditText().getText().toString(),
                            edtTacGia.getEditText().getText().toString(),
                            edtNhaXB.getEditText().getText().toString(),
                            edtGiaBia.getEditText().getText().toString(),
                            edtSoLuong.getEditText().getText().toString(),
                            convertBitmapToBytes(imgAvatar)));
                }

                edtMaSach.getEditText().setText("");
                edtTacGia.getEditText().setText("");
                edtTieuDe.getEditText().setText("");
                edtNhaXB.getEditText().setText("");
                edtGiaBia.getEditText().setText("");
                edtSoLuong.getEditText().setText("");
                imgAvatar.setVisibility(View.GONE);
                quanlisach = ( quanlisach) getActivity();
                quanlisach.loadRcy();
                imgAvatar.setImageResource(0);
                imgAvatar.setVisibility(View.GONE);
                if (sach != null) {
                    dismiss();
                }
                list.clear();
                for (LoaiSach loaiSach : getSach.getLoaiSach()) {
                    list.add(loaiSach.getMaLoai());
                }
                setAdapterSpinner(list);
                lottie.setScaleX(0.8f);
                lottie.setScaleY(0.8f);
                lottie.setAnimation(R.raw.successful1);
                lottie.playAnimation();
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

        if (inputLayout.equals(edtMaSach)) {
            for (Sach thuThu : sachDAO.getSach()) {
                if (sach == null) {
                    if (thuThu.getMaSach().equals(inputLayout.getEditText().getText().toString())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã sách đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                } else {
                    if (thuThu.getMaSach().equals(inputLayout.getEditText().getText().toString()) &&
                            !sach.getMaSach().equals(inputLayout.getEditText().getText().toString())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã sách đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                }

            }
        }
        return x[0];
    }

    public void setAdapterSpinner(List<String> list) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(list);
        spnLoaiSach.setAdapter(arrayAdapter);
    }

    private void initView(View view) {
        img = view.findViewById(R.id.img);
        edtMaSach = view.findViewById(R.id.edtMaSach);
        edtTacGia = view.findViewById(R.id.edtTacGia);
        edtTieuDe = view.findViewById(R.id.edtTieuDe);
        edtNhaXB = view.findViewById(R.id.edtNhaXB);
        edtGiaBia = view.findViewById(R.id.edtGiaBia);
        edtSoLuong = view.findViewById(R.id.edtSoLuong);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgSelected = view.findViewById(R.id.imgSelected);
        imgCam = view.findViewById(R.id.imgCam);
        btnSave = view.findViewById(R.id.btnSave);
        btnHuy = view.findViewById(R.id.btnHuy);
        lottie = view.findViewById(R.id.lottie);
        sachDAO = new SachDAO(view.getContext());
        getSach = new LoaiSachDAO(view.getContext());
        spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
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
                        if (sach != null) {
                            img.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        }
                        img.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        imgAvatar.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    }
                } else {
                    imgAvatar.setVisibility(View.VISIBLE);
                    if (sach != null) {
                        img.setImageBitmap(MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData()));
                    }
                    imgAvatar.setImageBitmap(MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

