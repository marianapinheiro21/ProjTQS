package api.mappings.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

public class Reservation {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("memberId")
    private Integer memberId;
    @JsonProperty("bookId")
    private Integer bookId;
    @JsonProperty("reservationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate reservationDate;
    @JsonProperty("returnDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate returnDate;


    public static class ReservationBuilder {
        private Integer id;
        private Integer memberId;
        private Integer bookId;
        private LocalDate reservationDate;
        private LocalDate returnDate;

        ReservationBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("id")
        public Reservation.ReservationBuilder id(final Integer id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("memberId")
        public Reservation.ReservationBuilder memberId(final Integer memberId) {
            this.memberId = memberId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("bookId")
        public Reservation.ReservationBuilder bookId(final Integer bookId) {
            this.bookId = bookId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("reservationDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        public Reservation.ReservationBuilder reservationDate(final LocalDate reservationDate) {
            this.reservationDate = reservationDate;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("returnDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        public Reservation.ReservationBuilder returnDate(final LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Reservation build() {
            return new Reservation(this.id, this.memberId, this.bookId, this.reservationDate, this.returnDate);
        }

        @Override
        public String toString() {
            return "Reservation.ReservationBuilder(id=" + this.id + ", memberId=" + this.memberId + ", bookId=" + this.bookId + ", reservationDate=" + this.reservationDate + ", returnDate=" + this.returnDate + ")";
        }
    }

    public static Reservation.ReservationBuilder builder() {
        return new Reservation.ReservationBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getMemberId() {
        return this.memberId;
    }

    public Integer getBookId() {
        return this.bookId;
    }

    public LocalDate getReservationDate() {
        return this.reservationDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    @JsonProperty("id")
    public void setId(final Integer id) {
        this.id = id;
    }

    @JsonProperty("memberId")
    public void setMemberId(final Integer memberId) {
        this.memberId = memberId;
    }

    @JsonProperty("bookId")
    public void setBookId(final Integer bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("reservationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setReservationDate(final LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    @JsonProperty("returnDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setReturnDate(final LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Reservation)) return false;
        final Reservation other = (Reservation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$memberId = this.getMemberId();
        final Object other$memberId = other.getMemberId();
        if (this$memberId == null ? other$memberId != null : !this$memberId.equals(other$memberId)) return false;
        final Object this$bookId = this.getBookId();
        final Object other$bookId = other.getBookId();
        if (this$bookId == null ? other$bookId != null : !this$bookId.equals(other$bookId)) return false;
        final Object this$reservationDate = this.getReservationDate();
        final Object other$reservationDate = other.getReservationDate();
        if (this$reservationDate == null ? other$reservationDate != null : !this$reservationDate.equals(other$reservationDate)) return false;
        final Object this$returnDate = this.getReturnDate();
        final Object other$returnDate = other.getReturnDate();
        if (this$returnDate == null ? other$returnDate != null : !this$returnDate.equals(other$returnDate)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Reservation;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $memberId = this.getMemberId();
        result = result * PRIME + ($memberId == null ? 43 : $memberId.hashCode());
        final Object $bookId = this.getBookId();
        result = result * PRIME + ($bookId == null ? 43 : $bookId.hashCode());
        final Object $reservationDate = this.getReservationDate();
        result = result * PRIME + ($reservationDate == null ? 43 : $reservationDate.hashCode());
        final Object $returnDate = this.getReturnDate();
        result = result * PRIME + ($returnDate == null ? 43 : $returnDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Reservation(id=" + this.getId() + ", memberId=" + this.getMemberId() + ", bookId=" + this.getBookId() + ", reservationDate=" + this.getReservationDate() + ", returnDate=" + this.getReturnDate() + ")";
    }

    public Reservation() {
    }

    public Reservation(final Integer id, final Integer memberId, final Integer bookId, final LocalDate reservationDate, final LocalDate returnDate) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
        this.returnDate = returnDate;
    }
}
