/**
 * Hack to make the transition between menus look smooth.
 */
package tetris.gui;

import tetris.gui.widget.AnimatedRectangle;
import tetris.gui.widget.Button;
import tetris.wrapper.GraphicsWrapper;

import javax.swing.*;
import java.util.ArrayList;

public class GuiMenuTransition extends Gui{
    private static final long ANIMATION_TRANSITION = 200000000;

    private Gui nextScreen;

    private long currentTime;

    public GuiMenuTransition(Gui parentScreen, Gui nextScreen){
        super(parentScreen);
        this.nextScreen = nextScreen;
        nextScreen.loadAssets();
        topBar = nextScreen.topBar;
        bottomBar = nextScreen.bottomBar;

        ArrayList<AnimatedRectangle> parentComponentList = parentScreen.getComponentList();
        ArrayList<AnimatedRectangle> nextComponentList = nextScreen.getComponentList();

        ArrayList<Button> parentAssetList = parentScreen.getButtonList();
        ArrayList<Button> nextAssetList = nextScreen.getButtonList();

        for(AnimatedRectangle component : parentComponentList){
            component.initAnimate(component.animationType.getTransitionXOffset(), component.animationType.getTransitionYOffset(), 0, GuiMenuTransition.ANIMATION_TRANSITION);
            componentList.add(component);
        }
        for(AnimatedRectangle component : nextComponentList){
            component.opacity = 0;
            component.xOffsetCurrent = component.animationType.getTransitionXOffset();
            component.yOffsetCurrent = component.animationType.getTransitionYOffset();
            component.initAnimate(0, 0, 1, GuiMenuTransition.ANIMATION_TRANSITION);
            componentList.add(component);
        }

        for(Button button : parentAssetList){
            button.initAnimate(button.animationType.getTransitionXOffset(), button.animationType.getTransitionYOffset(), 0, GuiMenuTransition.ANIMATION_TRANSITION);
            button.inTransition = true;

            buttonList.add(button);
        }
        for(Button button : nextAssetList){
            button.opacity = 0;
            button.xOffsetCurrent = button.animationType.getTransitionXOffset();
            button.yOffsetCurrent = button.animationType.getTransitionYOffset();
            button.initAnimate(0, 0, 1, GuiMenuTransition.ANIMATION_TRANSITION);
            button.inTransition = true;
            buttonList.add(button);
        }
        currentTime = System.nanoTime();
    }

    public void draw(GraphicsWrapper g){
        if(System.nanoTime() - currentTime > GuiMenuTransition.ANIMATION_TRANSITION) {
            for(Button button : buttonList){
                button.reset();
            }
            for(AnimatedRectangle component : componentList){
                component.reset();
            }
            instance.displayGui(nextScreen);
            nextScreen.draw(g);
            return;
        }
        super.draw(g);
    }
}
