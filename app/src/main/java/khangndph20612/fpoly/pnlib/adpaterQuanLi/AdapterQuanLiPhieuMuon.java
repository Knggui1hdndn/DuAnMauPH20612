package khangndph20612.fpoly.pnlib.adpaterQuanLi;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.PhieuMuonDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.PhieuMuon;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialogthemphiuemuon;
import khangndph20612.fpoly.pnlib.quanli.quanliphieumuon;

public class AdapterQuanLiPhieuMuon extends RecyclerView.Adapter<AdapterQuanLiPhieuMuon.AdapterQuanLiLoaiSachViewHolder> {
    private List<PhieuMuon> loaiSaches;
    PhieuMuonDAO sachDAO;
 khangndph20612.fpoly.pnlib.quanli.quanliphieumuon quanliphieumuon;
    public void setData(List<PhieuMuon> loaiSaches) {
        this.loaiSaches = loaiSaches;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AdapterQuanLiLoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sachDAO = new PhieuMuonDAO(parent.getContext());
        quanliphieumuon= ( quanliphieumuon) parent.getContext();

        return new AdapterQuanLiLoaiSachViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlphieumuon, null));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLiLoaiSachViewHolder holder, int position) {
        PhieuMuon sach = loaiSaches.get(position);
        holder.maPM.setText(AdapterQuanLiLoaiSach.spannableString("Mã phiếu mượn:"+sach.getMaPM()));
        holder.tienThue.setText(AdapterQuanLiLoaiSach.spannableString("Tiền thuê:"+sach.getTienThue()));
        holder.ngayMuon.setText(AdapterQuanLiLoaiSach.spannableString("Ngày mượn:"+sach.getNgayMuon()));
        holder.gio.setText(AdapterQuanLiLoaiSach.spannableString("Giờ mượn:"+sach.getGio()+""));
        if (sach.getTraSach() == 0) {
            holder.traSach.setText("Chưa trả sách");
            holder.traSach.setTextColor(Color.RED);

        } else {
            holder.traSach.setText("Đã hoàn trả sách");
            holder.traSach.setTextColor(Color.GREEN);
        }
        holder.imgDelete.setOnClickListener(v -> {
            sachDAO.removePhieuMuon(sach);
            loaiSaches.remove(position);
            notifyDataSetChanged();
        });
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetdialogthemphiuemuon bottomsheetdialogthemphiuemuon=new bottomsheetdialogthemphiuemuon();
                Bundle bundle=new Bundle();
                bundle.putSerializable("phieumuon",sach);
                bottomsheetdialogthemphiuemuon.setArguments(bundle);
                bottomsheetdialogthemphiuemuon.show(quanliphieumuon.getSupportFragmentManager(),"tag");
            }
        });
        try {
            holder.maSach.setText(AdapterQuanLiLoaiSach.spannableString("Sách:" + sachDAO.getTenSachMuon(sach.getMaSach())));

        }catch (Exception e){
            holder.maSach.setText(AdapterQuanLiLoaiSach.spannableString("Sách:Đã bị xóa"));

        }
        try {
            holder.maTV.setText(AdapterQuanLiLoaiSach.spannableString("Người mượn:" + sachDAO.getTenThanhVienMuon(sach.getMaTV())));

        }catch (Exception e){
            holder.maTV.setText(AdapterQuanLiLoaiSach.spannableString("Người dùng:Đã bị xóa"));

        }

    }

    @Override
    public int getItemCount() {
        return loaiSaches.size();
    }

    public class AdapterQuanLiLoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView maPM, maTT, maTV, maSach, tienThue, ngayMuon, traSach,gio;
        ImageView imgDelete;
RelativeLayout mRelativeLayout;

        public AdapterQuanLiLoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            gio = itemView.findViewById(R.id.gio);
            maPM = itemView.findViewById(R.id.maPM);
            maTV = itemView.findViewById(R.id.maTV);
            maSach = itemView.findViewById(R.id.maSach);
            tienThue = itemView.findViewById(R.id.tienthue);
            ngayMuon = itemView.findViewById(R.id.ngayMuon);
            traSach = itemView.findViewById(R.id.traSach);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
