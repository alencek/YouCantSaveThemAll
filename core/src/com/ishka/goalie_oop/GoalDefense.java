package com.ishka.goalie_oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GoalDefense extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Goalie goalie;
	private Score score;
	private EndMsg endMsg;
	private float width, height;

	private Array<GameObjectDynamic> dynamicActors;

	private long gameStartTime;
	private int passedTime;


	@Override
	public void create () {
		Assets.Load();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.app.setLogLevel(Logger.DEBUG);
		score = new Score(0, 0, width, height);
		endMsg = new EndMsg(0, 0, width, height);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		batch = new SpriteBatch();

		goalie = new Goalie(width / 2, 0, Assets.goalieImage.getWidth(), Assets.goalieImage.getHeight());

		dynamicActors = new Array<GameObjectDynamic>();

		gameStartTime = TimeUtils.millis();

		spawnBall();
		spawnYellowCard();
	}

	private void spawnYellowCard() {
		CardYellow card = CardYellow.yellowCardPool.obtain();
		card.getRandomTopPosition(width, height);
		dynamicActors.add(card);
		CardYellow.setCreateNextInTime(10000);
	}


	private void spawnBall() {
		Ball ball = Ball.ballPool.obtain();
		ball.init(width, height);


		Ball.setCreateNextInTime(1000);
		dynamicActors.add(ball);

	}

	@Override
	public void render () {
		//clear screen
		Gdx.gl.glClearColor((float)169, 169, (float)0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// tell the camera to update its matrices.
		camera.update();
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);
		// process user input
		if( Gdx.input.isTouched() ) commandTouched(new Vector3()); //mouse or touch screen
		if( Gdx.input.isKeyPressed(Input.Keys.LEFT) ) goalie.commandMoveLeft();
		if( Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) goalie.commandMoveRight();

		passedTime = (int)(TimeUtils.timeSinceMillis(gameStartTime) / 5000) + 1;

		if (score.isEnd()) {
			batch.begin();
			{
				endMsg.render(batch);
				score.render(batch);
			}
			batch.end();
			//create();
		} else {
			goalie.update(Gdx.graphics.getDeltaTime());
			for (GameObjectDynamic act : dynamicActors) {
				act.update(Gdx.graphics.getDeltaTime());
			}

			if (Ball.isTimeToCreateNew()) spawnBall();
			if (CardYellow.isTimeToCreateNew()) spawnYellowCard();
		}
		batch.begin();
		{
			goalie.render(batch);
			for (GameObjectDynamic act : dynamicActors) {
				act.render(batch);
			}
			score.render(batch);
		}
		batch.end();

		for (Iterator<GameObjectDynamic> iter = dynamicActors.iterator(); iter.hasNext(); ) {
			GameObjectDynamic act = iter.next();
			if (act.bounds.y + act.bounds.height < 0) {
				iter.remove();
			}
			if (act.bounds.overlaps(goalie.bounds)) {
				score = act.updateScore(score);
				System.out.println(dynamicActors.size);
				iter.remove();
				act.finish();
			}

		}

	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void commandTouched(Vector3 touchPosition) {
		touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPosition);
		goalie.position.x = touchPosition.x - Assets.goalieImage.getWidth() / 2;
	}
}
