package org.delusion.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.delusion.game.ui.elements.UIElement;

import java.util.*;
import java.util.function.Consumer;

public class UI {
    private final UIInputProcessor inputProcessor;
    private SpriteBatch batch;
    public final FreeTypeFontGenerator uiFontGen;
    private boolean useBgTex = false;
    private Texture bgT = null;
    private Map<String, List<UIElement>> pages = new HashMap();
    private String curpage;

    public UI() {
        this.uiFontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ui.ttf"));
        this.batch = new SpriteBatch();
        this.inputProcessor = new UIInputProcessor();
    }

    public UI(String font) {
        this.uiFontGen = new FreeTypeFontGenerator(Gdx.files.internal(font));
        this.batch = new SpriteBatch();
        this.inputProcessor = new UIInputProcessor();
    }

    public void addElement(UIElement element, String page) {
        if (!this.pages.containsKey(page)) {
            this.addPage(page);
        }

        ((List)this.pages.get(page)).add(element);
    }

    public void show() {
        Gdx.input.setInputProcessor(this.inputProcessor);
    }

    public void render() {
        this.batch.begin();
        if (this.useBgTex && this.bgT != null) {
            this.batch.draw(this.bgT, 0.0F, 0.0F, (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
        }

        Iterator var1 = ((List)this.pages.get(this.curpage)).iterator();

        while(var1.hasNext()) {
            UIElement e = (UIElement)var1.next();
            e.render(this.batch);
        }

        this.batch.end();
    }

    public void updateInputs() {
        this.inputProcessor.loadListsFromPage((List)this.pages.get(this.curpage));
    }

    public void setBGTex(String s) {
        this.useBgTex = true;
        this.bgT = new Texture(Gdx.files.internal(s));
    }

    public void addPage(String name) {
        if (this.pages.keySet().isEmpty()) {
            this.curpage = name;
        }

        this.pages.put(name, new ArrayList());
    }

    public int getIndexFor(UIElement uiElement, String uiPage) {
        return !this.pages.containsKey(uiPage) ? -1 : ((List)this.pages.get(uiPage)).indexOf(uiElement);
    }

    public int hashOfElementInPage(String uiPage, int index) {
        return ((UIElement)((List)this.pages.get(uiPage)).get(index)).hashCode();
    }

    public void setPage(String page) {
        this.curpage = page;
        this.updateInputs();
    }

    public Consumer<Integer> pageSetter_onclick(String name) {
        return button -> setPage(name);
    }
}
