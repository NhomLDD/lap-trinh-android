package com.example.leeji.danhb;

/**
 * Created by lee ji on 30/05/2018.
 */

public class Contact {

    private int mID;
    private String mName;
    private String mAddress;
    private String mEmail;
    private String mNumber;
    private int isMale;

    public Contact(){

    }

    public Contact(String mName, String mAddress, String mEmail, String mPhoneNumber, int isMale) {
        this.mName = mName;
        this.mAddress = mAddress;
        this.mEmail = mEmail;
        this.mNumber = mPhoneNumber;
        this.isMale = isMale;
    }

    public Contact(int mID, String mName, String mAddress, String mEmail, String mPhoneNumber, int isMale) {
        this.mID = mID;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mEmail = mEmail;
        this.mNumber = mPhoneNumber;
        this.isMale = isMale;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
    public int getIsMale() {
        return isMale;
    }
    public void setIsMale(int isMale) {
        this.isMale = isMale;
    }
}
