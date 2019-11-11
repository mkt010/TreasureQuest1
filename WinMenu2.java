import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class WinMenu2 extends BaseScreen
{
    Sound buttonClickSound;
    
    private float audioVolume;
    private Music instrumental;
    public void initialize()
    {
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("assets/buttonClick.wav"));
        
        //menu music - change to a different file when I find one
        //or no music?
        //instrumental = Gdx.audio.newMusic(Gdx.files.internal("assets/gameMusic.mp3"));
        
        //audioVolume = 1.00f;
        //instrumental.setLooping(true);
        //instrumental.setVolume(audioVolume);
        //instrumental.play();
        
        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "assets/menubackground.png" );
        background.setSize(800,600);
        
        BaseActor title = new BaseActor(0,0, mainStage);
        title.loadTexture( "assets/title.png" );
        title.centerAtPosition(400,475);
        
        TextButton nextButton = new TextButton("Next",BaseGame.textButtonStyle);

        nextButton.addListener(
            (Event e) ->
            {
                if(!(e instanceof InputEvent) ||
                   !((InputEvent)e).getType().equals(Type.touchDown))
                    return false;
                    
                    buttonClickSound.play();
                    TreasureQuestGame.setActiveScreen(new LevelScreen3());
                    //instrumental.dispose();
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
        
        uiTable.add(nextButton).padRight(100).padTop(100);
        uiTable.add(exitButton).padTop(100);
    }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Keys.ENTER)){
            instrumental.dispose();
            TreasureQuestGame.setActiveScreen(new LevelScreen2());
        }
        if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
            instrumental.dispose();
            Gdx.app.exit();
        }
        return false;
    }
    
    public void update(float dt)
    {

    }
    
}
