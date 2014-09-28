precision mediump float;
uniform float uTime;
uniform vec2 uOthers[4];

varying lowp vec2 uva;

void main() {
	//float shake = 80.0 + sin(uTime * 2.0) * 80.0;
	//float zoom = 1.0 + 0.1 * sin(uTime * 4.0);
	//float rotate = 0.6 * uTime;
	const float zoom = 1.0;
	const float rotate = 0.0;

	vec2 uv = uva - vec2(0.5, 0.5);

	float d = zoom * length(uv);
	float a = atan(uv.y, uv.x) + rotate;
	//a += 0.01 * sin(d * shake);
    float ad = degrees(a);


	float dmin1 = 0.35;
	float dmax1 = 0.45;

	if ( (-ad < uTime - 180.0 - uOthers[1].y) && (-ad > uTime - 180.0 - uOthers[1].y - uOthers[2].x) && (d >= dmin1) && (d < dmax1)) {
		// gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);
		gl_FragColor = vec4(uOthers[0].x, uOthers[0].y, uOthers[1].x, 1.0);
	} else {
		discard;
	}
}
