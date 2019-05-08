/** ***************************************************************************
 ** Centro Universitário SENAC                                               **
 ** Tecnologia em jogos digitais - 1º Semestre de 2019                       **
 ** Bruno Cavalcante de Souza Sanches                                        **
 **                                                                          **
 ** Projeto Integrador III - Projeto Final                                   **
 ** Arquivo: Into the Dwarfness                                              **
 **                                                                          **
 ** Matheus Vicente Rodrigues da Silva                                       **
 ** Nathan André da Silva                                                    **
 ** Raphael Oliveira Melo                                                    **
 **                                                                          **
 ** 30/05/2019                                                               **
 ******************************************************************************/
package intothedwarfness;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Window;

public class IntoTheDwarfness {

    public static void main(String[] args) throws IOException, InterruptedException {

        //Loading the images of the game
        ArrayList<BufferedImage> sprites = new ArrayList();
        for (int i = 1; i <= 19; i++) {
            sprites.add(ImageIO.read(new File("images/" + i + ".png")));
        }

        //Creating the window of the game
        Window screen = new Window(sprites);
        screen.initialize();
        screen.run();

    }
}
