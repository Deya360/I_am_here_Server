package com.I_am_here.Database.Entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "participator")
public class Participator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "participator_id")
    private Integer participator_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phone_number;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "participator")
    private Set<Code_word> code_words;

    @Column(name = "access_token")
    private String access_token;

    @Column(name = "refresh_token")
    private String refresh_token;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "participator")
    private Set<Visit> visits;

    @ManyToMany
    @JoinTable(name = "party_participator", joinColumns = @JoinColumn(name = "participator_id"),
    inverseJoinColumns = @JoinColumn(name = "party_id"))
    private Set<Party> parties;

    public Participator() {
    }

    public Participator(String name, String email, String phone_number, Set<Code_word> code_words, String access_token, String refresh_token, Set<Visit> visits, Set<Party> parties) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.code_words = code_words;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.visits = visits;
        this.parties = parties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participator that = (Participator) o;

        if (!participator_id.equals(that.participator_id)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (!phone_number.equals(that.phone_number)) return false;
        if (code_words != null ? !code_words.equals(that.code_words) : that.code_words != null) return false;
        if (access_token != null ? !access_token.equals(that.access_token) : that.access_token != null) return false;
        if (refresh_token != null ? !refresh_token.equals(that.refresh_token) : that.refresh_token != null)
            return false;
        if (visits != null ? !visits.equals(that.visits) : that.visits != null) return false;
        return parties != null ? parties.equals(that.parties) : that.parties == null;

    }

    @Override
    public int hashCode() {
        int result = participator_id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + phone_number.hashCode();
        result = 31 * result + (code_words != null ? code_words.hashCode() : 0);
        result = 31 * result + (access_token != null ? access_token.hashCode() : 0);
        result = 31 * result + (refresh_token != null ? refresh_token.hashCode() : 0);
        result = 31 * result + (visits != null ? visits.hashCode() : 0);
        result = 31 * result + (parties != null ? parties.hashCode() : 0);
        return result;
    }

    public Integer getParticipator_id() {
        return participator_id;
    }

    public void setParticipator_id(Integer participator_id) {
        this.participator_id = participator_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Set<Code_word> getCode_words() {
        return code_words;
    }

    public void setCode_words(Set<Code_word> code_words) {
        this.code_words = code_words;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public Set<Party> getParties() {
        return parties;
    }

    public void setParties(Set<Party> parties) {
        this.parties = parties;
    }
}
