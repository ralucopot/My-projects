#define GLEW_STATIC
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp> //core glm functionality
#include <glm/gtc/matrix_transform.hpp> //glm extension for generating common transformation matrices
#include <glm/gtc/matrix_inverse.hpp> //glm extension for computing inverse matrices
#include <glm/gtc/type_ptr.hpp> //glm extension for accessing the internal data structure of glm types

#include "Window.h"
#include "Shader.hpp"
#include "Camera.hpp"
#include "Model3D.hpp"

#include <iostream>
#include "SkyBox.hpp"

// window
gps::Window myWindow;


// matrices
glm::mat4 model;
glm::mat4 view;
glm::mat4 projection;
glm::mat3 normalMatrix;
glm::mat4 lightRotation;
glm::mat4 mothModel;
glm::mat4 cauldronModel;

// light parameters
glm::vec3 lightDir;
glm::vec3 lightColor;
GLuint lightDirLoc;
GLuint lightColorLoc;
GLfloat lightAngle = -55.0f;


// shader uniform locations
GLint modelLoc;
GLint viewLoc;
GLint projectionLoc;
GLint normalMatrixLoc;

//constante
const int FELINARE = 6;
const unsigned int SHADOW_WIDTH = 8192;
const unsigned int SHADOW_HEIGHT = 8192;

//lumina punctiforma
//poz felinare
glm::vec3 pointLightPositions[] = {
	glm::vec3(9.7f, -3.56f, 10.58f) * 0.5f,
	glm::vec3(24.6f, -3.5f, 13.96f) * 0.5f,
	glm::vec3(43.0f, -3.6f, 20.15f) * 0.5f,
	glm::vec3(40.2f, -3.5f, 35.0f) * 0.5f,
	glm::vec3(26.78f, -3.7, 45.2f) * 0.5f,//
	glm::vec3(8.4f, -3.5f, 47.8f) * 0.5f

};

glm::vec3 pointLightColors[] = {

	glm::vec3(0.5f, 0.25f, 0.0f) // portocaliu

};
GLfloat pointLightConsts[] = {
	1.0f,

};
GLfloat pointLightLinears[] = {
	0.009f,

};
GLfloat pointLightQuads[] = {
	0.0032f,

};

// camera
gps::Camera myCamera(
	glm::vec3(0.0f, 0.0f, 3.0f),
	glm::vec3(0.0f, 0.0f, -10.0f),
	glm::vec3(0.0f, 1.0f, 0.0f));

GLfloat cameraSpeed = 0.3f;
glm::vec3 cameraPosition;

GLboolean pressedKeys[1024];

// models
gps::Model3D scena;
gps::Model3D moth;
gps::Model3D cauldron;
gps::Model3D lightCube;
gps::Model3D screenQuad;
gps::Model3D skyDome;
//skybox
gps::SkyBox mySkyBox;



// shaders
gps::Shader myBasicShader;
//gps::Shader lightShader;
gps::Shader screenQuadShader;
gps::Shader depthMapShader;
gps::Shader skyboxShader;

//mouse
float sensitivity = 0.05f;
bool firstMouse = true;
float lastX = 512.0f; //jumatatea ferestrei initiale
float lastY = 384.0f;
float pitch = 0.0f;
float yaw = 0.0f;
GLfloat angle;

//umbre
GLuint shadowMapFBO;
GLuint depthMapTexture;
bool showDepthMap;

//functionalitati
bool mothAnimation = true;
float fogDensity = 0.0f;
float delta = 0.0f;
float delta2 = 0.0f;
bool down = false;

GLenum glCheckError_(const char* file, int line)
{
	GLenum errorCode;
	while ((errorCode = glGetError()) != GL_NO_ERROR) {
		std::string error;
		switch (errorCode) {
		case GL_INVALID_ENUM:
			error = "INVALID_ENUM";
			break;
		case GL_INVALID_VALUE:
			error = "INVALID_VALUE";
			break;
		case GL_INVALID_OPERATION:
			error = "INVALID_OPERATION";
			break;
		case GL_STACK_OVERFLOW:
			error = "STACK_OVERFLOW";
			break;
		case GL_STACK_UNDERFLOW:
			error = "STACK_UNDERFLOW";
			break;
		case GL_OUT_OF_MEMORY:
			error = "OUT_OF_MEMORY";
			break;
		case GL_INVALID_FRAMEBUFFER_OPERATION:
			error = "INVALID_FRAMEBUFFER_OPERATION";
			break;
		}
		std::cout << error << " | " << file << " (" << line << ")" << std::endl;
	}
	return errorCode;
}
#define glCheckError() glCheckError_(__FILE__, __LINE__)

void windowResizeCallback(GLFWwindow* window, int width, int height) {
	fprintf(stdout, "Window resized! New width: %d , and height: %d\n", width, height);
	glCheckError();

	glfwGetFramebufferSize(window, &width, &height);
	glCheckError();
	myBasicShader.useShaderProgram();
	glCheckError();
	projection = glm::perspective(glm::radians(45.0f), (float)width / (float)height, 0.1f, 300.0f);
	glCheckError();
	projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
	glCheckError();
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));
	glCheckError();

	glViewport(0, 0, width, height);
	WindowDimensions win;
	win.width = (float)width;
	win.height = (float)height;
	myWindow.setWindowDimensions(win);
	//fprintf(stdout, "Window resized! New width: %d , and height: %d\n", myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
}

void keyboardCallback(GLFWwindow* window, int key, int scancode, int action, int mode) {
	if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
		glfwSetWindowShouldClose(window, GL_TRUE);
	}
	if (key == GLFW_KEY_M && action == GLFW_PRESS)
		showDepthMap = !showDepthMap;

	if (key >= 0 && key < 1024) {
		if (action == GLFW_PRESS) {
			pressedKeys[key] = true;
		}
		else if (action == GLFW_RELEASE) {
			pressedKeys[key] = false;
		}
	}
}

void mouseCallback(GLFWwindow* window, double xpos, double ypos) {

	if (firstMouse) {
		lastX = xpos;
		lastY = ypos;
		firstMouse = false;
	}

	float offsetX = xpos - lastX;
	float offsetY = lastY - ypos;
	lastX = xpos;
	lastY = ypos;

	offsetX *= sensitivity;
	offsetY *= sensitivity;

	angle += offsetX;
	pitch += offsetY;

	if (pitch > 90.0f)
		pitch = 90.0f;
	if (pitch < -90.0f)
		pitch = -90.0f;

	myCamera.rotate(pitch, angle);
	view = myCamera.getViewMatrix();
	myBasicShader.useShaderProgram();
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
}

void processMovement() {
	if (pressedKeys[GLFW_KEY_W]) {
		myCamera.move(gps::MOVE_FORWARD, cameraSpeed);
		//update view matrix
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}

	if (pressedKeys[GLFW_KEY_S]) {
		myCamera.move(gps::MOVE_BACKWARD, cameraSpeed);
		//update view matrix
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}

	if (pressedKeys[GLFW_KEY_A]) {
		myCamera.move(gps::MOVE_LEFT, cameraSpeed);
		//update view matrix
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}

	if (pressedKeys[GLFW_KEY_D]) {
		myCamera.move(gps::MOVE_RIGHT, cameraSpeed);
		//update view matrix
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}

	if (pressedKeys[GLFW_KEY_Q]) {
		angle -= 1.0f;
		// update model matrix for teapot
		model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0, 1, 0));
		// update normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}

	if (pressedKeys[GLFW_KEY_E]) {
		angle += 1.0f;
		// update model matrix for teapot
		model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0, 1, 0));
		// update normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_Z]) {
		angle -= 1.0f;
		myCamera.rotate(pitch, angle);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_X]) {
		angle += 1.0f;
		myCamera.rotate(pitch, angle);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_SPACE]) {
		myCamera.move(gps::MOVE_UP, cameraSpeed);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

	}
	if (pressedKeys[GLFW_KEY_LEFT_CONTROL]) {
		myCamera.move(gps::MOVE_DOWN, cameraSpeed);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		// compute normal matrix for teapot
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

	}
	//wireframe
	if (pressedKeys[GLFW_KEY_1]) {
		glBlendFunc(GL_ONE, GL_ZERO);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	//solid
	if (pressedKeys[GLFW_KEY_2]) {
		glBlendFunc(GL_ONE, GL_ZERO);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	//smooth
	if (pressedKeys[GLFW_KEY_3]) {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glEnable(GL_POLYGON_SMOOTH);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_DST_ALPHA);
	}
	//polygonal
	if (pressedKeys[GLFW_KEY_4]) {
		glBlendFunc(GL_ONE, GL_ZERO);
		glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
	}
	if (pressedKeys[GLFW_KEY_O]) {
		fogDensity += 0.001f;
		myBasicShader.useShaderProgram();
		glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, "fogDensity"), fogDensity);
	}
	if (pressedKeys[GLFW_KEY_P]) {
		fogDensity = 0.0f;
		myBasicShader.useShaderProgram();
		glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, "fogDensity"), fogDensity);
	}
	if (pressedKeys[GLFW_KEY_J]) {
		lightAngle += 1.0f;
	}
	if (pressedKeys[GLFW_KEY_L]) {
		lightAngle -= 1.0f;
	}
	if (pressedKeys[GLFW_KEY_R]) {
		mothAnimation = !mothAnimation;
	}
}

void initOpenGLWindow() {
	myWindow.Create(1024, 867, "OpenGL Project Core");

}

void setWindowCallbacks() {
	glfwSetWindowSizeCallback(myWindow.getWindow(), windowResizeCallback);
	glfwSetKeyCallback(myWindow.getWindow(), keyboardCallback);
	glfwSetCursorPosCallback(myWindow.getWindow(), mouseCallback);
}

void initOpenGLState() {
	glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
	glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
	glEnable(GL_FRAMEBUFFER_SRGB);
	glEnable(GL_DEPTH_TEST); // enable depth-testing
	glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
	glEnable(GL_CULL_FACE); // cull face
	glCullFace(GL_BACK); // cull back face
	glFrontFace(GL_CCW); // GL_CCW for counter clock-wise

	glEnable(GL_FRAMEBUFFER_SRGB);
}

void initModels() {
	scena.LoadModel("models/scene/scene.obj");
	moth.LoadModel("models/moth/moth.obj");
	cauldron.LoadModel("models/cauldron/cauldron.obj");
	//lightCube.LoadModel("models/cube/cube.obj");
	screenQuad.LoadModel("models/quad/quad.obj");
	//skyDome.LoadModel("models/skyDome/skyDome.obj");
}

void initShaders() {

	myBasicShader.loadShader("shaders/basic.vert", "shaders/basic.frag");
	myBasicShader.useShaderProgram();
	//lightShader.loadShader("shaders/lumina.vert", "shaders/lumina.frag");
	//lightShader.useShaderProgram();
	screenQuadShader.loadShader("shaders/screenQuad.vert", "shaders/screenQuad.frag");
	screenQuadShader.useShaderProgram();
	depthMapShader.loadShader("shaders/depth.vert", "shaders/depth.frag");
	depthMapShader.useShaderProgram();
}


void initSkyBoxMat() {
	std::vector<const GLchar*> faces;

	faces.push_back("textures/skybox/posx.jpg");
	faces.push_back("textures/skybox/negx.jpg");
	faces.push_back("textures/skybox/posy.jpg");
	faces.push_back("textures/skybox/negy.jpg");
	faces.push_back("textures/skybox/posz.jpg");
	faces.push_back("textures/skybox/negz.jpg");
	mySkyBox.Load(faces);

	skyboxShader.loadShader("shaders/skyboxShader.vert", "shaders/skyboxShader.frag");
	skyboxShader.useShaderProgram();
	view = myCamera.getViewMatrix();


	glUniformMatrix4fv(glGetUniformLocation(skyboxShader.shaderProgram, "view"), 1, GL_FALSE,
		glm::value_ptr(view));

	projection = glm::perspective(glm::radians(45.0f), (float)myWindow.getWindowDimensions().width / (float)myWindow.getWindowDimensions().height, 0.1f, 300.0f);
	projectionLoc = glGetUniformLocation(skyboxShader.shaderProgram, "projection");
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));
}

void initUniforms() {

	myBasicShader.useShaderProgram();
	model = glm::rotate(glm::mat4(1.0f), glm::radians(-190.0f), glm::vec3(0.0f, 1.0f, 0.0f));
	model = glm::translate(model, glm::vec3(-57.0f, -6.3f, 31.20f) * 0.5f);
	model = glm::scale(model, glm::vec3(0.5f));
	modelLoc = glGetUniformLocation(myBasicShader.shaderProgram, "model");

	// get view matrix for current camera
	view = myCamera.getViewMatrix();
	viewLoc = glGetUniformLocation(myBasicShader.shaderProgram, "view");
	// send view matrix to shader
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

	// compute normal matrix 
	normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	normalMatrixLoc = glGetUniformLocation(myBasicShader.shaderProgram, "normalMatrix");

	// create projection matrix
	projection = glm::perspective(glm::radians(45.0f),
		(float)myWindow.getWindowDimensions().width / (float)myWindow.getWindowDimensions().height,
		0.1f, 300.0f);
	projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
	// send projection matrix to shader
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));

	cameraPosition = myCamera.getPosition();
	glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "viewPos"), 1, glm::value_ptr(cameraPosition));

	//set the light direction (direction towards the light)
	lightDir = normalize(glm::vec3(-60.0f, 15.5f, -36.9f));
	lightDirLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightDir");
	// send light dir to shader
	glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightRotation)) * lightDir));

	// triite denstitatea cetii catre shader
	glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, "fogDensity"), fogDensity);
	//set light color
	lightColor = glm::vec3(0.5f, 0.5f, 0.5f); 
	lightColorLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor");
	// send light color to shader
	glUniform3fv(lightColorLoc, 1, glm::value_ptr(lightColor));

	// trimite datele pt lumina punctiforma 
	for (int i = 0; i < FELINARE; i++) {
		glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "pointLightPositions[" + 0 + ']'), 1, glm::value_ptr(pointLightPositions[i]));
		glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "pointLightColors[0]"), 1, glm::value_ptr(pointLightColors[0]));
		glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, "pointLightConsts[0]"), pointLightConsts[0]);
		glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, "pointLightLinears[0]"), pointLightLinears[0]);
		glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, "pointLightQuads[0]"), pointLightQuads[0]);
	}

	//lightShader.useShaderProgram();
	//glUniformMatrix4fv(glGetUniformLocation(lightShader.shaderProgram, "projection"), 1, GL_FALSE, glm::value_ptr(projection));

}
void initFBO() {
	//TODO - Create the FBO, the depth texture and attach the depth texture to the FBO
	//generate FBO ID
	glGenFramebuffers(1, &shadowMapFBO);

	//create depth texture for FBO
	glGenTextures(1, &depthMapTexture);
	glBindTexture(GL_TEXTURE_2D, depthMapTexture);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT,
		SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	float borderColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);

	//attach texture to FBO
	glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMapTexture, 0);
	glDrawBuffer(GL_NONE);
	glReadBuffer(GL_NONE);

	glBindFramebuffer(GL_FRAMEBUFFER, 0);
}

glm::mat4 computeLightSpaceTrMatrix() {
	//TODO - Return the light-space transformation matrix
	glm::vec3 lightVec = glm::vec3(glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f)) * glm::vec4(lightDir, 1.0f));

	glm::mat4 lightView = glm::lookAt(lightVec * 10.0f, glm::vec3(0.0f), glm::vec3(0.0f, 1.0f, 0.0f));
	const GLfloat near_plane = 0.1f, far_plane = 100.0f;
	glm::mat4 lightProjection = glm::ortho(-20.0f, 20.0f, -20.0f, 20.0f, near_plane, far_plane);
	glm::mat4 lightSpaceTrMatrix = lightProjection * lightView;

	return lightSpaceTrMatrix;
}

void renderObjects(gps::Shader shader, bool depthPass) {
	// select active shader program
	shader.useShaderProgram();

	glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
	// do not send the normal matrix if we are rendering in the depth map
	if (!depthPass) {
		normalMatrix = glm::mat3(glm::inverseTranspose(model));
		glUniformMatrix3fv(glGetUniformLocation(shader.shaderProgram, "normalMatrix"), 1, GL_FALSE, glm::value_ptr(normalMatrix));
	}
	scena.Draw(shader);

	//
	cauldronModel = model;
	cauldronModel = glm::translate(cauldronModel, glm::vec3(0.0f, delta2, 0.0f) * 0.5f);
	if (delta2 > 3.0f) {
		down = true;
	}
	else if (delta2 <= 0.0f) {
		down = false;
	}
	if (down) {
		delta2 -= 0.02f;
	}
	else {
		delta2 += 0.02f;
	}
	glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(cauldronModel));
	// do not send the normal matrix if we are rendering in the depth map
	if (!depthPass) {
		normalMatrix = glm::mat3(glm::inverseTranspose(cauldronModel));
		glUniformMatrix3fv(glGetUniformLocation(shader.shaderProgram, "normalMatrix"), 1, GL_FALSE, glm::value_ptr(normalMatrix));
	}
	cauldron.Draw(shader);

	//
	if (mothAnimation) {
		mothModel = model;
		mothModel = glm::translate(mothModel, glm::vec3(58.0f, 6.3f, -31.20f) * 0.5f);
		mothModel = glm::rotate(mothModel, glm::radians(delta), glm::vec3(0.0f, 0.1f, 0.0f));
		mothModel = glm::translate(mothModel, glm::vec3(-57.0f, -6.3f, 31.20f) * 0.5f);
		delta += 0.2f;
	}
	glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(mothModel));
	if (!depthPass) {
		normalMatrix = glm::mat3(glm::inverseTranspose(mothModel));
		glUniformMatrix3fv(glGetUniformLocation(shader.shaderProgram, "normalMatrix"), 1, GL_FALSE, glm::value_ptr(normalMatrix));
	}
	moth.Draw(shader);

}

void renderScene() {

	if (showDepthMap) {
		glViewport(0, 0, (float)myWindow.getWindowDimensions().width, (float)myWindow.getWindowDimensions().height);

		glClear(GL_COLOR_BUFFER_BIT);

		screenQuadShader.useShaderProgram();

		//bind the depth map
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, depthMapTexture);
		glUniform1i(glGetUniformLocation(screenQuadShader.shaderProgram, "depthMap"), 0);

		glDisable(GL_DEPTH_TEST);
		screenQuad.Draw(screenQuadShader);
		glEnable(GL_DEPTH_TEST);

		depthMapShader.useShaderProgram();

		glUniformMatrix4fv(glGetUniformLocation(depthMapShader.shaderProgram, "lightSpaceTrMatrix"),
			1,
			GL_FALSE,
			glm::value_ptr(computeLightSpaceTrMatrix()));
		glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
		glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
		glClear(GL_DEPTH_BUFFER_BIT);
		renderObjects(depthMapShader, false);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	else {
		// final scene rendering pass (with shadows)


		depthMapShader.useShaderProgram();
		glUniformMatrix4fv(glGetUniformLocation(depthMapShader.shaderProgram, "lightSpaceTrMatrix"),
			1,
			GL_FALSE,
			glm::value_ptr(computeLightSpaceTrMatrix()));
		glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
		glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
		glClear(GL_DEPTH_BUFFER_BIT);
		renderObjects(depthMapShader, false);
		glCheckError();
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glCheckError();


		glViewport(0, 0, (float)myWindow.getWindowDimensions().width, (float)myWindow.getWindowDimensions().height);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		myBasicShader.useShaderProgram();

		view = myCamera.getViewMatrix();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

		lightRotation = glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f));
		glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightRotation)) * lightDir));

		//bind the shadow map
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, depthMapTexture);
		glUniform1i(glGetUniformLocation(myBasicShader.shaderProgram, "shadowMap"), 3);

		glUniformMatrix4fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightSpaceTrMatrix"),
			1,
			GL_FALSE,
			glm::value_ptr(computeLightSpaceTrMatrix()));

		renderObjects(myBasicShader, false);
		mySkyBox.Draw(skyboxShader, view, projection);
	}

}

void cleanup() {
	myWindow.Delete();
}

int main(int argc, const char* argv[]) {

	try {
		initOpenGLWindow();
	}
	catch (const std::exception& e) {
		std::cerr << e.what() << std::endl;
		return EXIT_FAILURE;
	}

	initOpenGLState();
	initModels();
	initShaders();
	initSkyBoxMat();
	initUniforms();
	initFBO();
	setWindowCallbacks();

	glCheckError();
	// application loop
	while (!glfwWindowShouldClose(myWindow.getWindow())) {
		processMovement();
		renderScene();

		glfwPollEvents();
		glfwSwapBuffers(myWindow.getWindow());

		glCheckError();
	}

	cleanup();

	return EXIT_SUCCESS;
}
