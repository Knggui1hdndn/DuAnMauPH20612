package khangndph20612.fpoly.pnlib.ThucThe;

import java.util.List;

public class sach1 {
    private  String loaisach;
    private List<Sach>sachList;

    public sach1(String loaisach, List<Sach> sachList) {
        this.loaisach = loaisach;
        this.sachList = sachList;
    }

    public sach1() {
    }

    public String getSach() {
        return loaisach;
    }

    public void setSach(String loaisach) {
        this.loaisach = loaisach;
    }

    public List<Sach> getSachList() {
        return sachList;
    }

    public void setSachList(List<Sach> sachList) {
        this.sachList = sachList;
    }
}
