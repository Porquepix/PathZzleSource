#version 330 core

out vec4 fragColor;

uniform sampler2D texture1;
uniform sampler2D texture2;
uniform float blendFactor;
in vec2 texCoord;

void main() {
	vec4 texture1b = texture(texture1, texCoord);
	vec4 texture2b = texture(texture2, texCoord);

	fragColor = mix(texture1b, texture2b, blendFactor);
}