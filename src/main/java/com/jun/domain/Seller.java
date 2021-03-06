package com.jun.domain;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column seller.seller_id
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    private String sellerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column seller.seller_name
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    private String sellerName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column seller.seller_password
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    private String sellerPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column seller.seller_openid
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    private String sellerOpenid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column seller.create_time
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column seller.update_time
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seller.seller_id
     *
     * @return the value of seller.seller_id
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seller.seller_id
     *
     * @param sellerId the value for seller.seller_id
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seller.seller_name
     *
     * @return the value of seller.seller_name
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seller.seller_name
     *
     * @param sellerName the value for seller.seller_name
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seller.seller_password
     *
     * @return the value of seller.seller_password
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public String getSellerPassword() {
        return sellerPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seller.seller_password
     *
     * @param sellerPassword the value for seller.seller_password
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public void setSellerPassword(String sellerPassword) {
        this.sellerPassword = sellerPassword == null ? null : sellerPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seller.seller_openid
     *
     * @return the value of seller.seller_openid
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public String getSellerOpenid() {
        return sellerOpenid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seller.seller_openid
     *
     * @param sellerOpenid the value for seller.seller_openid
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public void setSellerOpenid(String sellerOpenid) {
        this.sellerOpenid = sellerOpenid == null ? null : sellerOpenid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seller.create_time
     *
     * @return the value of seller.create_time
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seller.create_time
     *
     * @param createTime the value for seller.create_time
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seller.update_time
     *
     * @return the value of seller.update_time
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seller.update_time
     *
     * @param updateTime the value for seller.update_time
     *
     * @mbg.generated Thu Sep 27 09:31:33 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}