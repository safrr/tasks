;WITH CTE_Stop_by_street AS
    (SELECT DISTINCT streets.s_id AS "Street ID",
                     streets.s_name AS "Street name",
                     ss_object_of_search,
                     ss_age_range,
                     ss_gender,
                     ss_officer_defined_ethnicity,
                     ss_outcome,
                    (COUNT(ss_object_of_search) OVER(PARTITION BY streets.s_id, ss_object_of_search)) AS search_obj_count,
                    (COUNT(ss_age_range) OVER(PARTITION BY streets.s_id, ss_age_range)) AS age_range_count,
                    (COUNT(ss_gender) OVER(PARTITION BY streets.s_id, ss_gender)) AS gender_count,
                    (COUNT(ss_officer_defined_ethnicity) OVER(PARTITION BY streets.s_id, ss_officer_defined_ethnicity)) AS ethnicity_count,
                    (COUNT(ss_outcome) OVER(PARTITION BY streets.s_id, ss_outcome)) AS outcome_count
    FROM stop_and_searches
    INNER JOIN locations on stop_and_searches.ss_location_id = locations.l_id
    INNER JOIN streets on locations.l_street = streets.s_id
    WHERE ss_officer_defined_ethnicity IS NOT NULL
      AND ss_object_of_search IS NOT NULL
      AND ss_date_time BETWEEN ? AND ?),

CTE_Most_popular_objects AS
    (SELECT DISTINCT "Street ID",
                     "Street name",
                     ss_object_of_search,
                     ss_age_range,
                     ss_gender,
                     ss_officer_defined_ethnicity,
                     ss_outcome,
                     search_obj_count,
                     age_range_count,
                     gender_count,
                     ethnicity_count,
                     outcome_count,
                    (MAX(search_obj_count) OVER(PARTITION BY "Street ID")) AS max_searced_obj_count,
                    (MAX(age_range_count) OVER(PARTITION BY "Street ID")) AS max_age_range_count,
                    (MAX(gender_count) OVER(PARTITION BY "Street ID")) AS max_gender_count,
                    (MAX(ethnicity_count) OVER(PARTITION BY "Street ID")) AS max_ethnicity_count,
                    (MAX(outcome_count) OVER(PARTITION BY "Street ID")) AS max_outcome_count
              FROM CTE_Stop_by_street)

 SELECT DISTINCT "Street ID",
                 "Street name",
                 ss_age_range AS "Most popular age range" ,
                 ss_gender AS "Most popular gender",
                 ss_officer_defined_ethnicity AS "Most popular ethnicity",
                 ss_object_of_search AS "Most popular object_of_search",
                 ss_outcome AS "Most popular outcome"
 FROM CTE_Most_popular_objects
 WHERE search_obj_count = max_searced_obj_count
   AND age_range_count = max_age_range_count
   AND gender_count = max_gender_count
   AND ethnicity_count = max_ethnicity_count
   AND outcome_count = max_outcome_count
 ORDER BY  "Street ID", "Street name"