#version 400 core

in vec3 position;
in vec2 texCoords;

out vec3 color;
out vec2 outTexCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    outTexCoords = texCoords;
}
