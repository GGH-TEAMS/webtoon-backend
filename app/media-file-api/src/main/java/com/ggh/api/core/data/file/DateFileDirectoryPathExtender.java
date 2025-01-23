package com.ggh.api.core.data.file;

import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;

public class DateFileDirectoryPathExtender implements FileDirectoryPathExtender {

    @Override
    public Path extendPath(Path base) {
        var currentDate = LocalDate.now(Clock.systemUTC());
        return extendPath(base, currentDate);
    }

    public Path extendPath(Path base, LocalDate date) {
        var year = date.getYear();
        var month = date.getMonthValue();
        var day = date.getDayOfMonth();

        return base.resolve(String.valueOf(year))
                .resolve(String.valueOf(month))
                .resolve(String.valueOf(day))
                .normalize();
    }
}
