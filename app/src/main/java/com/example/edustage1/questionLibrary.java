package com.example.edustage1;

public class questionLibrary {

    private String mQuestions[] = {
            "At a party do you", "Are you more", "Is it worse to", "Are you more impressed by", "Are more drawn toward the",
            "Do you prefer to work", "Do you tend to choose", "At parties do you", "Are you more attracted to", "Are you more interested in",

            "In judging others are you more swayed by", "In approaching others is your inclination to be somewhat", "Are you more", "Does it bother you more having things", "In your social groups do you:",
            "In doing ordinary things are you more likely to", "Writers should", "Which appeals to you more", "Are you more comfortable in making", "Do you want things:",

            "Would you say you are more", "In phoning do you", "Facts", "Are visionaries", "Are you more often",
            "Is it worse to be", "Should one usually let events occur", "Do you feel better about", "In company do you", "Common sense is",

            "Children often do not", "In making decisions do you feel more comfortable with", "Are you more", "Which is more admirable", "Do you put more value on",
            "Does new and non-routine interaction with others", "Are you more frequently", "Are you more likely to", "Which is more satisfying", "Which rules you more",

            "Are you more comfortable with work that is", "Do you tend to look for", "Do you prefer", "Do you go more by", "Are you more interested in",
            "Which is more of a compliment", "Do you value in yourself more that you are", "Do you more often prefer the", "Are you more comfortable", "Do you",

            "Are you more likely to trust your", "Do you feel", "Which person is more to be complimented – one of", "Are you inclined more to be", "Is it preferable mostly to",
            "In relationships should most things be", "When the phone rings do you", "Do you prize more in yourself", "Are you drawn more to", "Which seems the greater error",

            "Do you see yourself as basically", "Which situation appeals to you more", "Are you a person that is more", "Are you more inclined to be", "In writings do you prefer",
            "Is it harder for you to", "Which do you wish more for yourself", "Which is the greater fault", "Do you prefer the", "Do you tend to be more"
    };

    private String mChoices[][] = {
            {"Interact with many, including strangers", "Interact with a few, known to you"}, {"Realistic than speculative", "Speculative than realistic"},
            {"Have your “head in the clouds”", "Be “in a rut”"}, {"Principles", "Emotions"},
            {"Convincing", "Touching"}, {"To deadlines", "Just “whenever”"},
            {"Rather carefully", "Somewhat impulsively"}, {"Stay late, with increasing energy", "Leave early with decreased energy"},
            {"Sensible people", "Imaginative people"}, {"What is actual", "What is possible"},

            {"Laws than circumstances", "Circumstances than laws"}, {"Objective", "Personal"},
            {"Punctual", "Leisurely"}, {"Incomplete", "Completed"},
            {"Keep abreast of other’s happenings", "Get behind on the news"}, {"Do it the usual way", "Do it your own way"},
            {"“Say what they mean and mean what they say”", "Express things more by use of analogy"}, {"Consistency of thought", "Harmonious human relationships"},
            {"Logical judgments", "Value judgments"}, {"Settled and decided", "Unsettled and undecided"},

            {"Serious and determined", "Easy-going"}, {"Rarely question that it will all be said", "Rehearse what you’ll say"},
            {"“Speak for themselves”", "Illustrate principles"}, {"somewhat annoying", "rather fascinating"},
            {"a cool-headed person", "a warm-hearted person"}, {"unjust", "merciless"},
            {"by careful selection and choice", "randomly and by chance"}, {"having purchased", "having the option to buy"},
            {"initiate conversation", "wait to be approached"}, {"rarely questionable", "frequently questionable"},

            {"make themselves useful enough", "exercise their fantasy enough"}, {"standards", "feelings"},
            {"firm than gentle", "gentle than firm"}, {"the ability to organize and be methodical", "the ability to adapt and make do"},
            {"infinite", "open-minded"}, {"stimulate and energize you", "tax your reserves"},
            {"a practical sort of person", "a fanciful sort of person"}, {"see how others are useful", "see how others see"},
            {"to discuss an issue thoroughly", "to arrive at agreement on an issue"}, {"your head", "your heart"},

            {"contracted", "done on a casual basis"}, {"the orderly", "whatever turns up"},
            {"many friends with brief contact", "a few friends with more lengthy contact"}, {"facts", "principles"},
            {"production and distribution", "design and research"}, {"“There is a very logical person.”", "“There is a very sentimental person.”"},
            {"unwavering", "devoted"}, {"final and unalterable statement", "tentative and preliminary statement"},
            {"after a decision", "before a decision"}, {"speak easily and at length with strangers", "find little to say to strangers"},

            {"experience", "hunch"}, {"more practical than ingenious", "more ingenious than practical"},
            {"clear reason", "strong feeling"}, {"fair-minded", "sympathetic"},
            {"make sure things are arranged", "just let things happen"}, {"re-negotiable", "random and circumstantial"},
            {"hasten to get to it first", "hope someone else will answer"}, {"a strong sense of reality", "a vivid imagination"},
            {"fundamentals", "overtones"}, {"to be too passionate", "to be too objective"},

            {"hard-headed", "soft-hearted"}, {"the structured and scheduled", "the unstructured and unscheduled"},
            {"routinized than whimsical", "whimsical than routinized"}, {"easy to approach", "somewhat reserved"},
            {"the more literal", "the more figurative"}, {"identify with others", "utilize others"},
            {"clarity of reason", "strength of compassion"}, {"being indiscriminate", "being critical"},
            {"planned event", "unplanned event"}, {"deliberate than spontaneous", "spontaneous than deliberate"}
    };

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1(int a){
        String choice1 = mChoices[a][0];
        return choice1;
    }

    public String getChoice2(int a){
        String choice2 = mChoices[a][1];
        return choice2;
    }
}
