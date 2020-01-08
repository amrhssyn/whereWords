package me.amrhssyn.wherewords.util

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView


open class MyTouchListener(
    private val mOnTouchActionListener: OnTouchActionListener?
) : RecyclerView.OnItemTouchListener {
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {


    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

        val child = rv.findChildViewUnder(e.x, e.y)
        child?.let {
            val position = rv.getChildLayoutPosition(it)

            mOnTouchActionListener?.onInterceptTouchEvent(position)

        }

        if (e.actionMasked == MotionEvent.ACTION_UP) {
            mOnTouchActionListener?.touchEventFinished()
        }
        return false
    }


    interface OnTouchActionListener {
        fun touchEventFinished()
        fun onInterceptTouchEvent(position: Int)
    }

}
