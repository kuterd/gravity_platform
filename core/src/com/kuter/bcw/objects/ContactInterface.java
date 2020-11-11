package com.kuter.bcw.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public interface ContactInterface {
	void onContact(Contact contact, Fixture a, Fixture b, Vector2 pos);
}
