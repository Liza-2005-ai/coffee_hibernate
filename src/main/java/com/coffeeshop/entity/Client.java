package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.util.*;

//Клиент кофейного магазина

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(name = "middle_name", nullable = false, length = 100)
    private String middleName;

    @Column(name = "email_address", nullable = false, unique = true, length = 100)
    private String emailAddress;

    @Column(nullable = false, unique = true, length = 100)
    private String number;

    @Column(length = 100)
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "preferences",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_descriptor")
    )
    private Set<TasteDescriptor> preferences = new HashSet<>();

    protected Client() {}

    public Client(String name, String surname, String middleName,
                  String emailAddress, String number, String address) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.emailAddress = emailAddress;
        this.number = number;
        this.address = address;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Set<TasteDescriptor> getPreferences() { return preferences; }
    public void setPreferences(Set<TasteDescriptor> preferences) { this.preferences = preferences; }
    public void addPreference(TasteDescriptor descriptor) { preferences.add(descriptor); }
    public void removePreference(TasteDescriptor descriptor) { preferences.remove(descriptor); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client c)) return false;
        return Objects.equals(emailAddress, c.emailAddress);
    }

    @Override
    public int hashCode() { return Objects.hashCode(emailAddress); }

    @Override
    public String toString() {
        return String.format("Client{id=%d, %s %s %s, email='%s', tel='%s'}",
                id, surname, name, middleName, emailAddress, number);
    }
}
