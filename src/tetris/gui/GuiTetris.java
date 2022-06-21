/**
 * Author: Brian Yan, Aaron Zhang
 * Date: June 18, 2022
 *
 * Handles displaying the tetris game board and connecting Tetris and GamePanel (i.e. controls).
 */
package tetris.gui;

import tetris.GamePanel;
import tetris.controls.KeyboardInput;
import tetris.game.GameMode;
import tetris.game.Tetris;
import tetris.util.Assets;
import tetris.util.FrameTimer;
import tetris.util.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class GuiTetris extends Gui {
    // Timer for the black that fades out
    private FrameTimer blackfadeOutTimer;

    // Timer for the banner
    private FrameTimer bannerTimer;

    // Timer for the 3 2 1 countdown
    private FrameTimer countdownTimer;

    // Timer for the GO text that stays on the screen for a while after game has started
    private FrameTimer goTimer;

    // Timer for when player wants to resign (makes them hold they key down before they can resign)
    private FrameTimer resignTimer;

    // Timer for when player wants to restart (makes them hold they key down before they can restart)
    private FrameTimer restartTimer;

    // Holds the Tetris game board
    public Tetris tetris;

    // y offset and y velocity of the tetris board (used for animation)
    private int yOffset;
    private int yVelocity;

    //Timer for when the player has died (for animation)
    private FrameTimer diedTimer;

    //Current game mode
    public GameMode gameMode;

    // Booleans to check whether number has been played in countdown
    private int countdownLength;
    private boolean[] hasPlayedCountdown;
    private Image[] countDownImages;
    private File[] countDownSounds;

    //Boolean to check if key was pressed in the last frame to prevent multiple key presses
    private boolean held_hardDrop;
    private boolean held_rotateCW;
    private boolean held_rotateCCW;
    private boolean held_holdPiece;

    // Delay between auto-repeat soft drop
    private FrameTimer softDropTimer;

    // The initial long delay when moving left
    private FrameTimer moveLeftTimerDAS;
    // The shorter delay when moving left after init DAS delay has passed
    private FrameTimer moveLeftTimer;

    // The initial long delay when moving right
    private FrameTimer moveRightTimerDAS;
    // The shorter delay when moving right after init DAS delay has passed
    private FrameTimer moveRightTimer;

    // Length of animation when piece is hard dropped
    private FrameTimer hardDropAnimationTimer;


    private KeyboardInput keyboardInput;

    // All keybinds
    public int moveRightKey = KeyEvent.VK_RIGHT;
    public int moveLeftKey = KeyEvent.VK_LEFT;
    public int hardDropKey = KeyEvent.VK_SPACE;
    public int softDropKey = KeyEvent.VK_DOWN;
    public int rotateCWKey = KeyEvent.VK_UP;
    public int rotateCCWKey = KeyEvent.VK_Z;
    public int holdKey = KeyEvent.VK_C;
    public int restartKey = KeyEvent.VK_R;
    public int resignKey = KeyEvent.VK_ESCAPE;

    // The game banner for the current game mode
    private Image gameBanner;

    public GuiTetris(GameMode gameMode) {
        super();
        this.keyboardInput = instance.keyboardInput;

        this.gameMode = gameMode;
        gameBanner = gameMode.getBanner();

        // this object handles all game logic; only tetris.drawImage() and tetris.update() will cause objects inside game board to change.
        tetris = new Tetris(gameMode);

        //Change background and set opacity to be .5
        instance.getGameBackground().randomBackground();
        this.backgroundOpacity = 0.5f;

        //Initialize the timers
        //The below timers are used before the game starts
        //If timers are disabled, it means that they will be enabled later and haven't started yet

        blackfadeOutTimer = new FrameTimer(1);

        bannerTimer = new FrameTimer(5);
        bannerTimer.disable();

        countdownTimer = new FrameTimer(3.3);
        countdownTimer.disable();

        goTimer = new FrameTimer(1);
        goTimer.disable();

        //=========================================================

        //Timer for after the player has died (so the game falls down)
        diedTimer = new FrameTimer(1);
        diedTimer.disable();

        //Timer for when the player wants to resign
        resignTimer = new FrameTimer(1.5);
        resignTimer.disable();

        //Timer for when the player wants to restart
        restartTimer = new FrameTimer(1.5);
        restartTimer.disable();

        // Initialize the countdown variables
        countdownLength = 3; // length of countdown 3 seconds
        hasPlayedCountdown = new boolean[countdownLength]; // used to check if the number has been played so that it doesn't play again
        //images and sounds for the countdown
        countDownImages = new Image[]{Assets.Game.COUNTDOWN_1.get(), Assets.Game.COUNTDOWN_2.get(), Assets.Game.COUNTDOWN_3.get()};
        countDownSounds = new File[]{Assets.SFX.COUNTDOWN_1.get(), Assets.SFX.COUNTDOWN_2.get(), Assets.SFX.COUNTDOWN_3.get()};

        //Initialize the timers for keybinds
        softDropTimer = new FrameTimer(0.06);
        moveLeftTimerDAS = new FrameTimer(0.167);
        moveLeftTimer = new FrameTimer(0.033);
        hardDropAnimationTimer = new FrameTimer(0.1);
        moveRightTimerDAS = new FrameTimer(0.167);
        moveRightTimer = new FrameTimer(0.033);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        if (tetris.isDied()) { // This takes precedence over anything else since we need to transition to another screen if player is dead
            // diedTimer is used to hold things in place while dying animation plays
            if (diedTimer.isDisabled()) {
                diedTimer.reset();
            }

            BufferedImage board = (BufferedImage) tetris.drawImage();

            g.rotate(Math.toRadians(20) * diedTimer.getProgress());

            g.drawImage(board, GamePanel.INTERNAL_WIDTH / 2 - Tetris.BOARD_WIDTH / 2 + (int) (330 * diedTimer.getProgress()), GamePanel.INTERNAL_HEIGHT / 2 - Tetris.BOARD_HEIGHT / 2 + (int) (600 * diedTimer.getProgress()), null);
            if (diedTimer.isDone()) { // when diedTimer is done, transition to death screen
                instance.displayGui(new GuiMenuTransition(this, new GuiDied(gameMode)));
            }
        } else {
            //Draw tetris board
            g.drawImage(tetris.drawImage(), GamePanel.INTERNAL_WIDTH / 2 - Tetris.BOARD_WIDTH / 2, GamePanel.INTERNAL_HEIGHT / 2 - Tetris.BOARD_HEIGHT / 2 + yOffset - (int) (1400 * (1 - blackfadeOutTimer.getProgress())), Tetris.BOARD_WIDTH, Tetris.BOARD_HEIGHT, null);

            //The following below are the intro animations in order.
            if (!blackfadeOutTimer.isDone()) {
                drawBlackFadeOut(g);
            } else if (!bannerTimer.isDone()) {
                drawBanner(g);
            } else if (!countdownTimer.isDone() && !countdownTimer.isDisabled()) {
                drawCountDown(g);
            }

            if (!goTimer.isDisabled() && !goTimer.isDone()) {
                drawGo(g);
            }
        }

        if (!resignTimer.isDisabled() || !restartTimer.isDisabled()) {
            drawResignRestart(g);
        }
    }

    //Draws the black fade out when the game starts
    private void drawBlackFadeOut(Graphics2D g) {
        //Fade out the black background by reducing opacity and drawing a black rectangle over the board
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (1 - blackfadeOutTimer.getProgress())));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.INTERNAL_WIDTH, GamePanel.INTERNAL_HEIGHT);

        //Reset opacity
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }


    //Draws the banner after black fade out
    public void drawBanner(Graphics2D g) {
        float opacityProgress; // The progress of the opacity according to easing functions
        float opacity; // The opacity of the banner
        //easeOutQuint https://easings.net/#easeOutQuint to make the banner fade in and out

        //The coordinates of the banner (center of the screen)
        int xPos = GamePanel.INTERNAL_WIDTH / 2 - gameBanner.getWidth(null) / 2;
        int yPos = GamePanel.INTERNAL_HEIGHT / 2 - gameBanner.getHeight(null) / 2;

        if (bannerTimer.getProgress() <= 0.9) { //90% of the time, the banner will fade in
            opacityProgress = (float) (1 - Math.pow(1 - bannerTimer.getProgress() / 0.9, 5));
        } else { // The rest percent of the time, the banner will fade out
            opacityProgress = (float) -(1 - Math.pow(1 - (bannerTimer.getProgress() - 0.9) / 0.1, 5));
        }
        //Set the opacity of the banner. Clamp it to 0 and 1 to avoid errors
        opacity = Util.clamp(0.5f + 0.5f * opacityProgress, 0, 1);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        //Draw the banner
        g.drawImage(gameBanner, xPos, yPos, null);

        // reset the opacity
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    //Draws the countdown after the banner
    public void drawCountDown(Graphics2D g) {
        long bufferTime = (long) ((1e9) * (countdownTimer.getLength() - 3.0f)); //The time before the countdown starts
        float opacity; // The opacity of the countdown

        int xPos, yPos; //The coordinates of the countdown

        Image countDown = null; //The image  of the countdown (3, 2, or 1)
        long timeElapsedFromSecond = 0; //The time elapsed since the last second

        for (int i = 0; i < countdownLength; i++) {
            if (countdownTimer.timeElapsed() - bufferTime > (3 - i - 1) * 1e9) {
                timeElapsedFromSecond = (countdownTimer.timeElapsed() - bufferTime) - (3 - i - 1) * (long) 1e9;
                countDown = countDownImages[i];

                //Make sure sound is played only once
                if (!hasPlayedCountdown[i]) {
                    sfxPlayer.play(countDownSounds[i]);
                    hasPlayedCountdown[i] = true;
                }
                break; //Avoid other numbers from being drawn
            }
        }
        //If a number is being displayed, draw it fading out
        if (countDown != null) {
            //Set opacity
            opacity = Util.clamp(1 - 1 * (float) timeElapsedFromSecond / (float) 1e9, 0, 1);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

            //Draw the countdown (centered on screen)
            xPos = GamePanel.INTERNAL_WIDTH / 2 - countDown.getWidth(null) / 2;
            yPos = GamePanel.INTERNAL_HEIGHT / 2 - countDown.getHeight(null) / 2;

            g.drawImage(countDown, xPos, yPos, null);

            //Reset opacity
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    //Draw the "GO" text that appears after the countdown.
    //The text will fade out linearly
    private void drawGo(Graphics2D g) {
        float opacity = Util.clamp((float) (1 - 1 * goTimer.getProgress()), 0, 1); //The opacity of the "GO" text
        Image goImage = Assets.Game.GO.get(); //The image of the "GO" text

        //Center the text
        //The x and y position of the "GO" text
        int xPos = GamePanel.INTERNAL_WIDTH / 2 - goImage.getWidth(null) / 2;
        int yPos = GamePanel.INTERNAL_HEIGHT / 2 - goImage.getHeight(null) / 2;

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)); //Set the opacity

        //Draw the "GO" text
        g.drawImage(Assets.Game.GO.get(), xPos, yPos, null);

        //Reset opacity
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

    }

    //Draws the "Resign" and "Restart" rectangles on bottom of screen
    private void drawResignRestart(Graphics2D g) {
        FontMetrics fm; //The font metrics of the font used
        String text = ""; //The text to be displayed
        double progress = 0; //The progress of the timer  (0-1)

        int yPosRectangle; // Y coordinate of the top of the rectangle
        // x and y coordinates of the text
        int xPosText, yPosText;

        g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));

        //Draw whichever timer is active or the one with the longest progress
        if (!resignTimer.isDisabled() && restartTimer.isDisabled() || (!resignTimer.isDisabled() && !restartTimer.isDisabled() && resignTimer.getProgress() > restartTimer.getProgress())) {
            g.setColor(Color.RED);
            text = "KEEP HOLDING ESC TO FORFEIT";
            progress = resignTimer.getProgress();
        } else {
            g.setColor(Color.ORANGE);
            text = "KEEP HOLDING R TO RESTART";
            progress = restartTimer.getProgress();
        }
        //Decreases as progress increases to make rectangle grow upwards
        yPosRectangle = (int) (GamePanel.INTERNAL_HEIGHT - 150 * progress - 50);

        g.fillRect(0, yPosRectangle, GamePanel.INTERNAL_WIDTH, 300);
        g.setColor(Color.WHITE);

        fm = g.getFontMetrics();

        xPosText = GamePanel.INTERNAL_WIDTH / 2 - fm.stringWidth(text) / 2;
        yPosText = (int) (GamePanel.INTERNAL_HEIGHT - 150 * progress / 2 - 25 + fm.getHeight() / 2);
        g.drawString(text, xPosText, yPosText);
    }

    //Update method for handling keyboard, tetris updates, and timers
    @Override
    public void update() { // This is called every time game physics needs to update
        super.update();
        if (tetris.isObjectiveCompleted()) { // If game completion requirements are fulfilled, immediately move to the results screen.
            instance.displayGui(new GuiMenuTransition(this, new GuiResults(gameMode, tetris.getFinalScore())));
        }

/*        The following if/else structures work as follows:
            When a timer is not yet done, prevent anything below from updating.
            When that timer is done, start the next one.
            Only when all timers are done will the game physics update at tetris.update().
            At this point, all timers will be done and not disabled, preventing the pre-game timers from resetting.*/
        if (!blackfadeOutTimer.isDone()) { //black fade out
            return;
        } else if (bannerTimer.isDisabled()) { // Timer for game banner.
            bannerTimer.reset(); // Enable the timer.
            musicPlayer.stopMusic();
            sfxPlayer.play(Assets.SFX.START_SOLO_GAME.get());
        }

        if (!bannerTimer.isDone()) { // If the game banner is not done, prevent the game from updating.
            return;
        } else if (countdownTimer.isDisabled()) { // Timer for 3-2-1 countdown before game begins
            countdownTimer.reset(); // Enable the countdown timer.
        }

        if (!countdownTimer.isDone()) { // If the 3-2-1 countdown is not done, prevent the game from updating.
            return;
        } else if (goTimer.isDisabled()) { //Go timer (displays "GO"). Unlike the other timers, this one is stop the game from updating.
            goTimer.reset(); // Enable the timer
            sfxPlayer.play(Assets.SFX.GO.get());
            if (tetris.getGameMode() != GameMode.BLITZ) { // Change the BGM to fit the game mode. "VIRTUAL_LIGHT" fits the stress of Blitz mode
                musicPlayer.play(Assets.Music.VREMYA.get());
                musicPlayer.setLoop(true);
            } else {
                musicPlayer.play(Assets.Music.VIRTUAL_LIGHT.get());
                musicPlayer.setLoop(true);
            }
        }

        tetris.update(); // This updates the tetris game physics.

        handleKeyboard();

        // makes game board animate based on current velocity, prevent it from going too far
        yOffset += yVelocity;

        //If it has gone beyond the limit
        if (yOffset > 4) {
            yVelocity = -1; //Set the velocity to opposite direction
            yOffset = 4; //Set it to the limit
        } else if (yOffset < 0) { //If it has gone up beyond the limit, reset it to 0 and stop the animation
            yVelocity = 0;
            yOffset = 0;
        }

        //If a piece was hard dropped recently, set the y velocity to be downwards slowly like a bounce
        if (!hardDropAnimationTimer.isDone() && !hardDropAnimationTimer.isDisabled()) {
            yVelocity = 1;
        } else { // Once timer is over, disable the timer and let the board bounce back upwards
            hardDropAnimationTimer.disable();
        }

    }

    //Handles keyboard input
    private void handleKeyboard() {
        // This timer only stores how long escape was pressed, resetting when it is pressed and disabling when it is released.
        // The game will only accept the resignation if it is pressed continuously for some time.
        // This ensures that an errant press of the escape key does not cause an accidental resign.
        if (keyboardInput.isKeyPressed(resignKey) && resignTimer.isDisabled()) {
            resignTimer.reset();
        } else if (!keyboardInput.isKeyPressed(resignKey)) {
            resignTimer.disable();
        } else if (resignTimer.isDone()) { // Resignation takes the player back to the main menu.
            instance.displayGui(new GuiMenuTransition(this, new GuiMainMenu()));
        }

        if (keyboardInput.isKeyPressed(restartKey) && restartTimer.isDisabled()) {
            restartTimer.reset();
        } else if (!keyboardInput.isKeyPressed(restartKey)) {
            restartTimer.disable();
        } else if (restartTimer.isDone()) {
            instance.displayGui(new GuiTetris(gameMode));
        }

        // "soft dropping" is rate limited to prevent a short press from bringing the piece all the way down
        if (softDropTimer.isDone() && keyboardInput.isKeyPressed(softDropKey)) {
            softDropTimer.reset();
            tetris.dropPiece();
        }
        // "hard dropping" is disabled if the space bar is held to prevent multiple pieces from being hard dropped
        //An animation will be played if the piece is hard dropped.
        if (keyboardInput.isKeyPressed(hardDropKey) && !held_hardDrop) {
            tetris.hardDrop();
            hardDropAnimationTimer.reset();
        }

        // moving left and right is controlled by https://tetris.fandom.com/wiki/DAS to avoid operating system quirks
        // In the website, you can see that the initial delay is longer than the subsequent delays.
        if (keyboardInput.isKeyPressed(moveLeftKey)) {
            if (moveLeftTimerDAS.isDisabled()) { //Longer initial timer
                tetris.moveLeft();
                moveLeftTimerDAS.reset();
                moveLeftTimer.reset();
            } else if (moveLeftTimerDAS.isDone() && moveLeftTimer.isDone()) { //Shorter DAS timer
                tetris.moveLeft();
                moveLeftTimer.reset();
            }
        } else {
            // The key isn't held anymore, so the initial long delay applies again next time the key is pressed
            moveLeftTimerDAS.disable();
        }

        if (keyboardInput.isKeyPressed(moveRightKey)) {
            if (moveRightTimerDAS.isDisabled()) { //Longer initial timer
                tetris.moveRight();
                moveRightTimerDAS.reset();
                moveRightTimer.reset();
            } else if (moveRightTimerDAS.isDone() && moveRightTimer.isDone()) { //Shorter DAS timer
                tetris.moveRight();
                moveRightTimer.reset();
            }
        } else {
            // The key isn't held anymore, so the initial long delay applies again next time the key is pressed
            moveRightTimerDAS.disable();
        }

        //Once a rotation has occurred, the person must lift the key to rotate again.
        //Done in if else to avoid conflicts with both keys being pressed at the same time.
        if (keyboardInput.isKeyPressed(rotateCWKey) && !held_rotateCW) {
            tetris.rotateCW();
        } else if (keyboardInput.isKeyPressed(rotateCCWKey) && !held_rotateCCW) {
            tetris.rotateCCW();
        }

        //Prevent repeating the same key press.
        if (keyboardInput.isKeyPressed(holdKey) && !held_holdPiece) {
            tetris.holdPiece();
        }

        // Set the held keys to true so that the key functions is not called again until the key is released.
        held_hardDrop = keyboardInput.isKeyPressed(hardDropKey);
        held_rotateCW = keyboardInput.isKeyPressed(rotateCWKey);
        held_rotateCCW = keyboardInput.isKeyPressed(rotateCCWKey);
        held_holdPiece = keyboardInput.isKeyPressed(holdKey);
    }

}
