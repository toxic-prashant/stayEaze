package com.stayeaze.controller;

import com.stayeaze.model.Booking;
import com.stayeaze.model.Hotel;
import com.stayeaze.model.Role;
import com.stayeaze.model.Users;
import com.stayeaze.repo.BookingRepo;
import com.stayeaze.repo.HotelRepo;
import com.stayeaze.repo.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

  @Autowired
  private BookingRepo bookingRepository;

  @Autowired
  private HotelRepo hotelRepository;

  @Autowired
  private UsersRepo userRepository;

  /**
   * Books a room in the specified hotel if rooms are available.
   *
   * @param hotelId ID of the hotel in which to book a room.
   * @param user    User who is making the booking.
   * @return true if the booking was successful, false otherwise.
   */
  @Transactional
  public boolean bookRoom(Long hotelId, Users user) {
    // Find the hotel by ID
    Hotel hotel = hotelRepository.findById(hotelId)
        .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

    // Check if rooms are available
    if (hotel.getAvailableRooms() <= 0) {
      return false; // No rooms available
    }

    // Decrement the available rooms
    hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
    hotelRepository.save(hotel); // Update hotel with new room count

    // Save user if not already persisted
//    if (false) {
//      user = userRepository.save(user); // Persist user if not already saved
//    }

    // Create a new booking
    Booking booking = new Booking();
    booking.setHotel(hotel);
    booking.setUsers(user);
    booking.setStatus(true); // Set booking as active

    bookingRepository.save(booking); // Persist booking in the database

    return true;
  }

  /**
   * Cancels a booking by ID and increments the available room count in the hotel.
   *
   * @param bookingId ID of the booking to be canceled.
   */
  @Transactional
  public void cancelBooking(Long bookingId) {
    // Find the booking by ID
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

    // Ensure that only hotel managers can cancel bookings
    Users user = booking.getUsers();
    if (user.getRole() != Role.HOTEL_MANAGER) {
      throw new SecurityException("Only hotel managers can cancel bookings");
    }

    // Mark booking as canceled
    booking.setStatus(false); // Optional: if you want to keep a record

    // Increment the available room count in the hotel
    Hotel hotel = booking.getHotel();
    hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);

    hotelRepository.save(hotel); // Update hotel with incremented room count
    bookingRepository.delete(booking); // Delete booking from the database
  }
}
