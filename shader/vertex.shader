#version 400 core

in vec3 position;
in vec2 texCoords;

out vec3 color;
out vec2 outTexCoords;

uniform mat4 transformationMatrix;

void main(void) {
    gl_Position = transformationMatrix * vec4(position, 1.0);
    outTexCoords = texCoords;
}
