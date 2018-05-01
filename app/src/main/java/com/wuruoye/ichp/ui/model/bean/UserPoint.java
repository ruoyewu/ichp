package com.wuruoye.ichp.ui.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 18:58.
 * @Description :
 */

public class UserPoint implements Parcelable {
    private int commN;
    private int collN;
    private int entryN;
    private int apprN;
    private int recN;
    private int point;
    private int recP;
    private int collP;
    private int entryP;
    private int apprP;
    private int commP;

    private String title;
    private String level;
    private String authority;

    public int getCommN() {
        return commN;
    }

    public void setCommN(int commN) {
        this.commN = commN;
    }

    public int getCollN() {
        return collN;
    }

    public void setCollN(int collN) {
        this.collN = collN;
    }

    public int getEntryN() {
        return entryN;
    }

    public void setEntryN(int entryN) {
        this.entryN = entryN;
    }

    public int getApprN() {
        return apprN;
    }

    public void setApprN(int apprN) {
        this.apprN = apprN;
    }

    public int getRecN() {
        return recN;
    }

    public void setRecN(int recN) {
        this.recN = recN;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getRecP() {
        return recP;
    }

    public void setRecP(int recP) {
        this.recP = recP;
    }

    public int getCollP() {
        return collP;
    }

    public void setCollP(int collP) {
        this.collP = collP;
    }

    public int getEntryP() {
        return entryP;
    }

    public void setEntryP(int entryP) {
        this.entryP = entryP;
    }

    public int getApprP() {
        return apprP;
    }

    public void setApprP(int apprP) {
        this.apprP = apprP;
    }

    public int getCommP() {
        return commP;
    }

    public void setCommP(int commP) {
        this.commP = commP;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.commN);
        dest.writeInt(this.collN);
        dest.writeInt(this.entryN);
        dest.writeInt(this.apprN);
        dest.writeInt(this.recN);
        dest.writeInt(this.point);
        dest.writeInt(this.recP);
        dest.writeInt(this.collP);
        dest.writeInt(this.entryP);
        dest.writeInt(this.apprP);
        dest.writeInt(this.commP);
        dest.writeString(this.title);
        dest.writeString(this.level);
        dest.writeString(this.authority);
    }

    public UserPoint() {
    }

    protected UserPoint(Parcel in) {
        this.commN = in.readInt();
        this.collN = in.readInt();
        this.entryN = in.readInt();
        this.apprN = in.readInt();
        this.recN = in.readInt();
        this.point = in.readInt();
        this.recP = in.readInt();
        this.collP = in.readInt();
        this.entryP = in.readInt();
        this.apprP = in.readInt();
        this.commP = in.readInt();
        this.title = in.readString();
        this.level = in.readString();
        this.authority = in.readString();
    }

    public static final Parcelable.Creator<UserPoint> CREATOR = new Parcelable.Creator<UserPoint>() {
        @Override
        public UserPoint createFromParcel(Parcel source) {
            return new UserPoint(source);
        }

        @Override
        public UserPoint[] newArray(int size) {
            return new UserPoint[size];
        }
    };
}
