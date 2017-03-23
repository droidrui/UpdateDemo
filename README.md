# TaskDemo
这个一个简单易用、解耦业务、避免内存泄漏的Android异步任务库，异步处理思想参考RxJava。

### Task解决我在实际开发中的几个痛点：

* 一个任务要有统一的错误处理结果返回（Android自带的AsyncTask就没有错误返回）
* 把任务分离出Activity（这样任务还可以重用）
* 任务运行过程中的回调（比如提示上传第n张图片）
* 不用担心异步任务有内存泄漏

### 主要类说明：

1. Task参考RxJava的Observable，在静态方法中new Task，封装异步任务。

2. TaskManager负责管理Task的弱引用，在Activity onCreate时初始化，onDestroy时解开Task的回调，避免内存泄漏。

3. TaskCallback是Task的回调，包含onError(TaskError e) onSuccess(T result) onFinish() onMessage(int arg1, int arg2, Object obj)，满足一般项目的需求。

4. TaskError是Task的错误结果，包含code strCode msg throwable。


##### demo中使用干货（ http://gank.io ）的API， HTTP请求使用okhttp，JSON解析使用fastjson，感谢！
