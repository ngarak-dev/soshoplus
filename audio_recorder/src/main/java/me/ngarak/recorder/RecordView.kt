/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package me.ngarak.recorder

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.ngarak.recorder.R
import io.supercharge.shimmerlayout.ShimmerLayout
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val LESS_THAN_A_MINUTE_TIME = 1200

class RecordView : RelativeLayout {
    private var smallBlinkingMic: ImageView? = null
    private var basketImg: ImageView? = null
    private var counterTime: Chronometer? = null
    private var timer = Timer()
    private var slideToCancel: TextView? = null
    private var mStopView: TextView? = null
    private var slideToCancelLayout: ShimmerLayout? = null
    private var arrow: ImageView? = null
    private var initialX: Float = 0.toFloat()
    private var initialY: Float = 0.toFloat()
    private var basketInitialY: Float = 0.toFloat()
    private var difX = 0f
    private var difY = 0f
    private var initialDifY = 0f
    private var cancelBounds = DEFAULT_CANCEL_BOUNDS.toFloat()
    private var lockBounds = DEFAULT_LOCK_BOUNDS.toFloat()
    private var swipeDistance = DEFAULT_SWIPE_DISTANCE
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var mContext: Context? = null
    private var recordListener: OnRecordListener? = null
    private var isSwiped: Boolean = false
    private var isLessThanSecondAllowed = false
    private var isSoundEnabled = false
    private var RECORD_START = R.raw.record_start
    private var RECORD_FINISHED = R.raw.record_finished
    private var RECORD_ERROR = R.raw.record_error
    private var player: MediaPlayer? = null
    private var animationHelper: AnimationHelper? = null
    private var audioPath = ""
    private var recorder: MediaRecorder? = null
    private var recordLockView: RecordLockView? = null
    private var recordButton: RecordButton? = null
    private var animJump: Animation? = null
    private var animJumpFast: Animation? = null
    private var isHorizontal = false
    private var isVertical = false
    var isLocked = false

    private var lastX = 0f
    private var lastY = 0f
    private var firstX = 0f
    private var firstY = 0f

    constructor(context: Context) : super(context) {
        this.mContext = context
        init(context, null, -1, -1)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
        init(context, attrs, -1, -1)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContext = context
        init(context, attrs, defStyleAttr, -1)
    }

    fun setRecordLockView(recordLockView: RecordLockView?): RecordView? {
        this.recordLockView = recordLockView
        this.recordLockView?.visibility = View.INVISIBLE
        hideViews(true)
        return this
    }

    fun setRecordButton(recordButton: RecordButton?): RecordView? {
        this.recordButton = recordButton
        return this
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val view = View.inflate(context, R.layout.layout_record_view, null)
        addView(view)

        val viewGroup = view.parent as ViewGroup
        viewGroup.clipChildren = false

        arrow = view.findViewById(R.id.arrow)
        slideToCancel = view.findViewById(R.id.slide_to_cancel)
        smallBlinkingMic = view.findViewById(R.id.glowing_mic)
        counterTime = view.findViewById(R.id.counter_tv)
        basketImg = view.findViewById(R.id.basket_img)
        slideToCancelLayout = view.findViewById(R.id.shimmer_layout)
        mStopView = view.findViewById(R.id.stopView)

        animJump = AnimationUtils.loadAnimation(context, R.anim.jump)
        animJumpFast = AnimationUtils.loadAnimation(context, R.anim.jump_fast)

        hideViews(true)

        if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
            val typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.RecordView,
                defStyleAttr, defStyleRes
            )

            val slideArrowResource =
                typedArray.getResourceId(R.styleable.RecordView_slide_to_cancel_arrow, -1)
            val slideToCancelText =
                typedArray.getString(R.styleable.RecordView_slide_to_cancel_text)
            val slideMarginRight =
                typedArray.getDimension(R.styleable.RecordView_slide_to_cancel_margin_right, 30f)
                    .toInt()
            val counterTimeColor =
                typedArray.getColor(R.styleable.RecordView_counter_time_color, -1)
            val arrowColor =
                typedArray.getColor(R.styleable.RecordView_slide_to_cancel_arrow_color, -1)
            val cancelBounds =
                typedArray.getDimensionPixelSize(R.styleable.RecordView_slide_to_cancel_bounds, -1)
            val stopText = typedArray.getString(R.styleable.RecordView_stop_text)

            if (cancelBounds != -1)
                setCancelBounds(
                    cancelBounds.toFloat(),
                    false
                )//don't convert it to pixels since it's already in pixels

            if (slideArrowResource != -1) {
                val slideArrow = AppCompatResources.getDrawable(context, slideArrowResource)
                arrow!!.setImageDrawable(slideArrow)
            }

            if (slideToCancelText != null)
                slideToCancel?.text = slideToCancelText

            if (counterTimeColor != -1)
                setCounterTimeColor(counterTimeColor)

            if (arrowColor != -1)
                setSlideToCancelArrowColor(arrowColor)

            if (stopText != null)
                mStopView?.text = stopText

            setMarginRight(slideMarginRight, true)

            typedArray.recycle()
        }

        animationHelper = AnimationHelper(context, basketImg!!, smallBlinkingMic!!)

        mStopView?.setOnClickListener {
            isLocked = false
            val time = System.currentTimeMillis() - startTime

            //if the time was less than one second then do not start basket animation
            if (isLessThanOneSecond(time)) {
                hideViews(true)
                animationHelper?.clearAlphaAnimation(false)
                animationHelper?.onAnimationEnd()
            } else {
                hideViews(false)
                animationHelper?.animateBasket(basketInitialY)
            }

            animationHelper?.moveRecordButtonAndSlideToCancelBack(
                recordButton,
                slideToCancelLayout,
                initialX,
                difX,
                initialY
            )
            counterTime?.stop()
            slideToCancelLayout?.stopShimmerAnimation()
            isSwiped = true
            animationHelper?.setStartRecorded(false)
            recordListener?.onCancel()
        }
    }

    private fun hideViews(hideSmallMic: Boolean) {
        firstX = 0f
        firstY = 0f
        lastX = 0f
        lastY = 0f
        isLocked = false
        slideToCancelLayout?.visibility = View.GONE
        counterTime?.visibility = View.GONE
        mStopView?.visibility = View.GONE

        if (hideSmallMic)
            smallBlinkingMic?.visibility = View.GONE

        if (recordLockView?.visibility == View.VISIBLE)
            recordLockView?.showOut()
        recordLockView?.getImageViewLockArrow()?.clearAnimation()

        recordButton?.run {
            if (getImageResource() != -1) {
                setTheImageResource(getImageResource())
            }
        }
    }

    private fun showViews() {
        mStopView?.visibility = View.GONE
        slideToCancelLayout?.visibility = View.VISIBLE
        smallBlinkingMic?.visibility = View.VISIBLE
        counterTime?.visibility = View.VISIBLE

        recordLockView?.run {
            showIn()
            getImageViewLockArrow()?.clearAnimation()
            getImageViewLockArrow()?.startAnimation(animJumpFast)
            getImageViewLock()?.startAnimation(animJump)
        }
    }

    private fun showLockedViews() {
        slideToCancelLayout?.visibility = View.GONE
        mStopView?.visibility = View.VISIBLE

        recordLockView?.run {
            showOut()
            getImageViewLockArrow()?.clearAnimation()
        }

        recordButton?.run {
            if (getLockSendResource() != -1) {
                setTheImageResource(getLockSendResource())
            }

            animationHelper?.moveRecordButtonAndSlideToCancelBack(
                this,
                slideToCancelLayout,
                initialX,
                difX,
                initialY
            )
        }
    }

    private fun isLessThanOneSecond(time: Long): Boolean {
        return time <= LESS_THAN_A_MINUTE_TIME
    }

    private fun playSound(soundRes: Int) {

        if (isSoundEnabled) {
            if (soundRes == 0)
                return

            try {
                player = MediaPlayer()
                val afd = mContext!!.resources.openRawResourceFd(soundRes) ?: return
                player?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                player?.prepare()
                player?.start()
                player?.setOnCompletionListener { mp -> mp.release() }
                player?.isLooping = false
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun onActionDown(recordBtn: RecordButton, motionEvent: MotionEvent) {

        if (isLocked) {
            if (recorder != null) {
                recorder?.let { RecorderManager.stopRecord(it) }
                recorder = null
            }

            hideViews(!isSwiped)

            animationHelper?.clearAlphaAnimation(true)
            animationHelper?.moveRecordButtonAndSlideToCancelBack(
                recordBtn,
                slideToCancelLayout,
                initialX,
                difX,
                initialY
            )

            stopTimerAndChorno()

            slideToCancelLayout?.stopShimmerAnimation()

            if (!isLessThanSecondAllowed && isLessThanOneSecond(elapsedTime)) {
                recordListener?.onLessThanSecond()
                animationHelper?.setStartRecorded(false)

                playSound(RECORD_ERROR)

            } else {
                animationHelper?.setStartRecorded(false)
                playSound(RECORD_FINISHED)

                recordListener?.onFinish(elapsedTime, audioPath)
            }
            return
        }

        isHorizontal = false
        isVertical = false

        recordListener?.onStart()

        firstX = motionEvent.rawX
        firstY = motionEvent.rawY

        initialDifY = recordBtn.y - motionEvent.rawY

        animationHelper?.setStartRecorded(true)
        animationHelper?.resetBasketAnimation()
        animationHelper?.resetSmallMic()

        recordBtn.startScale()
        slideToCancelLayout?.startShimmerAnimation()

        initialX = recordBtn.x
        initialY = recordBtn.y

        basketInitialY = basketImg!!.y + 90

        playSound(RECORD_START)

        showViews()

        animationHelper?.animateSmallMicAlpha()

        counterTime?.base = SystemClock.elapsedRealtime()
        startTime = System.currentTimeMillis()
        counterTime?.start()

        timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    elapsedTime = System.currentTimeMillis() - startTime
                    recordListener?.onTickListener(elapsedTime)
                }

            }

        }, 1000, 1000)
        isSwiped = false
        startRecording()
    }

    private fun startRecording() {
        recorder = MediaRecorder()
        RecorderManager.recordAudio(recorder!!, file().absolutePath)
    }

    fun onActionMove(recordBtn: RecordButton, motionEvent: MotionEvent) {

        val time = System.currentTimeMillis() - startTime

        val xDiff = abs(motionEvent.rawX - firstX)
        val yDiff = abs(motionEvent.rawY - firstY)
        if (xDiff < 100 && yDiff < 100) {
            isVertical = false
            isHorizontal = false
            if (slideToCancelLayout?.visibility == View.GONE) {
                slideToCancelLayout?.visibility = View.VISIBLE
                slideToCancelLayout?.startShimmerAnimation()
            }

            if (recordLockView?.visibility == View.GONE)
                recordLockView?.run {
                    showIn()
                    getImageViewLockArrow()?.clearAnimation()
                    getImageViewLockArrow()?.startAnimation(animJumpFast)
                    getImageViewLock()?.startAnimation(animJump)
                }
        }

        if (!isSwiped) {

            //Swipe To Cancel
            if (!isVertical && slideToCancelLayout?.x != 0f && slideToCancelLayout!!.x <= counterTime!!.right + cancelBounds) {

                //if the time was less than one second then do not start basket animation
                if (isLessThanOneSecond(time)) {

                    hideViews(true)
                    animationHelper?.clearAlphaAnimation(false)
                    animationHelper?.onAnimationEnd()

                } else {
                    hideViews(false)
                    animationHelper?.animateBasket(basketInitialY)
                }

                animationHelper?.moveRecordButtonAndSlideToCancelBack(
                    recordBtn,
                    slideToCancelLayout,
                    initialX,
                    difX,
                    initialY
                )

                stopTimerAndChorno()

                slideToCancelLayout?.stopShimmerAnimation()
                isSwiped = true

                animationHelper?.setStartRecorded(false)
                recordListener?.onCancel()

            } else {

                //if statement is to Prevent Swiping out of bounds
                if (motionEvent.rawX < initialX && !isVertical) {

                    if (initialX - motionEvent.rawX >= swipeDistance && !isHorizontal) {
                        isHorizontal = true
                        recordLockView?.run {
                            showOut()
                            getImageViewLockArrow()?.clearAnimation()
                        }
                    }

                    recordBtn.animate()
                        .x(motionEvent.rawX)
                        .y(initialY)
                        .setDuration(0)
                        .start()

                    if (difX == 0f)
                        difX = initialX - slideToCancelLayout!!.x

                    slideToCancelLayout!!.animate()
                        .x(motionEvent.rawX - difX)
                        .setDuration(0)
                        .start()
                }

                if (motionEvent.rawY < firstY && !isHorizontal) {

                    if (firstY - motionEvent.rawY >= swipeDistance && !isVertical) {
                        isVertical = true
                        slideToCancelLayout?.visibility = View.GONE
                        slideToCancelLayout?.stopShimmerAnimation()
                    }

                    recordBtn.animate()
                        .x(initialX)
                        .y(motionEvent.rawY + initialDifY)
                        .setDuration(0)
                        .start()

                    if (difY == 0f)
                        difY = initialY - recordBtn.y

                    if (firstY - motionEvent.rawY >= lockBounds) {
                        isLocked = true
                        isSwiped = true
                        showLockedViews()
                    }
                }
            }
        }

        lastX = motionEvent.rawX
        lastY = motionEvent.rawY
    }

    fun onActionUp(recordBtn: RecordButton) {
        if (isLocked && mStopView?.visibility == View.VISIBLE) {
            return
        }

        if (recorder != null) {
            recorder?.let { RecorderManager.stopRecord(it) }
            recorder = null
        }

        //if user has swiped then do not hide SmallMic since it will be hidden after swipe Animation
        hideViews(!isSwiped)

        if (!isSwiped)
            animationHelper?.clearAlphaAnimation(true)

        animationHelper?.moveRecordButtonAndSlideToCancelBack(
            recordBtn,
            slideToCancelLayout,
            initialX,
            difX,
            initialY
        )

        stopTimerAndChorno()

        slideToCancelLayout?.stopShimmerAnimation()

        //the audio file is less than a second
        if (!isLessThanSecondAllowed && isLessThanOneSecond(elapsedTime) && !isSwiped) {
            recordListener?.onLessThanSecond()
            animationHelper?.setStartRecorded(false)

            playSound(RECORD_ERROR)

        } else {
            animationHelper?.setStartRecorded(false)

            if (!isSwiped)
                playSound(RECORD_FINISHED)

            if (!isSwiped)
                recordListener?.onFinish(elapsedTime, audioPath)
        }
    }

    private fun setMarginRight(marginRight: Int, convertToDp: Boolean) {
        val layoutParams = slideToCancelLayout!!.layoutParams as LayoutParams
        if (convertToDp) {
            layoutParams.rightMargin = DpUtil.toPixel(marginRight.toFloat(), mContext!!).toInt()
        } else
            layoutParams.rightMargin = marginRight

        slideToCancelLayout?.layoutParams = layoutParams
    }


    fun setOnRecordListener(recordListener: OnRecordListener) {
        this.recordListener = recordListener
    }

    fun setOnBasketAnimationEndListener(onBasketAnimationEndListener: OnBasketAnimationEnd) {
        animationHelper?.setOnBasketAnimationEndListener(onBasketAnimationEndListener)
    }

    fun setSoundEnabled(isEnabled: Boolean) {
        isSoundEnabled = isEnabled
    }

    fun setLessThanSecondAllowed(isAllowed: Boolean) {
        isLessThanSecondAllowed = isAllowed
    }

    fun setSlideToCancelText(text: String) {
        slideToCancel?.text = text
    }

    fun setSlideToCancelTextColor(color: Int) {
        slideToCancel?.setTextColor(color)
    }

    fun setSmallMicColor(color: Int) {
        smallBlinkingMic?.setColorFilter(color)
    }

    fun setSmallMicIcon(icon: Int) {
        smallBlinkingMic?.setImageResource(icon)
    }

    fun setSlideMarginRight(marginRight: Int) {
        setMarginRight(marginRight, true)
    }

    fun setCustomSounds(startSound: Int, finishedSound: Int, errorSound: Int) {
        //0 means do not play sound
        RECORD_START = startSound
        RECORD_FINISHED = finishedSound
        RECORD_ERROR = errorSound
    }

    fun getCancelBounds(): Float {
        return cancelBounds
    }

    fun setCancelBounds(cancelBounds: Float) {
        setCancelBounds(cancelBounds, true)
    }

    //set Chronometer color
    fun setCounterTimeColor(color: Int) {
        counterTime?.setTextColor(color)
    }

    fun setSlideToCancelArrowColor(color: Int) {
        arrow?.setColorFilter(color)
    }

    private fun setCancelBounds(cancelBounds: Float, convertDpToPixel: Boolean) {
        val bounds =
            if (convertDpToPixel) DpUtil.toPixel(cancelBounds, mContext!!) else cancelBounds
        this.cancelBounds = bounds
    }

    companion object {

        const val DEFAULT_CANCEL_BOUNDS = 8 //8dp
        const val DEFAULT_LOCK_BOUNDS = 300
        const val DEFAULT_SWIPE_DISTANCE = 50
    }

    private fun file(): File {
        @SuppressLint("SimpleDateFormat")
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val file =
//            File(Environment.getExternalStorageDirectory().toString() + "/Download/$timeStamp.m4a")
//        val file = File(context.cacheDir.absolutePath, "$timeStamp.m4a")
//        val file = File(context.cacheDir.absolutePath, "voice_record.m4a")
        val file = File(context.cacheDir.absolutePath, "voice_record.mp3")
        audioPath = file.path
        return file
    }

    fun stopTimerAndChorno() {
        counterTime?.stop()
        try {
            timer.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}


