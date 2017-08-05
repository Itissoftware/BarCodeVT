package mncomunity1.com.barcodevt.model;

public class Machine {
    String nameTh;
    String code;
    boolean box;


    public Machine(){

    }

    Machine(String nameTh, String code, boolean box) {
        this.nameTh = nameTh;
        this.code = code;
        box = box;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }


}
