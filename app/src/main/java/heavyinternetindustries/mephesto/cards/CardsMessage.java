package heavyinternetindustries.mephesto.cards;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mephest0 on 25.02.16.
 */
public class CardsMessage {
    public static final int TO_BE_DECIDED = 0;
    public static final int MANAGER_WIFIP2P = 9001;
    public static final String ARG_TICK = "T_ICK";
    public static final String ARG_STATE = "STA_TE";
    public static final String ARG_CHANGE = "CH_ANGE";
    public static final String ARG_EXTRA = "EXT_RA";
    public static final String ARG_USERNAME = "U_SER_NAM_E";
    public static final String ARG_CONTENT_START = "::[";
    public static final String ARG_CONTENT_END = "]::";
    public static final String ARG_SEPARATOR = ";;;;";

    public static final String MESSAGE_DECK_SEPARARATOR = "..";
    public static final String MESSAGE_VALUE_SUIT_SEPARATOR = "_!";
    public static final String MESSAGE_CARD_SEPARATOR = ",,";

    public static final String MESSAGE_DECK_DATA_START = "[[";
    public static final String MESSAGE_DECK_DATA_STOP = "]]";

    private String otherEndUsername, otherEndHost;
    private final int manager;
    private int tick;
    private String state, changes, extra, raw, senderUsername;

    /**
     * Incommign message
     * @param senderHostAddress sender
     * @param message message
     * @param manager link
     */
    public CardsMessage(String senderHostAddress, String message, int manager) {
        otherEndUsername = parseUserName(message);
        otherEndHost = senderHostAddress;

        tick = parseTick(message);
        state = parseState(message);
        changes = parseChange(message);
        extra = parseExtra(message);
        senderUsername = parseUserName(message);

        raw = message;

        if (tick < -1 || state == null || changes == null || extra == null) {
            System.out.println("MALFORMED PACKAGE :(");
            //System.out.println(" from: " + sender);
            //System.out.println(" message: " + message);
            //System.out.println(" link: " + manager);

            tick = -1;
            state = changes = extra = "";
        }

        this.manager = manager;
    }

    /**
     * Outgoing message
     * @param receiverUsername
     * @param tick
     * @param myUsername
     * @param state
     * @param changes
     * @param extra
     */
    public CardsMessage(String receiverUsername, int tick, String myUsername, String state, String changes, String extra) {
        otherEndUsername = receiverUsername;
        this.tick = tick;
        this.state = state;
        this.changes = changes;
        this.extra = extra;
        this.senderUsername = myUsername;

        manager = TO_BE_DECIDED;
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
        String ret = "message:\n" + "  sender: " + getOtherEndUsername() + "\n" + "  message: " + getMessage();

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

    public String getOtherEndUsername() {
        return otherEndUsername;
    }

    public String getOtherEndHost() {
        return otherEndHost;
    }

    public int getManager() {
        return manager;
    }

    public static String registerNewDeviceMessageAsString(String receiverUsername) {
        return new CardsMessage(receiverUsername, 0, MainActivity.getUsername() ,"state", "changes", "extra").toString();
    }

    public static CardsMessage registerNewDeviceMessage(String receiverUsername) {
        return new CardsMessage(receiverUsername, 0, MainActivity.getUsername() ,"state", "changes", "extra");
    }

    public void prepareForSending() {
        otherEndUsername = MainActivity.getUsername();
    }

    public ArrayList<CardsMessage> addressTo(ArrayList<String> players) {
        ArrayList<CardsMessage> ret = new ArrayList<>();

        for (String username : players) {
            CardsMessage message = createCopy();
            message.otherEndUsername = username;
            ret.add(message);
        }

        return ret;
    }

    public CardsMessage createCopy() {
        return new CardsMessage(otherEndUsername,
                tick,
                senderUsername,
                state,
                changes,
                extra);
    }

    public void appendExtra(String fieldName, String data) {
        StringBuilder builder = new StringBuilder();
        builder.append(extra);

        builder.append(fieldName);
        builder.append(MESSAGE_DECK_DATA_START);
        builder.append(data);
        builder.append(MESSAGE_DECK_DATA_STOP);

        builder.append(MESSAGE_DECK_SEPARARATOR);

        extra = builder.toString();
    }

    public String getExtra(String fieldName) {
        int fieldStart = extra.indexOf(fieldName);
        fieldStart = extra.indexOf(MESSAGE_DECK_DATA_START, fieldStart) + MESSAGE_DECK_DATA_START.length();

        int fieldStop = extra.indexOf(MESSAGE_DECK_DATA_STOP, fieldStart);

        return extra.substring(fieldStart, fieldStop);
    }
}
