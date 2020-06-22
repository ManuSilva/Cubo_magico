package Package;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Cubo_Game implements GLEventListener, KeyListener {

	private float rotateX, rotateY, rotateZ; 
	private float rotate_Z_1, rotate_Z_2, rotate_Z_3; 
	private float velocidade = 5;
	private GLU glu = new GLU();
	private int texture;

	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if (key == KeyEvent.VK_LEFT)
			rotateY -= velocidade;
		else if (key == KeyEvent.VK_RIGHT)
			rotateY += velocidade;
		else if (key == KeyEvent.VK_DOWN)
			rotateX += velocidade;
		else if (key == KeyEvent.VK_UP)
			rotateX -= velocidade;
		else if (key == KeyEvent.VK_PAGE_UP)
			rotateZ += velocidade;
		else if (key == KeyEvent.VK_PAGE_DOWN)
			rotateZ -= velocidade;
		else if (key == KeyEvent.VK_HOME)
			rotateX = rotateY = rotateZ  =
		 	rotate_Z_1 = rotate_Z_2 = rotate_Z_3 = 0;
		else if (key == KeyEvent.VK_NUMPAD1)
			rotate_Z_1 += velocidade;
		else if (key == KeyEvent.VK_NUMPAD2)
			rotate_Z_2 += velocidade;
		else if (key == KeyEvent.VK_NUMPAD3)
			rotate_Z_3 += velocidade;
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable arg0) {
		final GL2 gl = arg0.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -5.0f);

		// Rotação de camera
		 gl.glRotatef(rotateZ,0,0,1);
		 gl.glRotatef(rotateY,0,1,0);
		 gl.glRotatef(rotateX,1,0,0);

		 
		 float [] size = {1.0f, -2.0f, 1.0f};
		 
		
		  gl.glTranslatef(0f, 0f, 1.0f);
			 
			 for (int j = 0; j < 3 ; j++ ) { // Eixo Z
				 rotated_eixo_z(gl,j);
				 for (int i = 0; i < 3 ; i++ ) {// Eixo Y
					 for (int k = 0; k < 3 ; k++ ) { // Eixo X
						 desenha_quad(gl);
						 gl.glTranslatef(size[k], 0f, 0f);
					 }	
					 gl.glTranslatef(0f, size[i], 0f);
				 }
				 rotated_eixo_z_end(gl,j);
				 gl.glTranslatef(0f, 0f, -1.0f);
			 }


	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable arg0) {
		rotateX = 30;
		rotateY = 30;
		rotateZ = 0;
		rotate_Z_1 = 0;
		rotate_Z_2 = 0;
		rotate_Z_3 = 0;

		final GL2 gl = arg0.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		
	      gl.glEnable(GL2.GL_TEXTURE_2D);
	      try{
			
	         File im = new File("/Users/emman/Downloads/rubik_cube_white_texture.jpg");
	         Texture t = TextureIO.newTexture(im, true);
	         texture= t.getTextureObject(gl);
	          
	      }catch(IOException e){
	         e.printStackTrace();
	      }

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int x, int y, int width, int height) {
		final GL2 gl = arg0.getGL().getGL2();

		if (height <= 0)
			height = 1;

		final float h = (float) width / (float) height;
		
		gl.glMatrixMode(GL2.GL_PROJECTION); 
	    gl.glLoadIdentity();
		glu.gluPerspective(100, h, 0.1, 100);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();


	}

	public void desenha_quad(GL2 gl) {
		    
		gl.glPushMatrix();
		bloco(gl,1,0,0);        // Frente
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(180,0,1,0); // Posicionar face
		bloco(gl,0,1,1);        // Back
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,0,1,0); 
		bloco(gl,0,1,0);        // left 
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,0,1,0);
		bloco(gl,1,0,1);       // right 
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,1,0,0); 
		bloco(gl,0,0,1);        // top 
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0); 
		bloco(gl,1,1,0);        // bottom 
		gl.glPopMatrix();


	}
	
	private void bloco(GL2 gl, float r, float g, float b) {
		gl.glColor3f(r,g,b);         // Cor
		gl.glTranslatef(0,0,0.5f);    
		gl.glNormal3f(0,0,1);        
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

	    gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex2f(-0.5f,-0.5f);   
		
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex2f(0.5f,-0.5f);     
		
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex2f(0.5f,0.5f);      
		
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex2f(-0.5f,0.5f);   
		gl.glEnd();
	}
	
	private void rotated_eixo_z(GL2 gl, int i ) {
		
		 if(rotate_Z_1 != 0 && i == 0) {
			 gl.glPushMatrix();
			 gl.glRotatef(rotate_Z_1,0,0,1);
		}else if(rotate_Z_2 != 0 && i == 1) {
			 gl.glPushMatrix();
			 gl.glRotatef(rotate_Z_2,0,0,1);
		}else if(rotate_Z_3 != 0 && i == 2) {
			 gl.glPushMatrix();
			 gl.glRotatef(rotate_Z_3,0,0,1);
		}
		
		
	}
	
	private void rotated_eixo_z_end(GL2 gl, int i ) {
		
		 if(rotate_Z_1 != 0 && i == 0) {
			 gl.glPopMatrix();
		}else if(rotate_Z_2 != 0 && i == 1) {
			 gl.glPopMatrix();
		}else if(rotate_Z_3 != 0 && i == 2) {
			 gl.glPopMatrix();
		}
		
		
	}
	
	public static void main(String[] args) {

		// Getting the capabilities object of GL2 profile
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		// Cria o Canvas
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Cubo_Game cubo_game = new Cubo_Game();
		glcanvas.addGLEventListener(cubo_game);
		glcanvas.addKeyListener(cubo_game);
		glcanvas.setSize(800, 800);

		// Criar A tela (Swing)
		final JFrame frame = new JFrame("Cubo");

		// Adiciona o canvas na tela
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);

		animator.start();

	}

}
