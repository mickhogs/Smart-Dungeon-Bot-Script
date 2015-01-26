package smart.dungeon.listener;

import org.powerbot.game.bot.event.MessageEvent;

import smart.dungeon.Dungeon;
import smart.dungeon.doors.Keys;
import smart.dungeon.tasks.PickupItemsTask;

public class MessageListenerHandler {

	private MessageEvent messageEvent;

	public MessageListenerHandler(MessageEvent messageEvent) {
		this.messageEvent = messageEvent;
	}

	public void execute() {
		if (getMessageEvent().getMessage().contains("found a key")) {
			Keys.addKey(PickupItemsTask.getLastPickup());
			PickupItemsTask.setKey(true);
		} else if (getMessageEvent().getMessage().contains("Oh dear,")) {
			Dungeon.increaseTotalDeaths();
		}
	}

	private MessageEvent getMessageEvent() {
		return messageEvent;
	}

}
