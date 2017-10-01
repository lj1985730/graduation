
SET FOREIGN_KEY_CHECKS=0;

-- system tables ---------------------------------------------------------------------------------------------------------

-- ----------------------------
-- Table structure for T_SYS_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS T_SYS_CONFIG;
CREATE TABLE T_SYS_CONFIG (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  CFG_KEY                 VARCHAR(200) NOT NULL COMMENT '配置KEY',
  CFG_VALUE               VARCHAR(500) COMMENT '配置VALUE',
  ENABLE                  CHAR(1) COMMENT '是否生效',
  EDITABLE                CHAR(1) COMMENT '是否可在系统界面修改',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Table structure for T_SYS_COMBO_DATA
-- ----------------------------
DROP TABLE IF EXISTS T_SYS_COMBO_DATA;
CREATE TABLE T_SYS_COMBO_DATA (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  BUSINESS_KEY            VARCHAR(200) NOT NULL COMMENT '业务主键',
  CONTENT                 TEXT NOT NULL COMMENT '内容',
  CONDITIONS              TEXT COMMENT '过滤条件',
  ORDERBY                 TEXT COMMENT '排序',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='下拉菜单字典表';

-- ----------------------------
-- Table structure for T_SYS_DICT
-- ----------------------------
DROP TABLE IF EXISTS T_SYS_DICT;
CREATE TABLE T_SYS_DICT (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(200) NOT NULL COMMENT '名称',
  CODE                    VARCHAR(200) NOT NULL COMMENT '编号',
  CATEGORY_KEY            VARCHAR(200) COMMENT '字典分类',
  SORT_NUMBER             INTEGER(9) COMMENT '排序',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统字典表';