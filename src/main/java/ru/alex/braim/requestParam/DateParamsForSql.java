package ru.alex.braim.requestParam;

import lombok.Getter;

import java.util.Date;

@Getter
public class DateParamsForSql {
    private final java.sql.Timestamp startDate;
    private final java.sql.Timestamp endDate;

    public DateParamsForSql(Date startDate, Date endDate) {
        this.startDate = startDate == null ? null : new java.sql.Timestamp(startDate.getTime());
        this.endDate = endDate == null ? null : new java.sql.Timestamp(endDate.getTime());
    }
}
