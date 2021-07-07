package sg.edu.rp.c346.id20020036.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ArrayAdapter<Task> AA;
    ArrayList<Task> al;
    ListView lv;
    EditText etTask, etDate;
    Boolean asc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        etTask = findViewById(R.id.etTask);
        etDate = findViewById(R.id.etDate);
        lv = findViewById(R.id.lv);
        al = new ArrayList<>();
        asc = true;
        AA = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(AA);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelp = new DBHelper(MainActivity.this);
                String theTask = etTask.getText().toString();
                String theDate = etDate.getText().toString();
                dbHelp.insertTask(theTask, theDate);
                //dbHelp.insertTask("Submit RJ", "25 Apr 2021");
                etTask.setText("");
                etDate.setText("");
            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<String> data = db.getTaskContent();

                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i + ". " + data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);
                if(asc == true)
                {
                    asc = false;
                }
                else
                {
                    asc = true;
                }
                al.clear();
                al.addAll(db.getTasks(asc));
                AA.notifyDataSetChanged();
            }
        });
    }
}
