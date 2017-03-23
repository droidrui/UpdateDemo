# UpdateDemo
这个一个App检查版本更新的Demo，满足实际项目需求。

### Demo中实现的功能有：

1. 后台检查更新和手动检查更新
2. 忽略5次版本才真正忽略，每次忽略，在WIFI网络下，后台静默断点下载apk
3. 需要强制更新时，不能取消Dialog显示
4. 通知栏显示下载apk进度，下载失败时，可点击通知重新断点下载
5. 5次忽略版本之间，弹出Dialog需要间隔1小时，否则不弹出
6. 手动检查更新必弹Dialog
7. 二级Dialog在非WIFI网络时弹出，提示用户是否更新
8. 根据接口字段决定是否向用户弹出版本更新Dialog

### 主要类说明：

1. UpdateLogDialog显示版本更新日志。

2. UpdateAlertDialog显示非WIFI网络时，提示用户是否继续更新

3. UpdateManager负责apk包的断点下载，更新通知栏。


##### Demo中使用[淘宝RAP](http://rap.taobao.org/)的模拟API， HTTP请求使用okhttp，JSON解析使用fastjson，感谢！
