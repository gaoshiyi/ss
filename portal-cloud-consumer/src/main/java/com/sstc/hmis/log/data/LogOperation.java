package com.sstc.hmis.log.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LogOperation {
	
    private String id;

    private String grpId;

    private String hotelId;

    private String clientIp;

    private String serverIp;

    private Date bizDate;

    private String system;

    private String modular;

    private String fun;

    private String operation;

    private String accno;

    private String groupno;

    private String prono;

    private String rsvno;

    private Integer roomno;

    private String code;

    private String batch;

    private String editCon;

    private String beforeEdit;

    private String afterEdit;

    private Date buildDate;

    private String createBy;

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date operationDate;

    private String terminal;

    private Short result;

    private String frame;
    
    private String tableName ;
    
    private String startDate ;
    
    private String endDate ;
    
    private String roomnos;
    
    private String operationDateStr ;
    
    private String beforeView ;
    
    private String afterView ;


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	

	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Date getBizDate() {
		return bizDate;
	}

	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getModular() {
		return modular;
	}

	public void setModular(String modular) {
		this.modular = modular;
	}

	public String getFun() {
		return fun;
	}

	public void setFun(String fun) {
		this.fun = fun;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getGroupno() {
		return groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	public String getProno() {
		return prono;
	}

	public void setProno(String prono) {
		this.prono = prono;
	}

	public String getRsvno() {
		return rsvno;
	}

	public void setRsvno(String rsvno) {
		this.rsvno = rsvno;
	}

	public Integer getRoomno() {
		return roomno;
	}

	public void setRoomno(Integer roomno) {
		this.roomno = roomno;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getEditCon() {
		return editCon;
	}

	public void setEditCon(String editCon) {
		this.editCon = editCon;
	}

	public String getBeforeEdit() {
		return beforeEdit;
	}

	public void setBeforeEdit(String beforeEdit) {
		this.beforeEdit = beforeEdit;
	}

	public String getAfterEdit() {
		return afterEdit;
	}

	public void setAfterEdit(String afterEdit) {
		this.afterEdit = afterEdit;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Short getResult() {
		return result;
	}

	public void setResult(Short result) {
		this.result = result;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public String getRoomnos() {
		return roomnos;
	}

	public void setRoomnos(String roomnos) {
		this.roomnos = roomnos;
	}

	public String getOperationDateStr() {
		return operationDateStr;
	}

	public void setOperationDateStr(String operationDateStr) {
		this.operationDateStr = operationDateStr;
	}

	public String getBeforeView() {
		return beforeView;
	}

	public void setBeforeView(String beforeView) {
		this.beforeView = beforeView;
	}

	public String getAfterView() {
		return afterView;
	}

	public void setAfterView(String afterView) {
		this.afterView = afterView;
	}
    
    


}
