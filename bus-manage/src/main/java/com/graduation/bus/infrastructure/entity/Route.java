package com.graduation.bus.infrastructure.entity;

import com.graduation.core.base.entity.BaseEntity;
import com.graduation.core.system.entity.SystemDict;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 公交管理-线路-实体
 * @author Liu Jun at 2017-10-15 19:29:39
 * @since v1.0.0
 */
@Entity
@Table(name = "T_BUS_ROUTE")
public class Route extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotBlank(message = "名称不能为空")
	@Length(max = 100, message = "名称长度不能超过100")
	private String name;

	/**
	 * 类型
	 */
	@Length(max = 100, message = "类型长度不能超过100")
	private String categoryId;

	/**
	 * 类型
	 */
	private SystemDict category;

	/**
	 * 发车间隔
	 */
    @Length(max = 100, message = "发车间隔长度不能超过100")
	private String pullInterval;

	/**
	 * 始发站
	 */
	@Length(max = 36, message = "始发站长度不能超过36")
	private String startStationId;

	/**
	 * 始发站
	 */
	private Station startStation;

	/**
	 * 始发站首车发车时间
	 */
	private LocalTime startStationFirstTime;

	/**
	 * 始发站首车发车时间
	 */
	private String startStationFirstTimeStr;

	/**
	 * 始发站末车发车时间
	 */
	private LocalTime startStationLastTime;

	/**
	 * 始发站首车发车时间
	 */
	private String startStationLastTimeStr;

	/**
	 * 终点站
	 */
	@Length(max = 36, message = "终点站长度不能超过36")
	private String endStationId;

	/**
	 * 终点站
	 */
	private Station endStation;

	/**
	 * 终点站首车发车时间
	 */
	private LocalTime endStationFirstTime;

	/**
	 * 始发站首车发车时间
	 */
	private String endStationFirstTimeStr;

	/**
	 * 终点站末车发车时间
	 */
	private LocalTime endStationLastTime;

	/**
	 * 始发站首车发车时间
	 */
	private String endStationLastTimeStr;

	/**
	 * 票价
	 */
	private Float ticketPrice;

	/**
	 * 所属公司ID
	 */
	@Length(max = 36, message = "所属公司ID长度不能超过36")
	private String companyId;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "备注长度不能超过1000")
	private String remark;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CATEGORY")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY", insertable = false, updatable = false, unique = true)
	public SystemDict getCategory() {
		return category;
	}

	public void setCategory(SystemDict category) {
		this.category = category;
	}

	@Column(name = "PULL_INTERVAL")
	public String getPullInterval() {
		return pullInterval;
	}

	public void setPullInterval(String pullInterval) {
		this.pullInterval = pullInterval;
	}

	@Column(name = "START_STATION_ID")
	public String getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(String startStationId) {
		this.startStationId = startStationId;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "START_STATION_ID", insertable = false, updatable = false, unique = true)
	public Station getStartStation() {
		return startStation;
	}

	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}

	@Column(name = "START_STATION_FIRST_TIME")
	public LocalTime getStartStationFirstTime() {
		return startStationFirstTime;
	}

	public void setStartStationFirstTime(LocalTime startStationFirstTime) {
		this.startStationFirstTime = startStationFirstTime;
	}

	@Transient
	public String getStartStationFirstTimeStr() {
		if(this.startStationFirstTime != null) {
			startStationFirstTimeStr = this.startStationFirstTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
		return startStationFirstTimeStr;
	}

	public void setStartStationFirstTimeStr(String startStationFirstTimeStr) {
		this.startStationFirstTimeStr = startStationFirstTimeStr;
		if(StringUtils.isNotBlank(startStationFirstTimeStr)) {
			this.startStationFirstTime = LocalTime.parse(startStationFirstTimeStr);
		}
	}

	@Column(name = "START_STATION_LAST_TIME")
	public LocalTime getStartStationLastTime() {
		return startStationLastTime;
	}

	public void setStartStationLastTime(LocalTime startStationLastTime) {
		this.startStationLastTime = startStationLastTime;
	}

	@Transient
	public String getStartStationLastTimeStr() {
		if(this.startStationLastTime != null) {
			startStationLastTimeStr = this.startStationLastTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
		return startStationLastTimeStr;
	}

	public void setStartStationLastTimeStr(String startStationLastTimeStr) {
		this.startStationLastTimeStr = startStationLastTimeStr;
		if(StringUtils.isNotBlank(startStationLastTimeStr)) {
			this.startStationLastTime = LocalTime.parse(startStationLastTimeStr);
		}
	}

	@Column(name = "END_STATION_ID")
	public String getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(String endStationId) {
		this.endStationId = endStationId;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "END_STATION_ID", insertable = false, updatable = false, unique = true)
	public Station getEndStation() {
		return endStation;
	}

	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}

	@Column(name = "END_STATION_FIRST_TIME")
	public LocalTime getEndStationFirstTime() {
		return endStationFirstTime;
	}

	public void setEndStationFirstTime(LocalTime endStationFirstTime) {
		this.endStationFirstTime = endStationFirstTime;
	}

	@Transient
	public String getEndStationFirstTimeStr() {
		if(this.endStationFirstTime != null) {
			endStationFirstTimeStr = this.endStationFirstTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
		return endStationFirstTimeStr;
	}

	public void setEndStationFirstTimeStr(String endStationFirstTimeStr) {
		this.endStationFirstTimeStr = endStationFirstTimeStr;
		if(StringUtils.isNotBlank(endStationFirstTimeStr)) {
			this.endStationFirstTime = LocalTime.parse(endStationFirstTimeStr);
		}
	}

	@Column(name = "END_STATION_LAST_TIME")
	public LocalTime getEndStationLastTime() {
		return endStationLastTime;
	}

	public void setEndStationLastTime(LocalTime endStationLastTime) {
		this.endStationLastTime = endStationLastTime;
	}

	@Transient
	public String getEndStationLastTimeStr() {
		if(this.endStationLastTime != null) {
			endStationLastTimeStr = this.endStationLastTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
		return endStationLastTimeStr;
	}

	public void setEndStationLastTimeStr(String endStationLastTimeStr) {
		this.endStationLastTimeStr = endStationLastTimeStr;
		if(StringUtils.isNotBlank(endStationLastTimeStr)) {
			this.endStationLastTime = LocalTime.parse(endStationLastTimeStr);
		}
	}

	@Column(name = "TICKET_PRICE")
	public Float getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Float ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	@Column(name = "COMPANY_ID")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String addZero(int i) {
		return i < 10 ? "0" + i : i + "";
	}
}