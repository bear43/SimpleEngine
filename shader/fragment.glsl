#version 400 core

in vec2 outTexCoords;
in vec4 color;

out vec4 outColor;

uniform sampler2D tex;

void main(void) {
    outColor = color * texture(tex, outTexCoords);
}