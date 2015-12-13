var exec = require('cordova/exec');

module.exports = {
    downloadFile : function(fileUrl, params, win, fail) {
        exec(win, fail, "Downloader", "downloadFile", [fileUrl, params]);
    },
    downloadAndOpenFile: function(fileUrl, params, win, fail){
    	exec(win, fail, "Downloader", "downloadAndOpenFile", [fileUrl, params]);
    }
};