package nanjing.jun.mszdviewlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import nanjing.jun.viewlib.progress.MszdProgress_2;

/**
 * Created by 王黎军 on 2017/7/26.
 */

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
