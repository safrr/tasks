;WITH CTE_STOP_BY_OUTCOME AS
          ( SELECT DISTINCT *,
                            (COUNT(*) OVER(PARTITION BY "ss_officer_defined_ethnicity", ss_outcome)) AS stops_by_ethicity_and_outcome,
                            (COUNT(*) OVER(PARTITION BY "ss_officer_defined_ethnicity")) AS "Stops by ethnicity",
                            (COUNT("ss_object_of_search") OVER(PARTITION BY "ss_officer_defined_ethnicity","ss_object_of_search")) AS populariti_objects
            FROM stop_and_searches
            WHERE ss_officer_defined_ethnicity IS NOT NULL AND ss_date_time BETWEEN ? AND ?
          ),

      CTE_ARRESTS AS
          (   SELECT DISTINCT ss_officer_defined_ethnicity, "Stops by ethnicity", stops_by_ethicity_and_outcome AS "Arests"
              FROM CTE_STOP_BY_OUTCOME
              WHERE ss_outcome = 'Arrest'
          ),

      CTE_NOTHING_DONE AS
          (   SELECT DISTINCT ss_officer_defined_ethnicity, "Stops by ethnicity", stops_by_ethicity_and_outcome AS "Noting done"
              FROM CTE_STOP_BY_OUTCOME
              WHERE ss_outcome = 'A no further action disposal'
          ),

      CTE_OTHER_OUTCOMES AS
          (   SELECT DISTINCT ss_officer_defined_ethnicity,
                              "Stops by ethnicity",
                              (count(stops_by_ethicity_and_outcome) OVER(PARTITION BY "ss_officer_defined_ethnicity")) AS "Other outcomes"
              FROM CTE_STOP_BY_OUTCOME
              WHERE ss_outcome != 'A no further action disposal' AND ss_outcome != 'Arrest'
          ),

      CTE_Popular_Object_by_Ethn AS
          (   SELECT DISTINCT ss_officer_defined_ethnicity,
                              (MAX(populariti_objects) OVER(PARTITION BY "ss_officer_defined_ethnicity")) AS max_pop
              FROM CTE_STOP_BY_OUTCOME
              WHERE ss_outcome = 'A no further action disposal'
          ),

      CTE_Obj_list AS
          (   SELECT DISTINCT ss_object_of_search, populariti_objects
              FROM CTE_STOP_BY_OUTCOME
          )

 SELECT DISTINCT CTE_NOTHING_DONE."ss_officer_defined_ethnicity" AS "Officer-defined ethnicity",
                 CTE_NOTHING_DONE."Stops by ethnicity",
                 "Arests" AS "Arrest rate",
                 "Noting done" AS "No action rate",
                 "Other outcomes" AS "Other outcome rate",
                 CTE_Obj_list."ss_object_of_search" AS "Most popular object of search"
 FROM CTE_NOTHING_DONE
          INNER JOIN CTE_ARRESTS ON CTE_NOTHING_DONE."ss_officer_defined_ethnicity" = CTE_ARRESTS."ss_officer_defined_ethnicity"
          INNER JOIN CTE_OTHER_OUTCOMES ON CTE_ARRESTS."ss_officer_defined_ethnicity" = CTE_OTHER_OUTCOMES."ss_officer_defined_ethnicity"
          INNER JOIN CTE_Popular_Object_by_Ethn ON CTE_ARRESTS."ss_officer_defined_ethnicity" = CTE_Popular_Object_by_Ethn."ss_officer_defined_ethnicity"
          INNER JOIN CTE_Obj_list ON CTE_Popular_Object_by_Ethn.max_pop = CTE_Obj_list.populariti_objects
 ORDER BY CTE_NOTHING_DONE."ss_officer_defined_ethnicity"