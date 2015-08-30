#version 330 core
 
out vec4 fragColor;

in vec2 texCoord;
in vec4 color;

uniform sampler2D tex;

void main() {
	vec4 textColor = texture2D(tex, texCoord);

	vec4 c = vec4(textColor.r * color.r, textColor.g * color.g, textColor.b * color.b, textColor.a * color.a);

	gl_FragColor = clamp(c, 0.0, 1.0);
}