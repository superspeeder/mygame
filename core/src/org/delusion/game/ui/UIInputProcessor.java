package org.delusion.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import org.delusion.game.ui.elements.UIElement;

import java.util.*;

public class UIInputProcessor extends InputAdapter {
    private Map<UIEvents, List<UIElement>> lists = new HashMap<>();
    private List<Integer> keysPressed = new ArrayList<>();
    private Vector2 lastMousePosition = new Vector2();
    private List<Integer> buttonsPressed = new ArrayList<>();
    private List<UIElement> elementsOver = new ArrayList<>();

    public UIInputProcessor() {
        Arrays.asList(UIEvents.values()).forEach((uiEvents) -> {
            this.lists.put(uiEvents, new ArrayList<>());
        });
    }

    public void loadListsFromPage(List<UIElement> es) {
        this.lists.get(UIEvents.PAGE_CLOSED).forEach(UIElement::onPageClosed);
        Arrays.asList(UIEvents.values()).forEach((uiEvents) -> {
            this.lists.put(uiEvents, new ArrayList<>());
        });
        es.forEach((uiElement) -> {
            Arrays.asList(uiElement.wantedEvents()).forEach((uiEvents) -> {
                this.lists.get(uiEvents).add(uiElement);
            });
        });
        this.lists.get(UIEvents.PAGE_OPENED).forEach(UIElement::onPageOpened);
    }

    public void update() {
        this.lists.get(UIEvents.WHILE_KEY_PRESSED).forEach((uiElement) -> {
            this.keysPressed.forEach(uiElement::whileKeyPressed);
        });
        this.lists.get(UIEvents.WHILE_CLICKED).forEach((uiElement) -> {
            this.buttonsPressed.forEach((integer) -> {
                uiElement.whileClicked(integer, this.lastMousePosition);
            });
        });
        this.elementsOver.forEach((uiElement) -> {
            uiElement.onMouseOver(this.lastMousePosition);
        });
    }

    public boolean keyDown(int keycode) {
        this.lists.get(UIEvents.KEY_DOWN).forEach((uiElement) -> {
            uiElement.onKeyDown(keycode);
        });
        return super.keyDown(keycode);
    }

    public boolean keyUp(int keycode) {
        this.lists.get(UIEvents.KEY_UP).forEach((uiElement) -> {
            uiElement.onKeyUp(keycode);
        });
        return super.keyUp(keycode);
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.lists.get(UIEvents.CLICK_BEGAN).forEach((uiElement) -> {
            if (uiElement.getElementBoundingBox().contains((float)screenX, (float)(1080 - screenY))) {
                uiElement.onClick(button, new Vector2((float)screenX, (float)screenY));
            }

        });
        return super.touchDown(screenX, screenY, pointer, button);
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.lists.get(UIEvents.CLICK_RELEASED).forEach((uiElement) -> {
            if (uiElement.getElementBoundingBox().contains((float)screenX, (float)(Gdx.graphics.getHeight() - screenY))) {
                uiElement.onClickReleased(button, new Vector2((float)screenX, (float)screenY));
            }

        });
        return super.touchUp(screenX, screenY, pointer, button);
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    public boolean mouseMoved(int screenX, int screenY) {
        this.lists.get(UIEvents.MOUSE_ENTER).forEach((uiElement) -> {
            if (uiElement.getElementBoundingBox().contains((float)screenX, (float)(Gdx.graphics.getHeight() - screenY)) && !uiElement.getElementBoundingBox().contains(this.lastMousePosition)) {
                uiElement.onMouseEnter(new Vector2((float)screenX, (float)(Gdx.graphics.getHeight() - screenY)));
            }

        });
        this.lists.get(UIEvents.MOUSE_OVER).forEach((uiElement) -> {
            if (uiElement.getElementBoundingBox().contains((float)screenX, (float)(Gdx.graphics.getHeight() - screenY))) {
                uiElement.onMouseOver(new Vector2((float)screenX, (float)(Gdx.graphics.getHeight() - screenY)));
            }

        });
        this.lists.get(UIEvents.MOUSE_LEAVE).forEach((uiElement) -> {
            if (!uiElement.getElementBoundingBox().contains((float)screenX, (float)(Gdx.graphics.getHeight() - screenY)) && uiElement.getElementBoundingBox().contains(this.lastMousePosition)) {
                uiElement.onMouseLeave(new Vector2((float)screenX, (float)(Gdx.graphics.getHeight() - screenY)));
            }

        });
        this.lastMousePosition = new Vector2((float)screenX, (float)(Gdx.graphics.getHeight() - screenY));
        return super.mouseMoved(screenX, screenY);
    }

//    public boolean scrolled(int amount) {
//        return super.scrolled(amount);
//    }
}
