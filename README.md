# MszdViewList
一些自定义控件集合

# MszdButton_1
![MszdButton_1](https://github.com/wlj644920158/MszdViewList/blob/master/screenshots/ezgif-1-beeb011ae3.gif)

xml文件声明
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:mszd="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">
    <nanjing.jun.viewlib.button.MszdButton_1
        android:id="@+id/button_1"
        android:layout_width="100dp"
        android:layout_height="wrap_content"
        mszd:btn1delayTime="2000"
        mszd:btn1loadingFailText="登陆失败"
        mszd:btn1loadingSuccessText="登陆成功"
        mszd:btn1loadingText="正在登陆"
        mszd:btn1normalText="登陆" />
</LinearLayout>
```
# MszdButton_2
![MszdButton_2](https://github.com/wlj644920158/MszdViewList/blob/master/screenshots/ezgif-1-e58d405192.gif)

xml文件声明
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:mszd="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <nanjing.jun.viewlib.button.MszdButton_2
        android:id="@+id/button_2"
        android:layout_width="200dp"
        android:layout_height="40dp"
        mszd:btn2text="登陆" />
</LinearLayout>
```
# MszdButton_3
![MszdButton_3](https://github.com/wlj644920158/MszdViewList/blob/master/screenshots/ezgif-3-ab9baabb24.gif)

xml文件声明
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:mszd="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <nanjing.jun.viewlib.button.MszdButton_3
        android:id="@+id/button_3"
        android:layout_width="200dp"
        android:layout_height="40dp"
        mszd:btn3loadingText="正在登陆"
        mszd:btn3loadingFailText="登陆失败"
        mszd:btn3loadingSuccessText="登陆成功"
        mszd:btn3text="登陆" />
</LinearLayout>
```
# MszdProgress_1
![MszdProgress_1](https://github.com/wlj644920158/MszdViewList/blob/master/screenshots/ezgif-3-4f7a405a69.gif)

xml文件声明
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:mszd="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <nanjing.jun.viewlib.progress.MszdProgress_1
        android:id="@+id/progress_1"
        android:layout_width="40dp"
        android:layout_height="300dp"
        mszd:progress1bgcolor="#ddd"
        mszd:progress1round="false"
        mszd:progress1color="#00f" />

    <nanjing.jun.viewlib.progress.MszdProgress_1
        android:layout_marginTop="20dp"
        android:id="@+id/progress_1_1"
        android:layout_width="300dp"
        android:layout_height="40dp"
        mszd:progress1bgcolor="#ddd"
        mszd:progress1color="#f00" />
</LinearLayout>
```
# MszdProgress_2
![MszdProgress_2](https://github.com/wlj644920158/MszdViewList/blob/master/screenshots/ezgif-3-3fc793078d.gif)

xml文件声明
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:mszd="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">
    <nanjing.jun.viewlib.progress.MszdProgress_2
        android:id="@+id/progress_2"
        android:layout_width="match_parent"
        android:layout_height="40dp" />
</LinearLayout>
```
# MszdLoading_1
![MszdLoading_1](https://github.com/wlj644920158/MszdViewList/blob/master/screenshots/ezgif-3-9bb62f7a8f.gif)

xml文件声明
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:mszd="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#000"
    android:gravity="center"
    android:orientation="vertical">

    <nanjing.jun.viewlib.load.MszdLoading_1
        android:id="@+id/loading_1"
        android:layout_width="100dp"
        android:layout_height="100dp" />
</LinearLayout>
```
