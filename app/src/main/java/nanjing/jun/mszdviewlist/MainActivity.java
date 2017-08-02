package nanjing.jun.mszdviewlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void MszdButton_1(View view) {
        Intent intent = new Intent(this, TestMszdButton_1_Activity.class);
        startActivity(intent);
    }

    public void MszdButton_2(View view) {
        Intent intent = new Intent(this, TestMszdButton_2_Activity.class);
        startActivity(intent);
    }

    public void MszdButton_3(View view) {
        Intent intent = new Intent(this, TestMszdButton_3_Activity.class);
        startActivity(intent);
    }

    public void MszdProgress_1(View view) {
        Intent intent = new Intent(this, TestMszdProgress_1_Activity.class);
        startActivity(intent);
    }

    public void MszdProgress_2(View view) {
        Intent intent = new Intent(this, TestMszdProgress_2_Activity.class);
        startActivity(intent);
    }

    public void MszdLoading_1(View view) {
        Intent intent = new Intent(this, TestMszdLoading_1_Activity.class);
        startActivity(intent);
    }

    public void MszdMenu_1(View view) {
        Intent intent = new Intent(this, TestMszdMenu_1_Activity.class);
        startActivity(intent);
    }

    public void MszdLayout_1(View view) {
        Intent intent = new Intent(this, TestMszdLayout_1_Activity.class);
        startActivity(intent);
    }


}
