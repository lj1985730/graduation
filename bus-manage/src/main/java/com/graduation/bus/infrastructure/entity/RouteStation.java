package com.graduation.bus.infrastructure.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "T_BUS_ROUTE_STATION")
public class RouteStation extends BaseEntity {

    private String routeId;
    private Route route;
    private String stationId;
    private Station station;
    private Integer sort;
    private String remark;

    @Column(name = "ROUTE_ID")
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @NotFound(action = NotFoundAction.EXCEPTION)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_ID", insertable = false, updatable = false, unique = true)
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Column(name = "STATION_ID")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @NotFound(action = NotFoundAction.EXCEPTION)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "STATION_ID", insertable = false, updatable = false, unique = true)
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    @Column(name = "SORT_NUMBER")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
