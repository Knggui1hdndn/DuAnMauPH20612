package khangndph20612.fpoly.pnlib.bottomSheetDialogFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;


import java.util.Locale;

import khangndph20612.fpoly.pnlib.DAO.LoaiSachDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.LoaiSach;
import khangndph20612.fpoly.pnlib.quanli.quanliloaisach;

public class bottomsheetdialogLoaiSach extends BottomSheetDialogFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)
        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

    }

    TextInputLayout edtMaloai, edtTenloai, edtMoTa, edtViTri;
    Button btnSave, btnHuy;
    LottieAnimationView lottie;
    @Nullable
    LoaiSachDAO sachDAO;
 khangndph20612.fpoly.pnlib.quanli.quanliloaisach quanliloaisach;
    String s;
    TextView textView;
    LoaiSach loaiSachbundle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addtheloaisach, null);
        initView(view);
        textView = view.findViewById(R.id.tile);
        Bundle bundle = getArguments();
        if(bundle!=null){
            loaiSachbundle = (LoaiSach) bundle.getSerializable("loaisach");
            s = bundle.getString("update");
        }
        if (s != null) {
            textView.setText("Xem chi tiết");
            edtMaloai.getEditText().setText(loaiSachbundle.getMaLoai());
            edtTenloai.getEditText().setText(loaiSachbundle.getTenLoai());
            edtMoTa.getEditText().setText(loaiSachbundle.getMoTa());
            edtViTri.getEditText().setText(loaiSachbundle.getViTri());
        }
        view.findViewById(R.id.dissmis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMaloai.getEditText().setText("");
                edtTenloai.getEditText().setText("");
                edtMoTa.getEditText().setText("");
                edtViTri.getEditText().setText("");
                if(loaiSachbundle!=null){
                    dismiss();
                }
            }
        });
        btnSave.setOnClickListener(v -> {
            int x4 = check(edtMaloai);
            int x1 = check(edtTenloai);
            int x2 = check(edtMoTa);
            int x3 = check(edtViTri);
            if (x1 == 0 && x2 == 0 && x3 == 0 && x4 == 0) {
                if (s == null) {
                    sachDAO.addLoaiSach(new LoaiSach(edtMaloai.getEditText().getText().toString(),
                            edtTenloai.getEditText().getText().toString(),
                            edtMoTa.getEditText().getText().toString(),
                            edtViTri.getEditText().getText().toString()
                    ));
                } else {
                    sachDAO.updateLoaiSach(new LoaiSach(edtMaloai.getEditText().getText().toString(),
                            edtTenloai.getEditText().getText().toString(),
                            edtMoTa.getEditText().getText().toString(),
                            edtViTri.getEditText().getText().toString()
                    ), loaiSachbundle.getMaLoai());
                }
                quanliloaisach = ( quanliloaisach) getActivity();
                quanliloaisach.loadRcy();
                edtMaloai.getEditText().setText("");
                edtTenloai.getEditText().setText("");
                edtMoTa.getEditText().setText("");
                edtViTri.getEditText().setText("");
                lottie.setScaleX(0.8f);
                lottie.setScaleY(0.8f);
                lottie.setAnimation(R.raw.successful1);
                lottie.playAnimation();
                if(loaiSachbundle!=null){
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
        if (inputLayout.equals(edtMaloai)) {
            for (LoaiSach thuThu : sachDAO.getLoaiSach()) {
                if (loaiSachbundle != null) {
                    if (thuThu.getMaLoai().equals(inputLayout.getEditText().getText().toString())
                            &&
                            !inputLayout.getEditText().getText().toString().equals(loaiSachbundle.getMaLoai())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã loại sách đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                }else {
                    if (thuThu.getMaLoai().equals(inputLayout.getEditText().getText().toString())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã loại sách đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                }
            }
        }
        return x[0];
    }

    private void initView(View view) {
        edtMaloai = view.findViewById(R.id.edtMaloai);
        edtTenloai = view.findViewById(R.id.edtTenloai);
        edtMoTa = view.findViewById(R.id.edtMoTa);
        edtViTri = view.findViewById(R.id.edtViTri);
        btnSave = view.findViewById(R.id.btnSave);
        btnHuy = view.findViewById(R.id.btnHuy);
        lottie = view.findViewById(R.id.lottie);
        sachDAO = new LoaiSachDAO(view.getContext());
    }
}
