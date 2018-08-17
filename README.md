# WhirlLoadingView
A loading view that includes two rotating arcs(一个包含两条圆弧互相旋转的loading view).

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/WhirlLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/WhirlLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

<br>

  * [中文](#%E4%B8%AD%E6%96%87)
  * [English](#english)
  * [License](#license)

<br>

![gif](https://github.com/samlss/WhirlLoadingView/blob/master/screenshots/screenshot.gif)



## 中文

### 使用<br>
在根目录的build.gradle添加这一句代码：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```
dependencies {
    implementation 'com.github.samlss:WhirlLoadingView:1.0'
}
```

布局中使用：
```
 <com.iigo.library.WhirlLoadingView
        android:id="@+id/wlv_loading"
        android:layout_centerInParent="true"
        android:layout_width="60dp"
        app:duration="900"
        app:loadingColor="@android:color/white"
        app:interpolator="LinearInterpolator"
        android:layout_height="60dp" />

```

<br>

代码中使用：
```
  whirlLoadingView.pause(); //暂停动画
  whirlLoadingView.resume(); //恢复动画
   
  whirlLoadingView.start(); //开始动画
  whirlLoadingView.stop(); //停止动画
  
  whirlLoadingView.setColor(Color.RED); //圆弧颜色
```

<br>

属性说明：

| 属性        | 说明           |
| ------------- |:-------------:|
| loadingColor      | 旋转圆弧的颜色 |
| duration      | 动画时间 |
| interpolator | 动画加速器 |

### 插值器值interpolator: <br>
* AccelerateDecelerateInterpolator
* AccelerateInterpolator
* DecelerateInterpolator
* BounceInterpolator
* CycleInterpolator
* LinearInterpolator
* AnticipateOvershootInterpolator
* AnticipateInterpolator
* OvershootInterpolator

<br>

## English

### Use<br>
Add it in your root build.gradle at the end of repositories：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```
dependencies {
    implementation 'com.github.samlss:WhirlLoadingView:1.0'
}
```


in layout.xml：
```
<com.iigo.library.WhirlLoadingView
        android:id="@+id/wlv_loading"
        android:layout_centerInParent="true"
        android:layout_width="60dp"
        app:duration="900"
        app:loadingColor="@android:color/white"
        app:interpolator="LinearInterpolator"
        android:layout_height="60dp" />

```

<br>

in java code：
```
  whirlLoadingView.pause(); //pause animation
  whirlLoadingView.resume(); //resume animation
   
  whirlLoadingView.start(); //start animation
  whirlLoadingView.stop(); //stop animation
  
  whirlLoadingView.setColor(Color.RED); //set the color of the arcs
```
<br>


Attributes description：

| attr        | description  |
| ------------- |:-------------:|
| loadingColor      | 旋转圆弧的颜色 |
| duration      | 动画时间 |
| interpolator | 动画加速器 |

### interpolator: <br>
* AccelerateDecelerateInterpolator
* AccelerateInterpolator
* DecelerateInterpolator
* BounceInterpolator
* CycleInterpolator
* LinearInterpolator
* AnticipateOvershootInterpolator
* AnticipateInterpolator
* OvershootInterpolator

<br>

[id]: http://example.com/ "Optional Title Here"

## [LICENSE](https://github.com/samlss/WhirlLoadingView/blob/master/LICENSE)
