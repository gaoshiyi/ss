package com.sstc.hmis.notification.dbaccess.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PermsTNoticeAccountExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public PermsTNoticeAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andClIdIsNull() {
            addCriterion("cl_id is null");
            return (Criteria) this;
        }

        public Criteria andClIdIsNotNull() {
            addCriterion("cl_id is not null");
            return (Criteria) this;
        }

        public Criteria andClIdEqualTo(String value) {
            addCriterion("cl_id =", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdNotEqualTo(String value) {
            addCriterion("cl_id <>", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdGreaterThan(String value) {
            addCriterion("cl_id >", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_id >=", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdLessThan(String value) {
            addCriterion("cl_id <", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdLessThanOrEqualTo(String value) {
            addCriterion("cl_id <=", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdLike(String value) {
            addCriterion("cl_id like", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdNotLike(String value) {
            addCriterion("cl_id not like", value, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdIn(List<String> values) {
            addCriterion("cl_id in", values, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdNotIn(List<String> values) {
            addCriterion("cl_id not in", values, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdBetween(String value1, String value2) {
            addCriterion("cl_id between", value1, value2, "clId");
            return (Criteria) this;
        }

        public Criteria andClIdNotBetween(String value1, String value2) {
            addCriterion("cl_id not between", value1, value2, "clId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdIsNull() {
            addCriterion("cl_notice_id is null");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdIsNotNull() {
            addCriterion("cl_notice_id is not null");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdEqualTo(String value) {
            addCriterion("cl_notice_id =", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdNotEqualTo(String value) {
            addCriterion("cl_notice_id <>", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdGreaterThan(String value) {
            addCriterion("cl_notice_id >", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_notice_id >=", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdLessThan(String value) {
            addCriterion("cl_notice_id <", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdLessThanOrEqualTo(String value) {
            addCriterion("cl_notice_id <=", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdLike(String value) {
            addCriterion("cl_notice_id like", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdNotLike(String value) {
            addCriterion("cl_notice_id not like", value, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdIn(List<String> values) {
            addCriterion("cl_notice_id in", values, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdNotIn(List<String> values) {
            addCriterion("cl_notice_id not in", values, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdBetween(String value1, String value2) {
            addCriterion("cl_notice_id between", value1, value2, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClNoticeIdNotBetween(String value1, String value2) {
            addCriterion("cl_notice_id not between", value1, value2, "clNoticeId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdIsNull() {
            addCriterion("cl_account_id is null");
            return (Criteria) this;
        }

        public Criteria andClAccountIdIsNotNull() {
            addCriterion("cl_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andClAccountIdEqualTo(String value) {
            addCriterion("cl_account_id =", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdNotEqualTo(String value) {
            addCriterion("cl_account_id <>", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdGreaterThan(String value) {
            addCriterion("cl_account_id >", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_account_id >=", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdLessThan(String value) {
            addCriterion("cl_account_id <", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdLessThanOrEqualTo(String value) {
            addCriterion("cl_account_id <=", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdLike(String value) {
            addCriterion("cl_account_id like", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdNotLike(String value) {
            addCriterion("cl_account_id not like", value, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdIn(List<String> values) {
            addCriterion("cl_account_id in", values, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdNotIn(List<String> values) {
            addCriterion("cl_account_id not in", values, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdBetween(String value1, String value2) {
            addCriterion("cl_account_id between", value1, value2, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClAccountIdNotBetween(String value1, String value2) {
            addCriterion("cl_account_id not between", value1, value2, "clAccountId");
            return (Criteria) this;
        }

        public Criteria andClStatusIsNull() {
            addCriterion("cl_status is null");
            return (Criteria) this;
        }

        public Criteria andClStatusIsNotNull() {
            addCriterion("cl_status is not null");
            return (Criteria) this;
        }

        public Criteria andClStatusEqualTo(Short value) {
            addCriterion("cl_status =", value, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusNotEqualTo(Short value) {
            addCriterion("cl_status <>", value, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusGreaterThan(Short value) {
            addCriterion("cl_status >", value, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("cl_status >=", value, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusLessThan(Short value) {
            addCriterion("cl_status <", value, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusLessThanOrEqualTo(Short value) {
            addCriterion("cl_status <=", value, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusIn(List<Short> values) {
            addCriterion("cl_status in", values, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusNotIn(List<Short> values) {
            addCriterion("cl_status not in", values, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusBetween(Short value1, Short value2) {
            addCriterion("cl_status between", value1, value2, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClStatusNotBetween(Short value1, Short value2) {
            addCriterion("cl_status not between", value1, value2, "clStatus");
            return (Criteria) this;
        }

        public Criteria andClGrpIdIsNull() {
            addCriterion("cl_grp_id is null");
            return (Criteria) this;
        }

        public Criteria andClGrpIdIsNotNull() {
            addCriterion("cl_grp_id is not null");
            return (Criteria) this;
        }

        public Criteria andClGrpIdEqualTo(String value) {
            addCriterion("cl_grp_id =", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdNotEqualTo(String value) {
            addCriterion("cl_grp_id <>", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdGreaterThan(String value) {
            addCriterion("cl_grp_id >", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_grp_id >=", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdLessThan(String value) {
            addCriterion("cl_grp_id <", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdLessThanOrEqualTo(String value) {
            addCriterion("cl_grp_id <=", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdLike(String value) {
            addCriterion("cl_grp_id like", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdNotLike(String value) {
            addCriterion("cl_grp_id not like", value, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdIn(List<String> values) {
            addCriterion("cl_grp_id in", values, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdNotIn(List<String> values) {
            addCriterion("cl_grp_id not in", values, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdBetween(String value1, String value2) {
            addCriterion("cl_grp_id between", value1, value2, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClGrpIdNotBetween(String value1, String value2) {
            addCriterion("cl_grp_id not between", value1, value2, "clGrpId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdIsNull() {
            addCriterion("cl_hotel_id is null");
            return (Criteria) this;
        }

        public Criteria andClHotelIdIsNotNull() {
            addCriterion("cl_hotel_id is not null");
            return (Criteria) this;
        }

        public Criteria andClHotelIdEqualTo(String value) {
            addCriterion("cl_hotel_id =", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdNotEqualTo(String value) {
            addCriterion("cl_hotel_id <>", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdGreaterThan(String value) {
            addCriterion("cl_hotel_id >", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_hotel_id >=", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdLessThan(String value) {
            addCriterion("cl_hotel_id <", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdLessThanOrEqualTo(String value) {
            addCriterion("cl_hotel_id <=", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdLike(String value) {
            addCriterion("cl_hotel_id like", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdNotLike(String value) {
            addCriterion("cl_hotel_id not like", value, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdIn(List<String> values) {
            addCriterion("cl_hotel_id in", values, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdNotIn(List<String> values) {
            addCriterion("cl_hotel_id not in", values, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdBetween(String value1, String value2) {
            addCriterion("cl_hotel_id between", value1, value2, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClHotelIdNotBetween(String value1, String value2) {
            addCriterion("cl_hotel_id not between", value1, value2, "clHotelId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdIsNull() {
            addCriterion("cl_create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdIsNotNull() {
            addCriterion("cl_create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdEqualTo(String value) {
            addCriterion("cl_create_user_id =", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdNotEqualTo(String value) {
            addCriterion("cl_create_user_id <>", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdGreaterThan(String value) {
            addCriterion("cl_create_user_id >", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_create_user_id >=", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdLessThan(String value) {
            addCriterion("cl_create_user_id <", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("cl_create_user_id <=", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdLike(String value) {
            addCriterion("cl_create_user_id like", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdNotLike(String value) {
            addCriterion("cl_create_user_id not like", value, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdIn(List<String> values) {
            addCriterion("cl_create_user_id in", values, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdNotIn(List<String> values) {
            addCriterion("cl_create_user_id not in", values, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdBetween(String value1, String value2) {
            addCriterion("cl_create_user_id between", value1, value2, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("cl_create_user_id not between", value1, value2, "clCreateUserId");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeIsNull() {
            addCriterion("cl_create_time is null");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeIsNotNull() {
            addCriterion("cl_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeEqualTo(Date value) {
            addCriterion("cl_create_time =", value, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeNotEqualTo(Date value) {
            addCriterion("cl_create_time <>", value, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeGreaterThan(Date value) {
            addCriterion("cl_create_time >", value, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cl_create_time >=", value, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeLessThan(Date value) {
            addCriterion("cl_create_time <", value, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("cl_create_time <=", value, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeIn(List<Date> values) {
            addCriterion("cl_create_time in", values, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeNotIn(List<Date> values) {
            addCriterion("cl_create_time not in", values, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeBetween(Date value1, Date value2) {
            addCriterion("cl_create_time between", value1, value2, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("cl_create_time not between", value1, value2, "clCreateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdIsNull() {
            addCriterion("cl_update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdIsNotNull() {
            addCriterion("cl_update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdEqualTo(String value) {
            addCriterion("cl_update_user_id =", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdNotEqualTo(String value) {
            addCriterion("cl_update_user_id <>", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdGreaterThan(String value) {
            addCriterion("cl_update_user_id >", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_update_user_id >=", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdLessThan(String value) {
            addCriterion("cl_update_user_id <", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdLessThanOrEqualTo(String value) {
            addCriterion("cl_update_user_id <=", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdLike(String value) {
            addCriterion("cl_update_user_id like", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdNotLike(String value) {
            addCriterion("cl_update_user_id not like", value, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdIn(List<String> values) {
            addCriterion("cl_update_user_id in", values, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdNotIn(List<String> values) {
            addCriterion("cl_update_user_id not in", values, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdBetween(String value1, String value2) {
            addCriterion("cl_update_user_id between", value1, value2, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIdNotBetween(String value1, String value2) {
            addCriterion("cl_update_user_id not between", value1, value2, "clUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeIsNull() {
            addCriterion("cl_update_time is null");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeIsNotNull() {
            addCriterion("cl_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeEqualTo(Date value) {
            addCriterion("cl_update_time =", value, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeNotEqualTo(Date value) {
            addCriterion("cl_update_time <>", value, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeGreaterThan(Date value) {
            addCriterion("cl_update_time >", value, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cl_update_time >=", value, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeLessThan(Date value) {
            addCriterion("cl_update_time <", value, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("cl_update_time <=", value, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeIn(List<Date> values) {
            addCriterion("cl_update_time in", values, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeNotIn(List<Date> values) {
            addCriterion("cl_update_time not in", values, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("cl_update_time between", value1, value2, "clUpdateTime");
            return (Criteria) this;
        }

        public Criteria andClUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("cl_update_time not between", value1, value2, "clUpdateTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated do_not_delete_during_merge Wed May 09 16:17:03 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table perms.perms_t_notice_account
     *
     * @mbggenerated Wed May 09 16:17:03 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}