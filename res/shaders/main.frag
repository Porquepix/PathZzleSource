#version 330 core

out vec4 fragColor;

in vec4 color;
in vec2 texCoord;

uniform sampler2D tex;

void main() {
	vec4 textColor = texture2D(tex, texCoord);
	gl_FragColor = vec4(mix(color.rgb, textColor.rgb, textColor.a), color.a * textColor.a);
}