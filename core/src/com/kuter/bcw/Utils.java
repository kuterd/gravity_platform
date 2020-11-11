package com.kuter.bcw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

// Hiçbir yere ait olmayan bağzı utility fonksiyonları

public class Utils {

	// http://stackoverflow.com/questions/34441749/how-to-specify-a-color-with-hue-saturation-and-brightness-in-libgdx

	public static Color hsv(float hue, float saturation, float value) {
		Color target = new Color();
		saturation = MathUtils.clamp(saturation, 0, 1);
		hue %= 1f;
		value = MathUtils.clamp(value, 0, 1);

		float hf = (hue - (int) hue) * 6f;
		int ihf = (int) hf;
		float f = hf - ihf;

		float pv = value * (1f - saturation);
		float qv = value * (1f - saturation * f);
		float tv = value * (1f - saturation * (1f - f));

		switch (ihf) {
		case 0:
			target.r = value;
			target.g = tv;
			target.b = pv;
			break;
		case 1:
			target.r = qv;
			target.g = value;
			target.b = pv;
			break;
		case 2:
			target.r = pv;
			target.g = value;
			target.b = tv;
			break;
		case 3:
			target.r = pv;
			target.g = qv;
			target.b = value;
			break;
		case 4:
			target.r = tv;
			target.g = pv;
			target.b = value;
			break;
		case 5:
			target.r = value;
			target.g = pv;
			target.b = qv;
			break;
		}

		return target;
	}
	
	public static float angleDist(float c, float t) {	
		return ((t - c) % 360 + 360 + 180) % 360 - 180;
	}

}
