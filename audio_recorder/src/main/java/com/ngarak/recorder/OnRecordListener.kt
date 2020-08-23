/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.ngarak.recorder

interface OnRecordListener {
    fun onStart()
    fun onCancel()
    fun onFinish(recordTime: Long, audioPath: String)
    fun onLessThanSecond()
    fun onTickListener(recordTime: Long)
}
