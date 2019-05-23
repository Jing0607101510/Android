package com.example.jing.grandwordremember;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private TestDb testDb;
    private ArrayList<Integer> res_idx;
    private ArrayList<Integer> ans_idx;
    List<WordRec> words;
    private String wordColor;
    private Boolean saveStat;
    private int wordNum;
    private TestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("单词测验");

        Button summit = findViewById(R.id.summit);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summitRes();
            }
        });

        testDb = new TestDb(this);

        Intent intent = getIntent();
        wordColor = intent.getStringExtra("color");
        wordNum = intent.getIntExtra("word_num", 10);
        saveStat = intent.getBooleanExtra("save_stat", true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.test_menu1:
                showTestContent();
                break;
            case R.id.test_menu2:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showTestContent(){
        res_idx = new ArrayList<>();//正确位置
        ans_idx = new ArrayList<>();//选择位置
        for(int i = 0; i < wordNum; i++){
            ans_idx.add(-1);
        }
        Log.d("showTest", "showTest");
        Cursor cursor = testDb.query(null, new String[]{"_id"}, null, null, "_id desc limit 1");
        int maxId = -1;
        if(cursor!=null && cursor.moveToNext()){
            maxId = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        Resolver resolver = new Resolver(getContentResolver(), Uri.parse("content://com.example.jing.app.contentProvider/"));
        words = resolver.query(null, "_id>?", new String[]{String.valueOf(maxId)}, String.format("word limit %d",wordNum));
        ListView lv = (ListView)findViewById(R.id.test_content);
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        int size = words.size();
        for(int i = 0; i < size; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("word", words.get(i).getWord());
            int cnt = 0;
            ArrayList<Integer> nums = new ArrayList<>();
            nums.add(i);
            while(cnt < 3){
                int num = (int)(Math.random() * size);
                boolean flag = true;
                for(int j=0; j < nums.size(); j++){
                    if(num==nums.get(j)){
                        flag=false;
                        break;
                    }
                }
                if(flag) {
                    nums.add(num);
                    cnt++;
                }
            }
            ArrayList<Integer> idxes = new ArrayList<Integer>();idxes.add(0);idxes.add(1);idxes.add(2);idxes.add(3);
            Collections.shuffle(idxes);
            for(int j = 0; j < idxes.size(); j++){
                int num = nums.get(idxes.get(j));
                if(idxes.get(j)==0){
                    res_idx.add(j);
                }
                map.put(String.format("explanation%d",j+1), words.get(num).getExplanation());
            }
            data.add(map);
        }
        adapter = new TestAdapter(this, data, R.layout.test_content_item, new String[]{"word","explanation1","explanation2","explanation3","explanation4"}, new int[]{R.id.word_tv, R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4}, wordColor, res_idx, false);
        lv.setAdapter(adapter);

        Button bt  = (Button)findViewById(R.id.summit);
        bt.setVisibility(View.VISIBLE);
    }

    private void summitRes(){
        ListView lv = findViewById(R.id.test_content);
        for(int position=0; position <wordNum; position++){
            View v = getViewByPosition(position, lv);
            int[] rbids = new int[]{R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4};
            for(int i = 0; i < 4; i++){
                RadioButton rb = v.findViewById(rbids[i]);
                if(rb.isChecked()){
                    ans_idx.set(position, i);
                    break;
                }
            }
        }
        adapter.setShowAns(true);
        Button summit = findViewById(R.id.summit);
        summit.setVisibility(View.GONE);
        if(saveStat==true){
            for(int i = 0; i < words.size(); i++){
                Cursor cursor = testDb.query(null, null, "word=?", new String[]{words.get(i).getWord()}, null);
                if(cursor.getCount()!=0){
                    ContentValues cv = new ContentValues();
                    cursor.moveToFirst();
                    cv.put("test_count", cursor.getInt(cursor.getColumnIndex("test_count"))+1);
                    cv.put("correct_count", cursor.getInt(cursor.getColumnIndex("correct_count"))+(res_idx.get(i).equals(ans_idx.get(i))?1:0));
                    cv.put("last_test_time", "time('now')");
                    testDb.update(null, cv, "word=?", new String[]{words.get(i).getWord()});
                }else{
                    ContentValues cv = new ContentValues();
                    cv.put("word", words.get(i).getWord());
                    cv.put("level", words.get(i).getLevel());
                    cv.put("test_count", 1);
                    cv.put("correct_count", (res_idx.get(i).equals(ans_idx.get(i)))?1:0);
                    cv.put("last_test_time", "time('now')");
                    testDb.insert(null, cv);
                }

            }
        }
    }

    public View getViewByPosition(int pos, ListView listView) {
        int firstListItemPosition = listView.getFirstVisiblePosition();
        int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
