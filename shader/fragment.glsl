#version 400 core

in vec2 outTexCoords;

out vec4 outColor;

uniform sampler2D tex;

void main(void) {
    outColor = texture(tex, outTexCoords);
}