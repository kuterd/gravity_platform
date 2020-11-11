#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_position;

uniform vec2 u_ripplePos;
uniform float u_rippleTime;
uniform float u_rippleMRadius;
uniform float u_rippleInitRadius;


void main() {
	
	vec4 colr = v_color;	
	float distn = length(v_position - u_ripplePos);
	
	if (u_rippleTime * u_rippleMRadius + u_rippleInitRadius > distn) {
		float rippleAlpha =  0.80 - u_rippleTime * 0.80;
		colr.xyz = colr.xyz * (1.0 - rippleAlpha) + vec3(1.0 - colr.x, 1.0 - colr.y, 1.0 - colr.y) * rippleAlpha;
	}
	
	gl_FragColor = colr;
}