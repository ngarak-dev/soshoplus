/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package me.ngarak.recorder

import android.media.MediaRecorder

class RecorderManager {

    companion object {

        private val recorderList = mutableListOf<MediaRecorder>()

        /**
         * Records Audio
         * @param recorder Medial Player Recorder Object
         * @param path Path to local audio file location
         */
        fun recordAudio(recorder: MediaRecorder, path: String) {
            recorderList.add(recorder)
            val mediaRecorder = recorderList[recorderList.size - 1]
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder.setAudioChannels(1)
            mediaRecorder.setAudioSamplingRate(12000)
            mediaRecorder.setOutputFile(path)
            mediaRecorder.setMaxDuration(60000)
            mediaRecorder.prepare()
            mediaRecorder.start()
        }

        /**
         * Stop Record
         * @param recorder Media Recorder Object
         */
        fun stopRecord(recorder: MediaRecorder) {
            val indexOfRecorder = recorderList.indexOf(recorder)
            if (recorderList.isNotEmpty() && indexOfRecorder != -1) {
                val mediaRecorder = recorderList[indexOfRecorder]
                try {
                    mediaRecorder.stop()
                } catch (e: Exception) {
                }
                mediaRecorder.reset()
                mediaRecorder.release()
                recorderList.remove(recorder)
            }
        }

        /**
         * Clear the Media Recorder List
         */
        fun clear() {
            recorderList.clear()
        }
    }
}
