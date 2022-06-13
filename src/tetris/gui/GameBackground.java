package tetris.gui;

import tetris.GamePanel;
import tetris.util.Assets;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBackground {
    private ArrayList<ImageIcon> images;
    private ImageIcon currentBackground;
    private float opacity;

    public GameBackground(){
        images = new ArrayList<>();
        this.opacity = 0.25f;
        loadAssets();
        randomBackground();
    }

    public void draw(Graphics2D g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g.drawImage(currentBackground.getImage(), 0, 0, currentBackground.getIconWidth(), currentBackground.getIconHeight(), null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void randomBackground(){
        int randomIdx = (int)(Math.random() * images.size());
        currentBackground = images.get(randomIdx);
    }

    public void setOpacity(float newOpacity){
        this.opacity = newOpacity;
    }


    private void loadAssets(){
        try{
            for(int imageId=0;; imageId++){
                ImageIcon image = new ImageIcon(Assets.BACKGROUND_PREFIX + imageId + ".jpg");
                if(image.getIconWidth() == -1) break;
                images.add(image);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
