package com.mathgame.plugin.slidegame.slideview;

class AnimationTile extends Position {
    private final int   mAnimationType;
    private final long  mAnimationTime;
    private final long  mDelayTime;
    private final int[] mExtras;
    private       long  mTimeElapsed;

    public AnimationTile(int x, int y, int animationType, long length, long delay, int[] extras) {
        super(x, y);
        mAnimationType = animationType;
        mAnimationTime = length;
        mDelayTime = delay;
        mExtras = extras;
    }

    public int getAnimationType() {
        return mAnimationType;
    }

    public void tick(long timeElapsed) {
        this.mTimeElapsed = this.mTimeElapsed + timeElapsed;
    }

    public boolean animationDone() {
        return mAnimationTime + mDelayTime < mTimeElapsed;
    }

    public double getPercentageDone() {
        return Math.max(0, 1.0 * (mTimeElapsed - mDelayTime) / mAnimationTime);
    }

    public boolean isActive() {
        return (mTimeElapsed >= mDelayTime);
    }

    public int[] getExtras() {
        return mExtras;
    }
}
