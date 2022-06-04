package tetris.gui;

import tetris.GamePanel;
import tetris.util.Assets;
import tetris.wrapper.GraphicsWrapper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBackground {
    private ArrayList<ImageIcon> images;
    private ImageIcon currentBackground;

    public GameBackground(){
        images = new ArrayList<>();
        loadAssets();
        randomBackground();
    }

    public void draw(GraphicsWrapper g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
        g.drawImage(currentBackground.getImage(), 0, 0, currentBackground.getIconWidth(), currentBackground.getIconHeight());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void randomBackground(){
        int randomIdx = (int)(Math.random() * images.size());
        currentBackground = images.get(randomIdx);
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
            return;
        }
    }
}
