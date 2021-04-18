package org.delusion.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import org.delusion.game.screens.GameScreen;
import org.delusion.game.screens.MainMenu;
import org.delusion.game.ui.elements.ButtonElement;

import java.util.HashMap;
import java.util.Map;


/*
// Not going to be done in java, only working on how to implement some features

TODO NOW FOR TESTING: Basic world

TODO LATER:  basic surface gen
TODO LATER:  basic cave gen
TODO LATER:  multiple chunk
TODO LATER:  chunk manager
TODO LATER:  world saves
TODO NEXT:  a player (no fancy world border stop shit, just the basics)*
TODO NEXT:  collisions (2d, i've done them before at least 20 times)*
TODO LATER:  more tiles
TODO NEXT:  more overlay elements
TODO NEXT:  main and pause menus, settings menu, controls menu, etc..
TODO NEXT:  inventory system
TODO NEXT:  player save states?
TODO NEXT:  placeable tiles
TODO NEXT:  tools
TODO NEXT:	breakables
TODO NEXT:  interactables
TODO NEXT:  semi-pass-through-ables (think wood platforms from terraria)
TODO NEXT:  damaging tiles
TODO NEXT:  background/foreground tiles
TODO NEXT:  buffs
TODO NEXT:  entities
TODO NEXT:  entity ai
TODO NEXT:  enemies
TODO NEXT:  passive animals
TODO NEXT:  buffs / debuffs
TODO NEXT:  consumable items
TODO NEXT:  multistate tiles
TODO NEXT:  crafting mechanics
TODO NEXT:  multiple crafting locations
TODO NEXT:  explosives & other damaging effects on the world
TODO NEXT:  equipment
TODO NEXT:  more menus for inventories
TODO NEXT:  more entities
TODO NEXT:  projectile weaponry
TODO NEXT:  research system
TODO NEXT:  merchants
TODO NEXT:  mana
TODO NEXT:  mana type tools
TODO NEXT:  loot
TODO RPG:  NPC Conversations
TODO RPG:  NPC Actions
TODO RPG:  NPC Battle
TODO RPG:  NPC Responses
TODO RPG:  Jobs
TODO RPG:  Questing
TODO RPG:  Quest Areas
TODO RPG:  Less self involved research
TODO RPG:  Stronger Economical System
TODO NEXT:  surface biomes
TODO NEXT:	underground biomes (part 1) - duplication of surface
TODO NEXT:  underground biomes (part 2) - expansion / contraction into other biomes (maybe a exansion factor & expansion defense factor to calculate expansion amount?)
TODO NEXT:  underground biomes (part 3) - isolated biomes from surface (underground only biomes)
TODO NEXT:  surface structure generation
TODO NEXT:  fluid mechanics
TODO NEXT:  boss type mobs
TODO NEXT:  village,town,city recognition
TODO NEXT:  hardcore mode
TODO RPG:  Territory Boundries
TODO RPG:  Generated Towns
TODO RPG:  Town Expansion
TODO RPG:  Wandering Explorers and Traveling Armies
TODO RPG:  Nations
TODO RPG:  Wars
TODO RPG:  Entertainment
TODO RPG:  Trigger Zones
TODO FINAL: Game Modes = Survival: (Story, Hardcore Story, Infinite Sandbox, Hardcore Infinite Sandbox),
                         Creative (0% RPG,100% Sandbox), (No win condition or hardcore mode)
                         Questing & Battle (95% RPG, 5% Sandbox) (Winnable)
				Story - 65% RPG/Story, 35% Sandbox (Winnable)
				Infinite Sandbox - 35% RPG/Story, 65% Sandbox (No win condition)
 */
public class GameMain extends Game {

	private Map<String, Screen> screens = new HashMap<>();

	@Override
	public void create() {
		// Setup things
		ButtonElement.load();

		// Add screens
		screens.put("menu.main", new MainMenu(this));
		screens.put("game", new GameScreen(this));


		// Select screen
		select("menu.main");
	}

	public void select(String name) {
		setScreen(screens.getOrDefault(name, getScreen()));
	}

	@Override
	public void dispose() {
		super.dispose();
		screens.values().forEach(Screen::dispose);
	}
}
