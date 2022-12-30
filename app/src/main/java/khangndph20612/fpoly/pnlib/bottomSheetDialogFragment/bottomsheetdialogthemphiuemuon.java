package khangndph20612.fpoly.pnlib.bottomSheetDialogFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
 import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import khangndph20612.fpoly.pnlib.DAO.PhieuMuonDAO;
import khangndph20612.fpoly.pnlib.DAO.SachDAO;
import khangndph20612.fpoly.pnlib.DAO.ThanhVienDAO;
import khangndph20612.fpoly.pnlib.DAO.ThuThuDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.PhieuMuon;
import khangndph20612.fpoly.pnlib.ThucThe.Sach;
import khangndph20612.fpoly.pnlib.ThucThe.ThanhVien;
import khangndph20612.fpoly.pnlib.ThucThe.ThuThu;
import khangndph20612.fpoly.pnlib.quanli.quanliphieumuon;

public class bottomsheetdialogthemphiuemuon extends BottomSheetDialogFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)
        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

    }

    khangndph20612.fpoly.pnlib.quanli.quanliphieumuon quanliphieumuon;
    TextInputLayout edtMaPhieuMuon, edtTienThue, edtNgayMuon;
    CheckBox txtTraSach;
    Button btnSave, btnHuy;
    LottieAnimationView lottie;
    Spinner spnmaSach, spnmaTT, spnmaTV;
    @Nullable
    PhieuMuonDAO sachDAO;
    ImageView imgAvatar;
    PhieuMuon phieuMuon = null;
    TextView txt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addphieumuon, null);
        initView(view);

        Bundle bundle = getArguments();
        huy(view);
        if (bundle != null) {
            phieuMuon = (PhieuMuon) bundle.getSerializable("phieumuon");
            txt.setText("Xem chi tiết");
            txtTraSach.setEnabled(true);
            setData(view);
            txtTraSach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        txtTraSach.setText("Đã hoàn trả sách");
                        txtTraSach.setTextColor(Color.GREEN);
                    } else {
                        txtTraSach.setText("Chưa trả sách");
                        txtTraSach.setTextColor(Color.RED);

                    }
                }
            });
        }

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huy(view);
            }
        });
        view.findViewById(R.id.dissmis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(v -> {
            int x = check(edtMaPhieuMuon);
            int x1 = check(edtTienThue);
            int x2 = check(edtNgayMuon);

            if (x1 == 0 && x2 == 0 && x == 0 && !spnmaTT.getSelectedItem().toString().equals("Mã thủ thư")
                    && !spnmaTV.getSelectedItem().toString().equals("Mã thành viên")
                    && !spnmaSach.getSelectedItem().toString().equals("Mã sách")) {
                if (phieuMuon == null) {
                    sachDAO.addPhieuMuon(new PhieuMuon(edtMaPhieuMuon.getEditText().getText().toString(),
                            spnmaTT.getSelectedItem().toString(),
                            spnmaTV.getSelectedItem().toString(),
                            spnmaSach.getSelectedItem().toString(),
                            Integer.parseInt(edtTienThue.getEditText().getText().toString()),
                            edtNgayMuon.getEditText().getText().toString(),
                            0));
                } else {
                    sachDAO.updatePhieuMuon(new PhieuMuon(edtMaPhieuMuon.getEditText().getText().toString(),
                            spnmaTT.getSelectedItem().toString(),
                            spnmaTV.getSelectedItem().toString(),
                            spnmaSach.getSelectedItem().toString(),
                            Integer.parseInt(edtTienThue.getEditText().getText().toString()),
                            edtNgayMuon.getEditText().getText().toString(),
                            txtTraSach.isChecked() ? 1 : 0), phieuMuon.getMaPM());
                }
                quanliphieumuon = ( quanliphieumuon) getActivity();
                quanliphieumuon.loadRcy();

                lottie.setScaleX(0.8f);
                lottie.setScaleY(0.8f);
                lottie.setAnimation(R.raw.successful1);
                lottie.playAnimation();
                huy(view);
            } else {
                lottie.setScaleX(0.5f);
                lottie.setScaleY(0.5f);
                lottie.setAnimation(R.raw.failed);

                lottie.playAnimation();
            }
        });
        return view;
    }

    private void setData(View view) {
        if (phieuMuon != null) {
            setSpinner(view);
            if (phieuMuon.getTraSach() == 0) {
                txtTraSach.setChecked(false);
                txtTraSach.setText("Chưa trả sách");
                txtTraSach.setTextColor(Color.RED);
            } else {
                txtTraSach.setChecked(true);
                txtTraSach.setText("Đã hoàn trả sách");
                txtTraSach.setTextColor(Color.GREEN);
            }
        }
    }

    public void huy(View view) {
        setSpinner(view);
        edtTienThue.getEditText().setText("");
        edtNgayMuon.getEditText().setText("");
        edtMaPhieuMuon.getEditText().setText("");
        txtTraSach.setChecked(false);
        txtTraSach.setText("Chưa trả sách");
        txtTraSach.setTextColor(Color.RED);
        if (phieuMuon != null) {
            dismiss();
        }
    }

    public void setSpinner(View view) {
        List<String> sachList = new ArrayList<>();
        List<String> thuList = new ArrayList<>();
        List<String> thanhVienList = new ArrayList<>();
        for (Sach sach : new SachDAO(view.getContext()).getSach()) {
            sachList.add(sach.getMaSach());
        }
        for (ThuThu thuThu : new ThuThuDAO(view.getContext()).getThuThu()) {
            thuList.add(thuThu.getMaTT());
        }
        thuList.add(0, "admin(Mã của admin)");
        for (ThanhVien thanhVien : new ThanhVienDAO(view.getContext()).getThanhVien()) {
            thanhVienList.add(thanhVien.getMaTV());
        }
        if (phieuMuon != null) {
            thuList.remove(phieuMuon.getMaTT());
            thuList.add(0, phieuMuon.getMaTT());
            thanhVienList.remove(phieuMuon.getMaTV());
            thanhVienList.add(0, phieuMuon.getMaTV());
            sachList.remove(phieuMuon.getMaSach());
            sachList.add(0, phieuMuon.getMaSach());
            edtTienThue.getEditText().setText(phieuMuon.getTienThue()+"");
            edtNgayMuon.getEditText().setText(phieuMuon.getNgayMuon());
            edtMaPhieuMuon.getEditText().setText(phieuMuon.getMaPM());
        } else {
            thuList.add(0, "Mã thủ thư");
            thanhVienList.add(0, "Mã thành viên");
            sachList.add(0, "Mã sách");
        }
        setAdapterSpinner(sachList, spnmaSach);
        setAdapterSpinner(thuList, spnmaTT);
        setAdapterSpinner(thanhVienList, spnmaTV);
    }

    public void setAdapterSpinner(List<String> list, Spinner spinner) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(list);
        spinner.setAdapter(arrayAdapter);
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

        if (inputLayout.equals(edtMaPhieuMuon)) {
            for (PhieuMuon thuThu : sachDAO.getPhieuMuon()) {
                if (phieuMuon != null) {
                    if (thuThu.getMaPM().equals(inputLayout.getEditText().getText().toString())
                            &&
                            !inputLayout.getEditText().getText().toString().equals(phieuMuon.getMaPM())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã thủ thư đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                } else {
                    if (thuThu.getMaPM().equals(inputLayout.getEditText().getText().toString())) {
                        inputLayout.setErrorEnabled(true);
                        inputLayout.setError("Mã thủ thư đã tồn tại");
                        x[0] = 1;
                        break;
                    }
                }
            }
        }
        if(edtNgayMuon.equals(inputLayout)){
            SimpleDateFormat dateFormatSymbols=new SimpleDateFormat("yyyy-MM-dd");
            try {
                 dateFormatSymbols.parse(inputLayout.getEditText().getText().toString());
             } catch (ParseException e) {
                edtNgayMuon.setErrorEnabled(true);
                edtNgayMuon.setError("Vui lòng nhập đúng định dạng");
                return 1;
             }
        }
        if(edtTienThue.equals(inputLayout)){

            try {
                Integer.parseInt(inputLayout.getEditText().getText().toString());
            } catch (Exception e) {
                edtTienThue.setErrorEnabled(true);
                edtTienThue.setError("Tiền phải là số");
                return 1;
            }
        }
        return x[0];
    }

    private void initView(View view) {
        txt = view.findViewById(R.id.txt);
        edtMaPhieuMuon = view.findViewById(R.id.edtMaPhieuMuon);
        edtTienThue = view.findViewById(R.id.edtTienThue);
        edtNgayMuon = view.findViewById(R.id.edtNgayMuon);
        txtTraSach = view.findViewById(R.id.txtTraSach);
        spnmaSach = view.findViewById(R.id.spnmaSach);
        spnmaTT = view.findViewById(R.id.spnmaTT);
        spnmaTV = view.findViewById(R.id.spnmaTV);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        btnSave = view.findViewById(R.id.btnSave);
        btnHuy = view.findViewById(R.id.btnHuy);
        lottie = view.findViewById(R.id.lottie);
        sachDAO = new PhieuMuonDAO(view.getContext());
    }

}

