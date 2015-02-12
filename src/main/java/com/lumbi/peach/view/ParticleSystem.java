package com.lumbi.peach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gabriellumbi on 15-02-12.
 */
public class ParticleSystem
{
    private List<View> particles = new ArrayList<View>();
    private Random random = new Random();

    // Particle System Properties

    private View anchorView;
    private Interpolator interpolator = new LinearInterpolator();

    // Particle Properties

    private float particleStartScale = 0;
    private float particleEndScale = 1;
    private float particleStartAlpha = 1;
    private float particleEndAlpha = 1;
    private float particleTranslationX = 0;
    private float particleTranslationY = 0;
    private long particleLifetime = 1000;

    public ParticleSystem(int particleViewLayoutId, int maxParticleCount, Context context, ViewGroup worldViewGroup){
        for(int i = 0; i < maxParticleCount; i++){
            View particle = LayoutInflater.from(context).inflate(particleViewLayoutId, worldViewGroup, false);
            particle.setVisibility(View.GONE);
            particles.add(particle);
            worldViewGroup.addView(particle);
        }
    }

    public View getAnchorView() {
        return anchorView;
    }

    public void setAnchorView(View anchorView) {
        this.anchorView = anchorView;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public float getParticleStartScale() {
        return particleStartScale;
    }

    public void setParticleStartScale(float particleStartScale) {
        this.particleStartScale = particleStartScale;
    }

    public float getParticleEndScale() {
        return particleEndScale;
    }

    public void setParticleEndScale(float particleEndScale) {
        this.particleEndScale = particleEndScale;
    }

    public float getParticleStartAlpha() {
        return particleStartAlpha;
    }

    public void setParticleStartAlpha(float particleStartAlpha) {
        this.particleStartAlpha = particleStartAlpha;
    }

    public float getParticleEndAlpha() {
        return particleEndAlpha;
    }

    public void setParticleEndAlpha(float particleEndAlpha) {
        this.particleEndAlpha = particleEndAlpha;
    }

    public float getParticleTranslationX() {
        return particleTranslationX;
    }

    public void setParticleTranslationX(float particleTranslationX) {
        this.particleTranslationX = particleTranslationX;
    }

    public float getParticleTranslationY() {
        return particleTranslationY;
    }

    public void setParticleTranslationY(float particleTranslationY) {
        this.particleTranslationY = particleTranslationY;
    }

    public long getParticleLifetime() {
        return particleLifetime;
    }

    public void setParticleLifetime(long particleLifetime) {
        this.particleLifetime = particleLifetime;
    }

    public View emit(float dx, float dy){
        View newParticle = getUnusedParticle();
        if(newParticle != null){
            applyParticleStartParameters(newParticle);
            if(this.anchorView != null){
                newParticle.setX(ViewUtil.getCenterX(this.anchorView) + dx);
                newParticle.setY(ViewUtil.getCenterY(this.anchorView) + dy);
            }else{
                newParticle.setX(dx);
                newParticle.setY(dy);
            }

            animateParticle(newParticle);
            return newParticle;
        }
        return null;
    }

    public View emitWithinCircleOfRadius(float radius){
        float dx = 2*random.nextFloat() - 1;
        dx *= radius;
        float dy = 2*random.nextFloat() - 1;
        dy *= radius;
        return emit(dx,dy);
    }

    private View getUnusedParticle(){
        for(View particle : particles){
            if(particle.getVisibility() == View.GONE){
                return particle;
            }
        }
        return null;
    }

    private void applyParticleStartParameters(View particle){
        particle.setScaleX(this.particleStartScale);
        particle.setScaleY(this.particleStartScale);
        particle.setAlpha(this.particleStartAlpha);
    }

    private void animateParticle(final View particle){
        particle.setVisibility(View.VISIBLE);
        particle.animate()
                .alpha(this.particleEndAlpha)
                .scaleX(this.particleEndScale)
                .scaleY(this.particleEndScale)
                .xBy(this.particleTranslationX)
                .yBy(this.particleTranslationY)
                .setInterpolator(this.interpolator)
                .setDuration(this.particleLifetime)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        particle.setVisibility(View.GONE);
                    }
                })
                .start();
    }
}