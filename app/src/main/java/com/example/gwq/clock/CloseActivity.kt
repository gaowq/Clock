package com.example.gwq.clock

import android.os.Bundle
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import kotlinx.android.synthetic.main.activity_close.*
import android.widget.Toast
import android.R.string.cancel
import android.app.AlarmManager
import android.app.PendingIntent



class CloseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close)
        setSupportActionBar(toolbar)


        //button3.setOnClickListener({ startActivity(Intent(this,MainActivity::class.java))})


        button3.setOnClickListener({
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            //获取闹钟管理器
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this, "闹钟已经取消！", Toast.LENGTH_SHORT).show()
        })

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
