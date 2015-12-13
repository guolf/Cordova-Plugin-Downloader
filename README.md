# Cordova-Plugin-Downloader


安装：

```
cordova plugin add https://github.com/guolf/Cordova-Plugin-Downloader.git

```

删除

```
cordova plugin remove cordova-plugin-downloader
```

使用

```
window.plugins.Downloader.downloadAndOpenFile("http://xxxx.apk",{overwrite:true, progressInfo:"正在下载："},function(res){
},function(error){
});
```
