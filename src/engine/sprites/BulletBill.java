package engine.sprites;

import java.awt.Graphics;

import engine.core.MarioSprite;
import engine.graphics.MarioImage;
import engine.helper.Assets;
import engine.helper.SpriteType;

public class BulletBill extends MarioSprite {
    private MarioImage graphics;

    public BulletBill(boolean visuals, float x, float y, int dir) {
	super(x, y, SpriteType.BULLET_BILL);
	this.width = 4;
	this.height = 12;
	this.ya = -5;
	this.facing = dir;

	if (visuals) {
	    this.graphics = new MarioImage(Assets.enemies, 40);
	    this.graphics.originX = 8;
	    this.graphics.originY = 31;
	    this.graphics.width = 16;
	}
    }

    @Override
    public MarioSprite clone() {
	BulletBill sprite = new BulletBill(false, x, y, this.facing);
	sprite.xa = this.xa;
	sprite.ya = this.ya;
	sprite.width = this.width;
	sprite.height = this.height;
	return (MarioSprite)sprite;
    }

    @Override
    public void update() {
	super.update();
	float sideWaysSpeed = 4f;
	xa = facing * sideWaysSpeed;
	move(xa, 0);
	if (this.graphics != null) {
	    this.graphics.flipX = facing == -1;
	}
    }

    @Override
    public void render(Graphics og) {
	super.render(og);
	this.graphics.render(og, (int) (this.x - this.world.cameraX), (int) this.y);
    }

    public void collideCheck() {
	float xMarioD = world.mario.x - x;
	float yMarioD = world.mario.y - y;
	if (xMarioD > -16 && xMarioD < 16) {
	    if (yMarioD > -height && yMarioD < world.mario.height) {
		if (world.mario.ya > 0 && yMarioD <= 0 && (!world.mario.onGround || !world.mario.wasOnGround)) {
		    world.mario.stomp(this);
		    this.world.removeSprite(this);
		} else {
		    world.mario.getHurt();
		}
	    }
	}
    }

    private boolean move(float xa, float ya) {
	x += xa;
	return true;
    }

    public boolean fireballCollideCheck(Fireball fireball) {
	float xD = fireball.x - x;
	float yD = fireball.y - y;

	if (xD > -16 && xD < 16) {
	    if (yD > -height && yD < fireball.height) {
		return true;
	    }
	}
	return false;
    }

    public boolean shellCollideCheck(Shell shell, Mario mario) {
	float xD = shell.x - x;
	float yD = shell.y - y;

	if (xD > -16 && xD < 16) {
	    if (yD > -height && yD < shell.height) {
		this.world.removeSprite(this);
		return true;
	    }
	}
	return false;
    }
}
