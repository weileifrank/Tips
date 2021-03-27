package com.frank.stepprogressview

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    lateinit var dbv:FootCountProgressView
    val list = listOf("0","10","20","30","40")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbv = findViewById(R.id.dbv)
    }

    override fun onResume() {
        super.onResume()
        dbv.postDelayed({
            dbv.setText(list)
//            val length = 200.dp
            val keyframe1 = Keyframe.ofFloat(0f, 0f)
            val keyframe2 = Keyframe.ofFloat(0.2f, 0.4f )
            val keyframe3 = Keyframe.ofFloat(0.6f, 1.0f)
            val keyframe4 = Keyframe.ofFloat(1f, 0.2f)
            val keyframeHolder = PropertyValuesHolder.ofKeyframe("percent", keyframe1, keyframe2, keyframe3, keyframe4)
            val animator = ObjectAnimator.ofPropertyValuesHolder(dbv, keyframeHolder)
            animator.startDelay = 1000
            animator.duration = 2000
            animator.start()
        },500)
    }
}