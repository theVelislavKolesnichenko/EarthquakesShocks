package com.example.mapapi.models.enumerations;

public enum ShapeType {
    Earthquake("dsmw"),
    Soil("gdeqk");

    private final String text;

    /**
     * @param text
     */
    ShapeType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
