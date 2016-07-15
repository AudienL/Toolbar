# Toolbar

惭愧菜鸟用不熟官方的Toolbar，所以自定义了一个。

## 效果图：

![效果图](https://github.com/AudienL/Toolbar/blob/master/doc/show.png?raw=true)

## 使用：

### 一、在 project 根目录的 build.gradle 中添加：

```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

### 二、在 module 根目录的 build.gradle 中添加：

其中最后版本在 release 中查看，如：1.0
```groovy
dependencies {
    compile 'com.github.AudienL:Toolbar:最后版本'
}
```

### 三、使用

布局文件：
```xml
<com.audienl.library.Toolbar
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:toolbar_show_return="true"
    app:toolbar_src_button1="@mipmap/ic_launcher"
    app:toolbar_title="@string/app_name"/>
```

代码：
```java
mToolbar = (Toolbar) findViewById(R.id.toolbar);
mToolbar.setOnToolbarClickedListener(new Toolbar.SimpleOnToolbarClickedListener() {
    @Override
    public void onReturnClick() {
        finish();
    }

    @Override
    public void onButton1Click() {
        super.onButton1Click();
        Toast.makeText(MainActivity.this, "未实现", Toast.LENGTH_SHORT).show();
    }
});
```
