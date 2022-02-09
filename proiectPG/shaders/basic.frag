#version 410 core

in vec3 fPosition;
in vec3 fNormal;
in vec4 fPosEye;
in vec2 fTexCoords;
in vec4 fragPosLightSpace;

out vec4 fColor;

//matrices
uniform mat4 model;
uniform mat4 view;
uniform mat3 normalMatrix;
//lighting
uniform vec3 lightDir;
uniform vec3 lightColor;
uniform vec3 viewPos;
// textures
uniform sampler2D diffuseTexture;
uniform sampler2D specularTexture;
uniform sampler2D shadowMap;


uniform float fogDensity;

uniform vec3 pointLightPositions[6];
uniform vec3 pointLightColors[1];
uniform float pointLightConsts[1];
uniform float pointLightLinears[1];
uniform float pointLightQuads[1];

//components
vec3 ambient;
float ambientStrength = 0.2f;
vec3 diffuse;
vec3 specular;
float specularStrength = 0.5f;
float shininess = 32.0f;

void computeDirLight()
{		
	vec3 cameraPosEye = vec3(0.0f);//in eye coordinates, the viewer is situated at the origin
	
	//transform normal
	vec3 normalEye = normalize(fNormal);	
	
	//compute light direction
	vec3 lightDirN = normalize(lightDir);
	
	//compute view direction 
	vec3 viewDirN = normalize(cameraPosEye - fPosEye.xyz);
		
	//compute ambient light
	ambient = ambientStrength * lightColor;
	
	//compute diffuse light
	diffuse = max(dot(normalEye, lightDirN), 0.0f) * lightColor;
	
	//compute specular light
	vec3 reflection = reflect(-lightDirN, normalEye);
	float specCoeff = pow(max(dot(viewDirN, reflection), 0.0f), shininess);
	specular = specularStrength * specCoeff * lightColor;
}

void computePointLight(vec3 lightPosition, vec3 lightColor, float constant, float lin, float quad) {
    vec4 fPosLight = view * model * vec4(fPosition, 1.0f);
	float distance = length(lightPosition - fPosLight.xyz);
	float att = 1.0f / (constant + lin * distance + quad * (distance * distance));

	//ambient
	ambient += att * ambientStrength * lightColor;

	//diffuse
	vec3 norm = normalize(fNormal);
	vec3 lightDir_normalized =  normalize(lightPosition - fPosLight.xyz); //point light
	diffuse += att * max(dot(norm, lightDir_normalized), 0.0f) * lightColor;

	//spec
	vec3 viewDir_normalized = normalize(viewPos - fPosLight.xyz );
	vec3 reflection = reflect(-lightDir_normalized, norm);
	float spec = pow(max(dot(viewDir_normalized, reflection), 0.0f), 32);
	specular += att * specularStrength * spec * lightColor;
}

float computeShadow(){

	// perform perspective divide
	vec3 normalizedCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;

	// Transform to [0,1] range
	normalizedCoords = normalizedCoords * 0.5 + 0.5;

	if(normalizedCoords.z > 1){
		return 0.0f;
	}

	// Get closest depth value from light's perspective
	float closestDepth = texture(shadowMap, normalizedCoords.xy).r;

	// Get depth of current fragment from light's perspective
	float currentDepth = normalizedCoords.z;

	// Check whether current frag pos is in shadow
	//float shadow = currentDepth > closestDepth ? 1.0 : 0.0;
	// Check whether current frag pos is in shadow
// Check whether current frag pos is in shadow
	float bias = max(0.05f * (1.0f - dot(fNormal, lightDir)), 0.005f);
	float shadow = currentDepth - bias > closestDepth ? 1.0f : 0.0f;

	return shadow;

}

float computeFog(){
 //float fogDensity = 0.05f;
 float fragmentDistance = length(fPosEye);
 float fogFactor = exp(-pow(fragmentDistance * fogDensity, 2));

 return clamp(fogFactor, 0.0f, 1.0f);
}

void main() 
{
    computeDirLight();
    for(int i = 0; i < 6; i++){
        computePointLight(pointLightPositions[i], pointLightColors[0], pointLightConsts[0], pointLightLinears[0], pointLightQuads[0]);
    }

	
	ambient *= texture(diffuseTexture, fTexCoords).rgb;
	diffuse *= texture(diffuseTexture, fTexCoords).rgb;
	specular *= texture(specularTexture, fTexCoords).rgb;


    //compute final vertex color
    //vec3 color = min((ambient + diffuse) * texture(diffuseTexture, fTexCoords).rgb + specular * texture(specularTexture, fTexCoords).rgb, 1.0f);

	float shadow = computeShadow();
	vec3 color = min((ambient + (1.0f - shadow)*diffuse) + (1.0f - shadow)*specular, 1.0f);
    fColor = vec4(color, 1.0f);
	float fogFactor = computeFog();
	vec4 fogColor = vec4(0.5f, 0.5f, 0.5f, 1.0f);
	//fColor = mix(fogColor, color, fogFactor);


	vec4 colorFromTexture = texture(diffuseTexture, fTexCoords);
	if(colorFromTexture.a < 0.1)
discard;
fColor = colorFromTexture;

	fColor = fogColor * (1 - fogFactor) + vec4(color * fogFactor, 0.0f);

}
