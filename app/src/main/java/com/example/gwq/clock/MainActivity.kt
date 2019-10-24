package com.example.gwq.clock

import android.app.AlertDialog
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.content.DialogInterface;
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import java.util.*
import android.app.AlarmManager
import android.app.PendingIntent


//2.todo 闹钟机制
//3.todo 保存、读取
//4.todo 请求机制
class MainActivity : AppCompatActivity(), TimePicker.OnTimeChangedListener {
    private var listView: SlideListView? = null
    private var list = ArrayList<ClockModel>()
    private var listViewSlideAdapter: ListViewSlideAdapter? = null

    private var timePicker: TimePicker? = null
    //private var time:StringBuffer?=null
    private var hour: Int = 0;
    private var minute: Int = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    //初始化
    private fun initView() {
        listView = findViewById<SlideListView>(R.id.list)
        listViewSlideAdapter = ListViewSlideAdapter(this, list)
        listView!!.adapter = listViewSlideAdapter
        listViewSlideAdapter!!.setOnClickListenerEditOrDelete(object : ListViewSlideAdapter.OnClickListenerEditOrDelete {
            //修改时间
            override fun OnClickListenerEdit(position: Int) {
                setDate(position, false)
                listViewSlideAdapter!!.notifyDataSetChanged();//刷新
            }

            //删除闹钟
            override fun OnClickListenerDelete(position: Int) {
                list.removeAt(position);
                listViewSlideAdapter!!.notifyDataSetChanged();

            }

            //开关闹钟
            override fun OnClickListenerSwitch(position: Int) {
                list[position].On = !list[position].On!!;
                listViewSlideAdapter!!.notifyDataSetChanged();
            }
        })

        var fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            setDate(list.count(), true)

        }
    }

    //设置时间
    private fun setDate(position: Int, isNew: Boolean) {
        var builder2 = AlertDialog.Builder(this);

        //生成闹钟
        builder2.setPositiveButton("确定", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                if (position == list.count()) {
                    list.add(ClockModel(hour, minute, true, true))
                } else {
                    list[position] = ClockModel(hour, minute, true, true)
                }

                list.sortBy({ t -> t.Time() })
                listViewSlideAdapter!!.notifyDataSetChanged();
                if (!isNew) listView!!.turnToNormal();
                alarm(position);
                dialog.dismiss();

            }
        });
        builder2.setNegativeButton("取消", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                if (!isNew) listView!!.turnToNormal();
                dialog.dismiss();
            }
        });
        var dialog1 = builder2.create() as AlertDialog;
        var dialogView2 = View.inflate(this, R.layout.dialog_time, null) as View;
        var timePicker = dialogView2.findViewById(R.id.timePicker) as TimePicker;
        timePicker.setIs24HourView(true); //设置24小时制
        timePicker.setOnTimeChangedListener(this);
        dialog1.setTitle("设置时间");
        dialog1.setView(dialogView2);
        dialog1.show();
    }

    //时间选取继承方法
    override fun onTimeChanged(view: TimePicker?, hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
    }


    fun alarm(position: Int) {
        // 闹钟时间设置
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis();//跟日历同步
        calendar.set(Calendar.HOUR, list[position].Hour!!);
        calendar.set(Calendar.MINUTE, list[position].Minute!!);
        //将秒和毫秒设置为0
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        val intent = Intent(this, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(this, ReqCode(position), intent, 0)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, sender)
        //成功提示
        Toast.makeText(this, "设置闹钟的时间为：" + list[position].Time(), Toast.LENGTH_SHORT).show()
    }

    //生产闹钟编号
    fun ReqCode(position: Int): Int {
        list[position].ReqCode = (list[position].Hour!! * 60 + list[position].Minute!!) * 2 + (if (list[position].On!!) {
            1
        } else {
            0
        })
        return list[position].ReqCode!!;
    }

    //取消闹钟
    fun CancelClock(position: Int) {

    }
}

//闹钟类型
//小时、分钟、开关状态、是否工作闹钟
class ClockModel @JvmOverloads constructor(hour: Int, minute: Int, on: Boolean, isWork: Boolean) {
    var Hour: Int? = 0;
    var Minute: Int? = 0;
    var On: Boolean? = false;
    var IsWork: Boolean? = false;
    var ReqCode: Int? = 0;//闹钟编码

    init {
        this.Hour = hour;
        this.Minute = minute;
        this.On = on;
        this.IsWork = isWork;
    }

    fun Time(): String {
        return String.format("%02d:%02d", Hour, Minute)
    }
}


