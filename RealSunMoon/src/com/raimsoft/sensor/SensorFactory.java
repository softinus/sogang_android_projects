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
	
	private static SensorFactory sf= new SensorFactory();	// 싱글톤
	public SensorManager sensorMgr;					// 센서매니져
	
	private float value_Ori[]= new float[3];		// Listener에서 받아오는 원본 값
	private float value_Prev[]= new float[3];		// 수정 이전에 받아오는 값
	private float value_Measure[]= new float[3];	// 수정된 값
	//private final float value_Scale[]= new float[] { 2, 2.5f, 0.5f };	// 보정 값

	private Point value_Pos=new Point(0,0);			// 변경 값
	private Point value_Fix=new Point(0,0);			// 변경 누적 값
	
	private int sensorID;							// 센서 ID
	
	public final int SENSOR_FACTORY_UNKNOW	= -1;
	public final int SENSOR_FACTORY_NOCHANGE= 0;
	public final int SENSOR_FACTORY_RIGHT	= 1;
	public final int SENSOR_FACTORY_LEFT	= 2;
	public final int SENSOR_FACTORY_DOWN	= 3;
	public final int SENSOR_FACTORY_UP		= 4;
	
	public int Present_Orientation= -1;				// 현재 방향 (위 상수 참조)
	
	
	
	
	// 생성자
	private SensorFactory() {
		for(int i=0; i<3; i++)
		{
			value_Ori[i]= 0;
			value_Prev[i]= 0;
			value_Measure[i]= 0;
		}
		sensorID= sensorMgr.SENSOR_ALL;
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
		if (this.sensorID != sensorMgr.SENSOR_ORIENTATION)
		{
			return 0;
		}
		
		int _sensorForReturn=sf.SENSOR_FACTORY_UNKNOW;
		
		for (int i = 0; i < 3; i++)
        {
			value_Measure[i] = Math.round((value_Ori[i] - value_Prev[i]));
            value_Prev[i] = value_Ori[i];
        }
		
        value_Pos.x = Math.round(value_Measure[0]);
        value_Pos.y = Math.round(value_Measure[1]);
        
        if (Math.abs(value_Pos.x) <= 10)	// 갑자기 큰 폭으로 변경되면 fix값에서 누적제외
        {
        	value_Fix.x -= value_Pos.x;
            value_Fix.y -= value_Pos.y;
        }
       
        
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
	
	
	
//	/**
//	 * @refer SensorListener에서 받아온 값을 넣어주는데 주로 쓰입니다.
//	 * @param _val : SensorListener에서 "value[]"를 받아옵니다.
//	 */
//	public void setSensorValue(float[] _val)
//	{
//		for(int i=0; i<3; i++)
//		{
//			value_Ori[i]= _val[i];
//		}
//	}
//	
	
	/**
	 * @refer SensorListener에서 받아온 값을 넣어주는데 주로 쓰입니다.
	 * SensorFactory를 사용할 때 가장 처음 값을 등록하는 부분입니다.
	 * @param _val : SensorListener에서 "value[]"를 받아옵니다.
	 * @param _sensor : SensorListener에서 "sensor"를 받아옵니다.
	 */
	public void setSensorValue(float[] _val, int _sensor)
	{
		for(int i=0; i<3; i++)
		{
			value_Ori[i]= _val[i];
		}
		sensorID= _sensor;
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
	 * 변화된 값(value_Measure)을 가져옵니다.
	 * @return 기존 SensorListener에서 가져온 값에서 주기적으로 변화된 값
	 */
	public Point getSensorChangedValue()
	{
		return value_Pos;
	}
	
	/**
	 * 변화된 값(value_Measur)의 고정값을 가져옵니다.
	 * @return 현재 위치의 고정값
	 */
	public Point getSensorFixedValue()
	{
		return value_Fix;
	}
	
	
	/**
	 * 센서의 정보를 Logcat으로 출력합니다.
	 * SensorListenr에서 sensor값을 받아오는 오버로드된 메소드를 사용한 경우
	 * sensor값까지 출력됩니다.
	 */
	public void debugSensorInfo_Ori()
	{
		Log.i("SF_ORIGINAL","Sensor= "+sensorID
					+" ( " + Float.toString(Math.round(value_Ori[0]))
					+",   "+ Float.toString(Math.round(value_Ori[1]))
					+",   "+ Float.toString(Math.round(value_Ori[2]))
					+" )");
	}
	
	public void debugSensorInfo_Changed()
	{
		Log.i("SF_POSITION","( " + Float.toString(value_Pos.x)
							+",   "+ Float.toString(value_Pos.y)
							+" )");
	}
	
	public void debugSensorInfo_Fixed()
	{
		Log.i("SF_FIXED","( " + Float.toString(value_Fix.x)
							+",   "+ Float.toString(value_Fix.y)
							+" )");
	}
}
