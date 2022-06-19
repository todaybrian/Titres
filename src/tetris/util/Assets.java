/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Stores and loads nearly all the assets used in the game.
 * The game backgrounds are loaded in GameBackground.java.
 *
 * The Assets are split into the following categories:
 * - Fonts: used for text
 * - Gui: All the component assets
 * - Button: All the button assets
 * - Game: All the tetris assets
 * - Music: All the music assets
 * - SFX: Sound effects
 */
package tetris.util;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Assets {
    //Prefix of the game background assets
    public static final String BACKGROUND_PREFIX = "src/assets/backgrounds/";

    //All the fonts used in the game
    public enum Fonts {
        KDAM_FONT("KdamThmorPro-Regular.ttf");
        private Font font; //Store the font object

        //Constructor to load the font from the file
        Fonts(String file) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(Paths.get("src/assets/fonts/" + file)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Get the font object
        public Font get() {
            return font;
        }
    }

    //All the Gui components (buttons excluded) used in the game
    public enum Gui {
        LOGO("logo.png"),

        WELCOME_SCREEN("welcome_screen.gif"),

        TOP_MAIN_MENU("top_main_menu.png"),
        BOTTOM_MAIN_MENU("bottom_main_menu.png"),

        TOP_SETTINGS("top_settings.png"),
        BOTTOM_SETTINGS("bottom_settings.png"),

        TOP_SOLO("top_solo.png"),
        BOTTOM_SOLO("bottom_solo.png"),

        TOP_40("top_40.png"),
        BOTTOM_40("bottom_40.png"),

        TOP_BLITZ("top_blitz.png"),
        BOTTOM_BLITZ("bottom_blitz.png");

        private final Image image; //Store the image object

        //Constructor to load the image from the file
        Gui(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon("src/assets/gui/" + s);
                image = imageIcon.getImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //Get the image object
        public Image get() {
            return image;
        }
    }

    //All the button components used in the game
    public enum Button{
        EXIT_BUTTON("exit_button.png"),
        SOLO_BUTTON("solo_button.png"),
        BACK_BUTTON("back_button.png"),

        FOURTY_LINES_BUTTON("40_lines_button.png"),
        BLITZ_BUTTON("blitz_button.png"),

        SETTINGS_BUTTON("settings_button.png"),
        GITHUB_BUTTON("github_button.png"),

        SLIDER("slider.png"),

        START_40_BUTTON("start_40.gif"),
        START_BLITZ_BUTTON("start_blitz.gif"),

        RETRY_BUTTON("retry_button.png"),

        BACK_TO_TITLE_BUTTON("back_to_title_button.png");

        private final Image image; //Store the image object

        //Constructor to load the image from the file
        Button(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon("src/assets/button/"+s);
                image = imageIcon.getImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //Get the image object
        public Image get() {
            return image;
        }
    }

    //All the tetris components used in the game
    public enum Game{
        TETRIS_GRID ("tetris_grid.png"),
        PIECES("pieces.png"),
        FORTY_BANNER("40_banner.png"),
        BLITZ_BANNER("blitz_banner.png"),
        COUNTDOWN_1("countdown_one.png"),
        COUNTDOWN_2("countdown_two.png"),
        COUNTDOWN_3("countdown_three.png"),
        GO("countdown_go.png");

        private final Image image; //Store the image object

        //Constructor to load the image from the file
        Game(String s) {
            try {
                ImageIcon imageIcon = new ImageIcon("src/assets/game/" +s);
                image = imageIcon.getImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //Get the image object
        public Image get() {
            return image;
        }
    }

    //All the music assets used in the game
    public enum Music {
        NIGHT_SNOW("NightSnow.wav"),
        VREMYA("Vremya.wav"),
        VIRTUAL_LIGHT("VirtualLight.wav");

        private final File file; //Store the file object

        //Constructor to load the music from the file
        Music(String s) {
            file = new File("src/assets/music/" + s);
        }

        //Get the file object
        public File get() {
            return file;
        }
    }

    //All the sound effects used in the game
    public enum SFX {
        CLICK_1("sfx_click_lvl_1.wav"),
        CLICK_2("sfx_click_lvl_2.wav"),
        CLICK_START("click_start.wav"),
        HOVER("sfx_hover.wav"),

        CLICK_BACK("sfx_click_back.wav"),

        START_SOLO_GAME("start_solo_game.wav"),

        COUNTDOWN_3("sfx_countdown_3.wav"),
        COUNTDOWN_2("sfx_countdown_2.wav"),
        COUNTDOWN_1("sfx_countdown_1.wav"),
        GO("sfx_go.wav"),

        //This is used to unlag the music/sfx player
        SILENCE("silence.wav");

        private final File file; //Store the file object

        //Constructor to load the sound effect from the file
        SFX(String s) {
            file = new File("src/assets/sfx/" +s);
        }

        //Get the file object
        public File get() {
            return file;
        }
    }
}
