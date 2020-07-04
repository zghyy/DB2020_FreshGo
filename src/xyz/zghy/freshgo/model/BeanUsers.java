package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/3 ÏÂÎç8:34
 */
public class BeanUsers {
    private int userId;
    private String userName;
    private String userSex;
    private String userPwd;
    private int userPhone;
    private String userEmail;
    private String userCity;
    private Date userCreateDate;
    private String userIsVip;
    private Date userVipEndDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public int getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public Date getUserCreateDate() {
        return userCreateDate;
    }

    public void setUserCreateDate(Date userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    public String getUserIsVip() {
        return userIsVip;
    }

    public void setUserIsVip(String userIsVip) {
        this.userIsVip = userIsVip;
    }

    public Date getUserVipEndDate() {
        return userVipEndDate;
    }

    public void setUserVipEndDate(Date userVipEndDate) {
        this.userVipEndDate = userVipEndDate;
    }
}
