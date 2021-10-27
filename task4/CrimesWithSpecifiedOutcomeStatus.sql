SELECT 
    *,
   (PERCENT_RANK() OVER(ORDER BY "Crimes by street")) "% of total crimes"
FROM
    (SELECT streets.s_id AS "Street ID",
            streets.s_name AS "Street name",
            outcome_statuses.os_category AS "Outcome category value",
            (COUNT(streets.s_name) OVER(PARTITION BY outcome_statuses.os_category, streets.s_name)) "Crimes by street",
            (COUNT(streets.s_name) OVER(PARTITION BY outcome_statuses.os_category)) "Total crimes by outcome"
     FROM crimes
         INNER JOIN locations ON crimes.c_location = locations.l_id
         INNER JOIN streets ON locations.l_street = streets.s_id
         INNER JOIN outcome_statuses ON crimes.c_outcome_status = outcome_statuses.os_id
     WHERE c_month BETWEEN ? AND ?
        AND outcome_statuses.os_category = ?
     ORDER BY streets.s_name ) AS DERIVED
ORDER BY "Crimes by street" DESC;