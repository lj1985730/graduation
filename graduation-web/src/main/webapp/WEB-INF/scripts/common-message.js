/**
 * 系统消息对象
 * @type {{alertInfo}}
 */
var SysMessage = function() {

    var NO_SELECTED_MESSAGE = '请选择要操作的数据！';

    return {
        /**
         * 统一弹出正常信息
         */
        alertInfo : function(message) {
            Metronic.alert({ message : message, type : 'success', closeInSeconds : 3 });
        },
        /**
         * 统一弹出警告信息
         */
        alertWarning : function(message) {
            Metronic.alert({ message : message, type : 'warning', closeInSeconds : 3 });
        },
        /**
         * 统一弹出错误信息
         */
        alertError : function(message) {
            Metronic.alert({ message : message, type : 'danger', closeInSeconds : 3 });
        },
        /**
         * 统一弹出信息-未选择操作数据
         */
        alertNoSelection : function() {
            this.alertWarning(NO_SELECTED_MESSAGE);
            return false;
        }
    }
}();