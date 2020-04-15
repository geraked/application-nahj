package ir.geraked.nahj.recyclerlist;

public class Item {

    private int uId;
    private String uCat;
    private String uCount;
    private String uTitle;
    private String uShort;

    public Item(int uId, String uCat, String uCount, String uTitle, String uShort) {
        this.uId = uId;
        this.uCat = uCat;
        this.uCount = uCount;
        this.uTitle = uTitle;
        this.uShort = uShort;
    }

    public int getuId() {
        return uId;
    }

    public String getuCat() {
        return uCat;
    }

    public String getuCount() {
        return uCount;
    }

    public String getuTitle() {
        return uTitle;
    }

    public String getuShort() {
        return uShort;
    }

}