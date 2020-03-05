package ir.geraked.nahj.database;

public class Model {

    private int id;
    private int cat;
    private String num;
    private String title;
    private String cnt;
    private int fav;

    public Model(int id, int cat, String num, String title, String cnt, int fav) {
        this.id = id;
        this.cat = cat;
        this.num = num;
        this.title = title;
        this.cnt = cnt;
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat() {
        return cat;
    }

    public String getNum() {
        return persianNumEditor(num);
    }

    public String getTitle() {
        return persianEditor(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getcnt() {
        cnt = persianEditor(cnt);
        cnt = cnt.replaceAll("[.،؛:]+$", "");
        cnt = cnt.replaceAll("([^?!])$", "$1.");
        return cnt.trim();
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    private String persianEditor(String cnt) {
//        cnt = cnt.trim();
//        cnt = cnt.replace("\u200F", "\u200C");
//        cnt = cnt.replaceAll(" {2,}", " ");
//        cnt = cnt.replaceAll("\n{2,}", "\n\n");
//
//        cnt = cnt.replace("0", "۰");
//        cnt = cnt.replace("1", "۱");
//        cnt = cnt.replace("2", "۲");
//        cnt = cnt.replace("3", "۳");
//        cnt = cnt.replace("4", "۴");
//        cnt = cnt.replace("5", "۵");
//        cnt = cnt.replace("6", "۶");
//        cnt = cnt.replace("7", "۷");
//        cnt = cnt.replace("8", "۸");
//        cnt = cnt.replace("9", "۹");
//
//        cnt = cnt.replaceAll("\n([۰۱۲۳۴۵۶۷۸۹]+)([^-۰۱۲۳۴۵۶۷۸۹])", "\n$1-$2");
//        cnt = cnt.replaceAll("([^ \n۰۱۲۳۴۵۶۷۸۹])([۰۱۲۳۴۵۶۷۸۹]+)", "$1 $2");
//        cnt = cnt.replaceAll("([۰۱۲۳۴۵۶۷۸۹]+)([^-\n۰۱۲۳۴۵۶۷۸۹ ])", "$1 $2");
//
//        cnt = cnt.replace("ي", "ی");
//        cnt = cnt.replace("ی", "ی");
//        cnt = cnt.replace("ك", "ک");
//        cnt = cnt.replace("ب\u0649 آن که", "بی\u200Cآنکه");
//        cnt = cnt.replace(" ها\u0649 ", "\u200Cهای ");
//        cnt = cnt.replace(" ها ", "\u200Cها ");
//        cnt = cnt.replace("ه ا\u0649 ", "ه\u200Cای ");
//        cnt = cnt.replace(" م\u0649 ", " می\u200C");
//        cnt = cnt.replace(" نم\u0649 ", " نمی\u200C");
//        cnt = cnt.replace(" م\u06CCک", " می\u200Cک");
//        cnt = cnt.replace(" م\u06CCد", " می\u200Cد");
//        cnt = cnt.replace(" ب\u0649 ", " بی\u200C");
//        cnt = cnt.replace("آنها", "آن\u200Cها");
//        cnt = cnt.replace(" اند ", "\u200Cاند ");
//        cnt = cnt.replace("کسیکه", "کسی که");
//        cnt = cnt.replace("بمقدار", "به\u200Cمقدار");
//        cnt = cnt.replace("ّ", "");
//
//        cnt = cnt.replace("?", "؟");
//
//        cnt = cnt.replace(" .", ".");
//        cnt = cnt.replace(" :", ":");
//        cnt = cnt.replace(" ،", "،");
//        cnt = cnt.replace(" ؛", "؛");
//        cnt = cnt.replace(" !", "!");
//        cnt = cnt.replace(" ؟", "؟");
//
//        cnt = cnt.replace(".:", ".");
//        cnt = cnt.replace(":.", ".");
//        cnt = cnt.replace(".،", ".");
//        cnt = cnt.replace("،.", ".");
//
//        cnt = cnt.replaceAll("([.،:؟!)»؛]+)([^\n .،:؟!)»؛])", "$1 $2");
//        cnt = cnt.replaceAll("([^\n ])([(«\\[])", "$1 $2");
//
//        cnt = cnt.replace(" »", "»");
//        cnt = cnt.replace("« ", "«");
//        cnt = cnt.replace(" )", ")");
//        cnt = cnt.replace("( ", "(");

        return cnt.trim();
    }

    private String persianNumEditor(String cnt) {

        cnt = cnt.replace("0", "۰");
        cnt = cnt.replace("1", "۱");
        cnt = cnt.replace("2", "۲");
        cnt = cnt.replace("3", "۳");
        cnt = cnt.replace("4", "۴");
        cnt = cnt.replace("5", "۵");
        cnt = cnt.replace("6", "۶");
        cnt = cnt.replace("7", "۷");
        cnt = cnt.replace("8", "۸");
        cnt = cnt.replace("9", "۹");

        return cnt.trim();
    }
}
