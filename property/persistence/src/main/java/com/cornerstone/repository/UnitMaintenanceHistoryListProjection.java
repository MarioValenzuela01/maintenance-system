package com.cornerstone.repository;

import java.time.LocalDate;

public interface UnitMaintenanceHistoryListProjection {

    Long getId();

    Long getUnitId();

    String getCategory();

    String getItemName();

    LocalDate getCompletedDate();

    String getNotes();

    Long getTenantIdAtTime();

    String getTenantNameAtTime();

    String getUnitNumber();

    String getUnitDisplayName();
}