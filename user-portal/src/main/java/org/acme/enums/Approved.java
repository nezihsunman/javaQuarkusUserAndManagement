package org.acme.enums;

public enum Approved {
    APPROVED {
        @Override
        public String toString() {
            return "APPROVED";
        }
    },
    NOT_APPROVED {
        @Override
        public String toString() {
            return "NOT APPROVED";
        }
    },
    WaitingForApprove{
        @Override
        public String toString() {
            return "Waiting for approve";
        }
    },
}