package khangndph20612.fpoly.pnlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import khangndph20612.fpoly.pnlib.quanli.quanliloaisach;
import khangndph20612.fpoly.pnlib.quanli.quanliphieumuon;
import khangndph20612.fpoly.pnlib.quanli.quanlisach;
import khangndph20612.fpoly.pnlib.quanli.quanlithanhvien;
import khangndph20612.fpoly.pnlib.taiKhoan.doimatkhau;
import khangndph20612.fpoly.pnlib.taiKhoan.taoTaiKhoan;
import khangndph20612.fpoly.pnlib.thongKe.doanhthu;
import khangndph20612.fpoly.pnlib.thongKe.top10;

public class adapter extends RecyclerView.Adapter<adapter.RecyclerViewHolder> {
    private List<obj> objs;
    Context context;
int i;
    public adapter(int i) {
        this.i=i;
    }

    public void setData(List<obj> objs) {
        this.objs = objs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        obj obj = objs.get(position);
        holder.img.setImageResource(obj.getImg());
        holder.txt.setText(obj.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (obj.getName()) {
                    case "Quản lí Phiếu Mượn":
                        context.startActivity(new Intent(context, quanliphieumuon.class));
                        break;
                    case "Quản lí Sách":
                        context.startActivity(new Intent(context, quanlisach.class));
                        break;
                    case "Quản lí Loại Sách":
                        context.startActivity(new Intent(context, quanliloaisach.class));
                        break;
                    case "Quản lí Thành Viên":
                        context.startActivity(new Intent(context, quanlithanhvien.class));
                        break;
                    case "10 sách mượn nhiều nhất":
                        context.startActivity(new Intent(context, top10.class));
                        break;
                    case "Doanh thu":
                        context.startActivity(new Intent(context, doanhthu.class));
                        break;
                    case "Thêm thành viên":
                        Intent intent=new Intent(context, quanlithanhvien.class);
                        intent.putExtra("S","s");
                        context.startActivity(intent);
                        break;
                    case "Tạo tài khoản":
                         context.startActivity(new Intent(context, taoTaiKhoan.class));
                         break;
                    case "Đổi mật khẩu":
                        context.startActivity(new Intent(context, doimatkhau.class));
                        break;
                    case "Đăng xuất":
                        context.startActivity(new Intent(context, MainActivity.class));
                        break;

                    default:

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(i==1){
            return objs.size();

        }
        if (objs == null) {
            return 0;
        }
        if (objs.size() >= 6) {
            return 6;
        }
        return objs.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        LinearLayout linearLayout;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txt = itemView.findViewById(R.id.txt);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
