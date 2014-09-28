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
	// a += 0.01 * sin(d * shake);

	float dmin1 = 0.30;
	float dmax1 = 0.35;
	float dmin2 = 0.45;
	float dmax2 = 0.50;
	if (mod(degrees(a), 90.0) < 10.0) {
		dmin1 = 0.30;
		dmax1 = 0.40;
		dmin2 = 0.40;
		dmax2 = 0.50;
	}

	if ((d >= dmin1) && (d < dmax1)) {
		gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	} else if ((d >= dmin2) && (d < dmax2)) {
	    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	} else {
		discard;
	}
}
