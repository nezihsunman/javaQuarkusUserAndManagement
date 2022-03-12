package org.acme.enums;

public enum DisabledOrEnabled {
    ENABLED {
        @Override
        public String toString() {
            return "ENABLED";
        }
    },
    DISABLED {
        @Override
        public String toString() {
            return "DISABLED";
        }
    }
}
