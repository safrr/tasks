SELECT 
    DISTINCT "streets".s_id AS street_id, 
    "streets".s_name AS street_name,
    (COUNT("l_street") OVER(PARTITION BY "l_street")) AS crime_count,
    CONCAT_WS(' ','from',?,'till',?) AS Period
FROM "crimes"
    INNER JOIN "locations" ON "crimes"."c_location" = "locations".l_id
    INNER JOIN "streets" ON "locations"."l_street" = "streets".s_id
WHERE "c_month" BETWEEN ? AND ?
ORDER BY crime_count DESC;