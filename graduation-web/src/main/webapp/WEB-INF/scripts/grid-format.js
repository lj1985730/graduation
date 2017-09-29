//统一表格前端格式化文件
var lsFmt = {
    pub: {},	//通用方法
    company: {}, //企业
    person: {}, //人员
    post: {}, //岗位
    comp: {},	//设备
    certinfo: {},//证书
    maintenance: {},//维护保养
    shipinfo: {},//船舶信息
    material: {},//物料
    spare: {},//备件
    grease: {},//滑油、化学试剂
    safety: {}//安全管理
};

/**
 * 通用方法
 */
lsFmt.pub = {
    /**
     * 通用-是或否
     * @param value 0否；1是
     */
    trueOrFalse: function (value) {
        switch (value) {
            case '0':
            case 0:
                return '否';
            case '1':
            case 1:
                return '是';
            default:
                return '';
        }
    },
    /**
     * 通用-显示title
     * @param value 显示值
     */
    tooltipTitle: function (value) {
        if (value != "" && value != null && value != "null" && value != "NULL") {
            value = "<span class='tooltip-show' data-toggle='tooltip' data-placement='left' title='" + value + "'>" + value + "</span>";
        } else {
            value = '';
        }
        return value;
    },
    /**
     * 通用-是否使用
     * @param value 0禁用；1使用
     */
    isUsed: function (value) {
        switch (value) {
            case '0':
            case 0:
                return '禁用';
            case '1':
            case 1:
                return '使用';
            default:
                return '';
        }
    },
    /**
     * 通用-船端岸基
     * @param value  1全部2岸基3船端
     */
    landOrShip: function (value) {
        switch (value) {
            case '0':
            case 0:
                return '全部';
            case '1':
            case 1:
                return '岸基';
            case '2':
            case 2:
                return '船端';
            default:
                return '';
        }
    }
};

/**
 * 设备管理
 */
lsFmt.comp = {
    /**
     * 设备信息-来源
     * @param value 1初始化；2岸端；3船端
     */
    origin: function (value) {
        switch (value) {
            case '1':
                return '初始化';
            case '2':
                return '岸端';
            case '3':
                return '船端';
            default:
                return '';
        }
    },
    /**
     * 设备信息-状态
     * @param value 1使用;2停用;3报废
     */
    status: function (value) {
        switch (value) {
            case '1':
                return '使用';
            case '2':
                return '停用';
            case '3':
                return '报废';
            default:
                return '';
        }
    },
    /**
     * 设备信息-是否国外
     * @param value 1国内,2国外
     */
    isForeign: function (value) {
        switch (value) {
            case '1':
                return '国内';
            case '2':
                return '国外';
            default:
                return '';
        }
    },
    /**
     * 设备计数器记录方式
     * @param value 1初始化；2岸端；3船端
     */
    recordType: function (value) {
        switch (value) {
            case '1':
                return '总表值';
            case '2':
                return '增量值';
            default:
                return '';
        }
    },
    /**
     * 设备计数器抄表方式
     * @param value 1初始化；2岸端；3船端
     */
    meterType: function (value) {
        switch (value) {
            case '1':
                return '手动';
            case '2':
                return '自动';
            case '3':
                return '采集';
            default:
                return '';
        }
    },
    /**
     * 格式化显示图纸
     */
    drawingShow: function (value, row, index) {
        if (value != "" && value != "null" && value != "NULL") {
            var drawingId = row.drawingId;
            var businessId = "'" + drawingId + "'";
            value = "<a href='javascript:void(0)' onClick=readDrawing(" + businessId + ")>" + value + "</a>";
        }
        return f_add_tip(value, row, index);
    },
    /**
     * 格式化显示资料
     */
    attachmentShow: function (value, row, index) {
        if (value != "" && value != "null" && value != "NULL") {
            var componentAttachmentId = row.componentAttachmentId;
            var businessId = "'" + componentAttachmentId + "'";
            value = "<a href='javascript:void(0)' onClick=exportAttachment(" + businessId + ")>" + value + "</a>";
        }
        return f_add_tip(value, row, index);
    },
    /**
     * 附件类型
     * @param value 1设备资料2设备完工图3设备操作手册
     */
    attachmentType: function (value) {
        switch (value) {
            case '1':
                return '设备资料';
            case '2':
                return '设备完工图';
            case '3':
                return '设备操作手册';
            default:
                return '';
        }
    }
};

/**
 * 人员
 */
lsFmt.person = {
    /**
     * 人员信息-数据来源
     * @param value 1程序2导入
     */
    origin: function (value) {
        switch (value) {
            case '1':
                return '程序';
            case '2':
                return '导入';
            default:
                return '';
        }
    },
    /**
     * 人员信息-人员性质
     * @param value  1自有2借调3临时4外派
     */
    nature: function (value) {
        switch (value) {
            case '1':
            case 1:
                return '自有';
            case '2':
            case 2:
                return '借调';
            case '3':
            case 3:
                return '临时';
            case '4':
            case 4:
                return '外派';
            default:
                return '';
        }
    },
    /**
     * 人员信息-人员分类
     * @param value  10机关11船队12二级单位13分公司14办事处20船员
     */
    personCategory: function (value) {
        switch (value) {
            case '10':
                return '机关';
            case '11':
                return '船队';
            case '12':
                return '二级单位';
            case '13':
                return '分公司';
            case '14':
                return '办事处';
            case '20':
                return '船员';
            default:
                return '';
        }
    },
    /**
     * 人员信息-人员状态
     * @param value 0离职;1在职
     */
    status: function (value) {
        switch (value) {
            case '1':
                return '在职';
            case '2':
                return '离职';
            case '3':
                return '公休';
            case '4':
            	return '调配中';
            default:
                return '';
        }
    },
    /**
     * 人员信息-性别
     * @param value 1男;2女;3保密
     */
    gender: function (value) {
        switch (value) {
            case '1':
            case 1:
                return '男';
            case '2':
            case 2:
                return '女';
            case '3':
            case 3:
                return '保密';
            default:
                return '';
        }
    },
    /**
     * 人员信息-证件类型
     * @param value 1身份证;2驾照;3护照
     */
    identitytype: function (value) {
        switch (value) {
            case '1':
                return '身份证';
            case '2':
                return '驾照';
            case '3':
                return '护照';
            default:
                return '';
        }
    },
    /**
     * 人员名称格式化-点击弹出详细信息
     * @param value
     * @param row
     */
    popupReport : function (value, row) {
        if(!row.personId) { //无人员信息，直接返回原值
            return value;
        }

        if (value) {
            return '<a title="' + row.personId + '" onClick="showReport(\'/system/authorization/sysPerson/report/dtl/' + row.personId + '/html\', null, \'personDetailWin\');">' + value + '</a>';
        } else {
            return '';
        }
    }
};

/**
 * 岗位
 */
lsFmt.post = {
    /**
     * 岗位-岗位类型
     * @param value 1岸基；2船端
     */
    postType: function (value) {
        switch (value) {
            case '1':
                return '岸基';
            case '2':
                return '船端';
            default:
                return '';
        }
    }
};

/**
 * 企业
 */
lsFmt.company = {
    /**
     * 企业信息-认证状态
     * @param value 1为认证中；2认证通过；3认证拒绝
     */
    isAuthen: function (value) {
        switch (value) {
            case '1':
                return '为认证中';
            case '2':
                return '认证通过';
            case '3':
                return '认证拒绝';
            default:
                return '';
        }
    },
    /**
     * 企业信息-授权状态
     * @param value 1，初始化2，回收，3授权，4回收，5授权。依次排开，逐渐增大
     */
    authority: function (value) {
        if (value != "" && value != null) {
            if (Number(value) == 1) {
                return '初始化';
            } else {
                if (Number(value) % 2 == 0) {
                    return '回收';
                } else {
                    return '授权';
                }
            }
        } else {
            return '';
        }
    }
};

/**
 * 维护保养
 */
lsFmt.maintenance = {
    /**
     * 维护保养计划-审批类型
     * @param value 1.五年 2.年度 3.季度 4.月度
     */
    approvalType: function (value) {
        switch (value) {
            case '1':
                return '五年计划';
            case '2':
                return '年度计划';
            case '3':
                return '季度计划';
            case '4':
                return '月度计划';
            default:
                return '';
        }
    },
    /**
     * 维护保养计划-审批状态
     * @param value 001.新增    002.部门长汇总 003.船长汇总 100.岸基通过
     */
    approvalStatus: function (value) {
        switch (value) {
            case '001':
                return '新增';
            case '002':
                return '部门长汇总';
            case '003':
                return '船长汇总';
            case '100':
                return '岸基通过';
            default:
                return '';
        }
    },
    /**
     * 工作信息类型
     * @param value 1.定期 2.定时 3.定期&定时 4.视情况
     */
    schemeType: function (value) {
        switch (value) {
            case '1':
                return '定期';
            case '2':
                return '定时';
            case '3':
                return '定期&定时';
            case '4':
                return '视情况';
            default:
                return '';
        }
    },
    /**
     * 维护保养计划-来源
     * @param value 1.岸端制定 2.船端 3.上一年
     */
    schemeOrigin: function (value) {
        switch (value) {
            case '1':
                return '岸端制定';
            case '2':
                return '船端';
            case '3':
                return '上一年';
            default:
                return '';
        }
    },
    /**
     * 维护保养-来源
     * @param value 1.计划 2.岸基新增 3.船端新增 4.PSC 5.FSC 6.修理
     */
    origin: function (value) {
        switch (value) {
            case '1':
                return '计划';
            case '2':
                return '岸基新增';
            case '3':
                return '船端新增';
            case '4':
                return 'PSC';
            case '5':
                return 'FSC';
            case '6':
                return '修理';
            default:
                return '';
        }
    },
    /**
     * 维护保养-保养性质
     * @param value 1.正常 2.修理 3.PSC 4.FSC 5.年检 6.中间检验 7.特检
     */
    prope: function (value) {
        switch (value) {
            case '1':
                return '正常';
            case '2':
                return '修理';
            case '3':
                return 'PSC';
            case '4':
                return 'FSC';
            case '5':
                return '年检';
            case '6':
                return '中间检验';
            case '7':
                return '特检';
            default:
                return '';
        }
    },
    /**
     * 维护保养-保养状态
     * @param value 01.计划 02.停止 03.提前完成 04.按时完成(前) 05.按时完成 06.按时完成(后) 07.超期完成 08.免做完成 09.推迟完成
     */
    status: function (value) {
        switch (value) {
            case '01':
                return '计划';
            case '02':
                return '停止';
            case '03':
                return '提前完成';
            case '04':
                return '按时完成(前)';
            case '05':
                return '按时完成';
            case '06':
                return '按时完成(后)';
            case '07':
                return '超期完成';
            case '08':
                return '免做';
            case '09':
                return '推迟';
            default:
                return '';
        }
    },
    /**
     * 维护保养-计划审核状态
     * @param value 1年度计划审核完成；2月度计划审核完成
     */
    checkStatus: function (value) {
        switch (value) {
            case '1':
                return '年度计划审核完成';
            case '2':
                return '月度计划审核完成';
            default:
                return '';
        }
    },
    /**
     * 维护保养完工-完工类型
     * @param value 03.提前完成 04.按时完成(前) 05.按时完成 06.按时完成(后) 07.超期完成 08.免做完成 09.推迟完成
     */
    completeType: function (value) {
        switch (value) {
            case '03':
                return '提前完成';
            case '04':
                return '按时完成(前)';
            case '05':
                return '按时完成';
            case '06':
                return '按时完成(后)';
            case '07':
                return '超期完成';
            case '08':
                return '免做完成';
            case '09':
                return '推迟完成';
            default:
                return '';
        }
    }
};

/**
 * 工作流通用方法
 */
lsFmt.wf = {
    /**
     * 通用-是或否
     * @param value 0终止；1启用
     */
    trueOrFalse: function (value) {
        switch (value) {
            case '0':
                return '终止';
            case '1':
                return '启用';
            default:
                return '';
        }
    },
    /**
     * 通用-流程project状态
     * @param value 0未发布；1已发布
     */
    projStatus: function (value) {
        switch (value) {
            case '0':
                return '未发布';
            case '1':
                return '已发布';
            default:
                return '';
        }
    },
    /**
     * 通用-流程状态
     * @param value
     */
    instStatus: function (value) {
        switch (value) {
            case '0':
                return '进行中';
            case '1':
                return '已完成';
            case '8':
                return '已撤销';
            case '9':
                return '未开始';
            default:
                return '';
        }
    },
    /**
     * 通用-urgency
     * @param value 0终止；1启用
     */
    checkUrgency: function (value) {
        switch (value) {
            case '':
            case '0':
                return '常规';
            case '1':
                return '紧急';
            case '2':
                return '特急';
            default:
                return '';
        }
    },
    /**
     * 时限进度
     * @param value 百分比+"%"+title
     */
    limitProcess: function (value) {
    	var processHtml = "";
    	if(value) {
    		var processCss = value.split("_")[0];
    		var percert = value.split("_")[1];
    		var showPercert = value.split("_")[2];
    		var title = value.split("_")[3];
    		processHtml += "<div class=\"progress\" role=\"progressbar\" style=\"margin-bottom:0; border:1px solid #000;\" title=\"" + title + "\" >";
    		processHtml += "<div class=\"progress-bar progress-bar-" + processCss + "\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: " + percert + "%;\">";
    		processHtml += "<span class=\"\" style=\"width:20px\">" + showPercert + "%</span>";
    		processHtml += "</div>";
    		processHtml += "</div>";
    	}
    	else {
    		processHtml += "<div class=\"progress\" role=\"progressbar\" style=\"margin-bottom:0; border:1px solid #000;\" title=\"没有时限\" >";
    		processHtml += "<div class=\"progress-bar progress-bar-success\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 100%;\">";
    		processHtml += "<span class=\"\" style=\"width:20px\">100%</span>";
    		processHtml += "</div>";
    		processHtml += "</div>";
    	}
        return processHtml;
    }
};

/**
 * 船舶基础信息
 */
lsFmt.shipinfo = {
    /**
     * 船舶基础信息—船舶状态
     * @param value  00在航01停航99报废
     */
    shipStatus: function (value) {
        switch (value) {
            case '00':
                return '在航';
            case '01':
                return '停航';
            case '99':
                return '报废';
            default:
                return '';
        }
    },

    /**
     * 船舶基础信息—船舶性质
     * @param value  1:自有船 2:租借船
     */
    nature: function (value) {
        switch (value) {
            case '1':
                return '自有船';
            case '2':
                return '租借船';
            default:
                return '';
        }
    }
};

/**
 * 证书信息
 */
lsFmt.certinfo = {
    /**
     * 船舶证书—证书性质
     * @param value  1长期2定期3临时4其他
     */
    certPropety: function (value) {
        switch (value) {
            case '1':
                return '长期';
            case '2':
                return '定期';
            case '3':
                return '临时';
            case '4':
                return '其他';
            default:
                return '';
        }
    },
    /**
     * 船舶证书—根据预警和检验日期改变行颜色
     */
    rowStyle: function (row) {
        var classes = ['active', 'success', 'info', 'warning', 'danger'];

        if (row.alertMessage == "已到预警时间") {
            return {
                classes: classes[2]
            };
        } else {
            if (row.alertMessage == "已过检验日期") {
                return {
                    classes: classes[4]
                };
            }
        }
        return {};
    },
    /**
     * 船舶证书—证书附件管理
     */
    certFile: function (value, row) {
        return '<a onClick="certFileShow(\'' + row.certId + '\');">附件</a>';
    },
    /**
     * 船舶证书—证书附件下载
     * @param
     */
    certFileExport: function (value, row, index) {
        if (value != "" && value != "null" && value != "NULL") {
            var certFileId = row.sysFolderFileId;
            var businessId = "'" + certFileId + "'";
            value = "<a href='javascript:void(0)' onClick=exportCertFile(" + businessId + ")>" + value + "</a>";
        }
        return f_add_tip(value, row, index);
    }
};

/**
 * 库存管理
 */
lsFmt.storage = {
    /**
     * 库存管理—出入库类型
     * @param value 101正常出库；102保养出库；103修理出库；104盘点出库；105报废出库；106故障出库；
     201正常入库；202翻新入库；204盘点入库
     */
    inOutCategory: function (value) {
        switch (value) {
            case '101':
                return '正常出库';
            case '102':
                return '保养出库';
            case '103':
                return '修理出库';
            case '104':
                return '盘点出库';
            case '105':
                return '报废出库';
            case '106':
                return '故障出库';
            case '201':
                return '正常入库';
            case '202':
                return '翻新入库';
            case '204':
                return '盘点入库';
            default:
                return '';
        }
    },
    inOutCheckStatus: function (value) {
        switch (value) {
            case '1':
                return '未审核';
            case '2':
                return '审核中';
            case '3':
                return '已完成';
            default:
                return '';
        }
    },
    historyCount: function (value, row) {
        switch (row.operate) {
            case 'IN':
                return '+ ' + value;
            case 'OUT':
                return '- ' + value;
            default:
                return '';
        }
    }
};

/**
 * 物料管理
 */
lsFmt.material = {
    /**
     * 申请单分类
     */
    applyCategory: function (value) {
        switch (value) {
            case '00':
                return '全部';
            case '01':
                return '通用物料';
            case '11':
                return '航保资料';
            case '12':
                return '海图';
            case '13':
                return '劳保用品';
            case '14':
                return '办公室用品';
            case '15':
                return '药品';
            case '16':
                return '伙食';
            case '99':
                return '其他';
            default:
                return '';
        }
    },
    /**
     * 申请单类型
     */
    applyType: function (value) {
        switch (value) {
            case '1':
                return '常规';
            case '2':
                return '季度申请';
            case '3':
                return '应急申请';
            case '4':
                return '大修申请';
            case '5':
                return '其他';
            default:
                return '';
        }
    }
};

/**
 * 安全管理
 */
lsFmt.safety = {
    /**
     * 所属机构 2船端1岸基9其他
     */
    area: function (value) {
        switch (value) {
            case '1':
                return '机关';
            case '2':
                return '船舶';
            case '9':
                return '其他';
            default:
                return '';
        }
    },
    /**
     * 隐患级别  1一般2重大
     */
    dangerLevel: function (value) {
        switch (value) {
            case '1':
                return '一般';
            case '2':
                return '重大';
            default:
                return '';
        }
    }
};

/**
 *  滑油、化学试剂
 */
lsFmt.grease = {
		/**
		 *  滑油类型  101主机齿轮油102副机齿轮油199其他;
		 */
		greaseType: function (value) {
			switch (value) {
				case '101':
					return '主机齿轮油';
				case '102':
					return '副机齿轮油';
				case '199':
					return '其他';
				default:
					return '';
			}
		},
	    /**
	     * 化学试剂类型    201压载水处理剂202燃油添加剂203生活水处理剂204锅炉水处理剂205缸套水处理剂299其他
	     */
		chemicalReagentType: function (value) {
	        switch (value) {
	            case '201':
	                return '压载水处理剂';
	            case '202':
	                return '燃油添加剂';
	            case '203':
	                return '生活水处理剂';
	            case '204':
	                return '锅炉水处理剂';
	            case '205':
	                return '缸套水处理剂';
	            case '299':
	            	return '其他';
	            default:
	                return '';
	        }
	    }
};
/**
 *  备件申请
 */
lsFmt.spare = {
    /**
     * 所属机构 1全部2甲板3轮机4电气5其他
     */
    applyCategory: function (value) {
        switch (value) {
            case '1':
                return '全部';
            case '2':
                return '甲板';
            case '3':
                return '轮机';
            case '4':
                return '电气';
            case '5':
                return '其他';
            default:
                return '';
        }
    }
};