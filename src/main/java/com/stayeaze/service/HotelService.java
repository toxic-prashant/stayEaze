package com.stayeaze.service;

import com.stayeaze.model.Hotel;
import com.stayeaze.repo.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

  @Autowired
  private HotelRepo hotelRepo;

  public Hotel createHotel(Hotel hotel) {
    return hotelRepo.save(hotel);
  }

  public void deleteHotel(long hotelId) {
    hotelRepo.deleteById(hotelId);
  }
}
