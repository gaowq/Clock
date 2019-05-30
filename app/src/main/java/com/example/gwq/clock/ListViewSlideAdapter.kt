package com.example.gwq.clock

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView

/**
 * Created by 磊 on 2016/7/7.
 */
class ListViewSlideAdapter(private val context: Context, private val bulbList: List<ClockModel>) : BaseAdapter() {
    private var onClickListenerEditOrDelete: OnClickListenerEditOrDelete? = null

    override fun getCount(): Int {
        return bulbList.size
    }

    override fun getItem(position: Int): Any {
        return bulbList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bulb = bulbList[position]
        val view: View
        val viewHolder: ViewHolder
        if (null == convertView) {
            view = View.inflate(context, R.layout.item_slide_delete_edit, null)
            viewHolder = ViewHolder()
            viewHolder.tvContent = view.findViewById<View>(R.id.tvContent) as TextView
            viewHolder.img = view.findViewById<View>(R.id.imgLamp) as ImageView
            viewHolder.tvDelete = view.findViewById<View>(R.id.delete) as TextView
            viewHolder.tvEdit = view.findViewById<View>(R.id.tvEdit) as TextView
            viewHolder.isOn = view.findViewById<View>(R.id.switch1) as Switch
            view.tag = viewHolder//store up viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.img!!.setImageResource(R.mipmap.ic_launcher)
        viewHolder.tvContent!!.text = bulb.Time
        viewHolder.isOn!!.isChecked = bulb.On!!
        viewHolder.tvDelete!!.setOnClickListener {
            if (onClickListenerEditOrDelete != null) {
                onClickListenerEditOrDelete!!.OnClickListenerDelete(position)
            }
        }
        viewHolder.tvEdit!!.setOnClickListener {
            if (onClickListenerEditOrDelete != null) {
                onClickListenerEditOrDelete!!.OnClickListenerEdit(position)
            }
        }
        return view
    }

    private inner class ViewHolder {
        internal var tvContent: TextView? = null
        internal var tvEdit: TextView? = null
        internal var tvDelete: TextView? = null
        internal var img: ImageView? = null
        internal var isOn: Switch?=null
    }

    interface OnClickListenerEditOrDelete {
        fun OnClickListenerEdit(position: Int)
        fun OnClickListenerDelete(position: Int)
    }

    fun setOnClickListenerEditOrDelete(onClickListenerEditOrDelete1: OnClickListenerEditOrDelete) {
        this.onClickListenerEditOrDelete = onClickListenerEditOrDelete1
    }

}