package com.omri.renderer;

import java.util.ArrayList;
import java.util.List;

import com.omri.components.SpriteRenderer;
import com.omri.engine.GameObject;

public class Renderer {
	private final int MAX_BATCH_SIZE = 1000;
	private List<RenderBatch> batches;
	
	public Renderer() {
		this.batches = new ArrayList<>();
	}
	
	public void add(GameObject go) {
		SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
		if(spr != null) {
			add(spr);
		}
	}
	
	private void add(SpriteRenderer spr) {
		boolean added = false;
		for(RenderBatch batch : batches) {
			if(batch.hasRoom()) {
				batch.addSprite(spr);
				added = true;
				break;
			}
		}
		if(!added) {
			RenderBatch newBatch = new RenderBatch(this.MAX_BATCH_SIZE);
			newBatch.start();
			batches.add(newBatch);
			newBatch.addSprite(spr);
		}
	}
	
	public void render() {
		for(RenderBatch batch : batches) {
			batch.render();
		}
	}
}
