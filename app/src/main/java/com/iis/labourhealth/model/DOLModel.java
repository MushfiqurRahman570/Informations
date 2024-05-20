package com.iis.labourhealth.model;

public class DOLModel {

    private Integer mDOLId = null;
    private String mDOLName = null;
    private String mDrName = null;
    private String mDOLEmail = null;
    private String mDOLPhone = null;
    private String mDOLAddress = null;
    private String mDOLDivision = null;
    private String mDOLLatitude = null;
    private String mDOLLongitude = null;

    // Constructor with Id and WsmsName
    public DOLModel(Integer eWsmsId, String eWsmsName) {
        this.mDOLId = eWsmsId;
        this.mDOLName = eWsmsName;
    }

    // Constructor without ID  and Division
    public DOLModel(String eWsmsName, String eDrName, String eWsmsEmail, String eWsmsPhone,
                    String eWsmsAddress, String eWsmsLatitude, String eWsmsLongitude) {
        this.mDOLName = eWsmsName;
        this.mDrName = eDrName;
        this.mDOLEmail = eWsmsEmail;
        this.mDOLPhone = eWsmsPhone;
        this.mDOLAddress = eWsmsAddress;
        this.mDOLLatitude = eWsmsLatitude;
        this.mDOLLongitude = eWsmsLongitude;
    }

    // Constructor without ID
    public DOLModel(String eWsmsName, String eDrName, String eWsmsEmail, String eWsmsPhone,
                    String eWsmsAddress, String eWsmsDivision, String eWsmsLatitude, String eWsmsLongitude) {
        this.mDOLName = eWsmsName;
        this.mDrName = eDrName;
        this.mDOLEmail = eWsmsEmail;
        this.mDOLPhone = eWsmsPhone;
        this.mDOLAddress = eWsmsAddress;
        this.mDOLDivision = eWsmsDivision;
        this.mDOLLatitude = eWsmsLatitude;
        this.mDOLLongitude = eWsmsLongitude;
    }

    // All Getters and Setters
    public Integer getmDOLId() {
        return mDOLId;
    }

    public void setmDOLId(Integer eWsmsId) {
        this.mDOLId = eWsmsId;
    }

    public String getmDOLName() {
        //Log.d("Dhaka child name:", mDOLName);
        return mDOLName;
    }

    public void setmDOLName(String eWsmsName) {
        this.mDOLName = eWsmsName;
    }

    public String getmDrName() {
        return mDrName;
    }

    public void setmDrName(String eDrName) {
        this.mDOLName = eDrName;
    }
    public String getmDOLEmail() {
        return mDOLEmail;
    }

    public void setmDOLEmail(String eWsmsEmail) {
        this.mDOLEmail = eWsmsEmail;
    }

    public String getmDOLPhone() {
        return mDOLPhone;
    }

    public void setmDOLPhone(String eWsmsPhone) {
        this.mDOLPhone = eWsmsPhone;
    }

    public String getmDOLAddress() {
        return mDOLAddress;
    }

    public void setmDOLAddress(String eWsmsAddress) {
        this.mDOLAddress = eWsmsAddress;
    }

    public String getmDOLDivision() {
        return mDOLDivision;
    }

    public void setmDOLDivision(String eWsmsDivision) {
        this.mDOLDivision = eWsmsDivision;
    }

    public String getmDOLLatitude() {
        return mDOLLatitude;
    }

    public void setmDOLLatitude(String eWsmsLatitude) {
        this.mDOLLatitude = eWsmsLatitude;
    }

    public String getmDOLLongitude() {
        return mDOLLongitude;
    }

    public void setmDOLLongitude(String eWsmsLongitude) {
        this.mDOLLongitude = eWsmsLongitude;
    }

    // toString
    @Override
    public String toString() {
        return "DOLModel [DOLId=" + mDOLId + ", eDrName=" + mDrName + ", eDOLName=" + mDOLName
                + ", DOLEmail=" + mDOLEmail + ", DOLPhone=" + mDOLPhone + ", DOLAddress="	+ mDOLAddress
                + ", DOLLatitude=" + mDOLLatitude + ", DOLLongitude=" + mDOLLongitude + "]";
    }
}

