package nl.first8.library.domain;

import nl.first8.library.Constants;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leden")
@SequenceGenerator(name="lid_id_seq", initialValue=11, allocationSize=1000)
public class Leden {

    public static void changeBorrowLimit(Integer newLimit) {
        Constants.BORROWLIMIT = newLimit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lid_id_seq")
    private Long id;

    @Column(name = "voornaam")
    private  String voornaam;

    @Column(name = "agternaam")
    private  String agternaam;

    @Column(name = "huisNummer")
    private  Integer huisNummer;

    @Column(name = "straatNaam")
    private  String straatNaam;

    @Column(name = "plaats")
    private  String plaats;

    @Column(name = "postcode")
    private  String postcode;

    @Column(name = "disabled", columnDefinition = "boolean default false")
    private  boolean disabled;

    @Column(name = "productenGeleend", columnDefinition = "Integer default 0")
    private  Integer productenGeleend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAgternaam() {
        return agternaam;
    }

    public void setAgternaam(String agternaam) {
        this.agternaam = agternaam;
    }

    public Integer getHuisNummer() {
        return huisNummer;
    }

    public void setHuisNummer(Integer huisNummer) {
        this.huisNummer = huisNummer;
    }

    public String getStraatNaam() {
        return straatNaam;
    }

    public void setStraatNaam(String straatNaam) {
        this.straatNaam = straatNaam;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getProductenGeleend() {
        return productenGeleend;
    }

    public void setProductenGeleend(Integer productenGeleend) {
        this.productenGeleend = productenGeleend;
    }
}

