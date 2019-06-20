package com.example.gwq.clock

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import kotlinx.android.synthetic.main.activity_close.*

class CloseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close)
        setSupportActionBar(toolbar)


        button3.setOnClickListener({ startActivity(Intent(this,MainActivity::class.java))})

//        new AlertDialog.Builder(ClockActivity.this).setTitle("闹钟").setMessage("这是出门的闹铃！")
//
//                   .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {
//
//                           @Override
//                           public void onClick(DialogInterface dialog, int which) {
//
//                                 mediaPlayer.stop();
//
//                                 ClockActivity.this.finish();
//
//                           }
//
//              }).show();

    }

}
