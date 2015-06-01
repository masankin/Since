package Bean;

/**
 * Created by SHLSY on 2015/6/1.
 */
public class SinceBean {
    private  String content;
    private  int days_num;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        content = content;
    }

    public int getDays_num() {
        return days_num;
    }

    public void setDays_num(int days_num) {
        this.days_num = days_num;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }



    private  String img_url;

    public int getIs_forever() {
        return is_forever;
    }

    public void setIs_forever(int is_forever) {
        this.is_forever = is_forever;
    }

    int  is_forever;

}
