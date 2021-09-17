package consultant.eyecon.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.adapters.Globaladapter;
import consultant.eyecon.adapters.Reportbalanceadapter;
import consultant.eyecon.databinding.ActivityIntroBinding;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.AppConstants;
import consultant.eyecon.models.Globalmodel;
import consultant.eyecon.models.Partybalance;
import consultant.eyecon.services.BackgroudTask;

public class IntroActivity extends AppCompatActivity {

    ActivityIntroBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.prttypeid = "3";
                Intent i = new Intent(IntroActivity.this, CashActivity.class);
                startActivity(i);
            }
        });
        binding.delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.prttypeid = "2";
                Intent i = new Intent(IntroActivity.this,OrderBooking.class);
                i.putExtra("type",4);
                startActivity(i);            }
        });
        binding.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.prttypeid = "1";
                Intent i = new Intent(IntroActivity.this, OrderBooking.class);
                i.putExtra("type", 1);
                startActivity(i);
            }
        });

    }

}
