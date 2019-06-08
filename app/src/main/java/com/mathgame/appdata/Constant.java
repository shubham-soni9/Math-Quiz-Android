package com.mathgame.appdata;

public interface Constant {
    String EMPTY                  = "";
    String GENERATOR_NOTIFICATION = "generator_notification";
    int    QUESTION_DELAY_TIME    = 300;

    enum AppLanguage {
        ARABIC("العربية", "ar"),
        CHINESE("中国（北京话）", "zh"),
        CZECH("čeština", "cs"),
        DANISH("dansk", "da"),
        DUTCH("Nederlands", "nl"),
        ENGLISH("English", "en"),
        FILIPINO("Pilipino", "fil"),
        FRENCH("français", "fr"),
        GEORGIAN("ქართული", "ka"),
        GERMAN("Deutsche", "de"),
        GREEK("ελληνικά", "el"),
        HINDI("हिंदी", "hi"),
        HUNGARAIAN("Magyar", "hu"),
        INDONESIAN("bahasa Indonesia", "in"),
        ITALIAN("italiano", "it"),
        JAPANESE("日本語", "ja"),
        MALAY("Malay", "ms"),
        PERSIAN("فارسی", "fa"),
        POLISH("POLSKIE", "pl"),
        PORTUGUESE("português", "pt"),
        RUSSIAN("русский", "ru"),
        SPANISH("Español", "es"),
        SWAHILI("Kiswahili", "sw"),
        THAI("ไทย", "th"),
        TURKISH("Türk", "tr"),
        VIETNAMESE("Tiếng Việt", "vi"),
        KOREAN("한국어", "ko");

        public final String code;
        public final String name;

        AppLanguage(String name, String code) {
            this.code = code;
            this.name = name;
        }

        /**
         * Method to get the Language via the ISO Code
         */
        public static AppLanguage getLanguageByCode(String code) {

            AppLanguage mLanguage = null;

            for (AppLanguage language : values()) {
                if (language.code.equals(code)) {
                    mLanguage = language;
                    break;
                }
            }

            return mLanguage == null ? ENGLISH : mLanguage;
        }
    }

    interface MathSign {
        String ADDITION       = "+";
        String SUBTRACTION    = "-";
        String MULTIPLICATION = "*";
        String DIVISION       = "/";
        String PERCENTAGE     = "%";
        String SQUARE_ROOT    = "sqrt()";
    }

    interface QuestionFormat {
        String FORMAT_1  = "a + b = ?";
        String FORMAT_2  = "a - b = ?";
        String FORMAT_3  = "a * b = ?";
        String FORMAT_4  = "a / b = ?";
        String FORMAT_5  = "a + b + c = ?";
        String FORMAT_6  = "a + b - c = ?";
        String FORMAT_7  = "a + b * c = ?";
        String FORMAT_8  = "a + b / c = ?";
        String FORMAT_9  = "a - b + c = ?";
        String FORMAT_10 = "a - b - c = ?";
        String FORMAT_11 = "a - b * c = ?";
        String FORMAT_12 = "a - b / c = ?";
        String FORMAT_13 = "a * b + c = ?";
        String FORMAT_14 = "a * b - c = ?";
        String FORMAT_15 = "a * b * c = ?";
        String FORMAT_16 = "a * b / c = ?";
        String FORMAT_17 = "a / b * c = ?";
        String FORMAT_18 = "a / b - c = ?";
        String FORMAT_19 = "a / b + ? = c";
        String FORMAT_20 = "a * b - ? = c";
        String FORMAT_21 = "a * (b / c) = ?";
        String FORMAT_22 = "(a * b) - ? = c";
        String FORMAT_23 = "(a * b) / c = ?";
        String FORMAT_24 = "(a * ?) - b = c";
        String FORMAT_25 = "(a * ?) / b = c";
        String FORMAT_26 = "(a * b) * ? = c";
        String FORMAT_27 = "(sqt(a) * ?) - b = c";
        String FORMAT_28 = "(a * ?) - sqt(b) = c";
        String FORMAT_29 = "a + ? = b % c";
        String FORMAT_30 = "(a * b) - sqt(c) = ?";
    }

    interface DifficultyLevel {
        int SMALL  = 0;
        int MEDIUM = 1;
        int LARGE  = 2;
    }

    interface JSONFileNames {
        String TUTORIAL_FILE   = "tutorial.json";
        String CAREER_QUESTION = "career_question.json";
    }

    interface AnswerType {
        int CORRECT   = 1;
        int INCORRECT = 2;
        int SKIPPED   = 3;
    }
}

