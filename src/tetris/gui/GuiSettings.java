/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 20, 2022
 *
 * This is the GUI menu for the settings. One can change the music volume, sfx volume, and render FPS here.
 * Using information from the sliders, different methods are called to change the variables.
 */
package tetris.gui;

import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.AnimationType;
import tetris.gui.widget.Button;
import tetris.gui.widget.Slider;
import tetris.util.Assets;

import java.awt.*;

public class GuiSettings extends Gui {
    //Holds the music slider
    private final Slider musicSlider;

    //Holds the sfx slider
    private final Slider sfxSlider;

    //Holds the fps slider
    private final Slider fpsSlider;

    public GuiSettings() {
        super();
        //Set the top and bottom bar
        topBar = Assets.Gui.TOP_SETTINGS.get();
        bottomBar = Assets.Gui.BOTTOM_SETTINGS.get();

        Image sliderImage = Assets.Button.SLIDER.get(); //This is the image of the slider button on the slider
        Image back_button = Assets.Button.BACK_BUTTON.get();

        //Back button (Positioned on top left)
        buttonList.add(new Button(-170, 120, back_button, (click)->{

            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu())); //Transition to main menu
            instance.getSFXPlayer().play(Assets.SFX.CLICK_BACK.get()); //Play click back sound

        }, AnimationType.LEFT));


        //Music slider
        musicSlider = new Slider(320,500, 670, sliderImage,  (onChange)->{
            //The value shown by the screen represents the percentage of the volume.
            //We need to divide by 100 to convert it to a percent
            instance.getMusicPlayer().changeVolume((Math.round(onChange.getValue())/100.0));

        },0, 100, (instance.getMusicPlayer().getVolume()));

        //Sfx slider
        sfxSlider = new Slider(320,200, 670, sliderImage, (onChange)->{
            //The value shown by the screen represents the percentage of the volume.
            //We need to divide by 100 to convert it to a percent
            instance.getSFXPlayer().changeVolume((Math.round(onChange.getValue())/100.0));

        },0, 100, (instance.getSFXPlayer().getVolume()));

        //Music slider
        fpsSlider = new Slider(320,800, 670, sliderImage,  (onChange)->{

            instance.setRenderFPS((int)Math.round(onChange.getValue()));

        },10, 260, instance.getMaxRenderFPS()); // Minimum FPS is 10, maximum is 260.

        //Add the above sliders into the list of buttons to be rendered
        buttonList.add(musicSlider);
        buttonList.add(sfxSlider);
        buttonList.add(fpsSlider);

        //Settings component
        AnimatedRectangle settings = new AnimatedRectangle((g, offsetX)->{
            //offsetX is the horizontal offset variable which is used to create a transition effect for animation
            //It must be added to the x coordinate of relevant components

            //Set the color of the background of the results rectangle
            g.setColor(new Color(133, 64, 160));

            //Draw the background of the results rectangle
            g.fillRect(300 + offsetX, 200, 1700, 800);

            //Set font size and color which will draw the value of the settings
            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(Color.WHITE);

            //Draw the values that are represented by the slider
            g.drawString("Music: " + (int)Math.round(musicSlider.getValue()),1050+offsetX, 570);

            g.drawString("SFX: " + (int)Math.round(sfxSlider.getValue()),1050+offsetX,270);

            g.drawString("FPS: " + (int)Math.round(fpsSlider.getValue()),1050+offsetX,870);
        }, AnimationType.RIGHT); //Component is on the right of the screen

        componentList.add(settings); //Add settings to the list of components that will be drawn
    }

}
