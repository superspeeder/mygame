package org.delusion.game.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class UI {
    private Map<String, Page> pages = new HashMap<>();
    private Page currentPage;

    private SpriteBatch spriteBatch;
    private BitmapFont font;

    public UI() {
    }

    public void addPages(String... pages) {
        for (String name : pages) {
            this.pages.put(name, new Page(this, name));
        }
    }

    public Page getPage(String name) {
        return pages.getOrDefault(name, null);
    }

    public void render() {
        currentPage.render(spriteBatch);
    }

    public void setPage(String name) {
        if (!currentPage.getName().equals(name)) {
            if (pages.containsKey(name)) {
                currentPage = pages.get(name);
            }
        }
    }
}
