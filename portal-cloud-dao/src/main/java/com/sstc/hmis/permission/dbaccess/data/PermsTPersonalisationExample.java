package com.sstc.hmis.permission.dbaccess.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PermsTPersonalisationExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public PermsTPersonalisationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
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
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
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

        public Criteria andClKeyIsNull() {
            addCriterion("cl_key is null");
            return (Criteria) this;
        }

        public Criteria andClKeyIsNotNull() {
            addCriterion("cl_key is not null");
            return (Criteria) this;
        }

        public Criteria andClKeyEqualTo(String value) {
            addCriterion("cl_key =", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyNotEqualTo(String value) {
            addCriterion("cl_key <>", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyGreaterThan(String value) {
            addCriterion("cl_key >", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyGreaterThanOrEqualTo(String value) {
            addCriterion("cl_key >=", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyLessThan(String value) {
            addCriterion("cl_key <", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyLessThanOrEqualTo(String value) {
            addCriterion("cl_key <=", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyLike(String value) {
            addCriterion("cl_key like", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyNotLike(String value) {
            addCriterion("cl_key not like", value, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyIn(List<String> values) {
            addCriterion("cl_key in", values, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyNotIn(List<String> values) {
            addCriterion("cl_key not in", values, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyBetween(String value1, String value2) {
            addCriterion("cl_key between", value1, value2, "clKey");
            return (Criteria) this;
        }

        public Criteria andClKeyNotBetween(String value1, String value2) {
            addCriterion("cl_key not between", value1, value2, "clKey");
            return (Criteria) this;
        }

        public Criteria andClValueIsNull() {
            addCriterion("cl_value is null");
            return (Criteria) this;
        }

        public Criteria andClValueIsNotNull() {
            addCriterion("cl_value is not null");
            return (Criteria) this;
        }

        public Criteria andClValueEqualTo(String value) {
            addCriterion("cl_value =", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueNotEqualTo(String value) {
            addCriterion("cl_value <>", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueGreaterThan(String value) {
            addCriterion("cl_value >", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueGreaterThanOrEqualTo(String value) {
            addCriterion("cl_value >=", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueLessThan(String value) {
            addCriterion("cl_value <", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueLessThanOrEqualTo(String value) {
            addCriterion("cl_value <=", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueLike(String value) {
            addCriterion("cl_value like", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueNotLike(String value) {
            addCriterion("cl_value not like", value, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueIn(List<String> values) {
            addCriterion("cl_value in", values, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueNotIn(List<String> values) {
            addCriterion("cl_value not in", values, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueBetween(String value1, String value2) {
            addCriterion("cl_value between", value1, value2, "clValue");
            return (Criteria) this;
        }

        public Criteria andClValueNotBetween(String value1, String value2) {
            addCriterion("cl_value not between", value1, value2, "clValue");
            return (Criteria) this;
        }

        public Criteria andClUserIdIsNull() {
            addCriterion("cl_user_id is null");
            return (Criteria) this;
        }

        public Criteria andClUserIdIsNotNull() {
            addCriterion("cl_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andClUserIdEqualTo(String value) {
            addCriterion("cl_user_id =", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdNotEqualTo(String value) {
            addCriterion("cl_user_id <>", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdGreaterThan(String value) {
            addCriterion("cl_user_id >", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("cl_user_id >=", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdLessThan(String value) {
            addCriterion("cl_user_id <", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdLessThanOrEqualTo(String value) {
            addCriterion("cl_user_id <=", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdLike(String value) {
            addCriterion("cl_user_id like", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdNotLike(String value) {
            addCriterion("cl_user_id not like", value, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdIn(List<String> values) {
            addCriterion("cl_user_id in", values, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdNotIn(List<String> values) {
            addCriterion("cl_user_id not in", values, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdBetween(String value1, String value2) {
            addCriterion("cl_user_id between", value1, value2, "clUserId");
            return (Criteria) this;
        }

        public Criteria andClUserIdNotBetween(String value1, String value2) {
            addCriterion("cl_user_id not between", value1, value2, "clUserId");
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

        public Criteria andClCreateUserIsNull() {
            addCriterion("cl_create_user is null");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIsNotNull() {
            addCriterion("cl_create_user is not null");
            return (Criteria) this;
        }

        public Criteria andClCreateUserEqualTo(String value) {
            addCriterion("cl_create_user =", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserNotEqualTo(String value) {
            addCriterion("cl_create_user <>", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserGreaterThan(String value) {
            addCriterion("cl_create_user >", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("cl_create_user >=", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserLessThan(String value) {
            addCriterion("cl_create_user <", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserLessThanOrEqualTo(String value) {
            addCriterion("cl_create_user <=", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserLike(String value) {
            addCriterion("cl_create_user like", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserNotLike(String value) {
            addCriterion("cl_create_user not like", value, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserIn(List<String> values) {
            addCriterion("cl_create_user in", values, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserNotIn(List<String> values) {
            addCriterion("cl_create_user not in", values, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserBetween(String value1, String value2) {
            addCriterion("cl_create_user between", value1, value2, "clCreateUser");
            return (Criteria) this;
        }

        public Criteria andClCreateUserNotBetween(String value1, String value2) {
            addCriterion("cl_create_user not between", value1, value2, "clCreateUser");
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

        public Criteria andClUpdateUserIsNull() {
            addCriterion("cl_update_user is null");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIsNotNull() {
            addCriterion("cl_update_user is not null");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserEqualTo(String value) {
            addCriterion("cl_update_user =", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserNotEqualTo(String value) {
            addCriterion("cl_update_user <>", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserGreaterThan(String value) {
            addCriterion("cl_update_user >", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("cl_update_user >=", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserLessThan(String value) {
            addCriterion("cl_update_user <", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("cl_update_user <=", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserLike(String value) {
            addCriterion("cl_update_user like", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserNotLike(String value) {
            addCriterion("cl_update_user not like", value, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserIn(List<String> values) {
            addCriterion("cl_update_user in", values, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserNotIn(List<String> values) {
            addCriterion("cl_update_user not in", values, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserBetween(String value1, String value2) {
            addCriterion("cl_update_user between", value1, value2, "clUpdateUser");
            return (Criteria) this;
        }

        public Criteria andClUpdateUserNotBetween(String value1, String value2) {
            addCriterion("cl_update_user not between", value1, value2, "clUpdateUser");
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
     * This class corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated do_not_delete_during_merge Thu Apr 12 14:28:02 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table perms.perms_t_personalisation
     *
     * @mbggenerated Thu Apr 12 14:28:02 CST 2018
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