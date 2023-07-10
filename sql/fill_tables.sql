INSERT INTO public.investor_details( id, name, surname, dob, address, mobile, email)
    VALUES (111, 'Venkat', 'Meduri', '02-08-1988', 'Midrand', '0640818794', 'venkatrayudu.meduri@gmail.com');

INSERT INTO public.investor_details( id, name, surname, dob, address, mobile, email)
VALUES (111, 'Rayudu', 'Meduri', '01-01-2001', 'Lisbon', '0714779396', 'rayudu.meduri@gmail.com');

INSERT INTO public.investor_products( id, product_type, current_balance, investor_id)
    VALUES (1, 'RETIREMENT', 500000, 111);

INSERT INTO public.investor_products( id, product_type, current_balance, investor_id)
    VALUES (2, 'SAVING', 50000, 111);
