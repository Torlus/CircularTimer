precision mediump float;

uniform mat4 uMVPMatrix;
uniform float uTime;

attribute vec4 vPosition;
attribute vec2 vTexCoords;

varying vec2 uva;

void main() {
	gl_Position = uMVPMatrix * vPosition;
	uva = vTexCoords;
}

