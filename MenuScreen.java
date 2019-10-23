import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
public class MenuScreen extends BaseScreen
{
    public void initialize()
    {
        BaseActor ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture( "assets/sky.png" );
        ocean.setSize(800,600);
    }

    public void update(float dt)
    {
        if (Gdx.input.isKeyPressed(Keys.S))
        TreasureQuestGame.setActiveScreen( new LevelScreen() );
    }
    
}