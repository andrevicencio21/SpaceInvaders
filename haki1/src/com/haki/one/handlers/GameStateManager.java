package com.haki.one.handlers;

import java.util.Stack;

import com.haki.one.gamestates.GameState;
import com.haki.one.gamestates.Play;
import com.haki.one.main.Game;

public class GameStateManager {
	private Game game;
	private Stack<GameState> gameStates;
	public static final int PLAY = 101;

	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(PLAY);
	}

	public void update(float dt) {
		gameStates.peek().update(dt);
	}

	public void render() {
		gameStates.peek().render();
	}

	public Game getGame() {
		return game;
	}

	private GameState getState(int state) {
		if (state == PLAY)
			return new Play(this);
		return null;
	}
	public void setState(int state){
		popState();
		pushState(state);
	}
	public void pushState(int state){
		gameStates.push(getState(state));
	}
	public void popState(int state){
		gameStates.push(getState(state));
	}
	
	public void popState(){
		GameState g = gameStates.pop();
		g.dispose();
	}
}
