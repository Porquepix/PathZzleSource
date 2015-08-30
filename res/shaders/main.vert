#version 330 core

out vec4 color;
out vec2 texCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec4 in_color;
layout (location = 2) in vec2 in_texCoord;

void main() {
	color = in_color;
	texCoord = in_texCoord;

	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(in_position, 1.0);
}