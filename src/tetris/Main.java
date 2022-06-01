
package tetris;

import tetris.util.Assets;

public class Main {
    public static GameFrame instance;
    public static void main(String[] args){
        instance = new GameFrame();
        System.out.println(Assets.LOGO_FILE);
    }
}
