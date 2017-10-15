
SET FOREIGN_KEY_CHECKS=0;

-- authentication tables -----------------------------------------------------------------------------------------------

-- ----------------------------
-- Table structure for T_AUTH_DEPARTMENT
-- ----------------------------
DROP TABLE IF EXISTS T_BUS_STATION;
CREATE TABLE T_BUS_STATION (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(100) NOT NULL COMMENT '名称',
  CODE                    VARCHAR(100) COMMENT '编码',
  LOCATION                VARCHAR(200) NOT NULL COMMENT '地址',
  ENABLED                 CHAR(1) COMMENT '是否使用',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公交站点表';

-- ----------------------------
-- Table structure for T_AUTH_PERSON
-- ----------------------------
DROP TABLE IF EXISTS T_BUS_ROUTE;
CREATE TABLE T_BUS_ROUTE (
  ID                        CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                      VARCHAR(100) NOT NULL COMMENT '名称',
  CATEGORY                  CHAR(36) COMMENT '分类',
  PULL_INTERVAL             VARCHAR(100) COMMENT '发车间隔',
  START_STATION_ID          CHAR(36) COMMENT '起点站',
  START_STATION_FIRST_TIME  TIME COMMENT '起点站首车时间',
  START_STATION_LAST_TIME   TIME COMMENT '起点站末车时间',
  END_STATION_ID            CHAR(36) COMMENT '终点站',
  END_STATION_FIRST_TIME    TIME COMMENT '终点站首车时间',
  END_STATION_LAST_TIME     TIME COMMENT '终点站末车时间',
  TICKET_PRICE              NUMERIC(3,1) COMMENT '票价',
  COMPANY_ID                CHAR(36) COMMENT '所属公司',
  REMARK                    VARCHAR(1000) COMMENT '备注',
  DELETED                   CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID    VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公交线路表';

-- ----------------------------
-- Table structure for T_AUTH_POST
-- ----------------------------
DROP TABLE IF EXISTS T_BUS_ROUTE_STATION;
CREATE TABLE T_BUS_ROUTE_STATION (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  ROUTE_ID                CHAR(36) NOT NULL COMMENT '线路ID',
  STATION_ID              CHAR(36) NOT NULL COMMENT '站点ID',
  SORT_NUMBER             INTEGER COMMENT '序号',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公交线路站点关系表';

-- ----------------------------
-- Table structure for T_AUTH_POST
-- ----------------------------
DROP TABLE IF EXISTS T_BUS_STATION_DISTANCE;
CREATE TABLE T_BUS_STATION_DISTANCE (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  STATION_A               CHAR(36) NOT NULL COMMENT '站点AID',
  STATION_B               CHAR(36) NOT NULL COMMENT '站点BID',
  DISTANCE                NUMERIC(3, 1) COMMENT '距离（公里）',
  USE_TIME                NUMERIC(3, 1) COMMENT '用时（分钟）',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公交站点距离表';

