package heavyinternetindustries.mephesto.cards;

/**
 * Created by mephest0 on 25.02.16.
 */
public class CardsMessage {
    public static final int TO_BE_DECIDED = 0;
    public static final int MANAGER_WIFIP2P = 9001;
    public static final String ARG_TICK = "TICK";
    public static final String ARG_STATE = "STATE";
    public static final String ARG_CHANGE = "CHANGE";
    public static final String ARG_EXTRA = "EXTRA";
    public static final String ARG_USERNAME = "USERNAME";
    public static final String ARG_CONTENT_START = "[";
    public static final String ARG_CONTENT_END = "]";
    public static final String ARG_SEPARATOR = ";";
    private String otherEnd;
    private final int manager;
    private int tick;
    private String state, changes, extra, raw, senderUsername;

    /**
     * Incommign message
     * @param sender sender
     * @param message message
     * @param manager link
     */
    public CardsMessage(String sender, String message, int manager) {
        otherEnd = sender;

        tick = parseTick(message);
        state = parseState(message);
        changes = parseChange(message);
        extra = parseExtra(message);
        senderUsername = parseUserName(message);

        raw = message;

        if (tick == -1 || state == null || changes == null || extra == null) {
            System.out.println("MALFORMED PACKAGE :(");
            System.out.println(" from: " + sender);
            System.out.println(" message: " + message);
            System.out.println(" link: " + manager);

            tick = -1;
            state = changes = extra = null;
        }

        this.manager = manager;
    }

    /**
     * Outgoing message
     * @param receiver
     * @param tick
     * @param state
     * @param changes
     * @param extra
     */
    public CardsMessage(String receiver, int tick, String state, String changes, String extra) {
        otherEnd = receiver;
        this.tick = tick;
        this.state = state;
        this.changes = changes;
        this.extra = extra;

        manager = TO_BE_DECIDED;
    }

    public String getOtherEnd() {
        return otherEnd;
    }

    public String getMessage() {
        StringBuilder builder = new StringBuilder();

        builder.append(encapsulate(ARG_TICK, tick + ""));
        builder.append(encapsulate(ARG_STATE, state));
        builder.append(encapsulate(ARG_CHANGE, changes));
        builder.append(encapsulate(ARG_EXTRA, extra));
        builder.append(encapsulate(ARG_USERNAME, senderUsername));

        return builder.toString();
    }

    private static String encapsulate(String arg, String data) {
        StringBuilder ret = new StringBuilder();

        ret.append(arg);
        ret.append(ARG_CONTENT_START);
        ret.append(data);
        ret.append(ARG_CONTENT_END);
        ret.append(ARG_SEPARATOR);

        return ret.toString();
    }

    @Override
    public String toString() {
        String ret = "message:\n" + "  sender: " + getOtherEnd() + "\n" + "  message: " + getMessage();

        return ret;
    }

    private static int parseTick(String message) {
        String tickString = parseContents(message, ARG_TICK);

        int ret = -1;

        if (tickString != null) {
            try {
                ret = Integer.parseInt(tickString);
            } catch (Exception e) {
                System.err.println("Error parsing \"tick\"-value: " + tickString);
            }
        }
        return ret;
    }

    private static String parseState(String message) {
        return parseContents(message, ARG_STATE);
    }

    private static String parseChange(String message) {
        return parseContents(message, ARG_CHANGE);
    }

    private static String parseExtra(String message) {
        return parseContents(message, ARG_EXTRA);
    }

    private static String parseUserName(String message) {
        return parseContents(message, ARG_USERNAME);
    }

    private static String parseContents(String message, String arg) {

        String ret = null;

        if (message.contains(arg + ARG_CONTENT_START)) {
            int index = message.indexOf(arg + ARG_CONTENT_START);

            ret = message.substring(
                    index + arg.length() + ARG_CONTENT_START.length(),
                    message.indexOf(ARG_CONTENT_END, index));
        }

        return ret;
    }

    public int getTick() {
        return tick;
    }

    public String getState() {
        return state;
    }

    public String getChanges() {
        return changes;
    }

    public String getExtra() {
        return extra;
    }

    public String getUsername() {
        return senderUsername;
    }

    public String getRawMessage() {
        return raw;
    }

    public int getManager() {
        return manager;
    }
}
