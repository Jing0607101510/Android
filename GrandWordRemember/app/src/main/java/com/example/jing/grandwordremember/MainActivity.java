package com.example.jing.grandwordremember;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("-快速记忆法");

        settings = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.support, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu1:
                startTestActivity();
                break;
            case R.id.menu2:
                addNewWord();
                break;
            case R.id.menu3:
                startStatActivity();
                break;
            case R.id.menu4:
                break;
            case R.id.menu5:
                startActivity(new Intent(MainActivity.this, PrefActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void startStatActivity(){
        Intent intent = new Intent(MainActivity.this, StatActivity.class);
        startActivity(intent);
    }

    private void startTestActivity(){
        Bundle data = new Bundle();
        data.putString("color", settings.getString("color", "Red"));
        data.putBoolean("save_stat", settings.getBoolean("save_stat", true));
        data.putInt("word_num", Integer.parseInt(settings.getString("word_num", "10")));
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        intent.putExtras(data);
        startActivity(intent);
    }

    private void addNewWord(){
        DlgHandleWord dlgHandleWord = new DlgHandleWord(MainActivity.this);
        dlgHandleWord.showDialog();
    }
}
