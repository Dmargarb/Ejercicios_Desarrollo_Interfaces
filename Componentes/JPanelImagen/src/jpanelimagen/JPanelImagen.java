package jpanelimagen;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class JPanelImagen extends JPanel implements Serializable {

    private ImagenFondo imagenFondo;
    private boolean ratonPresionado = false;
    private Point puntoPresion;
    private ArrastreListener arrastreListener;

    public JPanelImagen() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (ratonPresionado) {
                    Point posicionActual = e.getPoint();
                    if (Math.abs(puntoPresion.x + posicionActual.x) > 50) {
                        if (arrastreListener != null) {
                            arrastreListener.arrastre();
                        }
                    }
                }
                ratonPresionado = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ratonPresionado = true;
                puntoPresion = e.getPoint();
            }
        });
    }

    public void addArrastreListener(ArrastreListener arrastreListener) {
        this.arrastreListener = arrastreListener;
    }

    public void removeArrastreListener(ArrastreListener arrastreListener) {
        this.arrastreListener = null;
    }

    public ImagenFondo getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(ImagenFondo imagenFondo) {
        this.imagenFondo = imagenFondo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null && imagenFondo.getRutaImagen() != null && imagenFondo.getRutaImagen().exists()) {
            ImageIcon imageIcon = new ImageIcon(imagenFondo.getRutaImagen().getAbsolutePath());
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, imagenFondo.getOpacidad()));
            g.drawImage(imageIcon.getImage(), 0, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }

}
