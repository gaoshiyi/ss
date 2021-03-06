package com.sstc.hmis.permission.dbaccess.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sstc.hmis.permission.dbaccess.data.TApkVersionDetail;
import com.sstc.hmis.permission.dbaccess.data.TApkVersionDetailExample;

@Mapper
public interface TApkVersionDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int countByExample(TApkVersionDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int deleteByExample(TApkVersionDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int deleteByPrimaryKey(String clId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int insert(TApkVersionDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int insertSelective(TApkVersionDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    List<TApkVersionDetail> selectByExample(TApkVersionDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    TApkVersionDetail selectByPrimaryKey(String clId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int updateByExampleSelective(@Param("record") TApkVersionDetail record, @Param("example") TApkVersionDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int updateByExample(@Param("record") TApkVersionDetail record, @Param("example") TApkVersionDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int updateByPrimaryKeySelective(TApkVersionDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table perms.t_apk_version_detail
     *
     * @mbggenerated Thu Sep 14 11:01:32 CST 2017
     */
    int updateByPrimaryKey(TApkVersionDetail record);
    
    /**
     * 获取APK信息
     */
    List<TApkVersionDetail> getNewApkDetail(Map<String, Object> paramMap);
}