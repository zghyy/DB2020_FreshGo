package xyz.zghy.freshgo.model;


import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/4 上午12:12
 */
public class BeanComments {
    private int userId;
    private String userName;
    private int goodsId;
    private int commentOrder;
    private String commentMsg;
    private Date commentDate;
    private int commentStar;
    //TODO 这里缺个图片变量


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCommentOrder() {
        return commentOrder;
    }

    public void setCommentOrder(int commentOrder) {
        this.commentOrder = commentOrder;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(int commentStar) {
        this.commentStar = commentStar;
    }
}
