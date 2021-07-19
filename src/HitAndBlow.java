import java.util.Random;
import java.util.Scanner;

public class HitAndBlow {
    private static final Scanner STDIN = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static final boolean IS_DEBUG_MODE = true;
    private static final int ROUND_OF_STARTING = 1;
    private static final int DIGITS = 2;
    private static final int SPECIFIC_ROUND = 3;
    private static final int MAX_RANGE_FOR_ONE_DIGIT = 10;

    private static final String MESSAGE_FORMAT_FOR_DEBUG_CORRECT = "[DEBUG] 正解=%s %n";
    private static final String MESSAGE_FOR_INPUT_WITH_NUM = "数字で入力してください";
    private static final String MESSAGE_FORMAT_FOR_INPUT_IN_DIGITS = "%d桁で入力してください %n";
    private static final String MESSAGE_FORMAT_FOR_INPUT_NUM_SEQUENCE_IN_DIGITS = "[%d 回目] %d桁の数字を入力してください: ";
    private static final String MESSAGE_FOR_NO_OVERLAPPED_NUM = "数字の並びに同じ数字を使わないでください";
    private static final String MESSAGE_FORMAT_FOR_JUDGEMENT_RESULTS = "ヒット：%d個、ブロー：%d個 %n";
    private static final String MESSAGE_FORMAT_FOR_SHOW_SUCCESS_IN_ROUND = "おめでとう！%d回目で成功♪ %n";
    private static final String MESSAGE_FOR_HINT_AS_OPEN_ONE_DIGIT = "[ヒント] %d 桁目の数字は %s です %n";
    private static final String MESSAGE_FOR_ALREADY_OPENED_ALL_HINT = "ヒントとしてすべての桁の数字は公開済みです";

    public static void main(String[] args) {
        int round = ROUND_OF_STARTING;
        String correctNumSequenceStr = generateNumSequence(DIGITS);
        showDebugCorrectNumSeq(correctNumSequenceStr, IS_DEBUG_MODE);

        playGame(correctNumSequenceStr, round);
    }

    private static void playGame(String correctNumSequenceStr, int round) {
        showRequireInputNumSequenceInDigits(round, DIGITS);
        String userAnswerNumSequenceStr = recieveInputtedNumInRangeStr(DIGITS);

        if (!isCorrect(correctNumSequenceStr, userAnswerNumSequenceStr)) {
            int hits = countHits(correctNumSequenceStr,
                    userAnswerNumSequenceStr);
            int blows = countBlows(correctNumSequenceStr,
                    userAnswerNumSequenceStr);
            showJudgementResults(hits, blows);

            if (isPerSpecificRounds(SPECIFIC_ROUND, round)) {
                showHintAsOpenOneDigit(correctNumSequenceStr, round);
            }

            round++;
            playGame(correctNumSequenceStr, round);
            return;
        }
        showSuccess(round);
    }

    private static void showHintAsOpenOneDigit(String correctNumSequenceStr,
            int round) {
        int digitPlaceForOpening = calcDigitIndxForOpening(SPECIFIC_ROUND,
                round);

        if (isAlreadyOpenedAllDigits(digitPlaceForOpening, DIGITS)) {
            show(MESSAGE_FOR_ALREADY_OPENED_ALL_HINT);
            return;
        }

        String numForOpening = pickUpNumForOpening(correctNumSequenceStr,
                digitPlaceForOpening);

        showHintAsOpenOneDigit(digitPlaceForOpening, numForOpening);
    }

    private static void showHintAsOpenOneDigit(int digitPlaceForOpening,
            String numForOpening) {
        System.out.format(MESSAGE_FOR_HINT_AS_OPEN_ONE_DIGIT,
                digitPlaceForOpening, numForOpening);
    }

    private static String pickUpNumForOpening(String correctNumSequenceStr,
            int digitPlaceForOpening) {
        int digitIndxForOpening = digitPlaceForOpening - 1;
        return pickUpOneChar(correctNumSequenceStr, digitIndxForOpening);
    }

    private static int calcDigitIndxForOpening(int specificRound, int round) {
        return round / specificRound;
    }

    private static boolean isAlreadyOpenedAllDigits(int digitIndxForOpening,
            int digits) {
        return digitIndxForOpening > DIGITS;
    }

    private static boolean isPerSpecificRounds(int specificRound, int round) {
        return round % specificRound == 0;
    }

    private static void showDebugCorrectNumSeq(String correct,
            boolean isDebugMode) {
        if (!isDebugMode) {
            return;
        }
        System.out.format(MESSAGE_FORMAT_FOR_DEBUG_CORRECT, correct);
    }

    private static void showSuccess(int round) {
        System.out.format(MESSAGE_FORMAT_FOR_SHOW_SUCCESS_IN_ROUND, round);
    }

    private static int countHits(String correct, String answer) {
        int hits = 0;
        for (int i = 0; i < correct.length(); i++) {
            String oneDigitOfCorrect = pickUpOneChar(correct, i);
            String oneDigitOfAnswer = pickUpOneChar(answer, i);

            if (oneDigitOfCorrect.equals(oneDigitOfAnswer)) {
                hits++;
            }
        }
        return hits;
    }

    private static int countBlows(String correct, String answer) {
        int blows = 0;

        for (int i = 0; i < correct.length(); i++) {

            for (int j = 0; j < answer.length(); j++) {
                if (isCheckSelf(i, j)) {
                    continue;
                }

                String oneDigitOfCorrect = pickUpOneChar(correct, i);
                String oneDigitOfAnswer = pickUpOneChar(answer, j);

                if (oneDigitOfCorrect.equals(oneDigitOfAnswer)) {
                    blows++;
                }
            }
        }
        return blows;
    }

    private static String pickUpOneChar(String str, int index) {
        return str.substring(index, index + 1);
    }

    private static boolean isCheckSelf(int i, int j) {
        return i == j;
    }

    private static void showJudgementResults(int counterHits,
            int counterBlows) {
        System.out.format(MESSAGE_FORMAT_FOR_JUDGEMENT_RESULTS, counterHits,
                counterBlows);
    }

    private static void showRequireInputNumSequenceInDigits(int round,
            int digit) {
        System.out.format(MESSAGE_FORMAT_FOR_INPUT_NUM_SEQUENCE_IN_DIGITS,
                round, digit);
    }

    private static String recieveInputtedNumInRangeStr(int digit) {
        String inputtedNumStr = recieveInputtedNumStr();
        inputtedNumStr = validate(inputtedNumStr, digit);
        return inputtedNumStr;
    }

    private static String validate(String inputtedNumStr, int digit) {
        if (!isNum(inputtedNumStr)) {
            show(MESSAGE_FOR_INPUT_WITH_NUM);
            return recieveInputtedNumInRangeStr(digit);
        }

        if (!isInDigit(inputtedNumStr, digit)) {
            showRequireInputInDigits(digit);
            return recieveInputtedNumInRangeStr(digit);
        }

        if (hasOverlappedNum(inputtedNumStr)) {
            show(MESSAGE_FOR_NO_OVERLAPPED_NUM);
            return recieveInputtedNumInRangeStr(digit);
        }
        return inputtedNumStr;
    }

    private static void showRequireInputInDigits(int digit) {
        System.out.format(MESSAGE_FORMAT_FOR_INPUT_IN_DIGITS, digit);
    }

    private static void show(String message) {
        System.out.println(message);
    }

    private static boolean hasOverlappedNum(String str) {
        for (int i = 0; i < str.length(); i++) {

            String num = pickUpOneChar(str, i);
            for (int j = i; j < str.length(); j++) {
                if (i == j) {
                    continue;
                }

                String target = pickUpOneChar(str, j);
                if (num.equals(target)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isInDigit(String str, int digit) {
        return str.length() == digit;
    }

    private static boolean isNum(String str) {
        try {
            parseToInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static int parseToInt(String str) {
        return Integer.parseInt(str);
    }

    private static String recieveInputtedNumStr() {
        return STDIN.nextLine();
    }

    private static boolean isCorrect(String correct, String answer) {
        return correct.equals(answer);
    }

    private static String generateNumSequence(int digit) {
        String generatedNumSequence = "";

        for (int i = 1; i <= digit; i++) {
            generatedNumSequence += RANDOM.nextInt(MAX_RANGE_FOR_ONE_DIGIT);

            if (hasOverlappedNum(generatedNumSequence)) {
                return generateNumSequence(digit);
            }
        }
        return generatedNumSequence;
    }
}