SELECT
       *,
       (LAG("Current month count",1) OVER(PARTITION BY "Crime category" )) AS "Previous month count",
       (round((("Current month count" - LAG("Current month count",1) OVER(PARTITION BY "Crime category"))/
       (LAG("Current month count",1) OVER(PARTITION BY "Crime category"))::float * 100)::numeric, 2)) AS "Basic growth rate %",
       (abs(("Current month count" - LAG("Current month count",1) OVER(PARTITION BY "Crime category")))) AS "Delta count"
FROM
     (SELECT
         DISTINCT c_category AS "Crime category",
         c_month AS "Month",
        (COUNT(c_category) OVER( PARTITION BY c_category, c_month)) AS "Current month count"
         FROM crimes
         WHERE c_month BETWEEN ? AND ?
         ORDER BY c_category, c_month) AS DERIVED;