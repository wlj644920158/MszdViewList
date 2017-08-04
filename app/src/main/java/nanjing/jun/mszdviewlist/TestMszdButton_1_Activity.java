package nanjing.jun.mszdviewlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import nanjing.jun.viewlib.button.MszdButton_1;
import nanjing.jun.viewlib.button.onMszdButtonClickListener;

/**
 * Created by 王黎军 on 2017/7/26.
 */

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
