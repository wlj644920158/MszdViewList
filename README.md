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
代码示例
```
public class TestMszdButton_1_Activity extends AppCompatActivity {
    private MszdButton_1 mszdButton_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_1);
        mszdButton_1 = (MszdButton_1) findViewById(R.id.button_1);
        mszdButton_1.setOnMszdButtonClickListener(new onMszdButtonClickListener() {
            @Override
            public void onMszdButtonClick() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(6000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mszdButton_1.setLoadingFail();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess() {

            }
        });
    }
}
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
代码示例
```
public class TestMszdButton_2_Activity extends AppCompatActivity {
    private MszdButton_2 mszdButton_2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_2);
        mszdButton_2 = (MszdButton_2) findViewById(R.id.button_2);
        mszdButton_2.setOnMszdButtonClickListener(new onMszdButtonClickListener() {
            @Override
            public void onMszdButtonClick() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mszdButton_2.setOnLoadingFail();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess() {

            }
        });
    }
}
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
代码示例
```
public class TestMszdButton_3_Activity extends AppCompatActivity {
    private MszdButton_3 mszdButton_3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_3);
        mszdButton_3 = (MszdButton_3) findViewById(R.id.button_3);
        mszdButton_3.setOnMszdButtonClickListener(new onMszdButtonClickListener() {
            @Override
            public void onMszdButtonClick() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mszdButton_3.setOnLoadingFail();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess() {

            }
        });
    }
}
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
代码示例
```
public class TestMszdProgress_1_Activity extends AppCompatActivity {
    MszdProgress_1 mszdProgress_1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_1);
        mszdProgress_1 = (MszdProgress_1) findViewById(R.id.progress_1_1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <=100; i = i + 10) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mszdProgress_1.setProgress(finalI);
                        }
                    });

                }
            }
        }).start();
    }
}
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
代码示例
```
public class TestMszdProgress_2_Activity extends AppCompatActivity {
    MszdProgress_2 mszdProgress_2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_2);
        mszdProgress_2 = (MszdProgress_2) findViewById(R.id.progress_2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(1000);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mszdProgress_2.setCurProgress(finalI);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
```
