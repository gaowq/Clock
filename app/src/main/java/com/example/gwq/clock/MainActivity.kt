package com.example.gwq.clock

import android.app.AlertDialog
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

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

//        timePicker = findViewById(R.id.timePicker) as TimePicker
//        timePicker!!.setIs24HourView(true);
        //time= StringBuffer()

        getData()
        initView()
    }

    //初始化
    private fun initView() {
        listView = findViewById<SlideListView>(R.id.list)
        listViewSlideAdapter = ListViewSlideAdapter(this, list)
        listView!!.adapter = listViewSlideAdapter
        listViewSlideAdapter!!.setOnClickListenerEditOrDelete(object : ListViewSlideAdapter.OnClickListenerEditOrDelete {
            override fun OnClickListenerEdit(position: Int) {
                //Toast.makeText(this@MainActivity, "edit position: $position", Toast.LENGTH_SHORT).show()
                setDate(position, false)
                listViewSlideAdapter!!.notifyDataSetChanged();
            }

            override fun OnClickListenerDelete(position: Int) {
                //Toast.makeText(this@MainActivity, "delete position: $position", Toast.LENGTH_SHORT).show()
                list.removeAt(position);
                listViewSlideAdapter!!.notifyDataSetChanged();

            }

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
        builder2.setPositiveButton("设置", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                if (position == list.count()) {
                    list.add(ClockModel(hour.toString() + "：" + minute.toString(), true, true))
                } else {
                    list[position] = ClockModel(hour.toString() + "：" + minute.toString(), true, true)
                }

                list.sortBy({ t -> t.Time })
                listViewSlideAdapter!!.notifyDataSetChanged();
                //if (time!!.length > 0) { //清除上次记录的日期
                //    time!!.delete(0, time!!.length);
                //}
                //tvTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
                if (!isNew) listView!!.turnToNormal();
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

    //获取数据
    private fun getData() {
//        for (i in 0..19) {
//            list.add(("第" + i + "个item") as String)
//        }
    }

    override fun onTimeChanged(view: TimePicker?, hour: Int, minute: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        this.hour = hour
        this.minute = minute
    }
}

class ClockModel @JvmOverloads constructor(time: String, on: Boolean, isWork: Boolean) {
    var Time: String? = "00:00";
    var On: Boolean? = false;
    var IsWork: Boolean? = false;

    init {
        this.Time = time;
        this.On = on;
        this.IsWork = isWork;
    }
}