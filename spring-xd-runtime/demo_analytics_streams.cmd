
stream create airshop_stream_flight_type_tap  --definition "tap:stream:airshop_stream.transform > field-value-counter --fieldName=flight.fltType --name='Flight Type'" --deploy

stream create airshop_stream_flight_destination_city_count_tap --definition "tap:stream:airshop_stream.transform > field-value-counter --fieldName=flight.airportCityArr --name='Destination City'" --deploy

stream create airshop_stream_flight_departure_city_count_tap --definition "tap:stream:airshop_stream.transform > field-value-counter --fieldName=flight.airportCityDpt --name='Departure City'" --deploy

stream create airshop_stream_flight_count_tap --definition "tap:stream:airshop_stream.transform > aggregate-counter --name='Flight Data'" --deploy
