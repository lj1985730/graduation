package com.graduation.bus.infrastructure.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * 公交管理-站点-实体
 * @author Liu Jun at 2017-10-26 21:00:21
 * @since v1.0.0
 */
@Entity
@Table(name = "T_BUS_STATION_DISTANCE")
public class StationDistance extends BaseEntity {

    @NotNull(message = "'起点站'不能为空！")
    private String stationAId;  //A站ID

    private Station stationA;   //A站

    @NotNull(message = "'终点站'不能为空！")
    private String stationBId;  //B站ID

    private Station stationB;   //B站

    @NotNull(message = "'距离'不能为空！")
    @Digits(integer = 5, fraction = 2, message = "'距离'的最大整数精度5,最大小数精度2")
    private Double distance;    //距离

    @Digits(integer = 5, fraction = 2, message = "'用时'的最大整数精度5,最大小数精度2")
    private Double useTime;     //用时（分钟）

    @Column(name = "STATION_A")
    public String getStationAId() {
        return stationAId;
    }

    public void setStationAId(String stationAId) {
        this.stationAId = stationAId;
    }

    @NotFound(action = NotFoundAction.EXCEPTION)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "STATION_A", insertable = false, updatable = false, unique = true)
    public Station getStationA() {
        return stationA;
    }

    public void setStationA(Station stationA) {
        this.stationA = stationA;
    }

    @Column(name = "STATION_B")
    public String getStationBId() {
        return stationBId;
    }

    public void setStationBId(String stationBId) {
        this.stationBId = stationBId;
    }

    @NotFound(action = NotFoundAction.EXCEPTION)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "STATION_B", insertable = false, updatable = false, unique = true)
    public Station getStationB() {
        return stationB;
    }

    public void setStationB(Station stationB) {
        this.stationB = stationB;
    }

    @Column(name = "DISTANCE")
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Column(name = "USE_TIME")
    public Double getUseTime() {
        return useTime;
    }

    public void setUseTime(Double useTime) {
        this.useTime = useTime;
    }
}
