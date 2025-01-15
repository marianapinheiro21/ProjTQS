package api.mappings.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

public class Member {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("phoneNumber")
    private Integer phoneNumber;
    @JsonProperty("nif")
    private Integer nif;
    @JsonProperty("email")
    private String email;
    @JsonProperty("birthDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
    @JsonProperty("registrationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate registrationDate;


    public static class MemberBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private String address;
        private String postalCode;
        private String city;
        private String country;
        private Integer phoneNumber;
        private Integer nif;
        private String email;
        private LocalDate birthDate;
        private LocalDate registrationDate;

        MemberBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("id")
        public Member.MemberBuilder id(final Integer id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("firstName")
        public Member.MemberBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("lastName")
        public Member.MemberBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("address")
        public Member.MemberBuilder address(final String address) {
            this.address = address;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("postalCode")
        public Member.MemberBuilder postalCode(final String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("city")
        public Member.MemberBuilder city(final String city) {
            this.city = city;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("country")
        public Member.MemberBuilder country(final String country) {
            this.country = country;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("phoneNumber")
        public Member.MemberBuilder phoneNumber(final Integer phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("nif")
        public Member.MemberBuilder nif(final Integer nif) {
            this.nif = nif;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("email")
        public Member.MemberBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("birthDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        public Member.MemberBuilder birthDate(final LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("registrationDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        public Member.MemberBuilder registrationDate(final LocalDate registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public Member build() {
            return new Member(this.id, this.firstName, this.lastName, this.address, this.postalCode, this.city, this.country, this.phoneNumber, this.nif, this.email, this.birthDate, this.registrationDate);
        }

        @Override
        public String toString() {
            return "Member.MemberBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", address=" + this.address + ", postalCode=" + this.postalCode + ", city=" + this.city + ", country=" + this.country + ", phoneNumber=" + this.phoneNumber + ", nif=" + this.nif + ", email=" + this.email + ", birthDate=" + this.birthDate + ", registrationDate=" + this.registrationDate + ")";
        }
    }

    public static Member.MemberBuilder builder() {
        return new Member.MemberBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public Integer getPhoneNumber() {
        return this.phoneNumber;
    }

    public Integer getNif() {
        return this.nif;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    @JsonProperty("id")
    public void setId(final Integer id) {
        this.id = id;
    }

    @JsonProperty("firstName")
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("address")
    public void setAddress(final String address) {
        this.address = address;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("city")
    public void setCity(final String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public void setCountry(final String country) {
        this.country = country;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(final Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("nif")
    public void setNif(final Integer nif) {
        this.nif = nif;
    }

    @JsonProperty("email")
    public void setEmail(final String email) {
        this.email = email;
    }

    @JsonProperty("birthDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty("registrationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setRegistrationDate(final LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Member)) return false;
        final Member other = (Member) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber)) return false;
        final Object this$nif = this.getNif();
        final Object other$nif = other.getNif();
        if (this$nif == null ? other$nif != null : !this$nif.equals(other$nif)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
        final Object this$postalCode = this.getPostalCode();
        final Object other$postalCode = other.getPostalCode();
        if (this$postalCode == null ? other$postalCode != null : !this$postalCode.equals(other$postalCode)) return false;
        final Object this$city = this.getCity();
        final Object other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) return false;
        final Object this$country = this.getCountry();
        final Object other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$birthDate = this.getBirthDate();
        final Object other$birthDate = other.getBirthDate();
        if (this$birthDate == null ? other$birthDate != null : !this$birthDate.equals(other$birthDate)) return false;
        final Object this$registrationDate = this.getRegistrationDate();
        final Object other$registrationDate = other.getRegistrationDate();
        if (this$registrationDate == null ? other$registrationDate != null : !this$registrationDate.equals(other$registrationDate)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Member;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $nif = this.getNif();
        result = result * PRIME + ($nif == null ? 43 : $nif.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $postalCode = this.getPostalCode();
        result = result * PRIME + ($postalCode == null ? 43 : $postalCode.hashCode());
        final Object $city = this.getCity();
        result = result * PRIME + ($city == null ? 43 : $city.hashCode());
        final Object $country = this.getCountry();
        result = result * PRIME + ($country == null ? 43 : $country.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $birthDate = this.getBirthDate();
        result = result * PRIME + ($birthDate == null ? 43 : $birthDate.hashCode());
        final Object $registrationDate = this.getRegistrationDate();
        result = result * PRIME + ($registrationDate == null ? 43 : $registrationDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Member(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", address=" + this.getAddress() + ", postalCode=" + this.getPostalCode() + ", city=" + this.getCity() + ", country=" + this.getCountry() + ", phoneNumber=" + this.getPhoneNumber() + ", nif=" + this.getNif() + ", email=" + this.getEmail() + ", birthDate=" + this.getBirthDate() + ", registrationDate=" + this.getRegistrationDate() + ")";
    }

    public Member() {
    }

    public Member(final Integer id, final String firstName, final String lastName, final String address, final String postalCode, final String city, final String country, final Integer phoneNumber, final Integer nif, final String email, final LocalDate birthDate, final LocalDate registrationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.nif = nif;
        this.email = email;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }
}
