package khangndph20612.fpoly.pnlib.ThucThe;

import java.io.Serializable;

public class LoaiSach implements Serializable {
    private String maLoai;
    private String tenLoai;
    private String moTa;
    private String viTri;

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public LoaiSach() {
    }

    public LoaiSach(String maLoai, String tenLoai, String moTa, String viTri) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
        this.viTri = viTri;
    }
}
