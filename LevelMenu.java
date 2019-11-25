import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class LevelMenu extends BaseScreen
{
    Sound buttonClickSound;
    
    private float audioVolume;
    private Music instrumental;
    public void initialize()
    {
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("assets/buttonClick.wav"));
        
        //menu music - change to a different file when I find one
        //or no music?
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("assets/gameMusic.mp3"));
        
        audioVolume = 1.00f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        instrumental.play();
        
        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "assets/MainMenu1.png" );
        background.setSize(800,600);
        
        TextButton level1Button = new TextButton("Easy",BaseGame.textButtonStyle);

        level1Button.addListener(
            (Event e) ->
            {
                if(!(e instanceof InputEvent) ||
                   !((InputEvent)e).getType().equals(Type.touchDown))
                    return false;
                    
                    buttonClickSound.play();
                    TreasureQuestGame.setActiveScreen(new LevelScreen());
                    instrumental.dispose();
                    return false;
                }
        );
        
        TextButton level2Button = new TextButton("Medium",BaseGame.textButtonStyle);

        level2Button.addListener(
            (Event e) ->
            {
                if(!(e instanceof InputEvent) ||
                   !((InputEvent)e).getType().equals(Type.touchDown))
                    return false;
                    
                    buttonClickSound.play();
                    TreasureQuestGame.setActiveScreen(new LevelScreen2());
                    instrumental.dispose();
                    return false;
                }
        );
        
                TextButton level3Button = new TextButton("Hard",BaseGame.textButtonStyle);

        level3Button.addListener(
            (Event e) ->
            {
                if(!(e instanceof InputEvent) ||
                   !((InputEvent)e).getType().equals(Type.touchDown))
                    return false;
                    
                    buttonClickSound.play();
                    TreasureQuestGame.setActiveScreen(new LevelScreen3());
                    instrumental.dispose();
                    return false;
                }
        );
        
        TextButton level4Button = new TextButton("Boss",BaseGame.textButtonStyle);

        level4Button.addListener(
            (Event e) ->
            {
                if(!(e instanceof InputEvent) ||
                   !((InputEvent)e).getType().equals(Type.touchDown))
                    return false;
                    
                    buttonClickSound.play();
                    TreasureQuestGame.setActiveScreen(new LevelScreen4());
                    instrumental.dispose();
                    return false;
                }
        );
                
        TextButton exitButton = new TextButton("Exit",BaseGame.textButtonStyle);
        
        exitButton.addListener(
            (Event e) ->
            {
                if (!(e instanceof InputEvent) ||
                   !((InputEvent)e).getType().equals(Type.touchDown))
                    return false;
                
                buttonClickSound.play();    
                Gdx.app.exit();
                return false;
            }
        );
         
        float w = level2Button.getWidth();
         
        uiTable.add(level1Button).width(w).padLeft(20);
        uiTable.add(level2Button);
        
        uiTable.add(level3Button).width(w);
        uiTable.add(level4Button).width(w);
        uiTable.row().expandX();

        uiTable.add(exitButton).colspan(8).center().width(w).padTop(10);
        
        uiTable.padTop(300);
    }

    public boolean keyDown(int keyCode)
    {

        if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
            Gdx.app.exit();
            instrumental.dispose();
        }
        return false;
    }
    
    public void update(float dt)
    {

    }
    
}
