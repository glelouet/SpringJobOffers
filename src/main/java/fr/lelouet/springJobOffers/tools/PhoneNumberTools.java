package fr.lelouet.springJobOffers.tools;

public class PhoneNumberTools {

	public static String standardizePhoneNumber(String number) {
		if (number == null) {
			return null;
		}
		number = number.replaceAll("\\s+", "");
		switch (number.length()) {
		case 10:
			if (number.startsWith("0")) {
				number = "+33" + number.substring(1);
			}
			break;
		}
		return number;
	}
}
