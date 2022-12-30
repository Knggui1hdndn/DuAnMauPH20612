package khangndph20612.fpoly.pnlib.ThucThe;

import java.io.Serializable;

public class ThanhVien implements Serializable {
    private String maTV,tenTV,soDT;
    private byte img[];


    public ThanhVien() {
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getSoDT() {
        return soDT;
    }

    public ThanhVien(String maTV, String tenTV, String soDT, byte[] img) {
        this.maTV = maTV;
        this.tenTV = tenTV;
        this.soDT = soDT;
        this.img = img;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }


}
