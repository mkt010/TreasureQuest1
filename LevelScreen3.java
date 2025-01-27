import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

public class LevelScreen3 extends BaseScreen
{
    Hero hero;
    Sword sword;
    
    Sound coinSound;
    Sound swordSound;
    Sound bushSound;
    Sound flyerKillSound;
    Sound rockHitSound;
    Sound playerDeathSound;
    Sound arrowSound;
    Sound buySound;
    Sound damageSound;
    Sound gameOverSound;
    Sound errorSound;
    
    private float audioVolume;
    private Music instrumental;

    int health;
    int coins;
    int arrows;

    boolean gameOver;
    boolean gameWin;
    Label healthLabel;
    Label coinLabel;
    Label arrowLabel;
    Label messageLabel;
    
    DialogBox dialogBox;

    Treasure treasure;
    ShopHeart shopHeart;
    ShopArrow shopArrow;

    TilemapActor tma;

    public void initialize() 
    {        
        
        tma = new TilemapActor("assets/map2.tmx", mainStage);

        coinSound = Gdx.audio.newSound(Gdx.files.internal("assets/coin.wav"));
        swordSound = Gdx.audio.newSound(Gdx.files.internal("assets/whoosh.wav"));
        bushSound = Gdx.audio.newSound(Gdx.files.internal("assets/Bush.wav"));
        rockHitSound = Gdx.audio.newSound(Gdx.files.internal("assets/RockHit.wav"));
        flyerKillSound = Gdx.audio.newSound(Gdx.files.internal("assets/FlyerKill.wav"));
        playerDeathSound = Gdx.audio.newSound(Gdx.files.internal("assets/PlayerDeath.wav"));
        arrowSound = Gdx.audio.newSound(Gdx.files.internal("assets/arrow.wav"));
        buySound = Gdx.audio.newSound(Gdx.files.internal("assets/buy.wav"));
        damageSound = Gdx.audio.newSound(Gdx.files.internal("assets/damage.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("assets/GameOver.wav"));
        errorSound = Gdx.audio.newSound(Gdx.files.internal("assets/Error.wav"));
        
        for (MapObject obj : tma.getRectangleList("Solid") )
        {
            MapProperties props = obj.getProperties();
            new Solid( (float)props.get("x"), (float)props.get("y"),
                (float)props.get("width"), (float)props.get("height"), 
                mainStage );
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage);

        sword = new Sword(0,0, mainStage);
        sword.setVisible(false);

        for (MapObject obj : tma.getTileList("Bush") )
        {
            MapProperties props = obj.getProperties();
            new Bush( (float)props.get("x"), (float)props.get("y"), mainStage );
        }

        for (MapObject obj : tma.getTileList("Rock") )
        {
            MapProperties props = obj.getProperties();
            new Rock( (float)props.get("x"), (float)props.get("y"), mainStage );
        }

        for (MapObject obj : tma.getTileList("Coin") )
        {
            MapProperties props = obj.getProperties();
            new Coin( (float)props.get("x"), (float)props.get("y"), mainStage );
        }

        MapObject treasureTile = tma.getTileList("Treasure").get(0);
        MapProperties treasureProps = treasureTile.getProperties();
        treasure = new Treasure( (float)treasureProps.get("x"), (float)treasureProps.get("y"), mainStage );

        health = 3;
        coins = 5;
        arrows = 3;
        gameOver = false;
        gameWin = false;

        healthLabel = new Label(" x " + health, BaseGame.labelStyle);
        healthLabel.setColor(Color.PINK);
        coinLabel  = new Label(" x " + coins,  BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        arrowLabel = new Label(" x " + arrows, BaseGame.labelStyle);
        arrowLabel.setColor(Color.TAN);
        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setVisible(false);

        dialogBox = new DialogBox(0,0, uiStage);
        dialogBox.setBackgroundColor( Color.TAN );
        dialogBox.setFontColor( Color.BROWN );
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        BaseActor healthIcon = new BaseActor(0,0,uiStage);
        healthIcon.loadTexture("assets/heart-icon.png");
        BaseActor coinIcon = new BaseActor(0,0,uiStage);
        coinIcon.loadTexture("assets/coin-icon.png");
        BaseActor arrowIcon = new BaseActor(0,0,uiStage);
        arrowIcon.loadTexture("assets/arrow-icon.png");

        uiTable.pad(20);
        uiTable.add(healthIcon);
        uiTable.add(healthLabel);
        uiTable.add().expandX();
        uiTable.add(coinIcon);
        uiTable.add(coinLabel);
        uiTable.add().expandX();
        uiTable.add(arrowIcon);
        uiTable.add(arrowLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(8).expandX().expandY();
        uiTable.row();
        uiTable.add(dialogBox).colspan(8);

        for (MapObject obj : tma.getTileList("Flyer") )
        {
            MapProperties props = obj.getProperties();
            new Flyer( (float)props.get("x"), (float)props.get("y"), mainStage );
        }

        for (MapObject obj : tma.getTileList("SmallFlyer"))
        {
            MapProperties props = obj.getProperties();
            new Flyer( (float)props.get("x"), (float)props.get("y"), mainStage );
        }
        
        for (MapObject obj : tma.getTileList("NPC") )
        {
            MapProperties props = obj.getProperties();
            NPC s = new NPC( (float)props.get("x"), (float)props.get("y"), mainStage );
            s.setID( (String)props.get("id") );
            s.setText( (String)props.get("text") );
        }

        MapObject shopHeartTile = tma.getTileList("ShopHeart").get(0);
        MapProperties shopHeartProps = shopHeartTile.getProperties();
        shopHeart = new ShopHeart( (float)shopHeartProps.get("x"), (float)shopHeartProps.get("y"), mainStage );

        MapObject shopArrowTile = tma.getTileList("ShopArrow").get(0);
        MapProperties shopArrowProps = shopArrowTile.getProperties();
        shopArrow = new ShopArrow( (float)shopArrowProps.get("x"), (float)shopArrowProps.get("y"), mainStage );

        hero.toFront();

        instrumental = Gdx.audio.newMusic(Gdx.files.internal("assets/bgm_menu.mp3"));
        
        audioVolume = 1.00f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        instrumental.play();
    }
    
    public void update(float dt)
    {
        if ( gameOver ){
            instrumental.dispose();
            return;
        }
        
        if ( gameWin ){
            instrumental.dispose();
            return;
        }
        
        healthLabel.setText(" x " + health);
        coinLabel.setText(" x " + coins);
        arrowLabel.setText(" x " + arrows);

        if ( !sword.isVisible() )
        {
            // hero movement controls
            //cannot move while sword is out
            if (Gdx.input.isKeyPressed(Keys.A)) 
                hero.accelerateAtAngle(180);
            if (Gdx.input.isKeyPressed(Keys.D)) 
                hero.accelerateAtAngle(0);
            if (Gdx.input.isKeyPressed(Keys.W)) 
                hero.accelerateAtAngle(90);
            if (Gdx.input.isKeyPressed(Keys.S)) 
                hero.accelerateAtAngle(270);
        }

        for (BaseActor solid : BaseActor.getList(mainStage, "Solid"))
        {
            hero.preventOverlap(solid);

            for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer"))
            {
                if (flyer.overlaps(solid))
                {
                    flyer.preventOverlap(solid);
                    flyer.setMotionAngle( flyer.getMotionAngle() + 180 );
                }
            }
            
            for (BaseActor smallFlyer : BaseActor.getList(mainStage, "SmallFlyer"))
            {
                if (smallFlyer.overlaps(solid))
                {
                    smallFlyer.preventOverlap(solid);
                    smallFlyer.setMotionAngle( smallFlyer.getMotionAngle() + 180 );
                }
            }
        }

        if ( sword.isVisible() )
        {
            for (BaseActor bush : BaseActor.getList(mainStage, "Bush"))
            {
                if (sword.overlaps(bush)){
                    bush.remove();
                    bushSound.play();
                }
            }
            
            for (BaseActor rock : BaseActor.getList(mainStage,"Rock"))
            {
                if (sword.overlaps(rock)){
                    rockHitSound.play();
                }
            }

            for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer"))
            {
                if (sword.overlaps(flyer))
                {
                    flyer.remove();
                    flyerKillSound.play();
                    for (int i = 0; i<2; i++){
                        SmallFlyer smallFlyer = new SmallFlyer(0,0,mainStage);
                        smallFlyer.centerAtActor(flyer);
                    }
                }
            }
            
            for(BaseActor smallFlyer : BaseActor.getList(mainStage, "SmallFlyer"))
            {
                if (sword.overlaps(smallFlyer))
                {
                    smallFlyer.remove();
                    flyerKillSound.play();
                    Coin coin = new Coin(0,0, mainStage);
                    coin.centerAtActor(smallFlyer);
                    Smoke smoke = new Smoke(0,0, mainStage);
                    smoke.centerAtActor(smallFlyer);
                }
            }
            
        }

        for ( BaseActor coin : BaseActor.getList(mainStage, "Coin") )
        {
            if ( hero.overlaps(coin) )
            {
                coin.remove();
                coins++;
                coinSound.play(0.10f);
            }
        }

        for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer"))
        {
            if ( hero.overlaps(flyer) )
            {
                hero.preventOverlap(flyer);                
                flyer.setMotionAngle( flyer.getMotionAngle() + 180 );
                Vector2 heroPosition  = new Vector2(  hero.getX(),  hero.getY() );
                Vector2 flyerPosition = new Vector2( flyer.getX(), flyer.getY() );
                Vector2 hitVector = heroPosition.sub( flyerPosition );
                hero.setMotionAngle( hitVector.angle() );
                hero.setSpeed(200);
                damageSound.play();
                health--;
            }
        }

        for (BaseActor smallFlyer : BaseActor.getList(mainStage, "SmallFlyer"))
        {
            if ( hero.overlaps(smallFlyer) )
            {
                hero.preventOverlap(smallFlyer);                
                smallFlyer.setMotionAngle( smallFlyer.getMotionAngle() + 180 );
                Vector2 heroPosition  = new Vector2(  hero.getX(),  hero.getY() );
                Vector2 smallFlyerPosition = new Vector2( smallFlyer.getX(), smallFlyer.getY() );
                Vector2 hitVector = heroPosition.sub( smallFlyerPosition );
                hero.setMotionAngle( hitVector.angle() );
                hero.setSpeed(200);
                damageSound.play();
                health--;
            }
        }
        
        for ( BaseActor npcActor : BaseActor.getList(mainStage, "NPC") )
        {
            NPC npc = (NPC)npcActor;

            hero.preventOverlap(npc);
            boolean nearby = hero.isWithinDistance(4, npc);

            if ( nearby && !npc.isViewing() )
            {
                // check NPC ID for dynamic text
                if ( npc.getID().equals("Gatekeeper") )
                {
                    int flyerCount = BaseActor.count(mainStage, "Flyer");
                    int smallFlyerCount = BaseActor.count(mainStage, "SmallFlyer");
                    int totalCount = (flyerCount + smallFlyerCount);
                    String message = "Destroy the slimes and you can have the treasure. ";
                    if ( totalCount > 1 )
                        message += "There are " + totalCount + " left.";
                    else if ( totalCount == 1 )
                        message += "There is " + totalCount + " left.";
                    else // totalCount == 0
                    {
                        message += "It is yours!";
                        npc.addAction( Actions.fadeOut(2.0f) );
                        npc.addAction( Actions.after( Actions.moveBy(-10000, -10000) ) );
                    }

                    dialogBox.setText(message);
                }
                else
                {
                    dialogBox.setText( npc.getText() );
                }
                dialogBox.setVisible( true );
                npc.setViewing( true );
               
            }

            if (npc.isViewing() && !nearby)
            {
                dialogBox.setText( " " );
                dialogBox.setVisible( false );
                npc.setViewing( false );
            }
        }

        if ( hero.overlaps(treasure) )
        {
            dialogBox.setText("You Win! Press any key to continue");
            dialogBox.setVisible(true);
            
            treasure.remove();

            gameWin = true; 
            gameOver = true;

        }

        if ( health <= 0 )
        {
            dialogBox.setText("You Lose! Press any key to return to the main menu.");
            dialogBox.setVisible(true);
            gameOverSound.play();
            hero.remove();
            Smoke smoke = new Smoke(0,0,mainStage);
            smoke.centerAtActor(hero);
            Grave grave = new Grave(0,0,mainStage);
            grave.centerAtActor(hero);
            gameOver = true;
        }
        
        for (BaseActor arrow : BaseActor.getList(mainStage, "Arrow"))
        {
            for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer"))
            {
                if (arrow.overlaps(flyer))
                {
                    flyer.remove();
                    arrow.remove();
                    flyerKillSound.play();
                    for (int i = 0; i<2; i++){
                        SmallFlyer smallFlyer = new SmallFlyer(0,0,mainStage);
                        smallFlyer.centerAtActor(flyer);
                    }
                }

            for(BaseActor smallFlyer : BaseActor.getList(mainStage, "SmallFlyer"))
            {
                if (arrow.overlaps(smallFlyer))
                {
                    smallFlyer.remove();
                    arrow.remove();
                    flyerKillSound.play();
                    Coin coin = new Coin(0,0, mainStage);
                    coin.centerAtActor(smallFlyer);
                    Smoke smoke = new Smoke(0,0, mainStage);
                    smoke.centerAtActor(smallFlyer);
                }
            }
            
            }

            for (BaseActor solid : BaseActor.getList(mainStage, "Solid"))
            {
                if (arrow.overlaps(solid))
                {
                    arrow.preventOverlap(solid);
                    arrow.setSpeed(0);
                    arrow.addAction( Actions.fadeOut(0.5f) );
                    arrow.addAction( Actions.after( Actions.removeActor() ) );
                }

            }
        }
    }

    public void swingSword()
    {
        // visibility determines if sword is currently swinging
        if ( sword.isVisible() )
            return;

        hero.setSpeed(0);

        float facingAngle = hero.getFacingAngle();

        Vector2 offset = new Vector2();
        if (facingAngle == 0)
            offset.set( 0.50f, 0.20f );
        else if (facingAngle == 90)
            offset.set( 0.65f, 0.50f );
        else if (facingAngle == 180)
            offset.set( 0.40f, 0.20f );
        else // facingAngle == 270
            offset.set( 0.25f, 0.20f );

        sword.setPosition( hero.getX(), hero.getY() );
        sword.moveBy( offset.x * hero.getWidth(), offset.y * hero.getHeight() );

        float swordArc = 90;
        sword.setRotation(facingAngle - swordArc/2);
        sword.setOriginX(0);

        sword.setVisible(true);
        swordSound.play();
        sword.addAction( Actions.rotateBy(swordArc, 0.25f) );
        sword.addAction( Actions.after( Actions.visible(false) ) );

        // hero should appear in front of sword when facing north or west
        if (facingAngle == 90 || facingAngle == 180)
            hero.toFront();
        else
            sword.toFront();
    }

    public void shootArrow()
    {
        if ( arrows <= 0 )
            return;

        arrows--;
        arrowSound.play();
        Arrow arrow = new Arrow(0,0, mainStage);
        arrow.centerAtActor(hero);
        arrow.setRotation( hero.getFacingAngle() );
        arrow.setMotionAngle( hero.getFacingAngle() );
    }

    // handle discrete input
    public boolean keyDown(int keycode)
    {
        //if gameOver pressing any key will open the main menu
        //then turn all other buttons off
        if (gameOver && !gameWin){
            TreasureQuestGame.setActiveScreen(new MenuScreen());
            return false;
        }
        
        if ( gameWin){
            TreasureQuestGame.setActiveScreen(new LevelMenu());
            return false;
        }
        
        if (keycode == Keys.SPACE ) 
            swingSword();     
            
        if (keycode == Keys.CONTROL_RIGHT )      
            shootArrow();
            
        if (keycode == Keys.B)
        {
            if (hero.overlaps(shopHeart) && coins >= 3)
            {
                coins -= 3;
                buySound.play();
                health += 1;
            }
            
            if (hero.overlaps(shopHeart) && coins < 3)
            {
                errorSound.play();
            }

            if (hero.overlaps(shopArrow) && coins >= 4)
            {
                coins -= 4;
                buySound.play();
                arrows += 3;
            }
            
            if (hero.overlaps(shopArrow) && coins < 4)
            {
                errorSound.play();
            }
        }
        
        //cheats
        
        //pressing p adds more arrows
        if(Gdx.input.isKeyPressed(Keys.P))
           arrows += 3;
        //pressing L forces game over   
        if(keycode == Keys.L)
           health = 0;
        //pressing k while near gatekeeper removes gatekeeper   
        if (keycode == Keys.K){
          for ( BaseActor npcActor : BaseActor.getList(mainStage, "NPC") )
          {
             NPC npc = (NPC)npcActor;
             if ( npc.getID().equals("Gatekeeper") )
             {
               npc.addAction( Actions.fadeOut(2.0f) );
               npc.addAction( Actions.after( Actions.moveBy(-10000, -10000) ) );
             }
          }
        }
        return false;
    }

}
