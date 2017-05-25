package com.tom.angsi.kotlinsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class MainActivity : BaseActivity(),DrawerLayout.DrawerListener {

    //设置layout
    override fun setLayout(): Int {
        return R.layout.activity_main;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initleft()
    }

    /*
     *左边菜单栏
     */
    private fun initleft() {
        val left = findViewById(R.id.left) as DrawerLayout
        left.addDrawerListener(this)

    }

    fun initView(){
        val background = findViewById(R.id.backgroud) as ImageView
    }

    //override DrawerLayout.DrawerListener
    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
        //log("onDrawerSlide is start")
    }

    override fun onDrawerClosed(drawerView: View?) {
        log("onDrawerClosed is start")
    }

    override fun onDrawerOpened(drawerView: View?) {
        log("onDrawerOpened is start")
    }

    override fun onDrawerStateChanged(newState: Int) {
        log("onDrawerStateChanged is start")
    }
}
