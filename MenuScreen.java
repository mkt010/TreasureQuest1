  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;

public class MenuScreen extends BaseScreen
{
    public void initialize()
    {
        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "assets/menubackground.png" );
        background.setSize(800,600);
        
        BaseActor title = new BaseActor(0,0, mainStage);
        title.loadTexture( "assets/title.png" );
        title.centerAtPosition(400,475);
        
        TextButton startButton = new TextButton("New Game",BaseGame.textButtonStyle);

        startButton.addListener(
            (Event e) ->
            {
                if(!(e instanceof InputEvent) ||
                   !((InputEvent)e).getType().equals(Type.touchDown))
                    return false;
                    
                    TreasureQuestGame.setActiveScreen(new LevelScreen());
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
                    
                Gdx.app.exit();
                return false;
            }
        );
        
        uiTable.add(startButton);
        uiTable.row();
        uiTable.add(exitButton);
    }

    public boolean keyDowwn(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Keys.ENTER))
            TreasureQuestGame.setActiveScreen(new LevelScreen());
        if(Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }
    public void update(float dt)
    {
        
    }
    
}