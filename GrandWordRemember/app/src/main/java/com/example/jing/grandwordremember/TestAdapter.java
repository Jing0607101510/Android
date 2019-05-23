package com.example.jing.grandwordremember;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestAdapter extends SimpleAdapter {
    private Context context;
    private ArrayList<Map<String, Object>> data;
    private LayoutInflater inflater;
    boolean[] isChecked;
    int[] index;
    private String color;
    ArrayList<Integer> ans_index;
    private boolean show_ans;
    public TestAdapter(Context context, ArrayList<Map<String, Object>> data, int resource, String[] from, int[] to, String color, ArrayList<Integer> ans_index, boolean show_ans){
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.color = color;
        this.ans_index = ans_index;
        this.show_ans = show_ans;
        isChecked = new boolean[data.size()];
        index = new int[data.size()];
        for(int i = 0; i < data.size(); i++){
            isChecked[i] = false;
            index[i] = -1;
        }
    }

    public void setShowAns(boolean show_ans){
        this.show_ans = show_ans;
    }
    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public Object getItem(int position){
        return data.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.test_content_item, parent, false);
        };
        RadioGroup rg = convertView.findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(null);

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView)convertView.findViewById(R.id.word_tv);
        if(color.equals("Green")) {
            tv.setTextColor(Color.argb(255, 0, 255, 0));
        }else if(color.equals("Black")){
            tv.setTextColor(Color.argb(255, 0, 0, 0));
        }else if(color.equals("Blue")){
            tv.setTextColor(Color.argb(255, 0, 0, 255));
        }else{
            tv.setTextColor(Color.argb(255, 255, 0, 0));
        }
        String word = (String)this.data.get(position).get("word");
        tv.setText(word);

        RadioButton rb1 = (RadioButton)convertView.findViewById(R.id.radio1);
        String ex1 = (String)data.get(position).get("explanation1");
        rb1.setText(ex1);

        RadioButton rb2 = (RadioButton)convertView.findViewById(R.id.radio2);
        String ex2 = (String)data.get(position).get("explanation2");
        rb2.setText(ex2);

        RadioButton rb3 = (RadioButton)convertView.findViewById(R.id.radio3);
        String ex3 = (String)data.get(position).get("explanation3");
        rb3.setText(ex3);

        RadioButton rb4 = (RadioButton)convertView.findViewById(R.id.radio4);
        String ex4 = (String)data.get(position).get("explanation4");
        rb4.setText(ex4);

        if(isChecked[position]){
            RadioButton radioButton = (RadioButton)rg.getChildAt(index[position]);
            radioButton.setChecked(true);
        }else{
            rg.clearCheck();
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int x){
                switch(x){
                    case R.id.radio1:
                        index[position] = 0;
                        isChecked[position] = true;
                        break;
                    case R.id.radio2:
                        index[position] = 1;
                        isChecked[position] = true;
                        break;
                    case R.id.radio3:
                        index[position] = 2;
                        isChecked[position] = true;
                        break;
                    case R.id.radio4:
                        index[position] = 3;
                        isChecked[position] = true;
                        break;
                    default:
                        break;
                }
            }
        });
        if(show_ans){
            for(int i = 0; i < 4; i++){
                ((RadioButton)rg.getChildAt(i)).setBackgroundColor(Color.argb(255, 255, 255, 255));
            }
            rg.getChildAt(ans_index.get(position)).setBackgroundColor(Color.argb(255, 200,200,200));
        }

        return convertView;
    }
}
