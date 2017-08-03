# BaseUtil
 
# 本框架主要用于Android快速开发，集成大部分常用API，让大多数人能够集成此Model就能马上入手开发。
# 使用方法：
在Application中注册：MainInit.getInstance().init(this);
# 功能注册：
```
BaseConfig.setDeBug(true);
BaseConfig.setBackFinish(false);
....
```
# 以下是API19以上支持的沉侵方案，API大于19默认开启（低于19默认不显示），状态条使用View，更加灵活，你可以给它设置GONE属性，让布局内容延伸到状态栏，或者设置你想要的颜色，例如透明色，达到可以自由控制状态栏颜色，并让内容布局预留状态栏空间，避免再次设置android:fitsSystemWindows="true"
![image](https://github.com/zhangzhichaolove/BaseUtil/blob/master/image/home.png) 
# 加载多布局：
## 你可以在应用初始化时配置这些布局，也可以在BaseActivity单独设置这些属性：
### 全局配置如下：
```
    CustomConfig.setLoadingView(R.layout.base_loading);
    CustomConfig.setEmptyView(R.layout.base_empty);
```
### 单独配置如下(Activity)：
```
setLoadingRes(R.layout.base_loading);
setEmptyRes(R.layout.base_empty);
```
### 使用：
```
showLoading();
showEmpty();
```
# 更多功能期待大家提出。。。
