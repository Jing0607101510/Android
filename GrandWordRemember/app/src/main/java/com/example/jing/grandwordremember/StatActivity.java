package com.example.jing.grandwordremember;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatActivity extends AppCompatActivity {
    private TestDb testDb;
    private ArrayList<Map<String, Object>> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("单词测验");

        testDb = new TestDb(this);
        initStatList();
    }

    private void initStatList(){
        Cursor cursor = testDb.query(null, null, null, null, "last_test_time desc");
        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<>();
            map.put("word", cursor.getString(cursor.getColumnIndex("word")));
            map.put("level", cursor.getInt(cursor.getColumnIndex("level")));
            map.put("test_count", cursor.getInt(cursor.getColumnIndex("test_count")));
            map.put("correct_count", cursor.getInt(cursor.getColumnIndex("correct_count")));
            data.add(map);
        }
        Toast.makeText(this, String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.stat_content_item,
                new String[]{"word", "level", "test_count", "correct_count"}, new int[]{R.id.stat_tv1, R.id.stat_tv2, R.id.stat_tv3, R.id.stat_tv4});
        ListView lv = findViewById(R.id.stat_list);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.stat, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
