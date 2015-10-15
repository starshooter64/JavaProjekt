import org.lwjgl.input.Keyboard; // Permet d'utiliser Keyboard.KEY_Z

public class demoDisplay implements IGame {
	int maTextureFantastique;
	DrawingWindow window;

	public static void main(String[] args) {
		new demoDisplay();
	}

	public demoDisplay(){
		// Ouverture de la fenêtre
		window = new DrawingWindow(this);
		// Charge une texture depuis le dossier "images"
		maTextureFantastique = window.loadPngTexture("moche");
		// Lance les routines d'affichages (update & draw)
		window.run();
	}

	// Le methode est appellée 60 fois par seconde, pour la mise à jour des données
	public void update(){
		// Keyboard.KEY_quelquechose est une constante entière (int) qui caractérise la touche quelquechose
		// Liste des constantes pour le clavier : http://legacy.lwjgl.org/javadoc/org/lwjgl/input/Keyboard.html

		// Voit si une touche est enfoncée
		if(window.keyPressed(Keyboard.KEY_UP)){
			window.scroll(0.1f, 0);// Fait un scroll (vertical, horizontal)
		}
		// Voit si une touche vient d'être enfoncée
		if(window.keyJustPressed(Keyboard.KEY_Z)){
			window.zoom(0.4f);// Zoom d'un coup
		}
		// Voit si une touche vient d'être relâchée
		if(window.keyJustReleased(Keyboard.KEY_D)){
			window.zoomSmooth(-0.4f);// Dézoom avec transition
		}
		/* Autre méthodes 
			scroll(float X, float Y)			scroll "d'un coup" relativement au scroll actuel
			scrollTo(float X, float Y)			scroll "d'un coup" en position absolue
			zoom(float level)					zoom "d'un coup" relativement
			zoomTo(float level)					zoom "d'un coup" abs

			## Les méthodes suivantes font pareil qu'au dessus, mais avec une animation
				scrollSmooth(float X, float Y)		
				scrollSmoothTo(float X, float Y)	
				zoomSmooth(float relativeLevel)		
				zoomSmoothTo(float absoluteLevel)	
		*/
	}

	// Le methode est appellée 60 fois par seconde, pour la mise à jour de l'affichage
	public void draw(){
		// Dessine sur une case du quadrillage
		window.drawOnGrid(1, 1, maTextureFantastique);// window.drawOnGrid(x, y, identifiant de la texture)
	}
}