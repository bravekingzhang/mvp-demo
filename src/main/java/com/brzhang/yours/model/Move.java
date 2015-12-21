package com.brzhang.yours.model;

/**
 * Created by brzhang on 15/12/21.
 * Description :
 */
public class Move {
    private String url;
    private String move_pic;
    private String move_name;

    public String getmMovePic() {
        return move_pic;
    }

    public void setmMovePic(String mMovePic) {
        this.move_pic = mMovePic;
    }

    public String getmMoveName() {
        return move_name;
    }

    public void setmMoveName(String mMoveName) {
        this.move_name = mMoveName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Move{" +
                "mMoveName='" + move_name + '\'' +
                ", mMovePic='" + move_pic + '\'' +
                '}';
    }
}
