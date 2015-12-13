cordova.define("cordova-plugin-downloader.Downloader", function(require, exports, module) { var exec = require('cordova/exec');

module.exports = {
    downloadFile : function(fileUrl, params, win, fail) {
        exec(win, fail, "Downloader", "downloadFile", [fileUrl, params]);
    },
    downloadAndOpenFile: function(fileUrl, params, win, fail){
    	exec(win, fail, "Downloader", "downloadAndOpenFile", [fileUrl, params]);
    }
};

});
