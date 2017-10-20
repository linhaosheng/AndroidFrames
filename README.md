一 ： 首先下载源码后在项目导入trunk模块(Module)后，在项目的build.gradle文件中添加一下插件依赖
 dependencies {
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.1'
		classpath 'com.novoda:bintray-release:0.3.4'
}

二 ：在Application 的Oncreate函数里面添加一下代码 FrameApp.init(this)即可使用.

三 以下是各个框架的信息.

(1)
图片加载框架 glide :  版本 ； 4.2.0  github地址: https://github.com/bumptech/glide 
使用说明 : 需要在主项目的gradle文件添加一下插件依赖
 dependencies {
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.1'
}
功能 : GlideImageUtil  : 加载本地和网络图片
(2) 
网络请求框架 : okhttp :  版本 ； 3.9.0  github地址: https://github.com/square/okhttp 
功能 : 1 : 简单的get请求  2 :  带有参数的post请求   3  下载文件  

(3)
json解析框架 : gson  :  版本 ； 2.8.2  github地址:  https://github.com/google/gson
功能 ： GsonUtils ： 返回一个gson解析对象

(4) 
权限检查框架 : HiPermission  版本 ； 1.4.0  github地址: https://github.com/yewei02538/HiPermission
功能 : HiPermissionUtils  :  不带样式的权限检查checkPermission ，带有样式的checkPermissionStyle

(5)  对话框框架 : Android-CircleDialog   版本: 2.1.6   github地址:  https://github.com/mylhyl/Android-CircleDialog
使用说明 ：需要在主项目的gradle文件添加一下插件依赖
 dependencies {
        classpath 'com.novoda:bintray-release:0.3.4'
}
功能 :  确定框 (ShowConfigDialog) ,  选择对话框 (SelectDialog) ,  输入框 (DialogInput)
显示下载的进度条 (DialogProgress)  


(6)  RecyclerAdapter适配框架 : BaseRecyclerViewAdapterHelper  版本 ； 2.9.30  github地址:  https://github.com/CymChad/BaseRecyclerViewAdapterHelper

(7)  轮播图 : banner 版本 ； 1.4.10   github地址:  https://github.com/youth5201314/banner
功能 :  BannerUtils： banner的使用说明

(8) 消息总线 : EventBus  版本 ； 3.0.0   github地址:  https://github.com/greenrobot/EventBus

(9)  开发工具类 utils :  
功能 :  应用工具类(AppUtils) 
(1)  Bitmap工具类主要包括获取Bitmap和对Bitmap的操作 (BitmapUtils) 
      (2)  UncaughtException处理类 (CrashHandlerUtils) ,
(3)  日期操作工具类(DateUtils)  ,  屏幕、尺寸、View相关工具类(DensityUtils)  
(4)  判断是否为空工具类(EmptyUtils) ,
(5)  加密工具类（只实现部分)(EncryptUtils)  
(6)  毛玻璃效果(FastBlurUtils) 
(7)  用来计算显示的时间是多久之前的(FormatTimeUtils)  
(8)  键盘相关工具类(KeyboardUtils) 
(9)  Log相关工具类(LogUtils)  
(10) 网络相关工具类(NetworkUtils) 
(11) 此类主要是用来放一些系统过时方法的处理(OutdatedUtils)  
(12) Preferences存取相关方法(PreferencesUtils)  
(13) 正则工具类* 验证邮箱、手机号、电话号码、身份证号码、银行卡卡号、IP、邮政编码、车牌号等(RegexUtils)  
(14) 发送短信(SmsObserver)  
(15) 格式化字符串文本(SpannableStringUtils) 。

(10) RecyclerAdapter适配框架 : BaseRecyclerViewAdapterHelper  版本 ； 2.9.30  github地址:  https://github.com/CymChad/BaseRecyclerViewAdapterHelper
功能 : BaseRecyclerViewAdapterHelperUtils : 使用说明
