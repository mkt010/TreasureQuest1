import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.MathUtils;

public class GiantFlyer extends BaseActor
{
    public GiantFlyer(float x, float y, Stage s)
    {
        super(x,y,s);
        loadAnimationFromSheet( "assets/slime.png", 1, 4, 0.05f, true);
        setSize(128,128);
        setBoundaryPolygon(6);
        
        
        setSpeed( MathUtils.random(50,80) );
        setMotionAngle( MathUtils.random(0,360) );
    }
    
    public void act(float dt)
    {
        super.act(dt);
        
        if ( MathUtils.random(1,120) == 1 )
            setMotionAngle( MathUtils.random(0,360) );
        
        applyPhysics(dt);
        boundToWorld();
    }
}
