package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Debug.DebugCameraController;
import com.mygdx.game.Debug.DebugViewportUtils;
import com.mygdx.game.Debug.MemoryInfo;


import java.util.Iterator;

import static org.omg.CORBA.ORB.init;

public class AstronautsGame extends ApplicationAdapter  implements InputProcessor{
    private static final Logger log = new Logger(AstronautsGame.class.getName(), Logger.DEBUG);
    public static int START_HEALTH = 100;
    public static int SPEED = 600; //pixels per second
    public static int SPEED_GOALIE = 200;
    public static int SPEED_BALL = 100;
    public static int SPEED_CARD = 110;
    public static long CREATE_BALL_TIME = 1000000000; // 1 Billion ns
    public static long CREATE_CARD_TIME = 2000000000; // 2 Billion ns
    private Array<Viewport> viewports = new Array<Viewport>();
    private int currentViewportIndex;
    private Viewport currentViewport;
    //3 naloga
    private static final float WORLD_WIDTH = 800.f;
    private static final float WORLD_HEIGHT = 800f;

    private static final float CAMERA_SPEED = 0.5f;
    private static final float CAMERA_ZOOM_SPEED = 0.5f;

    //4 naloga
    DebugCameraController dcc;
    ShapeRenderer sr;
    MemoryInfo memoryInfo;
    private boolean debug = false;
    private BitmapFont font;

    private Texture texture;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private Array<GameObjectDynamic> dynamicActors;
    private Score score;
    private Viewport viewport;
    private EndMsg endMsg;
    private float width, height;
    //particle
    ParticleEffect pe;
    ParticleEffect peL;
    ParticleEffect peR;



    @Override
    public void create() {
        Assets.Load();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        Gdx.app.setLogLevel(Logger.DEBUG);
        font = new BitmapFont();
        score = new Score(0, 0, width, height);
        endMsg = new EndMsg(0, 0, width, height);

        camera = new OrthographicCamera();
        camera.zoom = 1f;

        camera.setToOrtho(false, width, height);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        player = new Player(width / 2, 0, Assets.PlayerImage.getWidth(), Assets.PlayerImage.getHeight());
        dynamicActors = new Array<GameObjectDynamic>();

       // Embalaza embalaza = new Embalaza(0, 0, (int) width, (int) height);
//        Papir papir = new Papir(0, 0, (int) width, (int) height);

        // naloga Debug
        memoryInfo = new MemoryInfo(500);
        sr = new ShapeRenderer();
        dcc = new DebugCameraController();
        dcc.setStartPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

        //particle
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("fire01.pe"),Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        //pe.getEmitters().first().flipY();
        //pe.setFlip(true, true);
        pe.start();




        // embalaza.spawnElement();
        // papir.spawnElement();
        // spawnPapir();
        spawnElements();

        createViewports();
        selectNextViewport();
        Gdx.input.setInputProcessor(this);
    }
  // v 2 nalogi sem spremenil da je vse v isti funkciji( vsi se swpanajo)
    private void spawnElements() {

        // Embalaza embalaza = new Embalaza(0, 0, (int) width, (int) height);
        //embalaza.getRandomTopPosition(width, height);

        Igralec igralec = Igralec.papirPool.obtain();
        igralec.init(width, height);
        igralec.setCreateNextInTime(8000);
       dynamicActors.add(igralec);
    }


    @Override
    public void render() {
        queryInput();
        //clear screen
        Gdx.gl.glClearColor((float) 0, 1, (float) 0.2, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // tell the camera to update its matrices.
        camera.update();
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.

        //4 naloga
        if(debug){ // popravljaaaj
            memoryInfo.update();
            dcc.handleDebugInput(Gdx.graphics.getDeltaTime());
            dcc.applyTo(camera);
            batch.begin();
            {
                GlyphLayout layout = new GlyphLayout(font, "FPS:"+Gdx.graphics.getFramesPerSecond());
                font.setColor(Color.YELLOW);
                font.draw(batch,layout,Gdx.graphics.getWidth()-layout.width, Gdx.graphics.getHeight()-50);

                font.setColor(Color.YELLOW);
                font.draw(batch, "RC:" + batch.totalRenderCalls, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 20);
                memoryInfo.render(batch,font);


            }
            batch.end();

            batch.totalRenderCalls = 0;

            DebugViewportUtils.drawGrid(viewport, sr, 50);

            //Print rectangles
            sr.setProjectionMatrix(camera.combined);
            //https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/graphics/glutils/ShapeRenderer.html
            sr.begin(ShapeRenderer.ShapeType.Line);
            {
                sr.setColor(1, 1, 0, 1);
                for (GameObjectDynamic act: dynamicActors) {
                    sr.rect(act.bounds.x,act.bounds.y,Assets.igralec_image.getWidth(), Assets.igralec_image.getHeight());
                }
               /* for (Rectangle astronaut : astronauts) {
                    sr.rect(astronaut.x, astronaut.y, Assets.papirImage.getWidth(), Assets.papirImage.getHeight());
                }*/

                sr.setColor(0, 0, 1, 1);
                sr.rect(player.position.x, player.position.y,Assets.PlayerImage.getWidth(),Assets.PlayerImage.getHeight());
            }
            sr.end();
        }




        batch.setProjectionMatrix(camera.combined);

        if (pe.isComplete())
            pe.reset();
        // process user input
        if (!score.isEnd()) {
            if (Gdx.input.isTouched()) commandTouched(new Vector3()); //mouse or touch screen
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            {   player.commandMoveLeft();

            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            {
                player.commandMoveRight();

            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.F5)) debug=!debug;
        }


        if (score.isEnd()) {
            batch.begin();
            {

                endMsg.render(batch);
                score.render(batch);
            }
            batch.end();
            //create(); // cc
        } else {
            player.update(Gdx.graphics.getDeltaTime());
            for (GameObjectDynamic act : dynamicActors) {
                act.update(Gdx.graphics.getDeltaTime());
            }

            if (Igralec.isTimeToCreateNew()) spawnElements();

        }

      //  batch.setProjectionMatrix(camera.combined);


        batch.begin();
        {


            player.render(batch);
            for (GameObjectDynamic act : dynamicActors) {

                pe.setPosition(act.bounds.x + Assets.igralec_image.getWidth() / 2, act.bounds.y);
                pe.update(Gdx.graphics.getDeltaTime()); //move line in update part
                pe.draw(batch);

                act.render(batch);
            }
            score.render(batch);
        }
        batch.end();

        for (Iterator<GameObjectDynamic> iter = dynamicActors.iterator(); iter.hasNext(); ) {
            GameObjectDynamic act = iter.next();
            if (act.bounds.y + act.bounds.height < 0) iter.remove();
            if (act.bounds.overlaps(player.bounds)) {
                score = act.updateScore(score);


                iter.remove();
            }

        }

    }



    private void queryInput() {
        // deltaTime is time passed between two frames
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= CAMERA_SPEED * deltaTime*20;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += CAMERA_SPEED * deltaTime*20;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += CAMERA_SPEED * deltaTime*20;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= CAMERA_SPEED * deltaTime*20;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            log.debug("position= " + camera.position);
            log.debug("zoom= " + camera.zoom);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.Dispose();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        currentViewport.update(width, height, true);

    }

    public void commandTouched(Vector3 touchPosition) {
        touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPosition);
        player.position.x = touchPosition.x - Assets.PlayerImage.getWidth() / 2;
    }

    private void createViewports() {
        viewports.add(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        viewports.add(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        viewports.add(new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        viewports.add(new ScreenViewport(camera));
        viewports.add(new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        currentViewportIndex = -1; //we will call next that starts with 0
    }

    private void selectNextViewport()  {
        currentViewportIndex ++;
        currentViewportIndex = currentViewportIndex % viewports.size;
        currentViewport = viewports.get(currentViewportIndex);
        currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectNextViewport();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
