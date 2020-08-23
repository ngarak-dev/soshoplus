/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.ngarak.recorder

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecordButton : FloatingActionButton, View.OnTouchListener, View.OnClickListener {

    private var scaleAnim: ScaleAnim? = null
    private var recordView: RecordView? = null
    private var listenForRecord = true
    private var onRecordClickListener: OnRecordClickListener? = null
    private var imgResource = 0
    private var lockSendResource = 0

    fun setRecordView(recordView: RecordView) {
        this.recordView = recordView
        recordView.setRecordButton(this)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordButton)

            imgResource = typedArray.getResourceId(R.styleable.RecordButton_mic_icon, -1)
            lockSendResource =
                typedArray.getResourceId(R.styleable.RecordButton_lock_send_resource, -1)

            if (imgResource != -1) {
                setTheImageResource(imgResource)
            }

            compatElevation = 0f

            typedArray.recycle()
        }

        scaleAnim = ScaleAnim(this)

        this.setOnTouchListener(this)
        this.setOnClickListener(this)


    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setClip(this)
    }

    private fun setClip(v: View) {
        if (v.parent == null) {
            return
        }

        if (v is ViewGroup) {
            v.clipChildren = false
            v.clipToPadding = false
        }

        if (v.parent is View) {
            setClip(v.parent as View)
        }
    }

    fun setTheImageResource(imageResource: Int) {
        val image = AppCompatResources.getDrawable(context, imageResource)
        setImageDrawable(image)
    }

    fun getImageResource(): Int {
        return imgResource
    }

    fun setLockedSendResource(lockSendResource: Int) {
        this.lockSendResource = lockSendResource
    }

    fun getLockSendResource(): Int {
        return lockSendResource
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (listenForRecord) {
            when (event.action) {

                MotionEvent.ACTION_DOWN -> recordView?.onActionDown(v as RecordButton, event)

                MotionEvent.ACTION_MOVE -> recordView?.onActionMove(v as RecordButton, event)

                MotionEvent.ACTION_UP -> recordView?.onActionUp(v as RecordButton)
            }
        }
        return listenForRecord
    }

    fun startScale() {
        scaleAnim?.start()
    }

    fun stopScale() {
        scaleAnim?.stop()
    }

    fun setOnRecordClickListener(onRecordClickListener: OnRecordClickListener) {
        this.onRecordClickListener = onRecordClickListener
    }

    override fun onClick(v: View) {
        if (recordView?.isLocked == true) {
            return
        }
        if (onRecordClickListener != null)
            onRecordClickListener!!.onClick(v)
    }

    fun setListenForRecord(listenForRecord: Boolean) {
        this.listenForRecord = listenForRecord
    }

    fun isListenForRecord(): Boolean {
        return listenForRecord
    }

}
