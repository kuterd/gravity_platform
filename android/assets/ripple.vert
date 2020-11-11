attribute vec4 a_position;
attribute vec4 a_color;

varying vec4 v_color;
varying vec2 v_position;

uniform mat4 u_projModelView;
uniform mat4 u_inv_camera;

void main() {
	v_color = a_color; 
	gl_Position = u_projModelView * a_position;
	v_position = (u_inv_camera * gl_Position ).xy;
}


