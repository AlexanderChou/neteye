package com.base.model;



import java.util.Date;

/**
 * Ticket entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Ticket implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userByApproverId;
	private Long project;
	private Long userByUndertakerId;
	private Long category;
	private Long priority;
	private String title;
	private String content;
	private String description;
	private int status=-1;
	private Date createTime;
	private Date commitTime;
	private Date commitApproverTime;
	private Date receiveApproverTime;
	private Date approverPassTime;
	private Date closeTime;
	private Date delegateTime;
	private Date receiveDelegateTime;
	private Byte isdigest;
	private String ccIds;
	private String ccGroupIds;
	private Long pid;

	// Constructors

	/** default constructor */
	public Ticket() {
	}

	/** minimal constructor */
	public Ticket(Long id, Date createTime, Date commitTime,
			Date commitApproverTime, Date receiveApproverTime,
			Date approverPassTime, Date closeTime, Date delegateTime,
			Date receiveDelegateTime) {
		this.id = id;
		this.createTime = createTime;
		this.commitTime = commitTime;
		this.commitApproverTime = commitApproverTime;
		this.receiveApproverTime = receiveApproverTime;
		this.approverPassTime = approverPassTime;
		this.closeTime = closeTime;
		this.delegateTime = delegateTime;
		this.receiveDelegateTime = receiveDelegateTime;
	}

	/** full constructor */
	public Ticket(Long id, Long userPojoByApproverId, Long project,
			Long userPojoByUndertakerId, Long category, Long priority,
			String title, String content, String description, int status,
			Date createTime, Date commitTime, Date commitApproverTime,
			Date receiveApproverTime, Date approverPassTime, Date closeTime,
			Date delegateTime, Date receiveDelegateTime, Byte isdigest,
			String ccIds, String ccGroupIds, Long pid) {
		this.id = id;
		this.userByApproverId = userPojoByApproverId;
		this.project = project;
		this.userByUndertakerId = userPojoByUndertakerId;
		this.category = category;
		this.priority = priority;
		this.title = title;
		this.content = content;
		this.description = description;
		this.status = status;
		this.createTime = createTime;
		this.commitTime = commitTime;
		this.commitApproverTime = commitApproverTime;
		this.receiveApproverTime = receiveApproverTime;
		this.approverPassTime = approverPassTime;
		this.closeTime = closeTime;
		this.delegateTime = delegateTime;
		this.receiveDelegateTime = receiveDelegateTime;
		this.isdigest = isdigest;
		this.ccIds = ccIds;
		this.ccGroupIds = ccGroupIds;
		this.pid = pid;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserByApproverId() {
		return this.userByApproverId;
	}

	public void setUserByApproverId(Long UserByApproverId) {
		this.userByApproverId = UserByApproverId;
	}

	public Long getProject() {
		return this.project;
	}

	public void setProject(Long project) {
		this.project = project;
	}

	public Long getUserByUndertakerId() {
		return this.userByUndertakerId;
	}

	public void setUserByUndertakerId(Long UserByUndertakerId) {
		this.userByUndertakerId = UserByUndertakerId;
	}

	public Long getCategory() {
		return this.category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCommitTime() {
		return this.commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Date getCommitApproverTime() {
		return this.commitApproverTime;
	}

	public void setCommitApproverTime(Date commitApproverTime) {
		this.commitApproverTime = commitApproverTime;
	}

	public Date getReceiveApproverTime() {
		return this.receiveApproverTime;
	}

	public void setReceiveApproverTime(Date receiveApproverTime) {
		this.receiveApproverTime = receiveApproverTime;
	}

	public Date getApproverPassTime() {
		return this.approverPassTime;
	}

	public void setApproverPassTime(Date approverPassTime) {
		this.approverPassTime = approverPassTime;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getDelegateTime() {
		return this.delegateTime;
	}

	public void setDelegateTime(Date delegateTime) {
		this.delegateTime = delegateTime;
	}

	public Date getReceiveDelegateTime() {
		return this.receiveDelegateTime;
	}

	public void setReceiveDelegateTime(Date receiveDelegateTime) {
		this.receiveDelegateTime = receiveDelegateTime;
	}

	public Byte getIsdigest() {
		return this.isdigest;
	}

	public void setIsdigest(Byte isdigest) {
		this.isdigest = isdigest;
	}

	public String getCcIds() {
		return this.ccIds;
	}

	public void setCcIds(String ccIds) {
		this.ccIds = ccIds;
	}

	public String getCcGroupIds() {
		return this.ccGroupIds;
	}

	public void setCcGroupIds(String ccGroupIds) {
		this.ccGroupIds = ccGroupIds;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}