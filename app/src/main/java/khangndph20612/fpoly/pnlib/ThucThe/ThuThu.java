package khangndph20612.fpoly.pnlib.ThucThe;

public class ThuThu {
    private String maTT,tenTT,soDT,matKhau;
private byte [] img;

    public ThuThu(String maTT, String tenTT, String soDT, String matKhau, byte[] img) {
        this.maTT = maTT;
        this.tenTT = tenTT;
        this.soDT = soDT;
        this.matKhau = matKhau;
        this.img = img;
    }
    public ThuThu( String tenTT , byte[] img,String maTT) {

        this.maTT = maTT;
        this.tenTT = tenTT;

        this.img = img;
    }
    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public ThuThu() {
    }
}
