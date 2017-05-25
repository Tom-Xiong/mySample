package com.tom.angsi.kotlinsample

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION
import android.util.Log


abstract class BaseActivity : AppCompatActivity() {

    abstract fun setLayout() : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparenceStatusBar()
        noNavToolBar()
        setContentView(setLayout())
    }

    /*
     * 状态栏透明
     */
    private fun transparenceStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 透明
            //getWindow.setStatusBarColor(Color.TRANSPARENT)
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /*
     * 没有标题栏
     */
    private fun noNavToolBar() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)//AppCompatActivity
//        requestWindowFeature(Window.FEATURE_NO_TITLE)//Activity
    }

    /*
     * 提示信息
     */
    fun toast(content : String, length : Int = Toast.LENGTH_SHORT){
        Toast.makeText(this, content, length).show()
    }

    /*
     * 输出日志
     */
    fun log(content:String,tag:String = "outpt"){
        Log.d(tag,content);
    }

}
