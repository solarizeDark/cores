package ru.fedusiv.files_server.entities.converters;

import ru.fedusiv.files_server.entities.CityHall;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CityHallConverter implements AttributeConverter<CityHall, String> {

    @Override
    public String convertToDatabaseColumn(CityHall cityHall) {
        if (cityHall == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        if (cityHall.getName() == null && cityHall.getAddress() == null)
            return null;

        if (cityHall.getName() != null) {
            sb.append(cityHall.getName());
            sb.append(" ");
        }

        if (cityHall.getAddress() != null) {
            return sb.append(cityHall.getAddress()).toString();
        } else {
            return sb.toString().trim();
        }

    }

    @Override
    public CityHall convertToEntityAttribute(String rowCityHall) {
        if (rowCityHall == null || rowCityHall.isEmpty()) {
            return null;
        }

        String[] pieces = rowCityHall.split(" ");

        CityHall cityHall = new CityHall();

        cityHall.setName(pieces[0]);
        cityHall.setAddress(!pieces[1].isEmpty() ? pieces[1] : null);

        return cityHall;
    }

}
