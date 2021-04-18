package org.delusion.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.delusion.game.ui.elements.Element

import java.util.List;

public class Page {
    private final UI ui;
    private final String name;

    private List<Element> elements;

    public Page(UI ui, String name) {
        this.ui = ui;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UI getUI() {
        return ui;
    }

    public int addElement(Element element) {
        elements.add(element);
        return elements.size() - 1;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void render(SpriteBatch spriteBatch) {
        for (Element element : elements) {
            element.render(spriteBatch);
        }
    }


    public

}
