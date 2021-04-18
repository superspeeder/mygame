package org.delusion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.delusion.game.GameMain;
import org.delusion.game.ui.UI;
import org.delusion.game.ui.elements.ButtonElement;
import org.delusion.game.ui.elements.TextElement;
import org.delusion.game.utils.GLUtils;

public class MainMenu implements Screen {
    private final UI ui;
    private final GameMain gm;

    public MainMenu(GameMain gm) {
        this.gm = gm;
        GLUtils.setBG(Color.BLACK);
        ui = new UI("fonts/Orbitron/Orbitron-ExtraBold.ttf");
        ui.addPage("main");
        ui.addPage("credits");
        ui.addPage("credits.fonts");

        ui.addElement(new TextElement("Game", ui.uiFontGen, 48, new Vector2(20,20),false), "main");
        ui.addElement(new ButtonElement("Play", ui.uiFontGen, 24, new Vector2(20,100), false, i -> gm.select("game")), "main");
        ui.addElement(new ButtonElement("Exit", ui.uiFontGen, 24, new Vector2(20,140), false, i -> Gdx.app.exit()), "main");

        ui.updateInputs();
    }

    public void render(float delta) {
        GLUtils.clearScreen();
        this.ui.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void show() {
        this.ui.show();
        GLUtils.setBG(Color.BLACK);
    }


    public void dispose() {
    }
}
