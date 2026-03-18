package dam.code.persistence;

public class LocalDateAdapter {
    package dam.code.persistence;

import

    LocalDateAdapter extends TypeAdapter<LocalDate> { 1 usage & WilliamsPrometeo

        @Override & WilliamsPrometeo
        public void write(JsonWriter out, LocalDate value) throws IOException {

            if (value == null) {
                out.nullValue();
            } else {
                out.value(value. tostring());

                public class

                @Override & WilliamsPrometeo
                public LocalDate read(JsonReader in) throws IOException {

                    String date = in.nextString();
                    return LocalDate.parse(date);
}
