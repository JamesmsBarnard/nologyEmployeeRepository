package io.nology.employee.contracttype.factory;

public class ContractTypeFactoryOptions {
    String type;

    private ContractTypeFactoryOptions(Builder builder) {
        this.type = builder.type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String type;

        private Builder() {
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public ContractTypeFactoryOptions build() {
            return new ContractTypeFactoryOptions(this);
        }
    }

}
