package khangndph20612.fpoly.pnlib.adpaterQuanLi;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.ThanhVienDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.ThanhVien;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialog;
import khangndph20612.fpoly.pnlib.quanli.quanlithanhvien;

public class AdapterQuanLiThanhVien extends RecyclerView.Adapter<AdapterQuanLiThanhVien.AdapterQuanLiLoaiSachViewHolder> {
    private List<ThanhVien> loaiSaches;
    ThanhVienDAO sachDAO;
quanlithanhvien quanlinguoidung;
    public void setData(List<ThanhVien> loaiSaches) {
        this.loaiSaches = loaiSaches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterQuanLiLoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sachDAO = new ThanhVienDAO(parent.getContext());
        quanlinguoidung= (quanlithanhvien) parent.getContext();
        return new AdapterQuanLiLoaiSachViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlthanhvien, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLiLoaiSachViewHolder holder, int position) {
        ThanhVien sach = loaiSaches.get(position);
        holder.maTV.setText(AdapterQuanLiLoaiSach.spannableString(" - " + sach.getMaTV()));
        holder.soDT.setText(AdapterQuanLiLoaiSach.spannableString("Số ĐT:" + sach.getSoDT()));
        holder.tenTV.setText(sach.getTenTV());

      try{
          holder.imgAvatar.setImageBitmap(BitmapFactory.decodeByteArray(sach.getImg(), 0, sach.getImg().length));

      }catch (Exception e){

      }
         holder.img.setOnClickListener(v -> {
            sachDAO.removeThanhVien(sach);
            notifyDataSetChanged();
        });
         holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 bottomsheetdialog bottomsheetdialog=new bottomsheetdialog();
                 Bundle bundle=new Bundle();
                  bundle.putSerializable("thanhvien",sach);
                 bottomsheetdialog.setArguments(bundle);
                 bottomsheetdialog.show(quanlinguoidung.getSupportFragmentManager(),"tag");
             }
         });
    }

    @Override
    public int getItemCount() {
        return loaiSaches.size();
    }

    public class AdapterQuanLiLoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView tenTV, maTV, soDT;
        ImageView img,imgAvatar;
RelativeLayout mRelativeLayout;
        public AdapterQuanLiLoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            img = itemView.findViewById(R.id.imgDelete);
            maTV = itemView.findViewById(R.id.maTV);
            soDT = itemView.findViewById(R.id.soDT);
            tenTV = itemView.findViewById(R.id.tenTV);
        }
    }
}
