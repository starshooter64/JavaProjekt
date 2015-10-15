import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.io.*;
import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL11.*;
import java.util.*;

public class DrawingWindow {
	private int width;
	private int height;
	private IGame game;

	private int tileWidth = 50;
	private int tileHeight = 50;
	private float zoom = 1;
	private float offsetX = -1.5f;
	private float offsetY = 0;

	private float transitionZoom = 0;
	private float transitionZoomSpeed = 0.01f;

	private float transitionX = 0;
	private float transitionXSpeed = 0.01f;

	private float transitionY = 0;
	private float transitionYSpeed = 0.01f;


	ArrayList<Texture> textures = new ArrayList<Texture>();

	ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
	ArrayList<Integer> justPressedKeys = new ArrayList<Integer>();
	ArrayList<Integer> justReleasedKeys = new ArrayList<Integer>();

	public DrawingWindow(IGame game){
		this(640, 480, game);
	}
	public DrawingWindow(int width, int height, IGame game){
		this(width, height, "Bla", game);
	}
	public DrawingWindow(int width, int height, String title, IGame game){
		this.width = width;
		this.height = height;
		this.game = game;

		// Init
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		glEnable(GL_TEXTURE_2D); 
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);     

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glViewport(0, 0, width, height);
		glMatrixMode(GL_MODELVIEW);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	public Boolean keyJustReleased(int key){
		return justReleasedKeys.contains(key);
	}
	public Boolean keyJustPressed(int key){
		return justPressedKeys.contains(key);
	}
	public Boolean keyPressed(int key){
		return pressedKeys.contains(key);
	}

	public void run(){
		while(shouldContinue()){
			game.update();

			clearScreen();
			game.draw();

			justPressedKeys.clear();
			justReleasedKeys.clear();
			while (Keyboard.next())
				(Keyboard.getEventKeyState() ? justPressedKeys : justReleasedKeys).add(Keyboard.getEventKey());
			
			for(Integer key : justPressedKeys)
				if(!pressedKeys.contains(key))
					pressedKeys.add(key);

			for(Integer key : justReleasedKeys)
				if(pressedKeys.contains(key)){
					pressedKeys.remove(pressedKeys.indexOf(key));
				}

			update();

			if(transitionZoom!=0)
				if(transitionZoom>0){
					transitionZoom -= transitionZoomSpeed;
					zoom += transitionZoomSpeed;
				}else
					transitionZoom = 0;
			if(transitionX!=0)
				if(transitionX>0){
					transitionX -= transitionXSpeed;
					offsetX += transitionXSpeed;
				}else
					transitionX = 0;
			if(transitionY!=0)
				if(transitionY>0){
					transitionY -= transitionYSpeed;
					offsetY += transitionYSpeed;
				}else
					transitionY = 0;
		}
		close();
		System.exit(0);
	}

	public void update(){
		Display.update();
		Display.sync(60/*FPS*/);
	}

	private float TileWidth(){
		return zoom * tileWidth;
	}

	private float TileHeight(){
		return zoom * tileHeight;
	}

	public int loadPngTexture(String path){
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("images/"+path+".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		textures.add(texture);
		return texture.getTextureID();
	}

	private void drawRect(float x, float y, float w, float h){
		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);
		glVertex2d(x, y);

		glTexCoord2f(1,0);
		glVertex2d(x+w, y);

		glTexCoord2f(1,1);
		glVertex2d(x+w, y+h);

		glTexCoord2f(0,1);
		glVertex2d(x, y+h);

		glEnd();
	}

	private void clearScreen(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	public void drawOnGrid(float x, float y, int textureId){
		glBindTexture(GL_TEXTURE_2D, textureId);

		drawRect(
				(offsetX + x) * TileWidth(),
				(offsetY + y) * TileHeight(),
				TileWidth(),
				TileHeight()
			);
	}

	public Boolean shouldContinue(){
		return !Display.isCloseRequested();
	}

	public void scroll(float X, float Y){
		scrollTo(offsetX+X, offsetY+Y);
	}
	public void scrollTo(float X, float Y){
		offsetX = X;
		offsetY = Y;
	}
	public void zoom(float level){
		zoomTo(level+zoom);
	}
	public void zoomTo(float level){
		zoom = level;
	}
	public void scrollSmooth(float X, float Y){
		transitionX += X;
		transitionY += Y;
	}
	public void scrollSmoothTo(float X, float Y){
		scroll(X - offsetX, Y - offsetX);
	}
	public void zoomSmooth(float relativeLevel){
		transitionZoom = relativeLevel;
	}
	public void zoomSmoothTo(float absoluteLevel){
		zoom(absoluteLevel - zoom);
	}

	public void close(){
		Display.destroy();
	}

	// public static void main(String[] args) {
		
	// }
}