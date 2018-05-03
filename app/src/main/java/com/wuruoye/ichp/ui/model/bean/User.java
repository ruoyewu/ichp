package com.wuruoye.ichp.ui.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class User implements Parcelable {
    private String image_src;
    private String name;
    private int acc_point;
    private int role;
    private String account_name;
    private float reg_date;
    private String telephone;
    private String sign;
    private int user_id;
    private boolean isConcern;
    private int payNum;
    private int bePaidNum;

    public int getPayNum() {
        return payNum;
    }

    public void setPayNum(int payNum) {
        this.payNum = payNum;
    }

    public int getBePaidNum() {
        return bePaidNum;
    }

    public void setBePaidNum(int bePaidNum) {
        this.bePaidNum = bePaidNum;
    }

    public boolean isConcern() {
        return isConcern;
    }

    public void setConcern(boolean concern) {
        isConcern = concern;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcc_point() {
        return acc_point;
    }

    public void setAcc_point(int acc_point) {
        this.acc_point = acc_point;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public float getReg_date() {
        return reg_date;
    }

    public void setReg_date(float reg_date) {
        this.reg_date = reg_date;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image_src);
        dest.writeString(this.name);
        dest.writeInt(this.acc_point);
        dest.writeInt(this.role);
        dest.writeString(this.account_name);
        dest.writeFloat(this.reg_date);
        dest.writeString(this.telephone);
        dest.writeString(this.sign);
        dest.writeInt(this.user_id);
        dest.writeByte(this.isConcern ? (byte) 1 : (byte) 0);
        dest.writeInt(this.payNum);
        dest.writeInt(this.bePaidNum);
    }

    protected User(Parcel in) {
        this.image_src = in.readString();
        this.name = in.readString();
        this.acc_point = in.readInt();
        this.role = in.readInt();
        this.account_name = in.readString();
        this.reg_date = in.readFloat();
        this.telephone = in.readString();
        this.sign = in.readString();
        this.user_id = in.readInt();
        this.isConcern = in.readByte() != 0;
        this.payNum = in.readInt();
        this.bePaidNum = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
