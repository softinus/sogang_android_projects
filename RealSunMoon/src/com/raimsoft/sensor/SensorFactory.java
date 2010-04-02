package com.raimsoft.sensor;



import android.hardware.SensorManager;
import android.util.Log;

/**
 * @author Choi Jun Hyeok (Raim)
 * @see http://raimsoft.com
 * @since Copyright 2010 Raimsoft�� All reserved
 */
public class SensorFactory {
	
	private static SensorFactory sf= new SensorFactory();
	public SensorManager sm;
	
	private float value_Ori[]= new float[3];
	private float value_Prev[]= new float[3];
	private float value_Measure[]= new float[3];
	private final float value_Scale[]= new float[] { 2, 2.5f, 0.5f };   // accel

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
	 * ���Ⱚ�� ������ �ִ� ����� �����ɴϴ�.
	 * �� �޼ҵ带 ���� Present_Orientation ������ ���� ������ üũ�� �� �ֽ��ϴ�.
	 * @return 1:����, 2:������, 3:��, 4:�Ʒ� (0:��ȭ����, -1:�˼�����)
	 */
	public int getSensorOrientation()
	{
		int _sensorForReturn=sf.SENSOR_FACTORY_UNKNOW;
		
		for (int i = 0; i < 3; i++)
        {
			value_Measure[i] = Math.round(value_Scale[i] * (value_Ori[i] - value_Prev[i]) * 0.45f);
            value_Prev[i] = value_Ori[i];
        }
		
        float x = value_Measure[0];
        float y = value_Measure[1];
        boolean gestX = Math.abs(x) > 3;
        boolean gestY = Math.abs(y) > 3;

        if ((gestX || gestY) && !(gestX && gestY))
        {
            if (gestX)
            {
                if (x < 0) {
                    android.util.Log.i("Sensor", "Turn Right");
                    _sensorForReturn= sf.SENSOR_FACTORY_RIGHT;
                    Present_Orientation= sf.SENSOR_FACTORY_RIGHT;
                } else {
                    android.util.Log.i("Sensor", "Turn Left");
                    _sensorForReturn= sf.SENSOR_FACTORY_LEFT;
                    Present_Orientation= sf.SENSOR_FACTORY_LEFT;
                }
            } else {
                if (y < -2)
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
        Present_Orientation= sf.SENSOR_FACTORY_NOCHANGE;
        
        if (_sensorForReturn==sf.SENSOR_FACTORY_NOCHANGE)
        {
        	android.util.Log.i("Sensor", "Not Changed");
        }else if (_sensorForReturn==sf.SENSOR_FACTORY_UNKNOW)
        {
        	android.util.Log.e("Sensor", "I Don't Know");
        }
		return _sensorForReturn;
	}
	
	
	/**
	 * ��ȭ�� ��(value_Measure)�� �����ɴϴ�.
	 * �� �޼ҵ�� ���� Ŭ������ getSensorOrientation() �޼ҵ�� �Բ� ����ǹǷ�
	 * ����� ��ȭ�� ���� ���� ������ �� �ֽ��ϴ�.
	 * @return ��ȭ�� ���� �ǽð����� �����ɴϴ�.
	 */
	public float[] getSensorChangedValue()
	{
		this.getSensorOrientation();
		return value_Measure;
	}
	
	/**
	 * @refer SensorListener���� �޾ƿ� ���� �־��ִµ� �ַ� ���Դϴ�.
	 * @param _val : SensorListener���� "value[]"�� �޾ƿɴϴ�.
	 */
	public void setSensorValue(float[] _val)
	{
		for(int i=0; i<3; i++)
		{
			value_Ori[i]= _val[i];
		}
	}
	/**
	 * @refer SensorListener���� �޾ƿ� ���� �־��ִµ� �ַ� ���Դϴ�.
	 * @param _val : SensorListener���� "value[]"�� �޾ƿɴϴ�.
	 * @param _sensor : SensorListener���� "sensor"�� �޾ƿɴϴ�.
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
	 * �ַ� Activity�� SensorListener���� ������ ���� �����ϴµ� ���Դϴ�.
	 * @return value_Ori(���� value���� float �迭�� �����ɴϴ�.
	 */
	public float[] getSensorValue()
	{
		return value_Ori;
	}
	
	/**
	 * ������ ������ Logcat���� ����մϴ�.
	 * SensorListenr���� sensor���� �޾ƿ��� �����ε�� �޼ҵ带 ����� ���
	 * sensor������ ��µ˴ϴ�.
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
