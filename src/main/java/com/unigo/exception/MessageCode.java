package com.unigo.exception;

public final class MessageCode {

    private MessageCode() {
    }

    public static Status of(String feature) {
        if (feature == null || feature.isBlank()) {
            throw new IllegalArgumentException("feature must not be blank");
        }
        return new Impl(feature);
    }

    public interface Status {

        Expand error();

        Expand warning();
    }

    public interface Expand {

        String required();

        String invalid();

        String min(long min);

        String max(long max);

        String range(long from, long to);
    }

    private static final class Impl implements Status, Expand {

        private final StringBuilder code;

        private Impl(String feature) {
            this.code = new StringBuilder(feature);
        }

        @Override
        public Expand error() {
            code.append(".ERROR");
            return this;
        }

        @Override
        public Expand warning() {
            code.append(".WARNING");
            return this;
        }

        @Override
        public String required() {
            return code.append(".REQUIRED").toString();
        }

        @Override
        public String invalid() {
            return code.append(".INVALID").toString();
        }

        @Override
        public String min(long min) {
            return code.append(".MIN_").append(min).toString();
        }

        @Override
        public String max(long max) {
            return code.append(".MAX_").append(max).toString();
        }

        @Override
        public String range(long from, long to) {
            if (from >= to) {
                throw new IllegalArgumentException("From value must be less than to value");
            }
            return code.append(".FROM_").append(from).append("_TO_").append(to).toString();
        }
    }
}
