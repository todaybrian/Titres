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

    private boolean held_hardDrop;
    private boolean held_rotateCW;
    private boolean held_rotateCCW;
    private boolean held_holdPiece;

    private FrameTimer downTimer;

    private FrameTimer moveLeftTimerDAS;
    private FrameTimer moveLeftTimer;

    private FrameTimer hardDropAnimationTimer;

    private FrameTimer moveRightTimerDAS;
    private FrameTimer moveRightTimer;
    
    private KeyboardInput keyboardInput;

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

        instance.getGameBackground().randomBackground();

        this.backgroundOpacity = 0.5f;

        blackfadeOutTimer = new FrameTimer(1);

        bannerTimer = new FrameTimer(5);
        bannerTimer.disable();

        diedTimer = new FrameTimer(1);
        diedTimer.disable();

        countdownTimer = new FrameTimer(3.3);
        countdownTimer.disable();

        goTimer = new FrameTimer(1);
        goTimer.disable();

        resignTimer = new FrameTimer(1.5);
        resignTimer.disable();

        restartTimer = new FrameTimer(1.5);
        restartTimer.disable();

        // Initialize the hasPlayedCountdown array (used for the 3 2 1 countdown).
        countdownLength = 3;
        hasPlayedCountdown = new boolean[countdownLength];
        countDownImages = new Image[]{Assets.Game.COUNTDOWN_1.get(), Assets.Game.COUNTDOWN_2.get(), Assets.Game.COUNTDOWN_3.get()};
        countDownSounds = new File[]{Assets.SFX.COUNTDOWN_1.get(), Assets.SFX.COUNTDOWN_2.get(), Assets.SFX.COUNTDOWN_3.get()};

        downTimer = new FrameTimer(0.06);
        moveLeftTimerDAS = new FrameTimer(0.167);
        moveLeftTimer = new FrameTimer(0.033);
        hardDropAnimationTimer = new FrameTimer(0.1);
        moveRightTimerDAS = new FrameTimer(0.167);
        moveRightTimer = new FrameTimer(0.033);
    }
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        FontMetrics fm; // Font metrics

        if(tetris.isDied()){ // This takes precedence over anything else since we need to transition to another screen if player is dead
            // diedTimer is used to hold things in place while dying animation plays
            if(diedTimer.isDisabled()){
                diedTimer.reset();
            }

            BufferedImage board = (BufferedImage) tetris.drawImage();

            g.rotate(Math.toRadians(20) *diedTimer.getProgress());

            g.drawImage(board, GamePanel.INTERNAL_WIDTH / 2 - Tetris.BOARD_WIDTH / 2  + (int)(330 * diedTimer.getProgress()), GamePanel.INTERNAL_HEIGHT / 2 - Tetris.BOARD_HEIGHT / 2 + (int)(600 * diedTimer.getProgress()),null);
            if(diedTimer.isDone()){ // when diedTimer is done, transition to death screen
                instance.displayGui(new GuiMenuTransition(this, new GuiDied(gameMode)));
            }
        } else {
            g.drawImage(tetris.drawImage(), GamePanel.INTERNAL_WIDTH / 2 - Tetris.BOARD_WIDTH / 2, GamePanel.INTERNAL_HEIGHT / 2 - Tetris.BOARD_HEIGHT / 2 + yOffset - (int) (1400 * (1 - blackfadeOutTimer.getProgress())), Tetris.BOARD_WIDTH, Tetris.BOARD_HEIGHT, null);


            if (!blackfadeOutTimer.isDone()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (1 - blackfadeOutTimer.getProgress())));
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, instance.getWidth(), instance.getHeight());
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            } else if(!bannerTimer.isDone()){
                float opacityProgress;
                if(bannerTimer.getProgress() > 0.9){
                    //easeOutQuint
                    opacityProgress = (float) -(1-Math.pow(1-(bannerTimer.getProgress()-0.9)/0.1,5));
                } else{
                    //easeOutQuint
                    opacityProgress = (float) (1-Math.pow(1- bannerTimer.getProgress()/0.9,5));
                }

                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Util.clamp(0.5f + 0.5f * opacityProgress, 0, 1)));

                g.drawImage(gameBanner, 1920 / 2 - gameBanner.getWidth(null) / 2, 1080 / 2 - gameBanner.getHeight(null) / 2, null);

                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            } else if(!countdownTimer.isDone() && !countdownTimer.isDisabled()){
                long bufferTime = (long) ((1e9)*(countdownTimer.getLength() - 3.0f));

                g.setColor(Color.WHITE);

                Image countDown = null;
                long timeElapsedFromSecond= 0;

                for (int i = 0; i < countdownLength; i++) {
                    if(countdownTimer.timeElapsed() - bufferTime > (3-i-1)*1e9){
                        timeElapsedFromSecond = (countdownTimer.timeElapsed() - bufferTime) - (3-i-1)*(long)1e9;
                        countDown = countDownImages[i];
                        if(!hasPlayedCountdown[i]){
                            sfxPlayer.play(countDownSounds[i]);
                            hasPlayedCountdown[i] = true;
                        }
                        break;
                    }
                }
                if(countDown != null){
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Util.clamp(1 - 1 * (float)timeElapsedFromSecond / (float)1e9, 0, 1)));
                    g.drawImage(countDown, 1920 / 2 - countDown.getWidth(null) / 2, 1080 / 2 - countDown.getHeight(null) / 2, null);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
            }

            if(!goTimer.isDisabled() && !goTimer.isDone()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Util.clamp((float) (1 - 1 * goTimer.getProgress()), 0, 1)));
                g.drawImage(Assets.Game.GO.get(), 1920 / 2 - Assets.Game.GO.get().getWidth(null) / 2, 1080 / 2 - Assets.Game.GO.get().getHeight(null) / 2, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        }
        if (!resignTimer.isDisabled()) {
            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(Color.RED);
            g.fillRect(0, (int)(1080-150*resignTimer.getProgress() -50), 1920, 300);
            g.setColor(Color.WHITE);

            fm = g.getFontMetrics();
            String text = "KEEP HOLDING ESC TO FORFEIT";

            g.drawString(text, 1920/2 - fm.stringWidth(text)/2, (int)(1080 - 150*resignTimer.getProgress()/2 -25+ fm.getHeight()/2));

        }

        if(!restartTimer.isDisabled()){
            g.setFont(Assets.Fonts.KDAM_FONT.get().deriveFont(Font.BOLD, 50));
            g.setColor(Color.ORANGE);
            g.fillRect(0, (int)(1080-150*restartTimer.getProgress() -50), 1920, 300);
            g.setColor(Color.WHITE);

            fm = g.getFontMetrics();
            String text = "KEEP HOLDING R TO RESTART";

            g.drawString(text, 1920/2 - fm.stringWidth(text)/2, (int)(1080 - 150*restartTimer.getProgress()/2 -25+ fm.getHeight()/2));
        }
    }

    @Override
    public void update(){ // This is called every time game physics needs to update
        super.update();
        if (tetris.isObjectiveCompleted()) { // If game completion requirements are fulfilled, immediately move to the results screen.
            instance.displayGui(new GuiMenuTransition(this, new GuiResults(gameMode,tetris.getFinalScore())));
        }


/*        The following if/else structures work as follows:
            When a timer is not yet done, prevent anything below from updating.
            When that timer is done, start the next one.
            Only when all timers are done will the game physics update at tetris.update().
            At this point, all timers will be done and not disabled, preventing the pre-game timers from resetting.*/
        if(!blackfadeOutTimer.isDone()) { //black fade out
            return;
        } else if(bannerTimer.isDisabled()){ // Timer for game banner.
            bannerTimer.reset(); // Enable the timer.
            musicPlayer.stopMusic();
            sfxPlayer.play(Assets.SFX.START_SOLO_GAME.get());
        }

        if(!bannerTimer.isDone()){ // If the game banner is not done, prevent the game from updating.
            return;
        } else if(countdownTimer.isDisabled()){ // Timer for 3-2-1 countdown before game begins
            countdownTimer.reset(); // Enable the countdown timer.
        }

        if(!countdownTimer.isDone()){ // If the 3-2-1 countdown is not done, prevent the game from updating.
            return;
        } else if(goTimer.isDisabled()){ //Go timer (displays "GO"). Unlike the other timers, this one is stop the game from updating.
            goTimer.reset(); // Enable the timer
            sfxPlayer.play(Assets.SFX.GO.get());
            if (tetris.getGameMode() != GameMode.BLITZ) { // Change the BGM to fit the game mode. "VIRTUAL_LIGHT" fits the stress of Blitz mode
                musicPlayer.play(Assets.Music.VREMYA.get());
                musicPlayer.setLoop(true);
            }  else {
                musicPlayer.play(Assets.Music.VIRTUAL_LIGHT.get());
                musicPlayer.setLoop(true);
            }
        }

        tetris.update(); // This updates the tetris game physics.

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

        if(keyboardInput.isKeyPressed(restartKey) && restartTimer.isDisabled()){
            restartTimer.reset();
        } else if(!keyboardInput.isKeyPressed(restartKey)){
            restartTimer.disable();
        } else if(restartTimer.isDone()){
            instance.displayGui(new GuiTetris(gameMode));
        }

        // "soft dropping" is rate limited to prevent a short press from bringing the piece all the way down
        if (downTimer.isDone() && keyboardInput.isKeyPressed(softDropKey)) {
            downTimer.reset();
            tetris.dropPiece();
        }
        // "hard dropping" is disabled if the space bar is held to prevent multiple pieces from being hard dropped
        if (keyboardInput.isKeyPressed(hardDropKey) && !held_hardDrop) {
            tetris.hardDrop();
            hardDropAnimationTimer.reset();
        }

        // moving left and right is controlled by https://tetris.fandom.com/wiki/DAS to avoid operating system quirks
        if (keyboardInput.isKeyPressed(moveLeftKey)) {
            if (moveLeftTimerDAS.isDisabled()) {
                tetris.moveLeft();
                moveLeftTimerDAS.reset();
                moveLeftTimer.reset();
            } else if (moveLeftTimerDAS.isDone() && moveLeftTimer.isDone()) {
                tetris.moveLeft();
                moveLeftTimer.reset();
            }
        } else {
            moveLeftTimerDAS.disable();
        }

        if (keyboardInput.isKeyPressed(moveRightKey)) {
            if (moveRightTimerDAS.isDisabled()) {
                tetris.moveRight();
                moveRightTimerDAS.reset();
                moveRightTimer.reset();
            } else if (moveRightTimerDAS.isDone() && moveRightTimer.isDone()) {
                tetris.moveRight();
                moveRightTimer.reset();
            }
        } else {
            moveRightTimerDAS.disable();
        }

        // same logic as hard drop disabling on hold
        if (keyboardInput.isKeyPressed(rotateCWKey) && !held_rotateCW) {
            tetris.rotateCW();
        } else if (keyboardInput.isKeyPressed(rotateCCWKey) && !held_rotateCCW) {
            tetris.rotateCCW();
        }

        if (keyboardInput.isKeyPressed(holdKey) && !held_holdPiece) {
            tetris.holdPiece();
        }

        held_hardDrop = keyboardInput.isKeyPressed(hardDropKey);
        held_rotateCW = keyboardInput.isKeyPressed(rotateCWKey);
        held_rotateCCW = keyboardInput.isKeyPressed(rotateCCWKey);
        held_holdPiece = keyboardInput.isKeyPressed(holdKey);

        // makes game board animate based on current velocity, prevent it from going too far
        yOffset += yVelocity;

        //If it has gone beyond the limit
        if (yOffset > 4) {
            yVelocity = -1; //Set the velocity to opposite direction
            yOffset = 4; //Set it to the limit
        } else if(yOffset < 0){ //If it has gone up beyond the limit, reset it to 0 and stop the animation
            yVelocity = 0;
            yOffset = 0;
        }

        if(!hardDropAnimationTimer.isDone() && !hardDropAnimationTimer.isDisabled()) {
            yVelocity = 1;
        } else{
            hardDropAnimationTimer.disable();
        }

    }

}
