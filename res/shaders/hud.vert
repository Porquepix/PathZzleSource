#version 330

out vec2 texCoord;
out vec4 color;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

layout (location = 0) in vec4 in_position;
layout (location = 1) in vec4 in_color;
layout (location = 2) in vec2 in_texCoord;

void main() {
	texCoord = in_texCoord;
	color = in_color;

	gl_Position = projectionMatrix * viewMatrix * modelMatrix *  vec4(in_position.x, in_position.y, in_position.z, 1.0);
}