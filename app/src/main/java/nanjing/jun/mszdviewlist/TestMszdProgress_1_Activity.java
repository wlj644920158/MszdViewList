package nanjing.jun.mszdviewlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import nanjing.jun.viewlib.progress.MszdProgress_1;

/**
 * Created by 王黎军 on 2017/7/26.
 */

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
