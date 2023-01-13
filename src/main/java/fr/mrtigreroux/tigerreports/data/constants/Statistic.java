package fr.mrtigreroux.tigerreports.data.constants;

import fr.mrtigreroux.tigerreports.data.config.Message;
import fr.mrtigreroux.tigerreports.objects.CustomItem;
import org.bukkit.Material;

/**
 * @author MrTigreroux
 */

public enum Statistic {

	TRUE_APPRECIATIONS(20, Message.APPRECIATION, Message.TRUE, MenuRawItem.GREEN_CLAY),
	UNCERTAIN_APPRECIATIONS(22, Message.APPRECIATION, Message.UNCERTAIN, MenuRawItem.YELLOW_CLAY),
	FALSE_APPRECIATIONS(24, Message.APPRECIATION, Message.FALSE, MenuRawItem.RED_CLAY),
	REPORTS(38, Message.REPORTS_STATISTIC, Material.PAPER),
	REPORTED_TIMES(40, Message.REPORTED_TIMES_STATISTIC, Material.BOW),
	PROCESSED_REPORTS(42, Message.PROCESSED_REPORTS_STATISTIC, Material.BOOK);

	private final int position;
	private final Message name, appreciation;
	private final Material material;
	private final short durability;
	private final CustomItem customItem;

	Statistic(int position, Message name, Material material) {
		this(position, name, null, material, (short) 0, null);
	}

	Statistic(int position, Message name, Message appreciation, CustomItem customItem) {
		this(position, name, appreciation, null, (short) 0, customItem);
	}

	Statistic(int position, Message name, Message appreciation, Material material, short durability,
	        CustomItem customItem) {
		this.position = position;
		this.name = name;
		this.appreciation = appreciation;
		this.material = material;
		this.durability = durability;
		this.customItem = customItem;
	}

	public int getPosition() {
		return position;
	}

	public String getConfigName() {
		return name().toLowerCase();
	}

	public String getName() {
		String n = name.get();
		return appreciation != null ? n.replace("_Appreciation_", appreciation.get()) : n;
	}

	public CustomItem getCustomItem() {
		return material != null ? new CustomItem().type(material).damage(durability) : customItem.clone();
	}

	public static Statistic getStatisticAtPosition(int position) {
		for (Statistic statistic : values()) {
			if (statistic.getPosition() == position) {
				return statistic;
			}
		}
		return null;
	}
}
