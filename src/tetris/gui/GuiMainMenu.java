package tetris.gui;

import tetris.GamePanel;
import tetris.gui.widget.AnimationType;
import tetris.util.Assets;
import tetris.wrapper.GraphicsWrapper;
import tetris.controls.MouseInput;
import tetris.gui.widget.Button;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GuiMainMenu extends Gui {

    private ImageIcon topBar;
    private ImageIcon bottomBar;
    private ImageIcon logo;

    public GuiMainMenu(Gui parentScreen) {
        super(parentScreen);

        load_assets();

        ImageIcon exit_button = new ImageIcon(Assets.Button.EXIT_BUTTON);
        buttonList.add(new Button(-170,880, exit_button, (click)->{
            GamePanel.getGamePanel().exitGame();
        }, AnimationType.RIGHT));

        ImageIcon solo_button = new ImageIcon(Assets.Button.SOLO_BUTTON);
        buttonList.add(new Button(400, 400, solo_button, (click)->{

        }, AnimationType.LEFT));
        ImageIcon settings_button = new ImageIcon(Assets.Button.SETTINGS_BUTTON);
        buttonList.add(new Button(400, 600, settings_button, (click)->{
            GamePanel.getGamePanel().displayGui(new GuiMenuTransition(this, new GuiSettings(this)));
        }, AnimationType.LEFT));

        ImageIcon github_button = new ImageIcon(Assets.Button.GITHUB_BUTTON);
        buttonList.add(new Button(1800, 990, github_button, (click)->{
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/todaybrian/ics4u-assignment").toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, AnimationType.UP));


    }


    private void load_assets(){
        topBar = new ImageIcon(Assets.TOP_MAIN_MENU_FILE);
        bottomBar = new ImageIcon(Assets.BOTTOM_MAIN_MENU_FILE);
        logo = new ImageIcon(Assets.LOGO_FILE);
    }

    public void draw(GraphicsWrapper g){
        super.draw(g);

        //Top bar of Main Menu
        g.drawImage(topBar.getImage(), 0, 0, topBar.getIconWidth(), topBar.getIconHeight());

        //Bottom bar of Main Menu
        g.drawImage(bottomBar.getImage(), 0, GamePanel.INTERNAL_HEIGHT - bottomBar.getIconHeight(), bottomBar.getIconWidth(), bottomBar.getIconHeight());

        //Draw logo
        g.drawImage(logo.getImage(), 20, 100, 1.25*logo.getIconWidth(), 1.25*logo.getIconHeight());

//        g.drawString(String.valueOf(MouseInput.getLocation().getX()), 500, 500);
//        g.drawString(String.valueOf(MouseInput.getLocation().getY()), 550, 500);
//        g.drawString(String.valueOf(((MouseInput.getLocation().getX() > 600 && MouseInput.getLocation().getX() < 700) && (MouseInput.getLocation().getY() > 600 && MouseInput.getLocation().getY() < 700))), 150, 150);
    }
}
