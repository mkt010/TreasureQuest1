import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Grave extends BaseActor
{
    public Grave(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/grave.png");
        setSize(48,48);
    }
}
