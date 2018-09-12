package com.gregory.revelation;

public class FlavorGenerator {

    /*these appear randomly on the game screen as a fun little thing*/
    /*these are not meant to be professional and do not reflect professional behavior*/
    private static String[] texts = {
            "you exist. congratulations!",
            "truth is, we're all fucked up.",
            "if you hear voices, see a doctor.",
            "life is strange.",
            "adults post the darndest things.",
            "if they make you feel small, lose their number",
            "when it's a tossup, choose to be kind.",
            "hate must never win.",
            "listen to pink floyd some time.",
            "don't make people feel bad for liking things.",
            "thank you for playing my little game.",
            "like the game? tell your friends!",
            "what is up with the color red?",
            "rick and morty won an emmy. nice!",
            "take a deep breath and relax.",
            "black is a beautiful color.",
            "i'm not actually sad, it's just my aesthetic.",
            "do you need a brain update?",
            "the world is crazy, and getting crazier.",
            "there are no points in this game.",
            "holy shit, we landed on the moon!",
            "marijuana is slightly psychedelic.",
            "once you get the message, hang up the phone.",
            "probably don't have sex with your roommate!",
            "give someone near you a hug.",
            "there is no replacement for a reading habit.",
            "be creative! make things! you have ideas!",
            "one must imagine sisyphus happy.",
            "if it won't make you proud, don't do it!",
            "every dog is a good dog. no exceptions.",
            "cats probably know something we don't.",
            "appreciate someone? let them know!",
            "vegan men live 25% longer than omnivores",
            "brevity is the soul of wit.",
            "our planet is dying at an alarming rate.",
            "taylor swift is 25 years old",
            "life has a way of fixing itself.",
            "darling, you are definitely real.",
            "i love you.",
            "old school runescape: you should play it.",
            "find ethical inconsistencies in your life.",
            "think for yourself as much as possible.",
            "we are all stardust.",
            "psychedelics are crazy.",
            "become an expert in something.",
            "you've changed... for the better.",
            "a pet is great for your mental health.",
            "happiness is a journey, not a goal.",
            "this game doesn't even use the internet.",
            "water is, by definition, refreshing.",
            "is water wet or does water get things wet?",
            "immigrants commit less crimes than citizens.",
            "college rules.",
            "always be suspicious of your dentist.",
            "you should not be here. leave.",
            "he screams, for he can do nothing else.",
            "there was never hope in the first place.",
            "don't worry. we forgive you.",
            "begone, thought!",
            "i take cash, credit and debit. never lobster.",
            "pepsi is never okay.",
            "just say no to non-orientable shapes.",
            "why.",
            "i scream into the void. it answers with jazz.",
            "vimeo seems like the hot thing right now.",
            "wanna go out with me?",
            "the moon is nervous again.",
            "hate your gender? try astral projection.",
            "loading...",
            "are you having fun?",
            "it's ok to express yourself sexually.",
            "you can play this game one handed.",
            "this is a low-key game.",
            "does this even qualify as a game?",
            "*internal screaming*",
            "what is going on right now?",
            "help? there is no help.",
            "they're trying to build a prison.",
            "why do they always send the poor?!",
            "blast off! it's party time.",
            "where are you?",
            "still thinking. sorry...",
            "such a lonely day, and it's mine.",
            "what happened on the worst day of your life?",
            "sorry, it's my first time.",
            "tell rebecca that i think she's cute.",
            "i write these when i'm bored.",
            "if i don't make this, i won't make anything.",
            "if you don't laugh, you'll cry.",
            "don't let your dreams be memes.",
            "don't let your memes be dreams",
            "the word meme predates the internet.",
            "i'm feeding off of your gps data (jk).",
            "alright.",
            "ok.",
            "be careful of the milkman.",
            "i am the milkman. my milk is amazing.",
            "do not fear death. you've got to go some time.",
            "guacamole is definitely pretty extra.",
            "now introducing: fried coke.",
            "if i had any more beefs with you, it'd be a farm",
            "now introducing: red lemons.",
            "now introducing: purple oranges.",
            "please don't do coke in my bathroom",
            "searching for cell signal...",
            "hey there delilah.",
            "what makes you think a man made this?",
            "failed to add friend. she is too far away.",
            "sorry, come again?",
            "have you ever kissed a spider?",
            "she was beautiful. her smile was radiant.",
            "shelby put the 4 in 416",
            "if you have ugly thoughts, see a counselor.",
            "are you feeling it now?",
            "this game is about relaxing with your friends.",
            "have you ever tasted the color yellow?",
            "kink shaming is bad. don't do it.",
            "be excellent to each other.",
            "man gets self in hole. gets out again. classic.",
            "oh, how... how i wish you were here.",
            "i wonder if anyone will ever play this game.",
            "it would be embarrassing if this got popular.",
            "wow, what just happened?",
            "are you up or down?",
            "this isn't even my final form.",
            "before you touch anyone anywhere: ask.",
            "consent is not just sexy: it's required.",
            "drugs are fun, but terrible for productivity.",
            "bad decisions will always catch up to you.",
            "the world is one big, very kinky orgy.",
            "what do you want from me?",
            "should you stay or should you go?"
    };

    public static String grabFlavor(){
        return texts[Util.getRandomIntegerBetweenRange(0, texts.length)];
    }
}
