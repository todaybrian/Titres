/**
 * Hack to make the transition between menus look smooth.
 */
package tetris.gui;

import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.Button;
import tetris.util.FrameTimer;

import java.awt.*;
import java.util.ArrayList;

public class GuiMenuTransition extends Gui{
    private static final double DEFAULT_ANIMATION_LENGTH = 0.19;

    private FrameTimer timer;

    private final Gui nextScreen;
    private boolean blackIn;

    public GuiMenuTransition(Gui parentScreen, Gui nextScreen) {
        this(parentScreen, nextScreen, DEFAULT_ANIMATION_LENGTH, false);
    }

    public GuiMenuTransition(Gui parentScreen, Gui nextScreen, double animationLength, boolean blackIn) {
        super();
        this.nextScreen = nextScreen;
        this.blackIn = blackIn;

        //Use the top bar and the bottom bar of the next screen to be the transition screen's top bar and bottom bar
        topBar = nextScreen.topBar;
        bottomBar = nextScreen.bottomBar;

        //Get components from the previous and the next screen
        ArrayList<AnimatedRectangle> parentComponentList = parentScreen.getComponentList();
        ArrayList<AnimatedRectangle> nextComponentList = nextScreen.getComponentList();

        //Get buttons from the previous and the next screen
        ArrayList<Button> parentAssetList = parentScreen.getButtonList();
        ArrayList<Button> nextAssetList = nextScreen.getButtonList();

        //Animate all components from the previous screen
        for(AnimatedRectangle component : parentComponentList){
            //The component is currently on the screen, so it needs to be animated off
            component.initAnimate(component.animationType.getTransitionXOffset(), component.animationType.getTransitionYOffset(), 0, animationLength);
            //The component is currently in transition
            component.setInTransition(true);

            //Add the component to the list of components that need to be displayed
            componentList.add(component);
        }

        //Animate components from the next screen
        for(AnimatedRectangle component : nextComponentList){
            //The component should be set off the screen
            component.setOffsets(component.animationType.getTransitionXOffset(), component.animationType.getTransitionYOffset(), 0);

            //so that it can be animated onto the screen
            component.initAnimate(0, 0, 1, animationLength);

            //The component is currently in transition
            component.setInTransition(true);

            //Add the component to the list of components that need to be displayed
            componentList.add(component);
        }

        //Animate all buttons from the previous screen
        for(Button button : parentAssetList){
            //The button is currently on the screen, so it needs to be animated off
            button.initAnimate(button.animationType.getTransitionXOffset(), button.animationType.getTransitionYOffset(), 0, animationLength);

            //The button is currently in transition
            button.setInTransition(true);

            //Add the button to the list of buttons that need to be displayed
            buttonList.add(button);
        }

        //Animate buttons from the next screen
        for(Button button : nextAssetList){
            //The button should be set off the screen
            button.setOffsets(button.animationType.getTransitionXOffset(), button.animationType.getTransitionYOffset(), 0);

            //so that it can be animated onto the screen
            button.initAnimate(0, 0, 1, animationLength);

            //The button is currently in transition
            button.setInTransition(true);

            //Add the button to the list of buttons that need to be displayed
            buttonList.add(button);
        }

        timer = new FrameTimer(animationLength);
    }

    public void draw(Graphics2D g){
        //If the animation time has elapsed, switch to the next screen
        if(timer.isDone()) {
            //Reset all buttons and components to original positions and opacity
            for(Button button : buttonList){
                button.reset();
            }
            for(AnimatedRectangle component : componentList){
                component.reset();
            }

            //Switch to the next screen
            instance.displayGui(nextScreen);

            //Draw the next screen for this frame to avoid a white screen
            nextScreen.draw(g);
            return;
        }
        super.draw(g);
        if(blackIn){
            g.setColor(new Color(0, 0, 0, (int) (255 * (timer.getProgress()))));
            g.fillRect(0, 0, instance.getWidth(), instance.getHeight());
        }
    }
}
