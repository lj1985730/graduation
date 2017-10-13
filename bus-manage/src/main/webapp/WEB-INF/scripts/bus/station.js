!function ($) {

    'use strict';

    var baseUrl = 'bus/station';
    var $table;

    /**
     * 初始化
     */
    $(function() {

        $table = $('#stationTable');

        $table.bootstrapTable(
            $.extend({
                url : rootDir + 'bus/stations',
                formatSearch : function () {
                    return '搜索站点名称或编号';
                }
            }, generalTableOption)
        );
    });

    /**
     * 查询条件
     * @param params 基本查询条件，包含search、sort、order、limit、offset
     */
    function tableParams(params) {
        var localParams = {
        };
        return $.extend(localParams, null, params);
    }

}(jQuery);