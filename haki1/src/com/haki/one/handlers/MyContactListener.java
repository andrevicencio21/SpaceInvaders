package com.haki.one.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

	@Override
	// when 2 fixtres begin to collide
	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		System.out.println("Fixture A is" + fa.getUserData());
	}

	@Override
	// when two fixtures no longer collide
	public void endContact(Contact c) {
		// TODO Auto-generated method stub

	}

	// collision detection
	// presolve
	// collision handling
	// post solve
	@Override
	public void postSolve(Contact c, ContactImpulse ci) {

	}

	@Override
	public void preSolve(Contact c, Manifold m) {

	}

}
