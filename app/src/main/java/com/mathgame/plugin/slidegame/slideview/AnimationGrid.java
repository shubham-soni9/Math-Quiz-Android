package com.mathgame.plugin.slidegame.slideview;

import java.util.ArrayList;

class AnimationGrid {
    private final ArrayList<AnimationTile>[][] mAnimationGameGrid;
    private final ArrayList<AnimationTile>     mGlobalAnimation  = new ArrayList<>();
    private       int                          mActiveAnimations = 0;
    private       boolean                      mOneMoreFrame     = false;

    AnimationGrid(int x, int y) {
        mAnimationGameGrid = new ArrayList[x][y];
        for (int xx = 0; xx < x; xx++) {
            for (int yy = 0; yy < y; yy++) {
                mAnimationGameGrid[xx][yy] = new ArrayList<>();
            }
        }
    }

    void startAnimation(int x, int y, int animationType, long length, long delay, int[] extras) {
        AnimationTile animationToAdd = new AnimationTile(x, y, animationType, length, delay, extras);
        if (x == -1 && y == -1) {

            mGlobalAnimation.add(animationToAdd);
        } else {
            mAnimationGameGrid[x][y].add(animationToAdd);
        }
        mActiveAnimations = mActiveAnimations + 1;
    }

    void tickAll(long timeElapsed) {
        ArrayList<AnimationTile> cancelledAnimations = new ArrayList<>();
        for (AnimationTile animation : mGlobalAnimation) {
            animation.tick(timeElapsed);
            if (animation.animationDone()) {
                cancelledAnimations.add(animation);
                mActiveAnimations = mActiveAnimations - 1;
            }
        }

        for (ArrayList<AnimationTile>[] array : mAnimationGameGrid) {
            for (ArrayList<AnimationTile> list : array) {
                for (AnimationTile animation : list) {
                    animation.tick(timeElapsed);
                    if (animation.animationDone()) {
                        cancelledAnimations.add(animation);
                        mActiveAnimations = mActiveAnimations - 1;
                    }
                }
            }
        }

        for (AnimationTile animation : cancelledAnimations) {
            cancelAnimation(animation);
        }
    }

    boolean isAnimationActive() {
        if (mActiveAnimations != 0) {
            mOneMoreFrame = true;
            return true;
        } else if (mOneMoreFrame) {
            mOneMoreFrame = false;
            return true;
        } else {
            return false;
        }
    }

    ArrayList<AnimationTile> getAnimationCell(int x, int y) {
        return mAnimationGameGrid[x][y];
    }

    void cancelAnimations() {
        for (ArrayList<AnimationTile>[] array : mAnimationGameGrid) {
            for (ArrayList<AnimationTile> list : array) {
                list.clear();
            }
        }
        mGlobalAnimation.clear();
        mActiveAnimations = 0;
    }

    private void cancelAnimation(AnimationTile animation) {
        if (animation.getX() == -1 && animation.getY() == -1) {
            mGlobalAnimation.remove(animation);
        } else {
            mAnimationGameGrid[animation.getX()][animation.getY()].remove(animation);
        }
    }

    public ArrayList<AnimationTile> getGlobalAnimationList() {
        return mGlobalAnimation;
    }
}
