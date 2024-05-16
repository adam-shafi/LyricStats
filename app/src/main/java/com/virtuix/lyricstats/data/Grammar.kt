package com.virtuix.lyricstats.data

class Grammar {
    companion object {
        val ARTICLES = setOf("a", "an", "the")
        val PROPOSITIONS = setOf(
            "aboard",
            "about",
            "above",
            "absent",
            "across",
            "after",
            "against",
            "along",
            "alongside",
            "amid",
            "amidst",
            "among",
            "amongst",
            "around",
            "as",
            "astride",
            "at",
            "atop",
            "before",
            "afore",
            "behind",
            "below",
            "beneath",
            "beside",
            "besides",
            "between",
            "beyond",
            "by",
            "circa",
            "despite",
            "down",
            "during",
            "except",
            "for",
            "from",
            "in",
            "inside",
            "into",
            "less",
            "like",
            "minus",
            "near",
            "nearer",
            "nearest",
            "notwithstanding",
            "of",
            "off",
            "on",
            "onto",
            "opposite",
            "outside",
            "over",
            "past",
            "per",
            "save",
            "since",
            "through",
            "throughout",
            "to",
            "toward",
            "towards",
            "under",
            "underneath",
            "until",
            "up",
            "upon",
            "upside",
            "versus",
            "via",
            "with",
            "within",
            "without",
            "worth",
            "according to",
            "adjacent to",
            "ahead of",
            "apart from",
            "as of",
            "as per",
            "as regards",
            "aside from",
            "astern of",
            "back to",
            "because of",
            "close to",
            "due to",
            "except for",
            "far from",
            "inside of",
            "instead of",
            "left of",
            "near to",
            "next to",
            "opposite of",
            "opposite to",
            "out from",
            "out of",
            "outside of",
            "owing to",
            "prior to",
            "pursuant to",
            "rather than",
            "regardless of",
            "right of",
            "subsequent to",
            "such as",
            "thanks to",
            "up to",
            "as far as",
            "as opposed to",
            "as soon as",
            "as well as",
            "at the behest of",
            "by means of",
            "by virtue of",
            "for the sake of",
            "in accordance with",
            "in addition to",
            "in case of",
            "in front of",
            "in lieu of",
            "in place of",
            "in point of",
            "in spite of",
            "on account of",
            "on behalf of",
            "on top of",
            "with regard to",
            "with respect to",
            "with a view to"
        )
        val INTERJECTIONS = setOf(
            "aah",
            "ack",
            "agreed",
            "ah",
            "aha",
            "ahem",
            "alas",
            "all right",
            "amen",
            "argh",
            "as if",
            "aw",
            "ay",
            "aye",
            "bah",
            "blast",
            "boo hoo",
            "bother",
            "boy",
            "brr",
            "by golly",
            "bye",
            "cheerio",
            "cheers",
            "chin up",
            "come on",
            "crikey",
            "curses",
            "dear me",
            "doggone",
            "drat",
            "duh",
            "easy does it",
            "eek",
            "egads",
            "er",
            "exactly",
            "fair enough",
            "fiddle-dee-dee",
            "fiddlesticks",
            "fie",
            "foo",
            "fooey",
            "gadzooks",
            "gah",
            "gangway",
            "g'day",
            "gee",
            "gee whiz",
            "geez",
            "gesundheit",
            "get lost",
            "get outta here",
            "go on",
            "good",
            "good golly",
            "good job",
            "gosh",
            "gracious",
            "great",
            "grr",
            "gulp",
            "ha",
            "ha-ha",
            "hah",
            "hallelujah",
            "harrumph",
            "haw",
            "hee",
            "here",
            "hey",
            "hmm",
            "ho hum",
            "hoo",
            "hooray",
            "hot dog",
            "how",
            "huh",
            "hum",
            "humbug",
            "hurray",
            "huzza",
            "I say",
            "ick",
            "is it",
            "ixnay",
            "jeez",
            "just kidding",
            "just a sec",
            "just wondering",
            "kapish",
            "la",
            "la-di-dah",
            "lo",
            "look",
            "look here",
            "long time",
            "lordy",
            "man",
            "meh",
            "mmm",
            "most certainly",
            "my",
            "my my",
            "my word",
            "nah",
            "naw",
            "never",
            "no",
            "no can do",
            "nooo",
            "not",
            "no thanks",
            "no way",
            "nuts",
            "oh",
            "oho",
            "oh-oh",
            "oh no",
            "okay",
            "okey-dokey",
            "om",
            "oof",
            "ooh",
            "oopsey",
            "over",
            "oy",
            "oyez",
            "peace",
            "pff",
            "pew",
            "phew",
            "pish posh",
            "psst",
            "ptui",
            "quite",
            "rah",
            "rats",
            "ready",
            "right",
            "right on",
            "roger",
            "roger that",
            "rumble",
            "say",
            "see ya",
            "shame",
            "shh",
            "shoo",
            "shucks",
            "sigh",
            "sleep tight",
            "snap",
            "sorry",
            "sssh",
            "sup",
            "ta",
            "ta-da",
            "ta ta",
            "take that",
            "tally ho",
            "tch",
            "thanks",
            "there",
            "there there",
            "time out",
            "toodles",
            "touche",
            "tsk",
            "tsk-tsk",
            "tut",
            "tut-tut",
            "ugh",
            "uh",
            "uh-oh",
            "um",
            "ur",
            "urgh",
            "very nice",
            "very well",
            "voila",
            "vroom",
            "wah",
            "well",
            "well done",
            "well, well",
            "what",
            "whatever",
            "whee",
            "when",
            "whoa",
            "whoo",
            "whoopee",
            "whoops",
            "whoopsey",
            "whew",
            "why",
            "word",
            "wow",
            "wuzzup",
            "ya",
            "yea",
            "yeah",
            "yech",
            "yikes",
            "yippee",
            "yo",
            "yoo-hoo",
            "you bet",
            "you don't say",
            "you know",
            "yow",
            "yum",
            "yummy",
            "zap",
            "zounds",
            "zowie",
            "zzz"
        )
    }
}