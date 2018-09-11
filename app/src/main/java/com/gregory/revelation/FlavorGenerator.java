package com.gregory.revelation;

public class FlavorGenerator {

    private static String[] texts = {
            "you exist. congratulations!",
            "truth is, we're all fucked up.",
            "if you hear voices, see a doctor.",
            "life is strange.",
            "adults post the darndest things.",
            "if they make you feel small, lose their number",
            "when it's a tossup, choose to be kind.",
            "hate must never win.",
            "listen to Pink Floyd some time.",
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
            "as of writing this, taylor swift is 25 years old",
            "life has a way of fixing itself.",
            "darling, you are definitely real.",
            "i love you.",
            "old school runescape exists and you should play it.",
            "strive for ethical consistency in your life.",
            "philosophy is really interesting to read. try it.",
            "try to think for yourself as much as possible.",
            "we are all stardust.",
            "psychedelics are crazy.",
            "become an expert in something.",
            "you've changed... for the better.",
            "a pet is great for your mental health."
    };

    public static String grabFlavor(){
        return texts[getRandomIntegerBetweenRange(0, texts.length)];
    }

    public static int getRandomIntegerBetweenRange(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
