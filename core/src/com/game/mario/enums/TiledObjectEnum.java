package com.game.mario.enums;

import java.util.HashMap;
import java.util.Map;

public enum TiledObjectEnum {

	FLAG("flag"),
    TRANSFER_DOWN("transferDown"),
    TRANSFER_RIGHT("transferRight"),
	COIN("coin"),
	METAL_PLATEFORM("metalPlateform"),
	
	PIRANHA("piranha"),
	GOOMBA("goomba"),
	KOOPA("koopa"),
	RED_KOOPA("redKoopa"),
	CASTLE_FIREBAR("castleFirebar"),
	BOWSER("bowser");			

    /** enumLiteralMap */
    private static Map<String, TiledObjectEnum> enumLiteralMap = null;

    /** label */
    private String label;


    /**
     * full Constructor
     *
     * @param label
     *            parameter
     */
    private TiledObjectEnum(String label) {
        this.label = label;
    }

    /**
     * Return an CreationTypeEnum instance according to the literal value
     *
     * @param literal
     *            String value that represent the key value value of an enum. the key value is the sum of each field
     * @return the enum find or null if no enum found.
     */
    public static synchronized TiledObjectEnum fromLiteral(String literal) {
        if (enumLiteralMap == null) {
            enumLiteralMap = new HashMap<String, TiledObjectEnum>();
            for (int i = 0; i < values().length; i++) {
                enumLiteralMap.put(values()[i].toLiteral(), values()[i]);
            }
        }
        return enumLiteralMap.get(literal);
    }

    /**
     * Return a string value to use as a key to retrieve the enum from the Map
     *
     * @return The concatenation of each field of the enum
     */
    public String toLiteral() {
        return label;

    }

}

