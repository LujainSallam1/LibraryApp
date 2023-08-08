package nl.first8.library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "member")
@SequenceGenerator(name="member_id_seq", initialValue=11, allocationSize=1000)
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="member_id_seq")
    private Long memberid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name = "maxborrowed", nullable = false, columnDefinition = "integer default 5")
    private Long maxborrowed = 5L;

    public Long getId() {
        return memberid;
    }

    public void setId( Long id ) {
        this.memberid = memberid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet( String street ) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode( String zipcode ) { this.zipcode = zipcode; }

    public String getCity() {
        return city;
    }

    public void setCity( String city ) { this.city = city; }

    public boolean isActive() {
        return active;
    }

    public void setActive( boolean active ) {
        this.active = active;
    }

    public Long getMaxborrowed() { return maxborrowed; }

//    public void  setMaxborrowed ( Long maxborrowed) { this.maxborrowed = maxborrowed; } // PROBABLY BEST TO BLOCK FOR NOW. ONLY ADJUSTABLE IN THE SOURCE CODE


}
