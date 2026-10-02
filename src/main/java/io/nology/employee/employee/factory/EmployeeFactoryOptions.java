package io.nology.employee.employee.factory;

public class EmployeeFactoryOptions {
    String firstName;
    String middleName;
    String lastName;
    String email;
    String phoneNumber;
    String address;

    private EmployeeFactoryOptions(Builder builder)
    {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder 
    {
        private String firstName;
        private String middleName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String address;

        public Builder firstName (String firstName)
        {
            this.firstName = firstName;
            return this;
        }

        public Builder middleName (String middleName)
        {
            this.middleName = middleName;
            return this;
        }

        public Builder lastName (String lastName)
        {
            this.lastName = lastName;
            return this;
        }

        public Builder email (String email)
        {
            this.email = email;
            return this;
        }

        public Builder phoneNumber (String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
            return this;
        }


        public EmployeeFactoryOptions build()
        {
            return new EmployeeFactoryOptions(this);
        }
    }
}
