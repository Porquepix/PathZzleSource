#version 330 core

out vec4 fragColor;

in vec4 color;
in vec2 texCoord;

uniform sampler2D tex;

void main() {
	gl_FragColor = color;
}