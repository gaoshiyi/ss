package com.sstc.hmis.permission.data;

/**
 * apk版本信息Entity
 * @author Lrongrong
 *
 */
public class ApkDetail {

	/**
	 * 最新APK路径URL
	 */
	private String apkUrl;
	
	/**
	 * 最新APK版本号
	 */
	private String version;
	
	/**
	 * 更新日志
	 */
	private String log;
	
	/**
	 * 更新时间
	 */
	private long updateTime;
	
	/**
	 * 截止强制更新版本号
	 */
	private String updVersion;

	/**
	 * @return the apkUrl
	 */
	public String getApkUrl() {
		return apkUrl;
	}

	/**
	 * @param apkUrl the apkUrl to set
	 */
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(String log) {
		this.log = log;
	}

	/**
	 * @return the updateTime
	 */
	public long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updVersion
	 */
	public String getUpdVersion() {
		return updVersion;
	}

	/**
	 * @param updVersion the updVersion to set
	 */
	public void setUpdVersion(String updVersion) {
		this.updVersion = updVersion;
	}
}
