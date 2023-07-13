package sg.edu.rp.c346.id22000765.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYears;
    Button btnInsert, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYears = findViewById(R.id.etYears);
        btnInsert = findViewById(R.id.btnUpdate);
        btnShowList = findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String singers = etSingers.getText().toString().trim();
                String year = etYears.getText().toString().trim();

                if (title.isEmpty() || singers.isEmpty() || year.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper dbHelper = new DBHelper(MainActivity.this);
                boolean isInserted = dbHelper.insertSong(title, singers, year);
                dbHelper.close();

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Song inserted successfully", Toast.LENGTH_SHORT).show();
                    etTitle.setText("");
                    etSingers.setText("");
                    etYears.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Failed to insert song", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
