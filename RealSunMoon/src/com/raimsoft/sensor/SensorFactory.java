package com.raimsoft.sensor;



import android.graphics.Point;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * @author Choi Jun Hyeok (Raim)
 * @see http://raimsoft.com
 * @since Copyright 2010 Raimsoftⓒ All reserved
 */
public class SensorFactory {
	
	private static SensorFactory sf= new SensorFactory();
	public SensorManager sm;
	
	private float value_Ori[]= new float[3];
	private float value_Prev[]= new float[3];
	private float value_Measure[]= new float[3];
	private final float value_Scale[]= new float[] { 2, 2.5f, 0.5f };

	private Point value_Pos=new Point(0,0);
	
	private int sensor=0;
	
	public final int SENSOR_FACTORY_UNKNOW	= -1;
	public final int SENSOR_FACTORY_NOCHANGE= 0;
	public final int SENSOR_FACTORY_RIGHT	= 1;
	public final int SENSOR_FACTORY_LEFT	= 2;
	public final int SENSOR_FACTORY_DOWN	= 3;
	public final int SENSOR_FACTORY_UP		= 4;
	
	public int Present_Orientation= -1;
	
	
	private SensorFactory() {
		for(int i=0; i<3; i++)
		{
			value_Ori[i]= 0;
			value_Measure[i]= 0;
		}
	}
	
	public static SensorFactory getSensorFactory()
	{
		return sf;
	}
	
	/**
	 * 방향값을 가지고 있는 상수를 가져옵니다.
	 * 이 메소드를 통해 Present_Orientation 값으로 현재 방향을 체크할 수 있습니다.
	 * @return 1:왼쪽, 2:오른쪽, 3:위, 4:아래 (0:변화없음, -1:알수없음)
	 */
	public int getSensorOrientation()
	{
		int _sensorForReturn=sf.SENSOR_FACTORY_UNKNOW;
		
		for (int i = 0; i < 3; i++)
        {
			value_Measure[i] = Math.round(value_Scale[i] * (value_Ori[i] - value_Prev[i]) * 0.45f);
            value_Prev[i] = value_Ori[i];
        }
		
        value_Pos.x = Math.round(value_Measure[0]);
        value_Pos.y = Math.round(value_Measure[1]);
        
        boolean gestX = Math.abs(value_Pos.x) > 3;
        boolean gestY = Math.abs(value_Pos.y) > 3;

        if ((gestX || gestY) && !(gestX && gestY))
        {
            if (gestX)
            {
                if (value_Pos.x < 0) {
                    android.util.Log.i("Sensor", "Turn Right");
                    _sensorForReturn= sf.SENSOR_FACTORY_RIGHT;
                    Present_Orientation= sf.SENSOR_FACTORY_RIGHT;
                } else {
                    android.util.Log.i("Sensor", "Turn Left");
                    _sensorForReturn= sf.SENSOR_FACTORY_LEFT;
                    Present_Orientation= sf.SENSOR_FACTORY_LEFT;
                }
            } else {
                if (value_Pos.y < -2)
                {
                    android.util.Log.i("Sensor", "Turn Up");
                    _sensorForReturn= sf.SENSOR_FACTORY_UP;
                    Present_Orientation= sf.SENSOR_FACTORY_UP;
                } else {
                    android.util.Log.i("Sensor", "Turn Down");
                    _sensorForReturn= sf.SENSOR_FACTORY_DOWN;
                    Present_Orientation= sf.SENSOR_FACTORY_DOWN;
                }
            }
        }
        _sensorForReturn= sf.SENSOR_FACTORY_NOCHANGE;
        //Present_Orientation= sf.SENSOR_FACTORY_NOCHANGE;
        
        if (_sensorForReturn==sf.SENSOR_FACTORY_NOCHANGE)
        {
        	//android.util.Log.i("Sensor", "Not Changed");
        }else if (_sensorForReturn==sf.SENSOR_FACTORY_UNKNOW)
        {
        	android.util.Log.e("Sensor", "I Don't Know");
        }
		return _sensorForReturn;
	}
	
	
	/**
	 * 변화된 값(value_Measure)을 가져옵니다.
	 * 이 메소드는 현재 클래스의 getSensorOrientation() 메소드와 함께 실행되므로
	 * 방향과 변화된 값을 같이 가져올 수 있습니다.
	 * @return 변화된 값을 실시간으로 가져옵니다.
	 */
	public Point getSensorChangedValue()
	{
		return value_Pos;
	}
	
	/**
	 * @refer SensorListener에서 받아온 값을 넣어주는데 주로 쓰입니다.
	 * @param _val : SensorListener에서 "value[]"를 받아옵니다.
	 */
	public void setSensorValue(float[] _val)
	{
		for(int i=0; i<3; i++)
		{
			value_Ori[i]= _val[i];
		}
	}
	/**
	 * @refer SensorListener에서 받아온 값을 넣어주는데 주로 쓰입니다.
	 * @param _val : SensorListener에서 "value[]"를 받아옵니다.
	 * @param _sensor : SensorListener에서 "sensor"를 받아옵니다.
	 */
	public void setSensorValue(float[] _val, int _sensor)
	{
		for(int i=0; i<3; i++)
		{
			value_Ori[i]= _val[i];
		}
		sensor= _sensor;
	}
	
	/**
	 * 주로 Activity의 SensorListener에서 가져온 값을 공유하는데 쓰입니다.
	 * @return value_Ori(원본 value값의 float 배열을 가져옵니다.
	 */
	public float[] getSensorValue()
	{
		return value_Ori;
	}
	
	/**
	 * 센서의 정보를 Logcat으로 출력합니다.
	 * SensorListenr에서 sensor값을 받아오는 오버로드된 메소드를 사용한 경우
	 * sensor값까지 출력됩니다.
	 */
	public void debugSensorInfo()
	{
		if (sensor==0)
		{
			Log.i("Sensor_DEBUG","( " + Float.toString(Math.round(value_Ori[0]))
					+",   "+ Float.toString(Math.round(value_Ori[1]))
					+",   "+ Float.toString(Math.round(value_Ori[2]))
					+" )");
		}else{
			Log.i("Sensor_DEBUG","Sensor= "+sensor
					+" ( " + Float.toString(Math.round(value_Ori[0]))
					+",   "+ Float.toString(Math.round(value_Ori[1]))
					+",   "+ Float.toString(Math.round(value_Ori[2]))
					+" )");
		}
	}
}
