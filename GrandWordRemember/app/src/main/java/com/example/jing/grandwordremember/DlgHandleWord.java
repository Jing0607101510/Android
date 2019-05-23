package com.example.jing.grandwordremember;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.List;

public class DlgHandleWord {
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private Context context;
    private View view;
    public DlgHandleWord(final Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.add_word_form, null, false);
        this.context = context;
        builder = new AlertDialog.Builder(context);
        alertDialog = builder
                .setIcon(R.mipmap.dict)
                .setTitle("增加单词")
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addNewWord();
                    }
                })
                .create();
    }

    public void showDialog(){
        alertDialog.show();
    }

    private void addNewWord(){
        EditText word_spell = (EditText)view.findViewById(R.id.word_spell);
        String word = word_spell.getText().toString();

        EditText word_explanation = (EditText)view.findViewById(R.id.word_explanation);
        String explanation = word_explanation.getText().toString();

        EditText word_level = (EditText)view.findViewById(R.id.word_level);
        if(isNumeric((word_level.getText().toString()))==false){
            Toast.makeText(context, "级别只能输入0-6"+"你的输入为"+word_level.getText().toString()+")",Toast.LENGTH_SHORT).show();
            return;
        }
        int level = Integer.parseInt(word_level.getText().toString());

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.word_cover);
        boolean cover = checkBox.isChecked()?true:false;
        if(word.length()==0){
            Toast.makeText(context, "单词或级别不能为空", Toast.LENGTH_SHORT).show();
        }
        else{
            Resolver resolver = new Resolver(context.getContentResolver(), Uri.parse("content://com.example.jing.app.contentProvider/"));
            List<WordRec> words = resolver.query(null, "word=?", new String[]{word}, null);
            if(words.size()==0){
                ContentValues cv = new ContentValues();
                cv.put("word", word);
                cv.put("explanation", explanation);
                cv.put("level", level);
                resolver.insert(cv);
                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
            }
            else{
                if(cover==true){
                    ContentValues cv = new ContentValues();
                    cv.put("word", word);
                    cv.put("explanation", explanation);
                    cv.put("level", level);
                    resolver.insert(cv);
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "添加失败，单词库已有该单词", Toast.LENGTH_SHORT).show();
                }
            }
        }
        Log.e("xinxi", "runhere");
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-6]$");
        else
            return false;
    }

}
