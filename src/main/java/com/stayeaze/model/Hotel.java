package com.stayeaze.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String location;
  private String description;
  private int availableRooms;

  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Booking> bookings;
}

