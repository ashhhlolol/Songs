package sg.edu.rp.c346.id22000765.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

    public class SecondActivity extends AppCompatActivity {
        ListView lv;
        ArrayList<Song> songList;
        ArrayAdapter<Song> adapter;
        String moduleCode;
        Button btn5Stars;
        ArrayList<String> years;
        Spinner spinner;
        ArrayAdapter<String> spinnerAdapter;

        @Override
        protected void onResume() {
            super.onResume();
            DBHelper dbh = new DBHelper(this);
            songList.clear();
            songList.addAll(dbh.getAllSongs());
            adapter.notifyDataSetChanged();

            years.clear();
            years.addAll(dbh.getYears());
            spinnerAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);

            setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.etTitle.activity_main));

            lv = (ListView) this.findViewById(R.id.lv);
            btn5Stars = (Button) this.findViewById(R.id.btn5Stars);
            spinner = (Spinner) this.findViewById(R.id.spinner);

            DBHelper dbh = new DBHelper(this);
            songList = dbh.getAllSongs();
            years = dbh.getYears();
            dbh.close();

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                    i.putExtra("song", songList.get(position));
                    startActivity(i);
                }
            });

            btn5Stars.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper dbh = new DBHelper(SecondActivity.this);
                    songList.clear();
                    songList.addAll(dbh.getAllSongsByStars(5));
                    adapter.notifyDataSetChanged();
                }
            });

            spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
            spinner.setAdapter(spinnerAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    DBHelper dbh = new DBHelper(SecondActivity.this);
                    songList.clear();
                    songList.addAll(dbh.getAllSongsByYear(Integer.valueOf(years.get(position))));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }