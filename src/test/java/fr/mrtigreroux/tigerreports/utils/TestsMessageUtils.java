package fr.mrtigreroux.tigerreports.utils;

import fr.mrtigreroux.tigerreports.data.Holder;
import org.bukkit.Sound;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author MrTigreroux
 */
public class TestsMessageUtils {

	public static void mockSendStaffMessage(MockedStatic<MessageUtils> messageUtilsMock,
	        Holder<Object> sentStaffMessage) {
		messageUtilsMock.when(() -> MessageUtils.sendStaffMessage(any(Object.class), any(Sound.class)))
		        .then((invocation) -> {
			        Object msg = invocation.getArgument(0);
			        sentStaffMessage.set(msg);
			        return true;
		        });
	}

}
