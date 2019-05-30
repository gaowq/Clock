package com.example.gwq.clock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private SlideListView listView;
    private List<String> list=new ArrayList<String>();
    private ListViewSlideAdapter2 listViewSlideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        initView();
    }

    private void initView(){
        listView=(SlideListView)findViewById(R.id.list);
        listViewSlideAdapter=new ListViewSlideAdapter2(this,list);
        listView.setAdapter(listViewSlideAdapter);
        listViewSlideAdapter.setOnClickListenerEditOrDelete(new ListViewSlideAdapter2.OnClickListenerEditOrDelete() {
            @Override
            public void OnClickListenerEdit(int position) {
                Toast.makeText(MainActivity2.this, "edit position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnClickListenerDelete(int position) {
                Toast.makeText(MainActivity2.this, "delete position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){
        for (int i=0;i<20;i++){
            list.add(new String("第"+i+"个item"));
        }
    }
}