package khangndph20612.fpoly.pnlib.ThucThe;

import java.io.Serializable;

public class PhieuMuon implements Serializable {
    private String maPM,maTT,maTV,maSach,ngayMuon;
    private Integer traSach,tienThue;
private String  gio;


    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public PhieuMuon() {
    }

    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPM='" + maPM + '\'' +
                ", maTT='" + maTT + '\'' +
                ", maTV='" + maTV + '\'' +
                ", maSach='" + maSach + '\'' +
                ", ngayMuon='" + ngayMuon + '\'' +
                ", traSach=" + traSach +
                ", tienThue=" + tienThue +
                ", gio='" + gio + '\'' +
                '}';
    }


    public PhieuMuon(String maPM, String maTT, String maTV, String maSach, Integer tienThue, String ngayMuon, Integer traSach, String gio) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
        this.gio = gio;
    }
    public PhieuMuon(String maPM, String maTT, String maTV, String maSach, Integer tienThue, String ngayMuon, Integer traSach ) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
     }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public Integer getTienThue() {
        return tienThue;
    }

    public void setTienThue(Integer tienThue) {
        this.tienThue = tienThue;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Integer getTraSach() {
        return traSach;
    }

    public void setTraSach(Integer traSach) {
        this.traSach = traSach;
    }
}
