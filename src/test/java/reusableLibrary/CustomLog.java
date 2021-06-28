package reusableLibrary;

import org.assertj.core.api.Fail;
import org.springframework.util.StringUtils;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepEventBus;

public class CustomLog {

	public static class LogInfo {

		public static void WithoutScreenShot(String message, String status) {
			String logMessage;
			if (status.equalsIgnoreCase("fail")) {
				logMessage = StringUtils.replace(message, " ", "&nbsp;");
				StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(logMessage + " Pass"));
				StepEventBus.getEventBus().stepFinished();
			} else if (status.equalsIgnoreCase("fail")) {
				Fail.fail("Validation Failed at: " + message);

			}

		}

		public static void WithoutScreenShot(String message) {
			String logMessage;
			logMessage = StringUtils.replace(message, " ", "&nbsp;");
			StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(logMessage + " Pass"));
			StepEventBus.getEventBus().stepFinished();

		}

		public static void WithScreenShot(String message, String status) {
			String logMessage;
			if (status.equalsIgnoreCase("fail")) {
				logMessage = StringUtils.replace(message, " ", "&nbsp;");
				StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(logMessage + " Pass"));
				Serenity.takeScreenshot();
				StepEventBus.getEventBus().stepFinished();
			} else if (status.equalsIgnoreCase("fail")) {
				Serenity.takeScreenshot();
				Fail.fail("Validation Failed at: " + message);

			}

		}

		public static void WithScreenShot(String message) {
			String logMessage;
			logMessage = StringUtils.replace(message, " ", "&nbsp;");
			StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(logMessage + " Pass"));
			Serenity.takeScreenshot();
			StepEventBus.getEventBus().stepFinished();

		}
	}
}
