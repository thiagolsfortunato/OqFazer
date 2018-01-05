package br.com.oqfazer.utils;

import java.util.Arrays;

/**
 * O enum Constants tem todos paths destinados controle Socket
 * @author Thiago Fortunato
 * @version 1.0
 */
public enum Constants {

    DESTINATION("/topic"),
    DESTINATIONS_PREFIX("/user"),
    CHAT_CONECTION("/oqfazer"),
    JOIN("/join"),
    LEAVE("/leave"),
    HEARTBEAT("/heartbeat");

    private final String label;

    Constants(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static Constants getConstants(String label) {
        return Arrays.stream(Constants.values())
                .filter(e -> e.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", label)));
    }

    public String getLabel() { return label; }

}
