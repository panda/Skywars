package net.thelightmc.core.statistics;

import java.util.Calendar;
import java.util.Date;

public enum Stats {
    Matches {
        public Object getValue() {
            return 0;
        }
    }
    ,Wins {
        public Object getValue() {
            return 0;
        }
    },
    Losses {
        public Object getValue() {
            return 0;
        }
    },
    Deaths {
        public Object getValue() {
            return 0;
        }
    },
    Kills {
        public Object getValue() {
            return 0;
        }
    },
    Score {
        public Object getValue() {
            return 0;
        }
    },
    LastGame {
        public Object getValue() {
            return Calendar.getInstance().getTime();
        }
    };
    @Override
    public String toString() {
        return name();
    }

    public Object getValue() {
        throw new AbstractMethodError("This error should never be shown.");
    }
}