package nanjing.jun.mszdviewlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import nanjing.jun.viewlib.button.MszdButton_3;
import nanjing.jun.viewlib.button.onMszdButtonClickListener;

/**
 * Created by 王黎军 on 2017/7/26.
 */

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
