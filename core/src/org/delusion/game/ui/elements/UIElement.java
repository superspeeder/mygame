package org.delusion.game.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.delusion.game.ui.UI;
import org.delusion.game.ui.UIEvents;

public abstract class UIElement {
    public UIElement() {
    }

    public abstract void render(SpriteBatch var1);

    public abstract void onClick(int var1, Vector2 var2);

    public abstract void whileClicked(int var1, Vector2 var2);

    public abstract void onClickReleased(int var1, Vector2 var2);

    public abstract void onFocus();

    public abstract void onUnfocus();

    public abstract void focus();

    public abstract void unfocus();

    public abstract boolean isFocused();

    public abstract void setSendKeyEventsFocusedOnly(boolean var1);

    public abstract boolean getSendKeyEventsFocusedOnly();

    public abstract void onKeyDown(int var1);

    public abstract void whileKeyPressed(int var1);

    public abstract void onKeyUp(int var1);

    public abstract void onMouseEnter(Vector2 var1);

    public abstract void onMouseOver(Vector2 var1);

    public abstract void onMouseLeave(Vector2 var1);

    public abstract void onPageOpened();

    public abstract void onPageClosed();

    public abstract UIEvents[] wantedEvents();

    public abstract void beforeEvents();

    public abstract Rectangle getElementBoundingBox();

    public abstract void setPosition(Vector2 var1);

    public abstract void setSize(Vector2 var1);

    public abstract Vector2 getPosition();

    public abstract Vector2 getSize();

    public abstract void setParentUI(UI var1);

    public abstract void setUIPage(String var1);

    public abstract UI getParentUI();

    public abstract String getUIPage();

    public abstract int getIndexInPage();
}

