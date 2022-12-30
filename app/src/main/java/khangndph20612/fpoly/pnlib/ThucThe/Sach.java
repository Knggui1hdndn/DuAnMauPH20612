package khangndph20612.fpoly.pnlib.ThucThe;

import java.io.Serializable;

public class Sach implements Serializable {
    private String maSach,maLoai,tieuDe,tacGia,nhaXB,giaBan,soLuong;
private byte []img;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Sach(String maSach, String maLoai, String tieuDe, String tacGia, String nhaXB, String giaBan, String soLuong, byte[] img) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.nhaXB = nhaXB;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.img = img;
    }
    public Sach(String maSach, String maLoai, String tieuDe,String giaBan) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tieuDe = tieuDe;
        this.giaBan = giaBan;
    }
    public Sach() {
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNhaXB() {
        return nhaXB;
    }

    public void setNhaXB(String nhaXB) {
        this.nhaXB = nhaXB;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}
