#version 400 core

in vec2 position;
in vec2 texCoords;

out vec2 outTexCoords;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
    outTexCoords = texCoords;
}
