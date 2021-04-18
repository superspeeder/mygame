package org.delusion.game.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.delusion.game.ui.style.Padding;
import org.delusion.game.utils.WindowDetails;

import java.util.Objects;
import java.util.function.Supplier;

public class TextElement extends Element {
    private final Supplier<String> textgen;
    private final Padding padding;
    private final WindowDetails windowDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextElement that = (TextElement) o;
        return getTextGenerator().equals(that.getTextGenerator()) &&
                getPadding().equals(that.getPadding()) &&
                getWindowDetails().equals(that.getWindowDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTextGenerator(), getPadding(), getWindowDetails());
    }

    public Supplier<String> getTextGenerator() {
        return textgen;
    }

    public String generateText() {
        return textgen.get();
    }


    public Padding getPadding() {
        return padding;
    }

    public WindowDetails getWindowDetails() {
        return windowDetails;
    }

    public void render(SpriteBatch batch) {
        // render font
    }

    public TextElement(Supplier<String> textgen, Padding padding, WindowDetails windowDetails) {
        this.textgen = textgen;
        this.padding = padding;
        this.windowDetails = windowDetails;
    }
}
