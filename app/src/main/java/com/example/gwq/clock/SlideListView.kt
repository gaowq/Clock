package com.example.gwq.clock

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ListView

class SlideListView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : ListView(context, attrs, defStyle) {
    private val mScreenWidth: Int    // 屏幕宽度
    private var mDownX: Int = 0            // 按下点的x值
    private var mDownY: Int = 0            // 按下点的y值
    private var mDeleteBtnWidth: Int = 0// 删除按钮的宽度

    private var isDeleteShown: Boolean = false    // 删除按钮是否正在显示

    private var mPointChild: ViewGroup? = null    // 当前处理的item
    private var mLayoutParams: LinearLayout.LayoutParams? = null    // 当前处理的item的LayoutParams

    init {

        // 获取屏幕宽度
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        mScreenWidth = dm.widthPixels
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> performActionDown(ev)
            MotionEvent.ACTION_MOVE -> return performActionMove(ev)
            MotionEvent.ACTION_UP -> performActionUp()
        }

        return super.onTouchEvent(ev)
    }

    // 处理action_down事件
    private fun performActionDown(ev: MotionEvent) {
        if (isDeleteShown) {
            turnToNormal()
        }

        mDownX = ev.x.toInt()
        mDownY = ev.y.toInt()
        // 获取当前点的item
        mPointChild = getChildAt(pointToPosition(mDownX, mDownY) - firstVisiblePosition) as ViewGroup
        // 获取删除按钮的宽度
        mDeleteBtnWidth = mPointChild!!.getChildAt(1).layoutParams.width
        mLayoutParams = mPointChild!!.getChildAt(0)
                .layoutParams as LinearLayout.LayoutParams
        mLayoutParams!!.width = mScreenWidth
        mPointChild!!.getChildAt(0).layoutParams = mLayoutParams
    }

    // 处理action_move事件
    private fun performActionMove(ev: MotionEvent): Boolean {
        val nowX = ev.x.toInt()
        val nowY = ev.y.toInt()
        if (Math.abs(nowX - mDownX) > Math.abs(nowY - mDownY)) {
            // 如果向左滑动
            if (nowX < mDownX) {
                // 计算要偏移的距离
                var scroll = (nowX - mDownX) / 2
                // 如果大于了删除按钮的宽度， 则最大为删除按钮的宽度
                if (-scroll >= mDeleteBtnWidth) {
                    scroll = -mDeleteBtnWidth
                }
                // 重新设置leftMargin
                mLayoutParams!!.leftMargin = scroll * 2
                mPointChild!!.getChildAt(0).layoutParams = mLayoutParams
            }
            return true
        }
        return super.onTouchEvent(ev)
    }

    // 处理action_up事件
    private fun performActionUp() {
        // 偏移量大于button的一半，则显示button
        // 否则恢复默认
        if (-mLayoutParams!!.leftMargin >= mDeleteBtnWidth / 2) {
            mLayoutParams!!.leftMargin = -mDeleteBtnWidth * 2
            isDeleteShown = true
        } else {
            turnToNormal()
        }

        mPointChild!!.getChildAt(0).layoutParams = mLayoutParams
    }

    /**
     * 变为正常状态
     */
    fun turnToNormal() {
        mLayoutParams!!.leftMargin = 0
        mPointChild!!.getChildAt(0).layoutParams = mLayoutParams
        isDeleteShown = false
    }

    /**
     * 当前是否可点击
     * @return 是否可点击
     */
    fun canClick(): Boolean {
        return !isDeleteShown
    }


}