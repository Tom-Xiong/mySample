package com.tom.angsi.kotlinsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView

class SensorActivity : BaseActivity(), SensorEventListener,SurfaceHolder.Callback{


    var sensorManager:SensorManager?=null
    var count1:Int = 0;
    var count2:Int = 0;
    var count3:Int = 0;
    var surface:SurfaceView?=null
    var canvas:Canvas?=null
    var surfaceHolder: SurfaceHolder?=null
    private var mDrawThread: MyThread? = null
    override fun setLayout(): Int {
       return R.layout.activity_sensor_acitvity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSurface()
        initSensor()
    }

    private fun initSurface() {
        surface = findViewById(R.id.surface) as SurfaceView
        surfaceHolder = surface!!.holder
        surfaceHolder!!.addCallback(this)
        mDrawThread = MyThread(surfaceHolder as SurfaceHolder)
    }
    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mDrawThread!!.isRun = false;
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mDrawThread!!.isRun = true;
        mDrawThread!!.p = Paint();
        mDrawThread!!.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }


    class MyThread(holder : SurfaceHolder):Thread(){
            var holder : SurfaceHolder?=null
            var isRun : Boolean = true
            var p : Paint ?=null
            init {
                this.holder =holder
            }

        override fun run() {
            super.run()
            val count = 0
            while (isRun){
                var c :Canvas ?= null
                try {
                    c = holder!!.lockCanvas()

                    //原点设置
                    p!!.setColor(Color.WHITE);
                    c.drawText("O",40f,460f, p);

                    //y轴坐标线
                    p!!.setColor(Color.BLUE);
                    c.drawLine(30f,10f,30f,880f, p);

                    //y
                    c.drawText("Y",40f,30f, p);

                    //x轴坐标线
                    p!!.setColor(Color.GREEN);
                    c.drawLine(0f,440f,320f,440f, p);

                    //x
                    c.drawText("X",300f,460f, p);


                }catch (e : Exception){
                    e.printStackTrace()
                }
                finally {
                    if(c!=null){
                        holder!!.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
    /*
     * 初始化陀螺仪
     */
    private fun initSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magnetic = sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val gyroscope = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(sensorManager!=null){
            sensorManager!!.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_GAME)
            sensorManager!!.registerListener(this,magnetic, SensorManager.SENSOR_DELAY_GAME)
            sensorManager!!.registerListener(this,gyroscope, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event==null)
            return
        if (event.sensor.type === Sensor.TYPE_ACCELEROMETER) {
            //x,y,z分别存储坐标轴x,y,z上的加速度
            if(count1%20==0) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                var n = "TYPE_ACCELEROMETER" + x.toString() + "," + y.toString() + "," + z.toString()
                log(n,"TYPE_ACCELEROMETER")
            }else{
                count1++
            }
        }else
            if(event.sensor.type == Sensor.TYPE_GYROSCOPE){
                //从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
                if(count2%10==0) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2] - (-9.81)
                    var n = "TYPE_GYROSCOPE" + x.toString() + "," + y.toString() + "," + z.toString()
                    log(n)
                    count2 = 1;
                }else{
                    count2++
                }
            }else
                if(event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD){
                    // 三个坐标轴方向上的电磁强度
                    if(count3%40==0) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    var n = "TYPE_MAGNETIC_FIELD"+x.toString()+","+y.toString()+","+z.toString()
                    log(n,"TYPE_MAGNETIC_FIELD")
                        count3 = 1;
                    }else{
                        count3++
                    }
                }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause(){
        super.onPause()
        sensorManager!!.unregisterListener(this);
    }

}
