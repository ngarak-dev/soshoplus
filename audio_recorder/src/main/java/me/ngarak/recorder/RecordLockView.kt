/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package me.ngarak.recorder

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.ngarak.recorder.R

class RecordLockView : RelativeLayout {
    private var mLayoutLock: View? = null
    private var mImageViewLockArrow: View? = null
    private var mImageViewLock: View? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view =
            View.inflate(context, R.layout.layout_record_lock_view, null)
        addView(view)
        mLayoutLock = view.findViewById(R.id.layoutLock)
        mImageViewLock = view.findViewById(R.id.imageViewLock)
        mImageViewLockArrow = view.findViewById(R.id.imageViewLockArrow)
    }

    fun getLayoutLock(): View? {
        return mLayoutLock
    }

    fun getImageViewLockArrow(): View? {
        return mImageViewLockArrow
    }

    fun getImageViewLock(): View? {
        return mImageViewLock
    }
}