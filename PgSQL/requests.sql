-- search
SELECT last_name, first_name  
FROM buyers
Where last_name = 'Смирнов';

SELECT buyers.id, first_name, last_name, COUNT(*)
FROM purchases
INNER JOIN buyers
ON purchases.buyers_id = buyers.id
INNER JOIN products
ON purchases.products_id = products.id
Where products.name = 'Минеральная вода'
GROUP BY buyers.id, buyers.first_name, buyers.last_name
Having COUNT(*) >= 2;

SELECT buyers.id, first_name, last_name, SUM(products.price)
FROM purchases
INNER JOIN buyers
ON purchases.buyers_id = buyers.id
INNER JOIN products
ON purchases.products_id = products.id
GROUP BY buyers.id, first_name, last_name
HAVING SUM(products.price) >= 100 AND SUM(products.price) <= 1200;

SELECT buyers.id, first_name, last_name, SUM(products.price)
FROM purchases
INNER JOIN buyers
ON purchases.buyers_id = buyers.id
INNER JOIN products
ON purchases.products_id = products.id
GROUP BY buyers.id, first_name, last_name
ORDER BY SUM(products.price)
LIMIT 4;

-- stat
SELECT COUNT(working_day)
FROM working_calendar
WHERE working_day BETWEEN date '2022-11-14' AND date '2022-11-21';

SELECT CONCAT(last_name, ' ', first_name)  
FROM buyers
Where id = 6;

SELECT buyers.id
FROM purchases
INNER JOIN buyers
ON purchases.buyers_id = buyers.id
INNER JOIN products
ON purchases.products_id = products.id
WHERE purchase_date BETWEEN date '2022-11-11' AND date '2022-11-26'
	AND purchase_date = (SELECT working_day
		FROM working_calendar
		WHERE purchases.purchase_date = working_calendar.working_day)
GROUP BY buyers.id;

SELECT products.name, SUM(price)
FROM purchases
INNER JOIN buyers
ON purchases.buyers_id = buyers.id
INNER JOIN products
ON purchases.products_id = products.id
WHERE purchase_date BETWEEN date '2022-11-01' AND date '2022-11-28'
	AND purchase_date = (SELECT working_day
		FROM working_calendar
		WHERE purchases.purchase_date = working_calendar.working_day) AND buyers.id = 7
GROUP BY products.name;

do
$$
declare
    calc_year integer:=2022;
    begin_date date;
    end_date date;
    dow_value integer;
begin
    if calc_year between 2020 and 2025 then
        begin_date :=to_date('01.01.'||calc_year, 'dd.mm.yyyy');
        end_date :=to_date('31.12.'||calc_year, 'dd.mm.yyyy');
        
        while begin_date<=end_date
        loop
            raise info '%',to_char(begin_date,'dd.mm.yyyy');
            begin_date:=begin_date+interval '1 day';
            dow_value:=extract(dow from begin_date);
            if dow_value in(1, 2, 3, 4, 5) then
                insert into working_calendar(working_day)
                        values(begin_date);
            end if;
        end loop;
        insert into working_calendar(working_day) 
            values(to_date('05.03.2022','dd.mm.yyyy'));
		
		delete from working_calendar where working_day=to_date('03.01.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('04.01.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('05.01.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('06.01.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('07.01.2022','dd.mm.yyyy');
		
		delete from working_calendar where working_day=to_date('23.02.2022','dd.mm.yyyy');
		
		delete from working_calendar where working_day=to_date('07.03.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('08.03.2022','dd.mm.yyyy');
		
		delete from working_calendar where working_day=to_date('02.05.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('03.05.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('09.05.2022','dd.mm.yyyy');
		delete from working_calendar where working_day=to_date('10.05.2022','dd.mm.yyyy');
		
		delete from working_calendar where working_day=to_date('13.06.2022','dd.mm.yyyy');
		
		delete from working_calendar where working_day=to_date('04.11.2022','dd.mm.yyyy');
    end if;
end$$;