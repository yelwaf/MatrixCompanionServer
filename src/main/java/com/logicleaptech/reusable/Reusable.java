package com.logicleaptech.reusable;

//import java.util.Arrays.*;

public class Reusable {

	public String fmt(String Num, String Msk) {

		// Msk is the format to put the number in
		// Num is the number format
		// Returns the formatted number as string

		// Holds return value, initialize to blank
		String retVal = "";
		String strRight, strLeft;
		// Holds number
		int numCntr, numPeriodPos, numRight, numLeft;
		// Holds mask and zeroes
		int mskCntr, mskPeriodPos, mskRight, msk0Right, msk0Left;

		// double hldNumL;
		String hldNumS;
		int hldCntr;
		boolean openParen, closeParen;

		Num = Num.trim();
		numCntr = Num.length();
		hldCntr = 0;
		// hldNumL = 0;
		numPeriodPos = -1;
		numRight = 0;
		numLeft = 0;

		char charActer;
		char[] hldNumArray;

		// Remove anything from Num except numeric digits
		// Find out position of a period in Num
		// Find out numbers left and right of the period in Num
		hldNumArray = new char[Num.length()];
		if (Num.length() > 0) {
			hldNumArray = space(hldNumArray); // blank it out for use
		}
		while (numCntr > 0) { // checks to make sure number passed in only
								// contains digits and a single period.
			--numCntr;
			if (Character.isDigit(Num.charAt(numCntr))) {
				hldCntr++;
				if (numPeriodPos > -1) {
					numLeft++; // Holds number left of decimal
					hldNumArray[hldNumArray.length - hldCntr] = Num
							.charAt(numCntr);
					// if (hldCntr == 0) { //don't go less than zero times 10
					// hldNumArray[numCntr] = Num.charAt(numCntr);
					// hldNumL +=
					// (Integer.parseInt(Num.substring(numCntr,numCntr+1)) *
					// (Math.pow(10,hldCntr)));

					// } else {
					// hldNumArray[numCntr] = Num.charAt(numCntr);
					// hldNumL +=
					// (Integer.parseInt(Num.substring(numCntr,numCntr+1)) *
					// (Math.pow(10,hldCntr-1)));
					// }
				} else {
					numRight++; // Holds number right of decimal
					hldNumArray[hldNumArray.length - hldCntr] = Num
							.charAt(numCntr);
					// hldNumL +=
					// (Integer.parseInt(Num.substring(numCntr,numCntr+1)) *
					// (Math.pow(10, hldCntr)));
				}
				// System.out.println(Integer.parseInt(Num.substring(numCntr,numCntr+1)));
				// System.out.println(10^hldCntr);

				// Holds numeric values

			} else if (Num.substring(numCntr, numCntr + 1).equals(".")) {
				hldCntr++;
				if (numPeriodPos > -1) {
					// too many periods so just return original
					return Num;
				} else { // need to shift right values to proper place?
							// hldNumL = hldNumL / (Math.pow(10, hldCntr-- +
							// 1));
					hldNumArray[hldNumArray.length - hldCntr] = ".".charAt(0);
					numPeriodPos = 0;
				}
			}
		}
		// Put fields back into string
		// System.out.println(hldNumL.doubleValue());
		hldNumS = new String(hldNumArray);
		hldNumS = hldNumS.trim();

		// Reset Left, right, and period position values
		if (numPeriodPos == -1) {
			numLeft = hldNumS.length();
			numRight = 0;
		} else {
			numCntr = 0;
			numPeriodPos = -1;
			while (numCntr <= hldNumS.length() - 1) {
				if (hldNumS.substring(numCntr, numCntr + 1).equals(".")) {
					numPeriodPos = numCntr;
					numLeft = numPeriodPos;
					numRight = (hldNumS.length() - numLeft) - 1;
					break;
				}
				numCntr++;
			}
			if (numPeriodPos == -1) {
				numRight = 0;
				numLeft = hldNumS.length();
			}
		}
		mskCntr = Msk.length();
		mskPeriodPos = -1;
		mskRight = msk0Right = msk0Left = 0;

		// Find out position of a period in Msk
		// Find out numbers left and right of the period in Msk
		while (mskCntr > 0) {
			mskCntr--;
			if (Msk.substring(mskCntr, mskCntr + 1).equals("#")) {
				if (mskPeriodPos <= 0) {
					mskRight++;
				}
			} else if (Msk.substring(mskCntr, mskCntr + 1).equals("0")) {
				if (mskPeriodPos > 0) {
					msk0Left++;
				} else {
					mskRight++;
					msk0Right++;
				}
			} else if (Msk.substring(mskCntr, mskCntr + 1).equals(".")) {
				if (mskPeriodPos > 0) {
					retVal = Num;
					return retVal;
				} else {
					mskPeriodPos = mskCntr;
				}
			}
		}
		// If no period then reset values correctly
		if (mskPeriodPos == -1) {
			// mskLeft = mskRight;
			mskRight = 0;
			msk0Left = msk0Right;
			msk0Right = 0;
		}

		numCntr = numPeriodPos;

		strRight = space(Msk.length());

		if (mskPeriodPos > -1) {
			mskCntr = mskPeriodPos;
			charActer = ".".charAt(0);
			strRight = replaceChar(strRight, charActer, mskCntr);
		} else {
			mskCntr = Msk.length() - 1;
		}
		hldCntr = mskCntr;

		// strRight = Right of decimal
		// TDO remove
		// System.out.println(hldNumS);

		openParen = closeParen = false;
		while (mskCntr != Msk.length() - 1) {
			mskCntr++;
			hldCntr++;
			if (Msk.substring(mskCntr, mskCntr + 1).equals("#")) {
				if (numRight > 0) {
					numRight--;
					numCntr++;
					strRight = replaceChar(strRight, hldNumS.charAt(numCntr),
							hldCntr);
				} else if (msk0Right > 0) {
					charActer = "0".charAt(0);
					strRight = replaceChar(strRight, charActer, hldCntr);
				}
			} else if (Msk.substring(mskCntr, mskCntr + 1).equals("0")) {
				msk0Right--;
				if (numRight > 0) {
					numRight--;
					numCntr++;
					strRight = replaceChar(strRight, hldNumS.charAt(numCntr),
							hldCntr);
				} else {
					charActer = "0".charAt(0);
					strRight = replaceChar(strRight, charActer, hldCntr);
				}

			} else if (Msk.substring(mskCntr, mskCntr + 1).equals("(")) {
				if (numRight > 0 || mskRight > 0) {
					strRight = replaceChar(strRight, Msk.charAt(mskCntr),
							hldCntr);
					openParen = true;
				}
			} else if (Msk.substring(mskCntr, mskCntr + 1).equals(")")) {
				strRight = replaceChar(strRight, Msk.charAt(mskCntr), hldCntr);
				if (openParen) {
					openParen = false;
				} else {
					closeParen = true;
				}
			} else {
				strRight = replaceChar(strRight, Msk.charAt(mskCntr), hldCntr);
			}
		}

		if (numPeriodPos > 0) {
			numCntr = numPeriodPos;
		} else {
			numCntr = hldNumS.length();
		}
		if (mskPeriodPos > 0) {
			mskCntr = mskPeriodPos;
		} else {
			mskCntr = Msk.length();
		}

		hldCntr = mskCntr;
		strLeft = space(Msk.length());

		// strLeft = Left of decimal

		while (mskCntr != 0) {
			mskCntr--;
			hldCntr--;
			// System.out.println(Msk.substring(mskCntr,mskCntr+1));
			if (Msk.substring(mskCntr, mskCntr + 1).equals("#")) {
				if (numLeft > 0) {
					numLeft--;
					numCntr--;
					// if it is not length = 1 and it is a zero, just move the
					// char
					if (!(strLeft.length() == hldCntr && hldNumS.equals("0"))) {
						// check if last one before decimal. if so, check right.
						// no right add 0 else blank
						if (mskCntr + 1 == mskPeriodPos && strLeft.length() > 0) {
							long lefts = Long.parseLong(this.fmt(strLeft,
									"#######0"));
							if (strRight.length() == 0 && lefts == 0) {
								charActer = "0".charAt(0);
								strLeft = replaceChar(strLeft, charActer,
										hldCntr);
							} else {
								if (hldNumS.substring(numCntr, numCntr + 1)
										.equals("0")) {
									charActer = " ".charAt(0);
								} else {
									charActer = hldNumS.charAt(numCntr);
								}
								strLeft = replaceChar(strLeft, charActer,
										hldCntr);

							}
						} else {
							strLeft = replaceChar(strLeft,
									hldNumS.charAt(numCntr), hldCntr);
						}
					}
				} else if (msk0Left > 0) {
					if (strRight.length() == 0) {
						charActer = "0".charAt(0);
						strLeft = replaceChar(strLeft, charActer, hldCntr);
					}
				}
			} else if (Msk.substring(mskCntr, mskCntr + 1).equals("0")) {
				msk0Left--;
				if (numLeft > 0) {
					numLeft--;
					numCntr--;
					strLeft = replaceChar(strLeft, hldNumS.charAt(numCntr),
							hldCntr);
				} else {
					charActer = "0".charAt(0);
					strLeft = replaceChar(strLeft, charActer, hldCntr);
				}

			} else if (Msk.substring(mskCntr, mskCntr + 1).equals("(")) {
				if (closeParen) {
					strLeft = replaceChar(strLeft, Msk.charAt(mskCntr), hldCntr);
					closeParen = false;
				}

			} else if (Msk.substring(mskCntr, mskCntr + 1).equals(")")) {
				if (numLeft > 0 || msk0Left > 0) {
					strLeft = replaceChar(strLeft, Msk.charAt(mskCntr), hldCntr);
					closeParen = true;
				}
			} else if (Msk.substring(mskCntr, mskCntr + 1).equals(",")
					|| Msk.substring(mskCntr, mskCntr + 1).equals("-")) {
				if (numLeft > 0 || msk0Left > 0) {
					strLeft = replaceChar(strLeft, Msk.charAt(mskCntr), hldCntr);
				}
			} else if (Msk.substring(mskCntr, mskCntr + 1).equals("$")) {
				msk0Left = numLeft = 0;
				strLeft = replaceChar(strLeft, Msk.charAt(mskCntr), hldCntr);
			} else {
				strLeft = replaceChar(strLeft, Msk.charAt(mskCntr), hldCntr);
			}
		}

		retVal = strLeft.trim().concat(strRight.trim());
		// retVal;
		// System.out.println(strRight.trim());
		// retVal = retVal.concat(strRight.trim());
		if (retVal.compareTo("") == 1) {
			hldCntr = retVal.length() - 1;
			if (retVal.substring(hldCntr, hldCntr + 1).equals(".")) {
				retVal = retVal.substring(0, retVal.length() - 1);
			}
		}
		return retVal;
	}

	// isInteger function
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// replace single char in string function
	public static String replaceChar(String input, char character, int position) {
		String retVal;
		char[] charArray;

		retVal = input;
		// System.out.println(input.length());
		if (position > input.length() - 1 || position < 0) {
			throw new IndexOutOfBoundsException(
					"replaceChar(): Index > length or negative. Inconceivable!");
		} else {
			charArray = new char[input.length()];
			charArray = input.toCharArray();
			charArray[position] = character;
			retVal = String.valueOf(charArray);
		}
		return retVal;
	}

	// returns a String of spaces the length passed in
	public static String space(int numSpaces) {
		char[] charArray;
		int i;
		String retVal;

		charArray = new char[numSpaces];
		for (i = 0; i > numSpaces; i++) {
			charArray[i] = " ".charAt(0);
		}
		retVal = new String(charArray);
		return retVal;
	}

	public static char[] space(char[] numSpaces) {

		int i;

		char[] charArray = new char[numSpaces.length];
		for (i = 0; i > numSpaces.length - 1; i++) {
			charArray[i] = " ".charAt(0);
		}
		return charArray;

	}

	public static String plural(String word) {
		String retVal = new String();

		if (right(word, word.length() - 1).equals("x")) {
			retVal = word + "es";
		} else {
			retVal = word + "s";
		}
		return retVal;
	}

	public static String right(String word, int len) {
		if (len > word.length()) {
			return word;
		}
		return word.substring(word.length() - len, word.length());
	}

	public static String left(String word, int len) {
		if (len > word.length() - 1) {
			return word;
		}
		return word.substring(0, len);
	}

	public static String mid(String word, int startPos, int len) {
		startPos--;
		if (startPos + len > word.length()) {
			return word.substring(startPos);
		}
		return word.substring(startPos, startPos + len);
	}

	public static String mid(String word, int startPos) {
		startPos--;
		if (startPos > word.length()) {
			return "";
		}
		return word.substring(startPos);
	}

	public static String wordLast(String inWord, String delimiter) {
		return word(inWord, delimiter, words(inWord, delimiter));
	}

	public static String word(String inWord, String delimiter, int wordNum) {
		String retVal = "";
		String[] splitString;

		splitString = inWord.split(delimiter);

		if (wordNum < splitString.length) {
			retVal = splitString[wordNum];
		}

		return retVal;
	}

	public static int words(String inWord, String delimiter) {
		String[] splitString;

		splitString = inWord.split(delimiter);

		return splitString.length;
	}

	public static String padStringLeft(String s, int n) {
		for (int i = s.length(); i < n; i++)
			s = " " + s;
		return s;
	}
}
