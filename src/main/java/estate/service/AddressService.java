package estate.service;

import estate.domain.Address;
import estate.dto.AddressDto;
import estate.dto.AddressIdSpecificDto;
import estate.exception.AddressExistsException;
import estate.exception.AddressNotFoundException;
import estate.exception.NoDataFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private estate.dao.AddressDao addressDao;

    @Transactional
    public void addAddress(@Valid AddressDto addressDto) {

        List<Address> addresses = addressDao.findAll();

        Boolean addressExists = addresses.stream()
                .filter(item -> item.getCity().equals(addressDto.getCity())
                        && item.getStreet().equals(addressDto.getStreet())
                        && item.getNumber().equals(addressDto.getNumber()))
                .collect(Collectors.toList()).size() > 0;

        if (addressExists) {
            throw new AddressExistsException();
        }

        Address address = new Address();

        address.setCity(addressDto.getCity());
        address.setNumber(addressDto.getNumber());
        address.setStreet(addressDto.getStreet());

        addressDao.save(address);
    }

    @Transactional(readOnly = true)
    public Address findById(Long addressId) {
        return addressDao.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    @Transactional(readOnly = true)
    public List<AddressIdSpecificDto> findAll() {

        List<Address> addresses = addressDao.findAll();

        if (addresses.isEmpty()) {
            throw new NoDataFoundException();
        }

        List<AddressIdSpecificDto> addressList = addresses.stream().map(item -> new AddressIdSpecificDto(item)).collect(Collectors.toList());

        return addressList;
    }
}
